package com.zootopia.userservice.dto;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class UserLoginDTO {

    private String kakaoToken;

    private String fcmToken;
}
