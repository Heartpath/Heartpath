package com.zootopia.userservice.exception;

import lombok.Getter;


@Getter
public class JwtException extends IllegalAccessException{

    private final JwtErrorCode jwtErrorCode;

    public JwtException(JwtErrorCode jwtErrorCode) {
        super(jwtErrorCode.getMessage());
        this.jwtErrorCode = jwtErrorCode;
    }
}
