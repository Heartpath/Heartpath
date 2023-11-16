package com.zootopia.userservice.exception;

import lombok.Getter;


@Getter
public class FriendException extends RuntimeException {

    private final FriendErrorCode friendErrorCode;

    public FriendException(FriendErrorCode friendErrorCode) {
        super(friendErrorCode.getMessage());
        this.friendErrorCode = friendErrorCode;
    }
}
