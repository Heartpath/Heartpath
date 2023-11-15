package com.zootopia.userservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zootopia.userservice.common.ErrorResponse;
import com.zootopia.userservice.exception.JwtException;
import com.zootopia.userservice.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.zootopia.userservice.exception.JwtErrorCode.NOT_SUPPORTED_TOKEN;
import static com.zootopia.userservice.exception.JwtErrorCode.TOKEN_NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@Component
@RequiredArgsConstructor
public class VerifyJwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private static String getURL(HttpServletRequest req) {

        String method = req.getMethod();
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();
        String queryString = req.getQueryString();
        String remoteAddr = req.getRemoteAddr();

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append("[").append(method).append("]").append(" ");

        url.append(contextPath).append(servletPath);

        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }

        url.append(" from ").append(remoteAddr);

        return url.toString();
    }

    // TODO: yaml 파일로 빼기
    private static final String[] EXCLUDED_URLS = {
            "/user/health_check", "/api/fcm",
            "/user/login", "/user/register", "/user/check", "/user/token",
            "/api/user/", "/api/token", "/api/point/",
            "/swagger-ui", "/v3/api-docs"
    };

    private String extractJwtFromHeader(Optional<String> authorizationToken) throws JwtException {

        if (authorizationToken.isEmpty()) {
            throw new JwtException(TOKEN_NOT_FOUND);
        }

        String accessToken = authorizationToken.get();
        if (!accessToken.startsWith("Bearer ")) {
            throw new JwtException(NOT_SUPPORTED_TOKEN);
        }

        accessToken = accessToken.substring(7);

        return accessToken;
    }

    private void setErrorResponse(
            HttpServletResponse response,
            JwtException error
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(error.getJwtErrorCode().getHttpStatus().value());
        response.setContentType(APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = ErrorResponse.of(
                error.getJwtErrorCode().getHttpStatus().toString(),
                error.getJwtErrorCode().getHttpStatus().value(),
                error.getJwtErrorCode().getMessage()
        );

        try {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        log.info("Request : {}", getURL(request));

        /*
        WhiteList
        Check if the request URI matches any of the excluded URLs
        */
        String requestURI = request.getRequestURI();
        for (String excludedUrl : EXCLUDED_URLS) {

            if (requestURI.startsWith(excludedUrl)) {
                // Skip JWT validation for excluded URLs
                filterChain.doFilter(request, response);
                return;
            }
        }

        // JWT 확인
        try {

            Optional<String> authorizationToken = Optional.ofNullable(request.getHeader("Authorization"));
            String accessToken = extractJwtFromHeader(authorizationToken);

            // JWT 검증
            jwtProvider.validateToken(accessToken);
        } catch (JwtException e) {
            log.error(e.toString());
            setErrorResponse(response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
