package com.cs.campsite.member.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cs.campsite.member.config.FileStorageProperties;
import com.cs.campsite.member.dto.MemberUpdateRequest;
import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberMypageService {
	
	private final FileStorageProperties fileStorageProperties;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Member findByMemberId(String memberId) {
		return (Member) memberRepository.findByMemberId(memberId)
				.orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));
	}
	
	/** 프로필 이미지 제외 회원 정보 수정 */
	public void updateMemberInfoOnly(Member member, MemberUpdateRequest dto) throws IOException {
		if (dto.getMemberNickname() != null) member.setMemberNickname(dto.getMemberNickname());
		if (dto.getMemberEmail() != null) member.setMemberEmail(dto.getMemberEmail());
		if (dto.getMemberPhone() != null) member.setMemberPhone(dto.getMemberPhone());
		if (dto.getMemberGender() != null) member.setMemberGender(dto.getMemberGender());
		
		if (dto.getMemberPassword() != null && !dto.getMemberPassword().isBlank()) {
			String encodedPassword = passwordEncoder.encode(dto.getMemberPassword());
			member.setMemberPassword(encodedPassword);
		}
		
		memberRepository.save(member);
	
	}
	
	/** 회원 프로필 이미지만 수정*/
	public void updateProfileImage(Member member, MultipartFile profileImage) throws IOException {
		
		if (profileImage != null && !profileImage.isEmpty()) {
			
			try {
				String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
				
				// TODO : 추후 파일 경로를 수정합니다.
				
				String savePath = Paths.get(fileStorageProperties.getUploadDir(), fileName).toString();
				profileImage.transferTo(new File(savePath));
				member.setMemberImageUrl("/uploads/" + fileName);
				memberRepository.save(member);
			} catch (Exception e) {
				throw new RuntimeException("파일 업로드 오류 발생" + e.getMessage());
			}
		}
	}
	
	
	/** 회원정보 수정 API에서 사용하기 위해 오버로딩한 메소드 
	 * @throws IOException */
	public void updateMemberInfoOnly(String memberId, MemberUpdateRequest dto) throws IOException {
	    Member member = findByMemberId(memberId);
	    updateMemberInfoOnly(member, dto);
	    
	}

	/** 프로필 이미지 수정 API에서 사용하기 위해 오버로딩한 메소드 */
	public void updateProfileImage(String memberId, MultipartFile profileImage) throws IOException {
	    Member member = findByMemberId(memberId);
	    updateProfileImage(member, profileImage);
	}
}
