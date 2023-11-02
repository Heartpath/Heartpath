package com.zootopia.letterservice.common.FCM;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
public class FCMMessageDto {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private Notification notification;
        private Data data;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification{
        private String title;
        private String body;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data {
        // 모바일 측에서 처리할 데이터 data 객체 내에 담아 전송
    }

}
