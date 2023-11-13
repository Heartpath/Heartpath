package com.zootopia.storeservice.common.error.exception;

import com.zootopia.storeservice.common.error.code.ErrorCode;

public class ServerException extends RuntimeException{
    private ErrorCode errorCode;

    public ServerException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
