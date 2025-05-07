package com.cs.campsite.member.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cs.campsite.member.dto.MemberMypage;
import com.cs.campsite.member.dto.MemberUpdateRequest;
import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.service.MemberMypageService;

import com.cs.campsite.member.util.JwtUtil;
import com.cs.campsite.member.util.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberMypageController {
	
	private final JwtUtil jwt;
	private final MemberMypageService ms;
	
	/** 마이페이지 정보 API */
	@GetMapping("/api/memberpage")
	public ResponseEntity<MemberMypage> getMyInfo(HttpServletRequest request) {
		  String token = jwt.resolveToken(request);
		  
		  if (token == null || !jwt.validateToken(token)) {
			  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		  }
		  
		  String memberId = jwt.getMemberId(token);
		  Member member = ms.findByMemberId(memberId);
		  
		  if (member == null) {
			  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		  }
		  
		  
		  MemberMypage dto = new MemberMypage(
				  member.getMemberId(), 
				  member.getMemberName(), 
				  member.getMemberNickname(), 
				  member.getMemberEmail(), 
				  member.getMemberPhone(), 
				  member.getMemberGender(),
				  member.getMemberImageUrl()
				  );
		  
			return ResponseEntity.ok(dto);   
	}
	
	/** 회원정보 수정 API 
	 * @throws IOException */
	@PutMapping("/api/memberpage")
	public ResponseEntity<String> updateMemberInfo(
			@RequestBody MemberUpdateRequest dto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
		String memberId = userDetails.getMember().getMemberId(); // 편하게 접근
		ms.updateMemberInfoOnly(memberId, dto);
		return ResponseEntity.ok("회원정보가 수정되었습니다.");

	}
	
	
	@PutMapping("/api/memberpage/profile-image")
	public ResponseEntity<String> updateMemberInfo(
	        @RequestPart("profileImage") MultipartFile profileImage,
	        @AuthenticationPrincipal UserDetailsImpl userDetails 
	) throws IOException {
		 ms.updateProfileImage(userDetails.getMember().getMemberId(), profileImage);
	    return ResponseEntity.ok("프로필 이미지가 수정되었습니다.");
	}

}
