package org.musicplace.global.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.Jwts.claims;

@Component
public class JwtTokenUtil {

    @Value("${JWT_SECRET}")
    private String secretKey;

    @Value("${JWT_ACCESS_TIME}")
    private long expirationTime;

    // 유효하지 않은 토큰을 저장할 맵 (예시로 메모리 저장)
    private Map<String, String> invalidatedTokens = new HashMap<>();

    // JWT 생성
    public String generateToken(String memberId) {
        return Jwts.builder()
                .setSubject(memberId)  // memberId를 subject에 설정
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // JWT에서 사용자 memberId 추출
    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired");
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token");
        }
    }

    // 토큰 검증
    public boolean validateToken(String token, String memberId) {
        return memberId.equals(getUserIdFromToken(token))
                && !isTokenExpired(token)
                && !isTokenInvalidated(token);
    }

    // 토큰이 만료되었는지 확인
    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    // 토큰 무효화
    public void invalidateToken(String memberId, String token) {
        invalidatedTokens.put(memberId, token);  // 사용자별로 토큰 무효화
    }

    // 토큰이 무효화되었는지 확인
    private boolean isTokenInvalidated(String token) {
        return invalidatedTokens.containsValue(token);
    }
}
