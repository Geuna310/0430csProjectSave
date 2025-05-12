package com.cs.campsite.member.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.cs.campsite.customer.dto.CampsiteSearchCondition;
import com.cs.campsite.customer.dto.CampsiteSimpleDTO;
import com.cs.campsite.customer.entity.CampsiteSortType;
import com.cs.campsite.customer.entity.QCampsiteCategories;
import com.cs.campsite.customer.entity.QCampsiteFacility;
import com.cs.campsite.customer.entity.QCampsiteSectionTypes;
import com.cs.campsite.customer.entity.QFacility;
import com.cs.campsite.customer.entity.QReview;
import com.cs.campsite.member.entity.QCampsite;
import com.cs.campsite.member.entity.QCategory;
import com.cs.campsite.member.entity.QRoom;
import com.cs.campsite.member.entity.QSection;
import com.cs.campsite.member.entity.QSectionTypes;
import com.cs.campsite.customer.repository.CampsiteRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
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
        QReview review = QReview.review;

        /** 서브쿼리 */
        // TODO : 서브쿼리는 속도를 저하시킬 가능성 높아 추후 보완 필요할 수도 
        QReview reviewSub = new QReview("reviewSub"); // 별명
        
        SubQueryExpression<Long> reviewCountSubQuery = JPAExpressions
        		.select(reviewSub.count())
        		.from(reviewSub)
        		.where(reviewSub.campsite.eq(campsite));
        
        SubQueryExpression<Double> avgRatingSubQuery = JPAExpressions
        		.select(reviewSub.reviewRating.avg())
        		.from(reviewSub)
        		.where(reviewSub.campsite.eq(campsite));
  
        
        BooleanBuilder where = new BooleanBuilder();
        

        /** 이름 조건 */
        if (csc.getName() != null && !csc.getName().isBlank()) {
            where.and(campsite.campsiteName.contains(csc.getName()));
        }

        /** 인원 수용 조건 */
        if (csc.getMinCapacity() != null) {
            where.and(room.roomCapacity.goe(csc.getMinCapacity()));
        }

        /** 방 수량 조건 */
//        where.and(room.roomQuantity.gt(0));
        
        /** 가격 조건 (성수기, 비성수기 통합) */
        // TODO : 회의로 어떤 방식을 적용할지 정해야...
//        NumberExpression<Integer> effectivePrice = room.roomPrice.min()
//        	    .coalesce(0)
//        	    .min(room.roomPeakPrice.min().coalesce(0));
        
        NumberExpression<Long> reviewCountExpr = Expressions.numberTemplate(Long.class, "({0})", reviewCountSubQuery);
        NumberExpression<Double> avgRatingExpr = Expressions.numberTemplate(Double.class, "({0})", avgRatingSubQuery);

        /** 기본 쿼리 */ 
        JPQLQuery<Tuple> baseQuery = queryFactory
            .selectDistinct(
            		campsite.campsiteNo,
            		campsite.campsiteName,
            		campsite.campsiteLocation,
            		campsite.campsiteImageUrl,
            		reviewCountExpr,
            		avgRatingExpr
            		)
            .from(room)
            .join(room.campsite, campsite)
            .leftJoin(campsite.reviews, review)
            .leftJoin(campsiteCategories).on(campsiteCategories.campsiteNo.eq(campsite))
            .leftJoin(campsiteCategories.categoryNo, category)
            .leftJoin(room.section, section)
            .leftJoin(campsiteSectionTypes).on(campsiteSectionTypes.campsiteNo.eq(campsite).and(campsiteSectionTypes.sectionNo.eq(section)))
            .leftJoin(campsiteSectionTypes.sectionTypeNo, sectionTypes)
            .leftJoin(campsite.facilities, campsiteFacility)
            .leftJoin(campsiteFacility.facility, facility)
            .where(where)
            .groupBy(campsite.campsiteNo);

        /** 카테고리 필터 적용 */
        baseQuery = applyMultiCategoryFilters(baseQuery, csc, category, sectionTypes, facility);

        /** 정렬 조건 */
        if (csc.getSort() == CampsiteSortType.REVIEW_COUNT) {
            OrderSpecifier<?> orderByReviewCount = Expressions.numberTemplate(Long.class, "({0})", reviewCountSubQuery).desc();
            baseQuery = baseQuery.orderBy(orderByReviewCount);
        } else if (csc.getSort() == CampsiteSortType.RATING_AVG) {
            OrderSpecifier<?> orderByAvgRating = Expressions.numberTemplate(Double.class, "({0})", avgRatingSubQuery).desc();
            baseQuery = baseQuery.orderBy(orderByAvgRating);
        } else {
            baseQuery = baseQuery.orderBy(campsite.campsiteCreatedAt.desc());
        }
        
        List<CampsiteSimpleDTO> content = baseQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(tuple -> new CampsiteSimpleDTO(
            	tuple.get(campsite.campsiteNo),
                tuple.get(campsite.campsiteName),
                tuple.get(campsite.campsiteLocation),
                tuple.get(campsite.campsiteImageUrl),
                tuple.get(reviewCountExpr),
                tuple.get(avgRatingExpr)
            ))
            .toList();

        /** total count 쿼리 */
        JPQLQuery<Long> countQuery = queryFactory
            .select(campsite.countDistinct())
            .from(room)
            .join(room.campsite, campsite)
            .leftJoin(campsite.reviews, review)
            .leftJoin(campsiteCategories).on(campsiteCategories.campsiteNo.eq(campsite))
            .leftJoin(campsiteCategories.categoryNo, category)
            .leftJoin(room.section, section)
            .leftJoin(campsiteSectionTypes).on(campsiteSectionTypes.campsiteNo.eq(campsite).and(campsiteSectionTypes.sectionNo.eq(section)))
            .leftJoin(campsiteSectionTypes.sectionTypeNo, sectionTypes)
            .leftJoin(campsite.facilities, campsiteFacility)
            .leftJoin(campsiteFacility.facility, facility)
            .where(where);

        countQuery = applyMultiCategoryFilters(countQuery, csc, category, sectionTypes, facility);

        Long total = countQuery.fetchOne();
        return new PageImpl<CampsiteSimpleDTO>(content, pageable, total != null ? total : 0L);

    }
    

    /** 다중선택 필터 적용 메소드 */
    // TODO : 가격관련 완성한 뒤 여기도 수정해야 
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
    	     //   query.groupBy(QCampsite.campsite.campsiteNo);

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

    	    //    query.having(having);
    	    }
    	    
    	    return query;
    	}

}
