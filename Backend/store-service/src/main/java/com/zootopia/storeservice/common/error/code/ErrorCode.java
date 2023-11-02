package com.zootopia.storeservice.common.error.code;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // S3
    FAIL_UPLOAD_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "S3-001", "서버 장애로 s3에 파일에 업로드할 때 오류가 발생하였습니다."),
    FAIL_DELETE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "S3-002", "S3에 업로드 된 파일을 지울 때 오류가 발생하였습니다."),

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

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
