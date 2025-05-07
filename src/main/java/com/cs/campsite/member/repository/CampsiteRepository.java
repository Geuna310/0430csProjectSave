package com.cs.campsite.member.repository;


import com.cs.campsite.customer.repository.CampsiteRepositoryCustom;
import com.cs.campsite.member.entity.Campsite;
import com.cs.campsite.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface CampsiteRepository extends JpaRepository<Campsite, Integer>,
		JpaSpecificationExecutor<Campsite>, CampsiteRepositoryCustom{ // 조건을 동적으로 조합
	
	boolean existsByMember(Member member);
   
    
    List<Campsite> findAllByMember(Member member);
}