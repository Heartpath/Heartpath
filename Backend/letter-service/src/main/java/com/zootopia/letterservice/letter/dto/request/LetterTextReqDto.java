package com.zootopia.letterservice.letter.dto.request;

import com.zootopia.letterservice.letter.entity.Letter;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LetterTextReqDto {
    private String receiverId;
    private String text;
}
