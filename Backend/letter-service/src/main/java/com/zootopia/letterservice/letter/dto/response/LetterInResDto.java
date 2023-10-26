package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.LetterMySQL;
import lombok.*;

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

    public LetterInResDto(LetterMySQL letter) {
        this.index = letter.getId();
//        this.sender = letter.getSender().getNickname();
    }
}
