package com.zootopia.userservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class InsertUserDTO {

    private String memberID;

    private Long kakaoID;

    private String fcmToken;

    private String nickname;

    private int point;

    private String profileImagePath;

    private boolean status;

    private LocalDateTime createdDate;
}
