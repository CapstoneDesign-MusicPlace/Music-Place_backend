package org.musicplace.follow.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.follow.domain.FollowEntity;
import org.musicplace.follow.dto.FollowSaveDto;
import org.musicplace.follow.dto.ResponseDto;
import org.musicplace.follow.repository.FollowRepository;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.repository.SignInRepository;
import org.musicplace.member.service.SignInService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final SignInService signInService;

    @Transactional
    public Long FollowSave(FollowSaveDto followSaveDto, String member_id) {
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        signInService.CheckSignInDelete(signInEntity);
        FollowSameCheck(followSaveDto.getTarget_id(), signInEntity);
        FollowEntity followEntity = FollowEntity.builder()
                .target_id(followSaveDto.getTarget_id())
                .build();
        signInEntity.getFollowEntities().add(followEntity);
        followEntity.SignInEntity(signInEntity);
        followRepository.save(followEntity);
        return followEntity.getFollow_id();

    }

    @Transactional
    public void followDelete(Long follow_id) {
        FollowEntity followEntity = followFindById(follow_id);
        followRepository.delete(followEntity);
    }

    public List<ResponseDto> followFindAll(String member_id) {
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        List<FollowEntity> followEntities = signInEntity.getFollowEntities();
        List<ResponseDto> responseDtos = followEntities.stream()
                .map(followEntity -> ResponseDto.builder()
                        .follow_id(followEntity.getFollow_id())
                        .target_id(followEntity.getTarget_id())
                        .build())
                .collect(Collectors.toList());
        return responseDtos;
    }

    public FollowEntity followFindById(Long target_id) {
        FollowEntity followEntity = followRepository.findById(target_id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        return followEntity;
    }

    public void FollowSameCheck(String tartgetId, SignInEntity signInEntity) {
        List<FollowEntity> followEntities = signInEntity.getFollowEntities();
        for(FollowEntity target : followEntities) {
            if(target.getTarget_id().equals(tartgetId)) {
                new ExceptionHandler(ErrorCode.FOLLOW_SAME_ID);
            }
        }
    }


}
