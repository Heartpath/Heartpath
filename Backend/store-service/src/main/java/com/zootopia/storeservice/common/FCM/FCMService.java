package com.zootopia.storeservice.common.FCM;

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

    public void sendFCMNotification(String token, String body) {
        try {

            Map<String, String> data = new HashMap<>();

            // FCM 데이터 입력
            data.put("title", "편지 도착 알림");
            data.put("message", body); // "{사용자 id}님이 나에게 편지를 보냈어요!"

            Message message = Message.builder()
                    .putAllData(data)
                    .setToken(token)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
