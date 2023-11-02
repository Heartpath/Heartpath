package com.zootopia.letterservice.common.error.code;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StoreErrorCode {
    // LetterPaper
    NOT_EXISTS_LETTERPAPER(HttpStatus.BAD_REQUEST, "LP-001", "존재하지 않는 편지지 입니다."),

    // CrowTit
    NOT_EXISTS_CROWTIT(HttpStatus.BAD_REQUEST, "CT-001", "존재하지 않는 뱁새입니다."),
    FAIL_SEND_TO_MEMBER_CROWTIT(HttpStatus.INTERNAL_SERVER_ERROR, "CT-002", "멤버서버로 뱁새 정보 전송에 실패하였습니다."),

    // s3 임시
    FAIL(HttpStatus.BAD_REQUEST, "S-001", "뷁 실패!");

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    StoreErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
