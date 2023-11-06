package com.zootopia.userservice.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.zootopia.userservice.util.KakaoOAuthUtil;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    private final KakaoOAuthUtil kakaoOAuthUtil;

    /**
     * 카카오 서버 요청 보내기
     *
     * @param kakaoAccessToken 카카오 액세스 토큰
     * @return String
     */
    private String requestUserInfoToKakao(String kakaoAccessToken) {

        final String REQUEST_URL = kakaoOAuthUtil.getRequestURL();

        // Kakao Request Header 생성
        HttpHeaders kakaoHttpHeaders = kakaoOAuthUtil.getKakaoHttpHeaders(kakaoAccessToken);
        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(kakaoHttpHeaders);

        // Kakao Request 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> kakaoResponse = restTemplate.exchange(
                REQUEST_URL,
                HttpMethod.POST,
                kakaoRequest,
                String.class
        );

        // Kakao Response 받기
        String responseBody = kakaoResponse.getBody();
        log.info("ResponseBody: {} \n from Token: {}", responseBody, kakaoAccessToken);

        return responseBody;
    }

    /**
     * 카카오 서버로부터 받은 사용자 정보 Parsing
     *
     * @param kakaoToken 카카오 토큰
     * @return KakaoUserInfo 파싱된 사용자 정보
     */
    private KakaoUserInfo getUserInfoFromKakao(String kakaoToken) {

        // 카카오 서버에게 요청 보내고 반환값 받기
        String kakaoResponseBody = requestUserInfoToKakao(kakaoToken);

        // deserialize JSON content
        JsonNode jsonNode = null;
        try {
            jsonNode = new ObjectMapper().readTree(kakaoResponseBody);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        // parsing JSON content
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo();
        kakaoUserInfo.fillDataFromJson(jsonNode);
        log.info("파싱된 카카오 유저 정보: {}", kakaoUserInfo);

        return kakaoUserInfo;
    }
}
