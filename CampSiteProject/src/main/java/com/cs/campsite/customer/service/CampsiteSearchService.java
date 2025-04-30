package com.cs.campsite.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.cs.campsite.customer.dto.CampsiteSearchCondition;
import com.cs.campsite.customer.dto.CampsiteSimpleDTO;
import com.cs.campsite.member.repository.CampsiteRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CampsiteSearchService {
	
	private final CampsiteRepository campsiteRepository;
	
	public Page<CampsiteSimpleDTO> searchCampsites(CampsiteSearchCondition csc, Pageable pageable) {
		return campsiteRepository.searchCampsite(csc, pageable);
	}
	
}