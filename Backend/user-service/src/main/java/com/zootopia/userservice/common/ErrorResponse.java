package com.zootopia.userservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private int status;

    private String message;

    private String httpStatus;

    public static ErrorResponse of(String httpStatus, int status, String message) {
        return new ErrorResponse(status, message, httpStatus);
    }
}

