package com.cs.campsite.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterRequest {
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private String memberPhone;
    private String memberEmail;
    private String memberBirth;
    private String memberGender;
}
