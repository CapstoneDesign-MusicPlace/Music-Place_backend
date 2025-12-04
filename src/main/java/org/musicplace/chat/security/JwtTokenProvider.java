package org.musicplace.chat.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${JWT_SECRET}")  // application.properties 또는 application.yml에 설정된 비밀 키
    private String secretKey;

    @Value("${JWT_ACCESS_TIME}")  // JWT 만료 시간 설정
    private long expirationTime;

    // JWT에서 사용자 이름을 추출
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    // 토큰이 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    // JWT 유효성 검사
    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;  // 토큰이 유효하지 않거나 만료된 경우
        }
    }

    // 토큰에서 클레임 추출
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
