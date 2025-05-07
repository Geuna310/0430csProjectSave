package com.cs.campsite.member.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cs.campsite.member.dto.FindIdRequest;
import com.cs.campsite.member.dto.MemberInfo;
import com.cs.campsite.member.dto.MemberRegisterRequest;
import com.cs.campsite.member.dto.RegisterResponse;
import com.cs.campsite.member.entity.Campsite;
import com.cs.campsite.member.entity.LoginResponse;
import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.repository.CampsiteRepository;
import com.cs.campsite.member.repository.MemberRepository;
import com.cs.campsite.member.util.JwtUtil;
import com.cs.campsite.member.util.ThumbnailUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final CampsiteRepository campsiteRepository;
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final MailService mailService;

	private static final String UPLOAD_DIR = "C:/upload/member/";
	private static final String IMAGE_BASE_URL = "http://localhost:8081/images/member/";

	public LoginResponse login(String memberId, String memberPassword) {
		Member member = memberRepository.findByMemberId(memberId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

		if (!passwordEncoder.matches(memberPassword, member.getMemberPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		boolean hasCampsite = campsiteRepository.existsByMember(member);
		String role = hasCampsite ? "COMPANY" : "CUSTOMER";

		String token = jwtUtil.generateToken(memberId, role);

		return new LoginResponse(token, role.toUpperCase());
	}

	public RegisterResponse register(MemberRegisterRequest request, MultipartFile imageFile) {
		if (memberRepository.findByMemberId(request.getMemberId()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 ID입니다.");
		}

		String encodedPassword = passwordEncoder.encode(request.getMemberPassword());

		String imageUrl = "https://localhost:8081/images/xxxxxx.jpg"; // 기본 프로필
		File savedFile = null;

		try {
			if (imageFile != null && !imageFile.isEmpty()) {
				File dir = new File(UPLOAD_DIR);
				if (!dir.exists())
					dir.mkdirs();

				String originalFilename = imageFile.getOriginalFilename();
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				String uuid = UUID.randomUUID().toString();
				String savedFilename = uuid + ext;

				savedFile = new File(UPLOAD_DIR + savedFilename);
				imageFile.transferTo(savedFile);

				// 썸네일 생성
				ThumbnailUtil.createThumbnail(savedFile, 300, 300);

				imageUrl = IMAGE_BASE_URL + savedFilename;
			}

			Member member = Member.builder().memberId(request.getMemberId()).memberPassword(encodedPassword)
					.memberName(request.getMemberName()).memberNickname(request.getMemberNickname())
					.memberPhone(request.getMemberPhone()).memberEmail(request.getMemberEmail())
					.memberBirth(request.getMemberBirth()).memberGender(request.getMemberGender())
					.memberImageUrl(imageUrl).build();

			memberRepository.save(member);

			String role = "CUSTOMER";
			String token = jwtUtil.generateToken(member.getMemberId(), role);

			MemberInfo memberInfo = new MemberInfo(member.getMemberId(), member.getMemberNickname(),
					member.getMemberEmail(), member.getMemberName());

			return new RegisterResponse(token, memberInfo);

		} catch (Exception e) {
			if (savedFile != null && savedFile.exists()) {
				savedFile.delete();
			}
			throw new IllegalArgumentException("회원가입 실패: " + e.getMessage());
		}
	}

	public boolean checkMemberIdExists(String memberId) {
		return memberRepository.findByMemberId(memberId).isPresent();
	}

	public boolean checkMemberNicknameExists(String memberNickname) {
		return memberRepository.findByMemberNickname(memberNickname).isPresent();
	}

	public boolean existsByEmail(String email) {
		return memberRepository.existsByMemberEmail(email);
	}

	@Transactional
	public void deleteMember(Integer memberNo) {
		Member member = memberRepository.findById(memberNo)
				.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		// 1. 회원의 캠핑장 + 이미지 전부 삭제
		deleteCampsitesByMember(member);

		// 2. 회원 삭제
		memberRepository.delete(member);
	}

	private void deleteCampsitesByMember(Member member) {
		List<Campsite> campsites = campsiteRepository.findAllByMember(member);
		for (Campsite campsite : campsites) {
			deleteCampsiteImage(campsite.getCampsiteImageUrl());
		}
		campsiteRepository.deleteAll(campsites);
	}

	private void deleteCampsiteImage(String imageUrl) {
		final String CAMPSITE_UPLOAD_DIR = "C:/upload/campsite/";
		final String CAMPSITE_IMAGE_BASE_URL = "http://localhost:8081/images/campsite/";

		if (imageUrl == null || !imageUrl.startsWith(CAMPSITE_IMAGE_BASE_URL))
			return;

		String filename = imageUrl.replace(CAMPSITE_IMAGE_BASE_URL, "");
		File file = new File(CAMPSITE_UPLOAD_DIR + filename);
		if (file.exists()) {
			file.delete();
		}
	}

	public void sendVerificationCodeForFindId(FindIdRequest request) {
		Member member = memberRepository.findByMemberNameAndMemberPhoneAndMemberEmail(request.getMemberName(),
				request.getMemberPhone(), request.getMemberEmail())
				.orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));

		mailService.sendVerificationCode(request.getMemberEmail());
	}

	public boolean verifyCode(String email, String code) {
		return mailService.verifyCode(email, code);
	}

	public String findMemberId(String name, String phone, String email) {
		Member member = memberRepository.findByMemberNameAndMemberPhoneAndMemberEmail(name, phone, email)
				.orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보가 없습니다."));
		return member.getMemberId();
	}

}
