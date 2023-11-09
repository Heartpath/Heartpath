package com.zootopia.letterservice.letter.dto.request;

import com.zootopia.letterservice.letter.entity.LetterMongo;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LetterPlaceReqDto {
    private String id;
    private String senderId;
    private String receiverId;
    private Double lat;
    private Double lng;

    @Builder
    public LetterPlaceReqDto(String id, Double lat, Double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }
}
