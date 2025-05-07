package com.cs.campsite.customer.repository;

import com.cs.campsite.customer.entity.Bookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

	/** 회원이 찜한 캠핑장들 조회 */
    List<Bookmark> findByMember_MemberNo(Integer memberNo); 

    /** 특정 찜 조회 */
    Optional<Bookmark> findByMember_MemberNoAndCampsite_CampsiteNo(Integer memberNo, Integer campsiteNo); 

}
