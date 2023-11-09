package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.LetterMongo;
import lombok.*;

@Getter
@Setter
public class LetterUnsendResDto {
    private String index;
//    private String receiverId;

    public LetterUnsendResDto(LetterMongo letter) {
        this.index = letter.getId();
//        this.receiverId = letter.getReceiverId();
    }
}
