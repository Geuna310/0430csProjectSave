package com.cs.campsite.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CampsiteSimpleDTO {
	private Integer campsiteNo;
    private String campsiteName;
    private String campsiteLocation;
    private String campsiteImageUrl;
    private Long reviewCount;         
    private Double averageRating;    


}
