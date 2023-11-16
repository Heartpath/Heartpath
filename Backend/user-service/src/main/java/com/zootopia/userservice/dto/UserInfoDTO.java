package com.zootopia.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class UserInfoDTO {

    private String memberID;

    private String nickname;

    private String profileImagePath;

    private String characterImagePath;

    private int point;
}
