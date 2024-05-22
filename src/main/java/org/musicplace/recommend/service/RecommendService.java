package org.musicplace.recommend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.repository.SignInRepository;
import org.musicplace.member.service.SignInService;
import org.musicplace.recommend.domain.RecommendEntity;
import org.musicplace.recommend.dto.RecommendSaveDto;
import org.musicplace.recommend.dto.RecommendUpdateDto;
import org.musicplace.recommend.repository.RecommendRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;
    private final SignInService signInService;

    @Transactional
    public Long RecommendSave(RecommendSaveDto recommendSaveDto, String member_id) {
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        signInService.CheckSignInDelete(signInEntity);
        RecommendEntity recommendEntity = RecommendEntity.builder()
                .genre(recommendSaveDto.getGenre())
                .thema(recommendSaveDto.getThema())
                .singer(recommendSaveDto.getSinger())
                .build();
        signInEntity.getRecommendEntities().add(recommendEntity);
        recommendEntity.SignInEntity(signInEntity);
        recommendRepository.save(recommendEntity);
        return recommendEntity.getRecommend_id();
    }

    @Transactional
    public void RecommendUpdate(RecommendUpdateDto recommendUpdateDto) {

    }
}