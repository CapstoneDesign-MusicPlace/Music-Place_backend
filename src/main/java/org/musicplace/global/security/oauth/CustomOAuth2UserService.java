package org.musicplace.global.security.oauth;

import lombok.RequiredArgsConstructor;
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
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SignInRepository signInRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2 공급자에서 받은 사용자 정보
        Map<String, Object> attributes = userRequest.getAdditionalParameters(); // 수정된 부분
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google, facebook 등
        String providerId = attributes.get("sub").toString(); // 사용자의 고유 ID

        String email = attributes.get("email").toString();
        String name = attributes.get("name").toString();

        // 이미 존재하는 사용자면 로그인 처리, 아니면 새로 저장
        Optional<SignInEntity> userOptional = signInRepository.findByEmail(email);
        SignInEntity user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            // 새로운 OAuth 사용자 등록
            user = SignInEntity.builder()
                    .member_id(providerId) // OAuth2 provider에서의 고유 ID
                    .email(email)
                    .name(name)
                    .nickname(name) // 초기에는 이름을 닉네임으로 설정
                    .role("ROLE_USER") // 기본 권한
                    .oauthProvider(provider)
                    .oauthProviderId(providerId)
                    .build();
            signInRepository.save(user);
        }

        // DefaultOAuth2User 생성 시 권한 정보도 포함
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new DefaultOAuth2User(authorities, attributes, "name");
    }
}
