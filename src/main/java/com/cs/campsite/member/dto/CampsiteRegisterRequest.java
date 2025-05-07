package com.cs.campsite.member.dto;

import lombok.Getter;

@Getter
public class CampsiteRegisterRequest {

    private String campsiteName;
    private String campsiteLocation;
    private String campsiteDescription;
    private String campsiteImageUrl;
    private String campsitesBusinessNumber;
}
