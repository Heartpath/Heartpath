package com.zootopia.letterservice.common.FCM;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/heartpath-590b0/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);
        System.out.println("makeMessage : " + message);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
        System.out.println("1");
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("2");
                System.out.println(response.body().string());
                log.debug("전송 성공");
            } else {
                System.out.println("3");
                log.error("전송 실패: {} - {}", response.code(), response.message());
            }
        } catch (IOException e) {
            System.out.println("4");
            log.error("FCM 서버 요청 중 예외 발생", e);
            throw e; // 예외를 상위로 전파
        }
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
        FCMMessageDto fcmMessage = FCMMessageDto.builder()
                .message(FCMMessageDto.Message.builder()
                        .token(targetToken)
                        .notification(FCMMessageDto.Notification.builder()
                                .title(title)
                                .body(body)
                                .build()
                        ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        InputStream is = getClass().getResourceAsStream("/firebase/heartpath-adminsdk.json");

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(is)
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
