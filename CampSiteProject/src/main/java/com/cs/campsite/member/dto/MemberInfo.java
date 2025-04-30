package com.cs.campsite.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfo {
    private String memberId;
    private String memberNickname;
    private String memberEmail;
    private String memberName;
}
