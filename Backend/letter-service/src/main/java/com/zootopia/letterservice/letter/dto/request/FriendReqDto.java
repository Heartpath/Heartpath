package com.zootopia.letterservice.letter.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FriendReqDto {
    private String from;
    private String to;

    @Builder
    public FriendReqDto(String from, String to) {
        this.from = from;
        this.to = to;
    }
}
