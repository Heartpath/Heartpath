package com.zootopia.userservice.dto;

import lombok.Getter;


@Getter
public class UserRegisterDTO {

    private String memberID;

    private String kakaoToken;

    private String fcmToken;
}
