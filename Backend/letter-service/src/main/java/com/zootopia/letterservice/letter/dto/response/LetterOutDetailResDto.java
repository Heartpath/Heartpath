package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.LetterMySQL;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LetterOutDetailResDto {

    private Long index;
    private String content;
    private String sender;
    private String receiver;

    public LetterOutDetailResDto (LetterMySQL letter) {
        this.index = letter.getId();
        this.content = letter.getContent();
//        this.sender = letter.getSender();
//        this.receiver = letter.getReceiver();
    }
}
