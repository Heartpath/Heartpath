package com.zootopia.userservice.domain;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Member {

    private String MEMBER_ID;

    private String KAKAO_ID;

    private String FCM_TOKEN;

    private String nickname;

    private int point;

    private String profileImagePath;

    private LocalDateTime createdDate;

    private int status;
}
