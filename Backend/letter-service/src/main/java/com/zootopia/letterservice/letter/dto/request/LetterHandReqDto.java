package com.zootopia.letterservice.letter.dto.request;

import com.zootopia.letterservice.letter.entity.Letter;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetterHandReqDto {
    private String receiverId;

}
