package com.zootopia.letterservice.common.error.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // User Token 만료
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "Token 시간이 만료되었습니다."),

    // S3
    FAIL_UPLOAD_FILE(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 장애로 s3에 파일에 업로드할 때 오류가 발생하였습니다."),
    FAIL_DELETE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "S3에 업로드 된 파일을 지울 때 오류가 발생하였습니다."),

    // Letter
    NOT_EXISTS_CONTENT(HttpStatus.BAD_REQUEST, 4000, "편지 내용 파일은 필수 항목입니다."),
    INVALID_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, 4001, "지원하지 않는 이미지 파일 확장자입니다."),
    NOT_EXISTS_RECEIVER_ID(HttpStatus.BAD_REQUEST, 4002, "수신자 ID는 필수 항목 입니다."),
    NOT_EXISTS_TEXT(HttpStatus.BAD_REQUEST, 4003, "텍스트 편지 생성시 사용자 입력 텍스트는 필수 항목입니다."),
    EXISTS_FORBIDDEN_WORD(HttpStatus.BAD_REQUEST, 4004, "편지에 금칙어가 포함되어 있습니다. 금칙어를 제외하고 작성해주세요."),
    NOT_EQUAL_SENDER(HttpStatus.BAD_REQUEST, 4005, "편지 작성자와 요청을 보낸 사용자가 일치하지 않습니다."),
    NOT_EXISTS_LETTER(HttpStatus.BAD_REQUEST, 4006, "존재하지 않는 편지입니다."),
    NOT_EXISTS_LAT_OR_LNG(HttpStatus.BAD_REQUEST, 4007, "위도, 경도는 필수 항목입니다."),
    NOT_EXISTS_PLACE_IMAGES(HttpStatus.BAD_REQUEST, 4008, "배치 장소에 대한 이미지 파일은 필수 항목입니다."),
    NOT_EQUAL_SENDER_OR_RECEIVER(HttpStatus.BAD_REQUEST, 4009, "편지의 발신자와 수신자가 아닌 사용자는 상세 내용을 조회할 수 없습니다."),
    NOT_EQUAL_RECEIVER(HttpStatus.BAD_REQUEST, 4011, "편지의 수신자가 아니면 편지를 주울 수 없습니다."),


    // API 통신
    INVALID_USER_REQUEST(HttpStatus.BAD_REQUEST, 4010, "유효하지 않은 사용자의 요청입니다."),
    UNSTABLE_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "서버 오류입니다. 잠시 후에 다시 시도해주세요.");

    private HttpStatus httpStatus;
    private Integer errorCode;
    private String message;

    ErrorCode(HttpStatus httpStatus, Integer errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
