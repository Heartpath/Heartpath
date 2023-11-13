package com.zootopia.letterservice.common.FCM;

import com.google.firebase.messaging.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {

    public void sendFCMNotification(String token, String title, String body) {
        try {
            Map<String, String> data = new HashMap<>();

            // FCM 데이터 입력
            data.put("title", title);
            data.put("message", body);

            Message message = Message.builder()
                    .putAllData(data)
                    .setToken(token)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
