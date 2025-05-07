package com.cs.campsite.customer.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

// 직렬화와 역직렬화 문제 때문에 밑줄 그임 entitiy를 직접 화면에 안띄우면 상관없다고 함
public class CampsiteFacilityId implements Serializable {
    private int campsiteNo;
    private int facilityNo;
}
