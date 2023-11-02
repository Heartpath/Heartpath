package com.zootopia.letterservice.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LetterPaperBuyReqDto {

    private Long letterpaperId;

    public LetterPaperBuyReqDto(Long letterpaperId){
        this.letterpaperId = letterpaperId;
    }
}
