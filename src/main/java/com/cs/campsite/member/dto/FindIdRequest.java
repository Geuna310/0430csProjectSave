package com.cs.campsite.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdRequest {
	private String memberName;
	private String memberPhone;
	private String memberEmail;
}
