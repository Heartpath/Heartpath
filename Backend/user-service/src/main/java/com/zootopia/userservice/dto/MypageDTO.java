package com.zootopia.userservice.dto;

import lombok.Getter;


@Getter
public class MypageDTO {

    private String memberID;

    private String nickname;

    private String profileImagePath;

    private String characterImagePath;

    private int point;
}
