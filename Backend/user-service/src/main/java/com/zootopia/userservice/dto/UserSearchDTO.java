package com.zootopia.userservice.dto;

import lombok.Getter;


@Getter
public class UserSearchDTO {

    private String memberID;

    private String nickname;

    private String profileImagePath;

    private boolean isFriend;
}
