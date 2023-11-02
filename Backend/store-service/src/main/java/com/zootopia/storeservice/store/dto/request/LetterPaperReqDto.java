package com.zootopia.storeservice.store.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LetterPaperReqDto {
    private String name;
    private Long price;
    private String description;
    private String imagePath;

    @Builder
    public LetterPaperReqDto(String name, Long price, String description, String imagePath) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }
}
