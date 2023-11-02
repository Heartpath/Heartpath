package com.zootopia.letterservice.common.error.exception;

import com.zootopia.letterservice.common.error.code.ErrorCode;
import lombok.*;

@Getter
public class BadRequestException extends RuntimeException {

    private ErrorCode errorCode;

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
