package com.cs.campsite.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.campsite.member.dto.FindIdRequest;
import com.cs.campsite.member.dto.CodeVerificationRequest;
import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.repository.MemberRepository;
import com.cs.campsite.member.service.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class FindAccountController {

	private final MemberRepository memberRepository;
	private final MailService mailService;

	/** 아이디 찾기 - 인증코드 전송 */
	@PostMapping("/send-code")
	public ResponseEntity<?> sendVerificationCode(@RequestBody FindIdRequest request) {
		boolean exists = memberRepository.existsByMemberNameAndMemberPhoneAndMemberEmail(request.getMemberName(),
				request.getMemberPhone(), request.getMemberEmail());

		if (!exists) {
			return ResponseEntity.badRequest().body("일치하는 회원 정보가 없습니다.");
		}

		mailService.sendVerificationCode(request.getMemberEmail());
		return ResponseEntity.ok("인증코드가 이메일로 전송되었습니다.");
	}

	/** 인증코드 검증 후 아이디 반환 */
	@PostMapping("/verify-code")
	public ResponseEntity<?> verifyCodeAndReturnId(@RequestBody CodeVerificationRequest request) {
		boolean valid = mailService.verifyCode(request.getEmail(), request.getCode());

		if (!valid) {
			return ResponseEntity.badRequest().body("인증코드가 유효하지 않거나 만료되었습니다.");
		}

		Member member = memberRepository.findByMemberEmail(request.getEmail()).orElse(null);

		if (member == null) {
			return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
		}

		return ResponseEntity.ok().body(member.getMemberId());
	}
}
