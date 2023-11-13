package com.zootopia.storeservice.store.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LetterPaperResDto {
    private int letterpaperId;
    private String name;
    private int price;
    private String description;
    private String imagePath;
    private boolean isOwned;

    @Builder
    public LetterPaperResDto(int letterpaperId, String name, int price, String description, String imagePath, boolean isOwned) {
        this.letterpaperId = letterpaperId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.isOwned = isOwned;
    }
}
