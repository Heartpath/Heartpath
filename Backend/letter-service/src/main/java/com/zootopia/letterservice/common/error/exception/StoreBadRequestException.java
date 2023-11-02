package com.zootopia.letterservice.common.error.exception;

import com.zootopia.letterservice.common.error.code.StoreErrorCode;
import lombok.Getter;

@Getter
public class StoreBadRequestException extends RuntimeException {

    private StoreErrorCode errorCode;

    public StoreBadRequestException(StoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
