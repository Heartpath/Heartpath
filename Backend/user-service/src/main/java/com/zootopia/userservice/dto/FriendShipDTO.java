package com.zootopia.userservice.dto;

import lombok.Getter;


@Getter
public class FriendShipDTO {

    private String from;

    private String to;

    private boolean isBlocked;
}
