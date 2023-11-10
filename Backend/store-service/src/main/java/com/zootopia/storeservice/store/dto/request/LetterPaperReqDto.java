package com.zootopia.storeservice.store.dto.request;

import com.zootopia.storeservice.store.entity.LetterPaperBookId;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class LetterPaperReqDto {

    private LetterPaperBookId letterpaperId;

    public LetterPaperReqDto(int letterpaperId){
        this.letterpaperId = new LetterPaperBookId(letterpaperId);
    }

}
