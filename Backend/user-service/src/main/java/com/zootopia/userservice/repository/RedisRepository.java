package com.zootopia.userservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public <T> void saveData(String key, T data) {

        try {
            // 데이터 직렬화
            String value = objectMapper.writeValueAsString(data);
            // Redis에 Key-Value 쌍으로 넣기
            redisTemplate.opsForValue().set(key, value);
        } catch (JsonProcessingException e) {
            log.error("Error Occurs when saving data in Redis");
            log.error(e.toString());
        }
    }

    public <T> Optional<T> getData(String key, Class<T> classType) {

        // 반환값
        Optional<T> res = Optional.empty();

        // Redis에서 Key로 데이터 찾기
        Optional<String> findData = Optional.ofNullable(redisTemplate.opsForValue().get(key));

        // 데이터가 있을 경우
        if (findData.isPresent()) {

            try {
                String data = findData.get();
                res = Optional.of(objectMapper.readValue(data, classType));
            } catch (JsonProcessingException e) {
                log.error("Error Occurs when deserializing data From Redis");
                log.error(e.toString());
            }
        }

        return res;
    }
}
