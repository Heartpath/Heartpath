package com.zootopia.userservice.firebase;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor
public class FCMMessage {

    private boolean validateOnly;
    private Message message;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Message {
        private Data data;
        private String token;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Data {
        private String title;
        private String body;
    }
}
