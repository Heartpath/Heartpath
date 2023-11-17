package com.zootopia.userservice.dto;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class UserRegisterDTO {

    private String memberID;

    private String kakaoToken;

    private String fcmToken;
}
