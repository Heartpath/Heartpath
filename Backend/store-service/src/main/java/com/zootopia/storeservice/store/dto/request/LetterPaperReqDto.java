package com.zootopia.storeservice.store.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class LetterPaperReqDto {

    private int letterpaperId;

    @JsonCreator
    public LetterPaperReqDto(@JsonProperty("letterpaperId") int letterpaperId){
        this.letterpaperId = letterpaperId;
    }

}
