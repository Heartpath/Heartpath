package com.zootopia.letterservice.common.FCM;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {
    public void sendFCM(String token, String title, String body) {
        try{
            Map<String, String> data = new HashMap<>();

            data.put("title", title);
            data.put("message", body);

            System.out.println("title :" + title + "body :" + body);

            Message message = Message.builder()
                    .putAllData(data)
                    .setToken(token)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
}
