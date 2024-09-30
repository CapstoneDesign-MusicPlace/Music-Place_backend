package org.musicplace.global.security.oauth;

import lombok.RequiredArgsConstructor;
import org.musicplace.global.security.jwt.JwtTokenUtil;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.repository.SignInRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SignInRepository signInRepository;
    private final JwtTokenUtil jwtTokenUtil;  // JWT 토큰 유틸리티 추가

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = userRequest.getAdditionalParameters();
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 'sub' 및 'email' 필드가 존재하는지 확인하고, 예외 처리
        Object providerIdObj = attributes.get("sub");
        Object emailObj = attributes.get("email");

        if (providerIdObj == null || emailObj == null) {
            throw new OAuth2AuthenticationException("필수 사용자 정보가 제공되지 않았습니다.");
        }

        String providerId = providerIdObj.toString();
        String email = emailObj.toString();
        String name = attributes.get("name") != null ? attributes.get("name").toString() : "Unknown";

        Optional<SignInEntity> userOptional = signInRepository.findByEmail(email);
        SignInEntity user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            // 새로운 사용자 저장
            user = SignInEntity.builder()
                    .member_id(providerId)
                    .email(email)
                    .name(name)
                    .nickname(name)
                    .role("ROLE_USER")
                    .oauthProvider(provider)
                    .oauthProviderId(providerId)
                    .build();
            signInRepository.save(user);
        }

        // JWT 토큰 발급
        String jwtToken = jwtTokenUtil.generateToken(user.getUsername());

        // 사용자 정보와 JWT 토큰 반환
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Map<String, Object> extendedAttributes = new HashMap<>(attributes);
        extendedAttributes.put("jwtToken", jwtToken);  // JWT 토큰 추가

        return new DefaultOAuth2User(authorities, extendedAttributes, "name");
    }
}
