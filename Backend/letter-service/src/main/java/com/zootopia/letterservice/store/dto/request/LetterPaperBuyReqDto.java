package com.zootopia.letterservice.store.dto.request;

import lombok.Data;

@Data
public class LetterPaperBuyReqDto {

    private Long letterpaperId;

    public LetterPaperBuyReqDto(Long letterpaperId){
        this.letterpaperId = letterpaperId;
    }
}
