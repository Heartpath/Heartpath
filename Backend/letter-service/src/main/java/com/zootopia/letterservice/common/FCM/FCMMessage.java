package com.zootopia.letterservice.common.FCM;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
public class FCMMessage {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification{
        private String title;
        private String body;
    }

}
