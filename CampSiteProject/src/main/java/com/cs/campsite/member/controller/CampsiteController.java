package com.cs.campsite.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cs.campsite.member.dto.CampsiteRegisterRequest;
import com.cs.campsite.member.dto.CampsiteResponse;
import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.service.CampsiteService;
import com.cs.campsite.member.util.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/campsite")
@RequiredArgsConstructor
public class CampsiteController {

	private final CampsiteService campsiteService;

	// 캠핑장 등록
	@PostMapping(value = "/register", consumes = "multipart/form-data")
	public ResponseEntity<String> registerCampsite(@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestPart("campsite") CampsiteRegisterRequest request,
			@RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

		Member member = userDetails.getMember();
		campsiteService.registerCampsite(member.getMemberNo(), request, imageFile);

		return ResponseEntity.ok("캠핑장 등록 성공!");
	}

	// 전체 캠핑장 리스트 조회
	@GetMapping("/list")
	public ResponseEntity<List<CampsiteResponse>> getAllCampsites() {
		return ResponseEntity.ok(campsiteService.getAllCampsites());
	}

	// 내 캠핑장 목록 조회
	@GetMapping("/myCampsites")
	public ResponseEntity<List<CampsiteResponse>> getMyCampsites(@AuthenticationPrincipal UserDetailsImpl userDetails) {

		Member member = userDetails.getMember();
		return ResponseEntity.ok(campsiteService.getCampsitesByMember(member.getMemberNo()));
	}

	// 단일 캠핑장 상세 조회
	@GetMapping("/{campsiteNo}")
	public ResponseEntity<CampsiteResponse> getCampsite(@PathVariable("campsiteNo") int campsiteNo) {
		return ResponseEntity.ok(campsiteService.getCampsiteById(campsiteNo));
	}

	// 캠핑장 수정
	@PutMapping(value = "/{campsiteNo}", consumes = "multipart/form-data")
	public ResponseEntity<String> updateCampsite(@PathVariable("campsiteNo") int campsiteNo,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestPart("campsite") CampsiteRegisterRequest request,
			@RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

		Member member = userDetails.getMember();
		campsiteService.updateCampsite(campsiteNo, member.getMemberNo(), request, imageFile);

		return ResponseEntity.ok("캠핑장 수정 완료");
	}

	// 캠핑장 삭제
	@DeleteMapping("/{campsiteNo}")
	public ResponseEntity<String> deleteCampsite(@PathVariable("campsiteNo") int campsiteNo,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {

		Member member = userDetails.getMember();
		campsiteService.deleteCampsite(campsiteNo, member.getMemberNo());

		return ResponseEntity.ok("캠핑장 삭제 완료");
	}
}
