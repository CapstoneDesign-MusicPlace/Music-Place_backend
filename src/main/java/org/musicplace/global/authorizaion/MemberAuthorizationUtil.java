package org.musicplace.global.authorizaion;

import org.musicplace.member.domain.SignInEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MemberAuthorizationUtil {

    private MemberAuthorizationUtil() {
        throw new AssertionError();
    }
    public static String getLoginMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SignInEntity userDetails = (SignInEntity) authentication.getPrincipal();

        return userDetails.getMember_id();
    }
}
