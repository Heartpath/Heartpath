package com.zootopia.userservice.exception;

import com.zootopia.userservice.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(FriendException.class)
    protected ResponseEntity<ErrorResponse> handleFriendException(FriendException error) {

        FriendErrorCode e = error.getFriendErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(e.getHttpStatus().toString(), e.getErrorCode(), e.getMessage());

        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }
}
