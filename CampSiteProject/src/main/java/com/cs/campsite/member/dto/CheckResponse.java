package com.cs.campsite.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckResponse {
    private boolean memberIdAvailable;
    private boolean memberNicknameAvailable;
    private boolean memberEmailAvailable;
}
