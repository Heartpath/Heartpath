package com.zootopia.storeservice.store.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CharacterBuyReqDto {

    private Long crowtitId;

    public CharacterBuyReqDto(Long crowtitId){
        this.crowtitId = crowtitId;
    }
}
