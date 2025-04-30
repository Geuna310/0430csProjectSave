package com.cs.campsite.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "member_table")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberNo;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String memberPassword;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String memberNickname;

    @Column(nullable = false, unique = true)
    private String memberPhone;

    @Column(nullable = false, unique = true)
    private String memberEmail;

    @Column(nullable = false)
    private String memberBirth;

    @Column(nullable = false)
    private String memberGender;

    @Column(updatable = false)
    private String memberCreatedAt;

    @Column
    private String memberImageUrl;
    
    @Column(nullable = false)
    private boolean isBusiness = false;

    @Transient
    private String role;
    
    @Builder
    public Member(String memberId, String memberPassword, String memberName, String memberNickname,
                  String memberPhone, String memberEmail, String memberBirth, String memberGender, String memberImageUrl) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberNickname = memberNickname;
        this.memberPhone = memberPhone;
        this.memberEmail = memberEmail;
        this.memberBirth = memberBirth;
        this.memberGender = memberGender;
        this.memberImageUrl = memberImageUrl;
    }
}
