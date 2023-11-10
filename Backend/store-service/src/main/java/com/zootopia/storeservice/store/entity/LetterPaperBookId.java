package com.zootopia.storeservice.store.entity;

import java.io.Serializable;

public class LetterPaperBookId implements Serializable {
    private int letterPaperId;
    private String memberId;

    public LetterPaperBookId() {
    }

    public LetterPaperBookId(int letterpaperId, String memeberId) {
        this.letterPaperId = letterpaperId;
        this.memberId = memeberId;
    }
}
