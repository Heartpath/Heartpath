package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.LetterMySQL;
import com.zootopia.letterservice.letter.entity.LetterImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class LetterReceivedDetailResDto {

    private Long index;
    private String content;
//    private String sender;
//    private String receiver;
    private LocalDateTime time;
    private Double lat;
    private Double lng;
    private List<String> files;
//    private boolean isFriend;

    public LetterReceivedDetailResDto(LetterMySQL letter) {
        this.index = letter.getId();
        this.content = letter.getContent();
//        this.sender = letter.getSender();
//        this.receiver = letter.getReceiver();
        this.time = letter.getCreatedDate();
        this.lat = letter.getLat();
        this.lng = letter.getLng();
        this.files = letter.getLetterImages().stream()
                .map(LetterImage::getImagePath)
                .collect(Collectors.toList());
    }
}
