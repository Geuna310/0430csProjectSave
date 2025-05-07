package com.cs.campsite.member.dto;

import lombok.Data;

@Data
public class MemberUpdateRequest {
	private String memberNickname;
	private String memberPassword;
	private String memberEmail;
	private String memberPhone;
	private String memberGender;
	private String memberImageUrl;

}

