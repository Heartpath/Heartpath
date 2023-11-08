package com.zootopia.userservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class KakaoOAuthUtil {

    private final String PREFIX;
    private final String REQUEST_URL;

    public KakaoOAuthUtil(@Value("${kakao.prefix}") String prefix,
                          @Value("${kakao.url}") String url
    ) {
        this.PREFIX = prefix;
        this.REQUEST_URL = url;
    }

    public HttpHeaders getKakaoHttpHeaders(final String ACCESS_TOKEN) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", PREFIX.concat(ACCESS_TOKEN));
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return headers;
    }

    public String getRequestURL() {
        return REQUEST_URL;
    }
}
