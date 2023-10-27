package com.zootopia.letterservice.letter.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetterPlaceReqDto {
    private long letterId;
    private double lat;
    private double lng;
}
