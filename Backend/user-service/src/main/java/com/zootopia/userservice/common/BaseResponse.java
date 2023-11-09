package com.zootopia.userservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public class BaseResponse {

    private HttpStatus status;

    private String message;

    private Object data;
}
