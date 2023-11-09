package com.zootopia.storeservice.store.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CharacterBuyReqDto {

    private Long characterId;

    public CharacterBuyReqDto(Long characterId){
        this.characterId = characterId;
    }
}
