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
    private String sender;
    private String senderID;
    private String receiver;
    private LocalDateTime time;
    private Double lat;
    private Double lng;
    private List<String> files;
    private boolean isFriend;

    public LetterReceivedDetailResDto(LetterMySQL letter, String sender, String senderID, String receiver, boolean flag) {
        this.index = letter.getId();
        this.content = letter.getContent();
        this.sender = sender;
        this.senderID = senderID;
        this.receiver = receiver;
        this.time = letter.getCreatedDate();
        this.lat = letter.getLat();
        this.lng = letter.getLng();
        this.files = letter.getLetterImages().stream()
                .map(LetterImage::getImagePath)
                .collect(Collectors.toList());
        this.isFriend = flag;
    }
}
