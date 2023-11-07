package com.zootopia.userservice.jwt;

import com.zootopia.userservice.util.DateUtil;
import com.zootopia.userservice.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;


@Slf4j
@Component
public class JwtProvider {

    private final Key secretKey;

    private final int jwtExpiration;
    private final int refreshExpiration;

    @Autowired
    public JwtProvider(@Value("${jwt.secret-key}") String secretKey,
                       @Value("${jwt.expiration}") int jwtExpiration,
                       @Value("${jwt.refresh-token.expiration}") int refreshExpiration
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        this.jwtExpiration = jwtExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    private Date ONE_DAY;
    private Date SEVEN_DAYS;

    @PostConstruct
    public void calculateDate() {
        long durationOfOneDay = DateUtil.toDays(jwtExpiration);
        ONE_DAY = DateUtil.toDate(durationOfOneDay);

        long durationOfSevenDays = DateUtil.toDays(refreshExpiration);
        SEVEN_DAYS = DateUtil.toDate(durationOfSevenDays);
    }

    public String createAccessToken(String memberID) {

        String accessToken = Jwts.builder()
                .setHeaderParams(JwtUtil.getHeaderMap())
                .setIssuedAt(DateUtil.getCurrentDate())
                .setExpiration(ONE_DAY)
                .claim("email", memberID)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        log.info("Create AccessToken: {}", accessToken);

        return accessToken;
    }

    public String createRefreshToken(String memberID) {

        String refreshToken = Jwts.builder()
                .setHeaderParams(JwtUtil.getHeaderMap())
                .setIssuedAt(DateUtil.getCurrentDate())
                .setExpiration(SEVEN_DAYS)
                .claim("email", memberID)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        log.info("Create RefreshToken: {}", refreshToken);

        return refreshToken;
    }
}
