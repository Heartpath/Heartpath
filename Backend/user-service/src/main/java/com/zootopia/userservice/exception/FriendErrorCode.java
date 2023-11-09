package com.zootopia.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Getter
public enum FriendErrorCode {

    ADD_FRIEND_TO_MYSELF(BAD_REQUEST, 1004, "자기 자신에게 친구 추가를 할 수 없습니다."),
    ALREADY_ADDED_FRIEND(BAD_REQUEST, 1005, "이미 친구 추가가 되어있습니다."),
    NON_EXISTENT_USER(BAD_REQUEST, 1006, "존재하지 않은 유저에게 친구 추가를 할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String message;

    FriendErrorCode(HttpStatus httpStatus, int errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
