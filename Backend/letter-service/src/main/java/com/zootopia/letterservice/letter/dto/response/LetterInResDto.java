package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.Letter;
import lombok.*;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class LetterInResDto {

    private Long index;
    private String sender;
    private LocalDateTime time;
    private Double lat;
    private Double lng;

    private List<String> location;

    public LetterInResDto(Letter letter) {
        this.index = letter.getId();
//        this.receiver = letter.getReceiver().getNickname();
    }
}
