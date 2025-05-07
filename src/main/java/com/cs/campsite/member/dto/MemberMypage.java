package com.cs.campsite.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberMypage {
	  private String memberId;
	  private String memberName;
	  private String memberNickname;
	  private String memberEmail;
	  private String memberPhone;
	  private String memberGender;
	  private String memberImageUrl;
}
