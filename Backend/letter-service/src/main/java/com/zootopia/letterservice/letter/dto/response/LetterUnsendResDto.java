package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.LetterMongo;
import lombok.*;

@Getter
@Setter
public class LetterUnsendResDto {
    private String index;
    private String receiver;

    public LetterUnsendResDto(LetterMongo letter, String receiver) {
        this.index = letter.getId();
        this.receiver = receiver;
    }
}
