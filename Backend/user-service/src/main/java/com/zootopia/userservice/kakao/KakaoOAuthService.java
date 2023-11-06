package com.zootopia.userservice.kakao;

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
}
