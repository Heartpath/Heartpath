package com.zootopia.storeservice.common.error.exception;

import com.zootopia.storeservice.common.error.code.StoreErrorCode;
import lombok.Getter;

@Getter
public class StoreBadRequestException extends RuntimeException {

    private StoreErrorCode errorCode;

    public StoreBadRequestException(StoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
