package com.cs.campsite.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cs.campsite.member.entity.Campsite;

public interface CustomerCampsiteRepository extends JpaRepository<Campsite, Integer> {

    // 캠핑장 + 편의시설을 한 번에 가져오기 위한 fetch join 쿼리
    @Query("""
        SELECT DISTINCT c
        FROM Campsite c
        LEFT JOIN FETCH c.facilities cf
        LEFT JOIN FETCH cf.facility
        ORDER BY c.campsiteCreatedAt DESC
        """)
    List<Campsite> findRecommendedCampsitesWithFacilities();
    
    @Query("""
            SELECT c
            FROM Campsite c
            LEFT JOIN FETCH c.facilities cf
            LEFT JOIN FETCH cf.facility
            WHERE c.campsiteNo = :campsiteNo
        """)
        Optional<Campsite> findCampsiteWithFacilities(@Param("campsiteNo") int campsiteNo);
    
    @Query("""
    	    SELECT DISTINCT c
    	    FROM Campsite c
    	    JOIN c.categories cat
    	    LEFT JOIN FETCH c.facilities cf
    	    LEFT JOIN FETCH cf.facility
    	""")
    	List<Campsite> findAllWithCategory();
    
    
}


