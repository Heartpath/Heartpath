package com.zootopia.storeservice.store.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LetterPaperResDto {
    private String name;
    private int price;
    private String description;
    private String imagePath;
    private boolean isOwned;

    @Builder
    public LetterPaperResDto(String name, int price, String description, String imagePath, boolean isOwned) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.isOwned = isOwned;
    }
}
