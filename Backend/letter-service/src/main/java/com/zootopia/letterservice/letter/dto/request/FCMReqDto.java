package com.zootopia.letterservice.letter.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FCMReqDto {
    private String targetToken;
    private String title;
    private String body;

    @Builder
    public FCMReqDto(String token, String title, String body) {
        this.targetToken = token;
        this.title = title;
        this.body = body;
    }
}
