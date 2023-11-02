package com.zootopia.storeservice.common.error.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LetterErrorCode {
    // S3
    FAIL_UPLOAD_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "S3-001", "서버 장애로 s3에 파일에 업로드할 때 오류가 발생하였습니다."),
    FAIL_DELETE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "S3-002", "S3에 업로드 된 파일을 지울 때 오류가 발생하였습니다."),

    // Letter
    NOT_EXISTS_CONTENT(HttpStatus.BAD_REQUEST, "L-001", "편지 내용 파일은 필수 항목입니다."),
    INVALID_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, "L-002", "지원하지 않는 이미지 파일 확장자입니다."),
    NOT_EXISTS_RECEIVER_ID(HttpStatus.BAD_REQUEST, "L-003", "수신자 ID는 필수 항목 입니다."),
    NOT_EXISTS_TEXT(HttpStatus.BAD_REQUEST, "L-004", "텍스트 편지 생성시 사용자 입력 텍스트는 필수 항목입니다."),
    EXISTS_FORBIDDEN_WORD(HttpStatus.BAD_REQUEST, "L-005", "편지에 금칙어가 포함되어 있습니다. 금칙어를 제외하고 작성해주세요."),
    NOT_EQUAL_USER(HttpStatus.BAD_REQUEST, "L-006", "편지 작성자와 요청을 보낸 사용자가 일치하지 않습니다."),
    NOT_EXISTS_LETTER(HttpStatus.BAD_REQUEST, "L-007", "존재하지 않는 편지입니다."),
    NOT_EXISTS_LAT_OR_LNG(HttpStatus.BAD_REQUEST, "L-008", "위도, 경도는 필수 항목입니다."),
    NOT_EXISTS_PLACE_IMAGES(HttpStatus.BAD_REQUEST, "L-009", "배치 장소에 대한 이미지 파일은 필수 항목입니다.");

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    LetterErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
