package org.musicplace.global.security.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.global.security.dto.LoginResponseDto;
import org.musicplace.global.security.jwt.JwtTokenUtil;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.dto.LoginRequestDto;
import org.musicplace.member.service.SignInService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final SignInService signInService;

    // 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        SignInEntity user = signInService.authenticate(loginRequestDto.getMember_id(), loginRequestDto.getPw());
        String token = jwtTokenUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    // 로그아웃 기능
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization") String token) {
        String actualToken = token.substring(7);  // "Bearer " 제거
        String username = jwtTokenUtil.getUsernameFromToken(actualToken);

        // 토큰 무효화 로직 호출
        jwtTokenUtil.invalidateToken(username, actualToken);

        // 현재 보안 컨텍스트 제거
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
