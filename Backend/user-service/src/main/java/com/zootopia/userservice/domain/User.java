package com.zootopia.userservice.domain;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class User {

    private String memberID;

    private String kakaoID;

    private String fcmToken;

    private String nickname;

    private int point;

    private String profileImagePath;

    private LocalDateTime createdDate;

    private int status;
}
