
package com.cs.campsite.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CodeVerificationRequest {
    private String name;
    private String phone;
    private String email;
    private String code;
}
