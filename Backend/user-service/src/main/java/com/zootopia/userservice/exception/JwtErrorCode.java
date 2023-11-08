package com.zootopia.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
public enum JwtErrorCode {

    TOKEN_NOT_FOUND(NOT_FOUND, 1000, "Authorization Token이 없습니다."),
    NOT_SUPPORTED_TOKEN(NOT_ACCEPTABLE, 1001, "지원하지 않은 Authorization Token입니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, 1002, "Token 시간이 만료되었습니다."),
    INVALID_TOKEN(UNAUTHORIZED, 1003, "잘못된 토큰입니다.");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String message;

    JwtErrorCode(HttpStatus httpStatus, int errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
