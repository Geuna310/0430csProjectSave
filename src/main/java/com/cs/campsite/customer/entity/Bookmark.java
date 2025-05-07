package com.cs.campsite.customer.entity;

import java.time.LocalDateTime;

import com.cs.campsite.member.entity.Campsite;
import com.cs.campsite.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campsite_no")
    private Campsite campsite;

    private LocalDateTime bookmarkCreatedAt;

    @Builder
	public Bookmark(Member member, Campsite campsite, LocalDateTime bookmarkCreatedAt) {
		this.member = member;
		this.campsite = campsite;
		this.bookmarkCreatedAt = bookmarkCreatedAt;
	}
    

}
