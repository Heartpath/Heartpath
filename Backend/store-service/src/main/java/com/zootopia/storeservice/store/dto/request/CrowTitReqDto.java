package com.zootopia.storeservice.store.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zootopia.storeservice.store.entity.CrowTitBookId;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CrowTitReqDto {

    private int crowTitId;

    @JsonCreator
    public CrowTitReqDto(@JsonProperty("crowTitId") int crowTitId){
        this.crowTitId = crowTitId;
    }
}
