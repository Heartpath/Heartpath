package com.zootopia.storeservice.common.error.exception;

import com.zootopia.storeservice.common.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private ErrorCode errorCode;

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
