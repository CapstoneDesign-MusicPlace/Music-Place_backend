package org.musicplace.chat.interceptor;

import lombok.RequiredArgsConstructor;
import org.musicplace.chat.security.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // HTTP 요청 헤더에서 Authorization 헤더 추출
        String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Authorization 헤더에서 JWT 토큰 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 부분 제거

            // JWT 토큰 유효성 검사
            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                attributes.put("username", username);  // 인증된 사용자 이름을 WebSocket 세션에 저장
                return true;  // 인증 성공 시 WebSocket 연결 허용
            }
        }

        // 인증 실패 시 HTTP 401 응답
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 인증 후 후처리가 필요한 경우 여기에 추가
    }
}
