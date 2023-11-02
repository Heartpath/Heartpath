package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.LetterMySQL;
import lombok.*;

@Getter
@Setter
public class LetterSendResDto {
    private Long index;
//    private String receiver;

    public LetterSendResDto(LetterMySQL letter) {
        this.index = letter.getId();
//        this.receiver = letter.getReceiverId;
    }
}
