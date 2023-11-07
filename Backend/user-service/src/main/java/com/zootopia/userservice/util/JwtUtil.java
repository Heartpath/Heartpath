package com.zootopia.userservice.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class JwtUtil {

    @Getter
    private static final HashMap<String, Object> headerMap = new HashMap<>() {{
        put("alg", "HS256");
        put("typ", "JWT");
    }};
}
