package com.cs.campsite.customer.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.campsite.customer.entity.Bookmark;
import com.cs.campsite.customer.repository.BookmarkRepository;
import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.repository.CampsiteRepository;
import com.cs.campsite.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final CampsiteRepository campsiteRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String toggleBookmark(Integer memberNo, Integer campsiteNo) {
    	
    	/** 찜 여부 확인 */
        Optional<Bookmark> existingBookmark = bookmarkRepository
            .findByMember_MemberNoAndCampsite_CampsiteNo(memberNo, campsiteNo);

        /** 이미 찜 되어있으면 삭제(해제), 없으면 등록*/
        if (existingBookmark.isPresent()) {
            bookmarkRepository.delete(existingBookmark.get());
            return "찜이 해제되었습니다.";
        } else {
            var campsite = campsiteRepository.findById(campsiteNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠핑장입니다."));

            Member member;
		
			member = memberRepository.findById(memberNo)
					   .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

            Bookmark newBookmark = Bookmark.builder()
            	   .member(member)
            	   .campsite(campsite)
            	   .bookmarkCreatedAt(LocalDateTime.now())
            	   .build();

            bookmarkRepository.save(newBookmark);
            return "찜이 등록되었습니다.";
        }
    }
    

    /** 북마크 전체 조회 */
    @Transactional(readOnly = true)
    public List<Bookmark> getBookmarks(Integer memberNo) {
        return bookmarkRepository.findByMember_MemberNo(memberNo);
    }
}
