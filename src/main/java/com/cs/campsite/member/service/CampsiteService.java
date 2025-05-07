package com.cs.campsite.member.service;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cs.campsite.member.dto.CampsiteRegisterRequest;
import com.cs.campsite.member.dto.CampsiteResponse;
import com.cs.campsite.member.entity.Campsite;
import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.repository.CampsiteRepository;
import com.cs.campsite.member.repository.MemberRepository;
import com.cs.campsite.member.util.ThumbnailUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampsiteService {

	private final CampsiteRepository campsiteRepository;
	private final MemberRepository memberRepository;

	private static final String UPLOAD_DIR = "C:/upload/campsite/";
	private static final String IMAGE_BASE_URL = "http://localhost:8081/images/campsite/";

	public boolean isCampsiteOwner(Member member) {
		return campsiteRepository.existsByMember(member);
	}

	public void registerCampsite(Integer memberNo, CampsiteRegisterRequest request, MultipartFile imageFile) {
		Member member = memberRepository.findById(memberNo)
				.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		String imageUrl = "https://localhost:8081/images/xxxxxx.jpg";
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

				ThumbnailUtil.createThumbnail(savedFile, 300, 300);

				imageUrl = IMAGE_BASE_URL + savedFilename;
			}

			Campsite campsite = Campsite.builder().campsiteName(request.getCampsiteName())
					.campsiteLocation(request.getCampsiteLocation())
					.campsiteDescription(request.getCampsiteDescription()).campsiteImageUrl(imageUrl)
					.campsitesBusinessNumber(request.getCampsitesBusinessNumber()).member(member).build();

			campsiteRepository.save(campsite);

			if (!member.isBusiness()) {
				member.setBusiness(true);
				memberRepository.save(member);
			}

		} catch (Exception e) {
	        if (savedFile != null && savedFile.exists()) {
	            savedFile.delete();
	        }
	        throw new IllegalArgumentException("캠핑장 등록 실패: " + e.getMessage());
	    }
	}

	public List<CampsiteResponse> getAllCampsites() {
		return campsiteRepository.findAll().stream().map(CampsiteResponse::from).collect(Collectors.toList());
	}

	public List<CampsiteResponse> getCampsitesByMember(int memberNo) {
		Member member = memberRepository.findById(memberNo)
				.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		return campsiteRepository.findAllByMember(member).stream().map(CampsiteResponse::from)
				.collect(Collectors.toList());
	}

	public CampsiteResponse getCampsiteById(int campsiteNo) {
		Campsite campsite = campsiteRepository.findById(campsiteNo)
				.orElseThrow(() -> new IllegalArgumentException("캠핑장을 찾을 수 없습니다."));
		return CampsiteResponse.from(campsite);
	}

	public void updateCampsite(int campsiteNo, Integer memberNo, CampsiteRegisterRequest request,
			MultipartFile imageFile) {
		Campsite campsite = campsiteRepository.findById(campsiteNo)
				.orElseThrow(() -> new IllegalArgumentException("캠핑장을 찾을 수 없습니다."));

		if (!campsite.getMember().getMemberNo().equals(memberNo)) {
			throw new IllegalArgumentException("수정 권한이 없습니다.");
		}

		String imageUrl = campsite.getCampsiteImageUrl();
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

				ThumbnailUtil.createThumbnail(savedFile, 300, 300);

				imageUrl = IMAGE_BASE_URL + savedFilename;
			}

			campsite.setCampsiteName(request.getCampsiteName());
			campsite.setCampsiteLocation(request.getCampsiteLocation());
			campsite.setCampsiteDescription(request.getCampsiteDescription());
			campsite.setCampsiteImageUrl(imageUrl);
			campsite.setCampsitesBusinessNumber(request.getCampsitesBusinessNumber());

			campsiteRepository.save(campsite);

		} catch (Exception e) {
	        if (savedFile != null && savedFile.exists()) {
	            savedFile.delete();
	        }
	        throw new IllegalArgumentException("캠핑장 수정 실패: " + e.getMessage());
	    }
	}

	public void deleteCampsite(int campsiteNo, Integer memberNo) {
		Campsite campsite = campsiteRepository.findById(campsiteNo)
				.orElseThrow(() -> new IllegalArgumentException("캠핑장을 찾을 수 없습니다."));

		if (!campsite.getMember().getMemberNo().equals(memberNo)) {
			throw new IllegalArgumentException("삭제 권한이 없습니다.");
		}

		campsiteRepository.delete(campsite);
	}
}
