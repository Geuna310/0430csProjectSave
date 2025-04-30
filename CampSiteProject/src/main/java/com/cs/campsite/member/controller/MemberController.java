package com.cs.campsite.member.controller;

import com.cs.campsite.member.dto.MemberLoginRequest;
import com.cs.campsite.member.dto.MemberRegisterRequest;
import com.cs.campsite.member.dto.RegisterResponse;
import com.cs.campsite.member.entity.LoginResponse;
import com.cs.campsite.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

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

}
