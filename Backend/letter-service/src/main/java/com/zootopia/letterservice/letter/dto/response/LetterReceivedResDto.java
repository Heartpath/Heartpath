package com.zootopia.letterservice.letter.dto.response;

import com.zootopia.letterservice.letter.entity.LetterMySQL;
import com.zootopia.letterservice.letter.entity.PlaceImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class LetterReceivedResDto {

    private Long index;
//    private String sender;
    private LocalDateTime time;
    private Double lat;
    private Double lng;

    private List<String> location;

    public LetterReceivedResDto(LetterMySQL letter) {
        this.index = letter.getId();
//        this.sender = letter.getSender().getNickname();
        this.time = letter.getCreatedDate();
        this.lat = letter.getLat();
        this.lng = letter.getLng();
        this.location = letter.getPlaceImages()
                .stream()
                .map(PlaceImage::getImagePath)
                .collect(Collectors.toList());
    }
}
