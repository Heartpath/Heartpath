package com.zootopia.letterservice.common.error.exception;

import com.zootopia.letterservice.common.error.code.ErrorCode;
import lombok.*;

@Getter
public class ServerException extends RuntimeException {

    private ErrorCode errorCode;

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
