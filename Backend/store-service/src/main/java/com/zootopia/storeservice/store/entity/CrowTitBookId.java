package com.zootopia.storeservice.store.entity;

import java.io.Serializable;

public class CrowTitBookId implements Serializable {
    private int crowTitId;
    private String memberId;

    public CrowTitBookId() {
    }

    public CrowTitBookId(int crowTitId, String memberId){
        this.crowTitId = crowTitId;
        this.memberId = memberId;
    }
}