package com.cs.campsite.member.dto;

import lombok.Data;

@Data
public class CheckRequest {
    private String memberId;
    private String memberNickname;
    private String memberEmail;
}
