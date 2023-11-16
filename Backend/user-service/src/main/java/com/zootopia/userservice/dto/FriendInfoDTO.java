package com.zootopia.userservice.dto;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class FriendInfoDTO {

    private String memberID;

    private String nickname;

    private String profileImagePath;
}
