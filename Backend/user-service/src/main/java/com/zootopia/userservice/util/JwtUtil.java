package com.zootopia.userservice.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@Component
public class JwtUtil {

    @Getter
    private static final HashMap<String, Object> headerMap = new HashMap<>() {{
        put("alg", "HS256");
        put("typ", "JWT");
    }};

    public static String getTokenFromHeader(HttpServletRequest request) {

        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.substring(7);

        return accessToken;
    }
}
