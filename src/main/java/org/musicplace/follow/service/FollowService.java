package org.musicplace.follow.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.follow.domain.FollowEntity;
import org.musicplace.follow.dto.FollowSaveDto;
import org.musicplace.follow.dto.FollowResponseDto;
import org.musicplace.follow.repository.FollowRepository;
import org.musicplace.global.security.authorizaion.MemberAuthorizationUtil;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.service.SignInService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final SignInService signInService;

    @Transactional
    public Long FollowSave(FollowSaveDto followSaveDto) {
        String member_id = MemberAuthorizationUtil.getLoginMemberId();
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        signInService.CheckSignInDelete(signInEntity);
        FollowCheck(followSaveDto.getTarget_id(), signInEntity);
        FollowEntity followEntity = FollowEntity.builder()
                .target_id(followSaveDto.getTarget_id())
                .nickname(followSaveDto.getNickname())
                .profile_img_url(followSaveDto.getProfile_img_url())
                .build();
        signInEntity.getFollowEntities().add(followEntity);
        followEntity.SignInEntity(signInEntity);
        followRepository.save(followEntity);
        return followEntity.getFollow_id();
    }

    @Transactional
    public void followDelete(Long follow_id) {
        String member_id = MemberAuthorizationUtil.getLoginMemberId();
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        FollowEntity followEntity = followFindById(follow_id);
        if (signInEntity.getFollowEntities().contains(followEntity)) {
            signInEntity.getFollowEntities().remove(followEntity);
            followRepository.delete(followEntity);
        } else {
            throw new ExceptionHandler(ErrorCode.FOLLOW_NO_ID);
        }
    }

    public List<FollowResponseDto> followFindAll() {
        String member_id = MemberAuthorizationUtil.getLoginMemberId();
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        List<FollowEntity> followEntities = signInEntity.getFollowEntities();
        List<FollowResponseDto> followResponseDtos = followEntities.stream()
                .map(followEntity -> FollowResponseDto.builder()
                        .follow_id(followEntity.getFollow_id())
                        .target_id(followEntity.getTarget_id())
                        .nickname(followEntity.getNickname())
                        .profile_img_url(followEntity.getProfile_img_url())
                        .build())
                .collect(Collectors.toList());
        return followResponseDtos;
    }

    public Long followCount() {
        String member_id = MemberAuthorizationUtil.getLoginMemberId();
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        return signInEntity.getFollowEntities().stream().count();
    }

    public Long otherFollowCount(String otherMemberId) {
        SignInEntity signInEntity = signInService.SignInFindById(otherMemberId);
        return signInEntity.getFollowEntities().stream().count();
    }

    public FollowEntity followFindById(Long target_id) {
        FollowEntity followEntity = followRepository.findById(target_id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.FOLLOW_NOT_FOUND));
        return followEntity;
    }

    public void FollowCheck(String targetId, SignInEntity signInEntity) {
        List<FollowEntity> followEntities = signInEntity.getFollowEntities();

        for (FollowEntity target : followEntities) {
            if (target.getTarget_id().equals(targetId)) {
                throw new ExceptionHandler(ErrorCode.FOLLOW_SAME_ID);
            }
        }

        if (signInEntity.getMemberId().equals(targetId)) {
            throw new ExceptionHandler(ErrorCode.NOT_FOLLOW_SELF);
        }
    }


}
