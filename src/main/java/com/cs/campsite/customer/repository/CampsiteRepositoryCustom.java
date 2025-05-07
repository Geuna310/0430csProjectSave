package com.cs.campsite.customer.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cs.campsite.customer.dto.CampsiteSearchCondition;
import com.cs.campsite.customer.dto.CampsiteSimpleDTO;

public interface CampsiteRepositoryCustom {

	Page<CampsiteSimpleDTO> searchCampsite(CampsiteSearchCondition csc, Pageable pageable);
}