package com.cs.campsite.member.dto;

import com.cs.campsite.member.entity.Campsite;
import lombok.Getter;

@Getter
public class CampsiteResponse {

    private final int campsiteNo;
    private final String campsiteName;
    private final String campsiteLocation;
    private final String campsiteDescription;
    private final String campsiteImageUrl;
    private final String campsitesBusinessNumber;

    public CampsiteResponse(Campsite campsite) {
        this.campsiteNo = campsite.getCampsiteNo();
        this.campsiteName = campsite.getCampsiteName();
        this.campsiteLocation = campsite.getCampsiteLocation();
        this.campsiteDescription = campsite.getCampsiteDescription();
        this.campsiteImageUrl = campsite.getCampsiteImageUrl();
        this.campsitesBusinessNumber = campsite.getCampsitesBusinessNumber();
    }

    public static CampsiteResponse from(Campsite campsite) {
        return new CampsiteResponse(campsite);
    }
}
