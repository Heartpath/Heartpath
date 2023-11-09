package com.zootopia.letterservice.common.error.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    private String httpStatus;
    private String errorCode;
    private String errorMessage;

    public static ErrorResponse of(String httpStatus, String errorCode, String errorMessage) {
        return ErrorResponse.builder()
                .httpStatus(httpStatus)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
}
