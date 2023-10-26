package com.zootopia.letterservice.store.dto.request;

import lombok.Data;

@Data
public class CharacterBuyReqDto {

    private Long characterId;

    public CharacterBuyReqDto(Long characterId){
        this.characterId = characterId;
    }
}
