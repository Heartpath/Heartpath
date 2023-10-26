package com.zootopia.letterservice.common.error.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // S3
    FAIL_UPLOAD_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "S3-001", "서버 장애로 s3에 파일에 업로드할 때 오류가 발생하였습니다."),
    FAIL_DELETE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "S3-002", "S3에 업로드 된 파일을 지울 때 오류가 발생하였습니다."),

    // Letter
    NOT_EXISTS_CONTENT(HttpStatus.BAD_REQUEST, "L-001", "편지 내용 파일은 필수 항목입니다."),
    INVALID_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, "L-002", "지원하지 않는 이미지 파일 확장자입니다.");

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
