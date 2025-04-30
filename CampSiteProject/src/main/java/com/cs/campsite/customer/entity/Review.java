package com.cs.campsite.customer.entity;

import com.cs.campsite.member.entity.Campsite;
import com.cs.campsite.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNo;

    private int reviewRating;

    @Column(columnDefinition = "TEXT")
    private String reviewText;

    private LocalDateTime reviewCreatedAt;

    // 캠핑장 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campsite_no", nullable = false)
    private Campsite campsite;

    // 회원 연관관계 (리뷰 작성자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;
}
