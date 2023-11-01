package com.zootopia.letterservice.common.error.exception;

import com.zootopia.letterservice.common.error.code.LetterErrorCode;
import lombok.*;

@Getter
public class LetterBadRequestException extends RuntimeException {

    private LetterErrorCode errorCode;

    public LetterBadRequestException(LetterErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
