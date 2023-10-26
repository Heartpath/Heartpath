package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.LetterMySQL;

public class LetterOutResDto {
    private Long index;
    private String receiver;

    public LetterOutResDto(LetterMySQL letter) {
        this.index = letter.getId();
//        this.receiver = letter.getReceiver().getNickname();
    }
}
