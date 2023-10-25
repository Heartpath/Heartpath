package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.Letter;

public class LetterOutResDto {
    private Long index;
    private String receiver;

    public LetterOutResDto(Letter letter) {
        this.index = letter.getId();
//        this.receiver = letter.getReceiver().getNickname();
    }
}
