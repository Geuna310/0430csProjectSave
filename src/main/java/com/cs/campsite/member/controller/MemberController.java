package com.cs.campsite.member.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cs.campsite.member.dto.CodeVerificationRequest;
import com.cs.campsite.member.dto.FindIdRequest;
import com.cs.campsite.member.dto.MemberLoginRequest;
import com.cs.campsite.member.dto.MemberRegisterRequest;
import com.cs.campsite.member.dto.RegisterResponse;
import com.cs.campsite.member.entity.LoginResponse;
import com.cs.campsite.member.service.MailService;
import com.cs.campsite.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MailService mailService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody MemberLoginRequest loginRequest) {
		try {
			LoginResponse loginResponse = memberService.login(loginRequest.getMemberId(),
					loginRequest.getMemberPassword());
			return ResponseEntity.ok(Map.of("token", loginResponse.getToken(), "role", loginResponse.getRole()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "서버 오류가 발생했습니다."));
		}
	}

	@PostMapping(value = "/register", consumes = "multipart/form-data")
	public ResponseEntity<?> register(@RequestPart("member") MemberRegisterRequest registerRequest,
			@RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
		try {
			RegisterResponse response = memberService.register(registerRequest, imageFile);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/members/check-id")
	public ResponseEntity<Boolean> checkMemberId(@RequestParam String memberId) {
		boolean exists = memberService.checkMemberIdExists(memberId);
		return ResponseEntity.ok(!exists);
	}

	@GetMapping("/members/check-nickname")
	public ResponseEntity<Boolean> checkMemberNickname(@RequestParam String memberNickname) {
		boolean exists = memberService.checkMemberNicknameExists(memberNickname);
		return ResponseEntity.ok(!exists);
	}

	@GetMapping("/members/check-email")
	public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String email) {
		boolean exists = memberService.existsByEmail(email);
		if (exists) {
			return ResponseEntity.ok(Map.of("available", false, "message", "이미 사용 중인 이메일입니다."));
		} else {
			return ResponseEntity.ok(Map.of("available", true, "message", "사용 가능한 이메일입니다."));
		}
	}

	@DeleteMapping("/members/{memberNo}")
	public ResponseEntity<?> deleteMember(@PathVariable Integer memberNo) {
		memberService.deleteMember(memberNo);
		return ResponseEntity.ok("회원 탈퇴 완료!");
	}

	@PostMapping("/members/find-id/request-code")
	public ResponseEntity<?> sendFindIdCode(@RequestBody FindIdRequest request) {
		try {
			mailService.sendVerificationCode(request.getMemberEmail());
			return ResponseEntity.ok(Map.of("message", "인증코드가 이메일로 전송되었습니다."));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "인증코드 전송 실패: " + e.getMessage()));
		}
	}

	@PostMapping("/members/find-id/verify")
	public ResponseEntity<?> verifyCodeAndReturnId(@RequestBody CodeVerificationRequest request) {
		boolean verified = mailService.verifyCode(request.getEmail(), request.getCode());

		if (!verified) {
			return ResponseEntity.badRequest().body(Map.of("error", "인증코드가 일치하지 않거나 만료되었습니다."));
		}

		// 인증 성공 시 ID 반환
		String memberId = memberService.findMemberId(request.getName(), request.getPhone(), request.getEmail());
		return ResponseEntity.ok(Map.of("memberId", memberId));
	}

}
