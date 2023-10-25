package com.zootopia.letterservice.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseBody<T> {
    private String status;
    private String message;
    private T data;

    public BaseResponseBody(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
