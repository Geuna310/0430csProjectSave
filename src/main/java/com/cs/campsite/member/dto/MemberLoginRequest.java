package com.cs.campsite.member.dto;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private String memberId;
    private String memberPassword;
}
