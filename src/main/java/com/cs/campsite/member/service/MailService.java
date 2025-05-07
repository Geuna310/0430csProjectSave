package com.cs.campsite.member.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	// 이메일 → 인증정보(code + 만료시간) 저장
	private final Map<String, VerificationCodeInfo> verificationCodeStorage = new ConcurrentHashMap<>();
	private final Map<String, Long> lastRequestTimeStorage = new ConcurrentHashMap<>();

	// 인증코드 유효시간: 3분
	private static final long EXPIRATION_TIME_MILLIS = 3 * 60 * 1000;

	// 재요청 제한시간: 60초
	private static final long RESEND_LIMIT_INTERVAL = 60 * 1000;

	/** 인증코드 전송 */
	public void sendVerificationCode(String email) {
		long now = System.currentTimeMillis();
		Long lastTime = lastRequestTimeStorage.get(email);

		if (lastTime != null && now - lastTime < RESEND_LIMIT_INTERVAL) {
			throw new IllegalStateException("인증 요청은 60초 간격으로 가능합니다. 잠시 후 다시 시도해주세요.");
		}

		String code = generateVerificationCode();
		long expireTime = now + EXPIRATION_TIME_MILLIS;

		verificationCodeStorage.put(email, new VerificationCodeInfo(code, expireTime));
		lastRequestTimeStorage.put(email, now);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("[캠프사이트] 이메일 인증코드");
		message.setText("인증코드: " + code + "\n3분 이내로 입력해주세요.");

		mailSender.send(message);
	}

	/** 인증코드 검증 */
	public boolean verifyCode(String email, String inputCode) {
		VerificationCodeInfo info = verificationCodeStorage.get(email);
		if (info == null || info.isExpired()) {
			return false;
		}
		return info.getCode().equals(inputCode);
	}

	/** 인증코드 생성 */
	private String generateVerificationCode() {
		return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
	}

	/** 내부 저장 클래스: 인증코드 + 만료시간 */
	private static class VerificationCodeInfo {
		private final String code;
		private final long expireTime;

		public VerificationCodeInfo(String code, long expireTime) {
			this.code = code;
			this.expireTime = expireTime;
		}

		public boolean isExpired() {
			return System.currentTimeMillis() > expireTime;
		}

		public String getCode() {
			return code;
		}
	}
}
