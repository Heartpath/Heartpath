package com.zootopia.storeservice.common.error.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    private String httpStatus;
    private int status;
    private String message;

    public static ErrorResponse of(String httpStatus, int status, String message) {
        return ErrorResponse.builder()
                .httpStatus(httpStatus)
                .status(status)
                .message(message)
                .build();
    }
}
