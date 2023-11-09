package com.zootopia.storeservice.store.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class LetterPaperBuyReqDto {

    private Long letterpaperId;

    public LetterPaperBuyReqDto(Long letterpaperId){
        this.letterpaperId = letterpaperId;
    }
}
