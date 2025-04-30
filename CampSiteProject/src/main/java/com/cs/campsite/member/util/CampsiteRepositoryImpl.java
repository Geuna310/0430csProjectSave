package com.cs.campsite.member.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.cs.campsite.customer.dto.CampsiteSearchCondition;
import com.cs.campsite.customer.dto.CampsiteSimpleDTO;
import com.cs.campsite.customer.entity.QCampsiteCategories;
import com.cs.campsite.customer.entity.QCampsiteFacility;
import com.cs.campsite.customer.entity.QCampsiteSectionTypes;
import com.cs.campsite.customer.entity.QFacility;
import com.cs.campsite.member.entity.Campsite;
import com.cs.campsite.member.entity.QCampsite;
import com.cs.campsite.member.entity.QCategory;
import com.cs.campsite.member.entity.QRoom;
import com.cs.campsite.member.entity.QSection;
import com.cs.campsite.member.entity.QSectionTypes;
import com.cs.campsite.customer.repository.CampsiteRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CampsiteRepositoryImpl implements CampsiteRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CampsiteSimpleDTO> searchCampsite(CampsiteSearchCondition csc, Pageable pageable) {
        QCampsite campsite = QCampsite.campsite;
        QRoom room = QRoom.room;
        QFacility facility = QFacility.facility;
        QCampsiteFacility campsiteFacility = QCampsiteFacility.campsiteFacility;
        QCategory category = QCategory.category;
        QCampsiteCategories campsiteCategories = QCampsiteCategories.campsiteCategories;
        QSection section = QSection.section;
        QSectionTypes sectionTypes = QSectionTypes.sectionTypes;
        QCampsiteSectionTypes campsiteSectionTypes = QCampsiteSectionTypes.campsiteSectionTypes;

        BooleanBuilder where = new BooleanBuilder();

        // 이름 조건
        if (csc.getName() != null && !csc.getName().isBlank()) {
            where.and(campsite.campsiteName.contains(csc.getName()));
        }

        // 최소 인원 수용 조건
        if (csc.getMinCapacity() != null) {
            where.and(room.roomCapacity.goe(csc.getMinCapacity()));
        }

        // 캠핑장 수량 조건
//        where.and(room.roomQuantity.gt(0));

        // 기본 쿼리
        JPQLQuery<Campsite> baseQuery = queryFactory
            .select(campsite).distinct()
            .from(room)
            .join(room.campsite, campsite)
            .leftJoin(campsiteCategories).on(campsiteCategories.campsiteNo.eq(campsite))
            .leftJoin(campsiteCategories.categoryNo, category)
            .leftJoin(room.section, section)
            .leftJoin(campsiteSectionTypes).on(campsiteSectionTypes.campsiteNo.eq(campsite).and(campsiteSectionTypes.sectionNo.eq(section)))
            .leftJoin(campsiteSectionTypes.sectionTypeNo, sectionTypes)
            .leftJoin(campsite.facilities, campsiteFacility)
            .leftJoin(campsiteFacility.facility, facility)
            .where(where);

        // 카테고리 필터 적용
        baseQuery = applyMultiCategoryFilters(baseQuery, csc, category, sectionTypes, facility);

        List<CampsiteSimpleDTO> content = baseQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(c -> new CampsiteSimpleDTO(
                c.getCampsiteNo(),
                c.getCampsiteName(),
                c.getCampsiteLocation(),
                c.getCampsiteImageUrl()
            ))
            .toList();

        // total count 쿼리
        JPQLQuery<Long> countQuery = queryFactory
            .select(campsite.countDistinct())
            .from(room)
            .join(room.campsite, campsite)
            .leftJoin(campsiteCategories).on(campsiteCategories.campsiteNo.eq(campsite))
            .leftJoin(campsiteCategories.categoryNo, category)
            .leftJoin(room.section, section)
            .leftJoin(campsiteSectionTypes).on(campsiteSectionTypes.campsiteNo.eq(campsite).and(campsiteSectionTypes.sectionNo.eq(section)))
            .leftJoin(campsiteSectionTypes.sectionTypeNo, sectionTypes)
            .leftJoin(campsite.facilities, campsiteFacility)
            .leftJoin(campsiteFacility.facility, facility)
            .where(where);

        countQuery = applyMultiCategoryFilters(countQuery, csc, category, sectionTypes, facility);

        long total = countQuery.fetch().size();

        return new PageImpl<>(content, pageable, total);
    }
    

    // 다중선택 필터 적용 메소드
    private <T extends JPQLQuery<?>> T applyMultiCategoryFilters(
    	    T query,
    	    CampsiteSearchCondition csc,
    	    QCategory category,
    	    QSectionTypes sectionTypes,
    	    QFacility facility
    	) {
    	    List<String> selectedCategoryNames = csc.getCategoryNames();
    	    List<String> selectedSectionTypes = csc.getSectionTypeNames();
    	    List<String> selectedFacilityNames = csc.getFacilityCategoryNames();

    	    BooleanBuilder categoryFilter = new BooleanBuilder();

    	    if (selectedCategoryNames != null && !selectedCategoryNames.isEmpty()) {
    	        categoryFilter.and(category.categoryName.in(selectedCategoryNames));
    	    }


    	    if (selectedSectionTypes != null && !selectedSectionTypes.isEmpty()) {
    	        categoryFilter.and(sectionTypes.sectionTypeName.in(selectedSectionTypes));
    	    }
    	    
    	    if (selectedFacilityNames != null && !selectedFacilityNames.isEmpty()) {
    	    	categoryFilter.and(facility.facilityName.in(selectedFacilityNames));
    	    }

    	    if (categoryFilter.hasValue()) {
    	        query.where(categoryFilter);
    	        query.groupBy(QCampsite.campsite.campsiteNo);

    	        BooleanBuilder having = new BooleanBuilder();
    	        if (selectedCategoryNames != null && !selectedCategoryNames.isEmpty()) {
    	            having.and(category.categoryName.countDistinct().eq((long) selectedCategoryNames.size()));
    	        }
    	        if (selectedSectionTypes != null && !selectedSectionTypes.isEmpty()) {
    	            having.and(sectionTypes.sectionTypeName.countDistinct().eq((long) selectedSectionTypes.size()));
    	        }
    	        if (selectedFacilityNames != null && !selectedFacilityNames.isEmpty()) {
    	        	having.and(facility.facilityName.countDistinct().eq((long) selectedFacilityNames.size()));
    	        }

    	        query.having(having);
    	    }

    	    return query;
    	}

}
