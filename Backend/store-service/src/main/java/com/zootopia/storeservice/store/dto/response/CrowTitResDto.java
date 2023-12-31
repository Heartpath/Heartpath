package com.zootopia.storeservice.store.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CrowTitResDto {
    private int crowTitId;
    private String name;
    private int price;
    private String description;
    private String imagePath;
    private boolean isOwned;
    private boolean isMain;

    @Builder
    public CrowTitResDto(int crowTitId, String name, int price, String description, String imagePath, boolean isOwned, boolean isMain) {
        this.crowTitId = crowTitId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.isOwned = isOwned;
        this.isMain = isMain;
    }
}
