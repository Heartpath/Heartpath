package com.zootopia.userservice.jwt;

import com.zootopia.userservice.exception.JwtException;
import com.zootopia.userservice.util.DateUtil;
import com.zootopia.userservice.util.JwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

import static com.zootopia.userservice.exception.JwtErrorCode.*;


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
        long durationOfOneDay = DateUtil.toDays(2);
        ONE_DAY = DateUtil.toDate(durationOfOneDay);

        long durationOfSevenDays = DateUtil.toDays(refreshExpiration);
        SEVEN_DAYS = DateUtil.toDate(durationOfSevenDays);
    }

    public void validateToken(String token) throws JwtException {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            // 유효 기간이 지난 토큰
            log.error("JWT 유효 기간 경과");
            throw new JwtException(EXPIRED_TOKEN);
        } catch (CompressionException | MalformedJwtException | UnsupportedJwtException | SignatureException e) {
            // 압축 오류, 키 틀림 오류, 해당 토큰과 맞지 않는 토큰 타입 오류
            log.error("JWT ERROR: {}", e.toString());
            throw new JwtException(INVALID_TOKEN);
        }
    }

    public String createAccessToken(String memberID) {

        String accessToken = Jwts.builder()
                .setHeaderParams(JwtUtil.getHeaderMap())
                .setIssuedAt(DateUtil.getCurrentDate())
                .setExpiration(ONE_DAY)
                .claim("memberID", memberID)
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
                .claim("memberID", memberID)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        log.info("Create RefreshToken: {}", refreshToken);

        return refreshToken;
    }

    public String getMemberIDFromToken(String token) {

        String nickname;

        try {
            nickname = (String) Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().get("memberID");
        } catch (NullPointerException e) {
            log.error("Token에서 MemberID를 찾을 수 없습니다.");
            nickname = "";
        }

        return nickname;
    }
}
