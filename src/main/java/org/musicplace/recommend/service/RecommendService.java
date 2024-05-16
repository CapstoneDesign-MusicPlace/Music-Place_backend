package org.musicplace.recommend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.recommend.domain.RecommendEntity;
import org.musicplace.recommend.dto.RecommendSaveDto;
import org.musicplace.recommend.dto.RecommendUpdateDto;
import org.musicplace.recommend.repository.RecommendRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;

    @Transactional
    public void RecommendSave(RecommendSaveDto recommendSaveDto) {
        recommendRepository.save(RecommendEntity.builder()
                .recommend_id(recommendSaveDto.getRecommend_id())
                .genre(recommendSaveDto.getGenre())
                .thema(recommendSaveDto.getThema())
                .singer(recommendSaveDto.getSinger())
                .build());
    }

    @Transactional
    public void RecommendUpdate(RecommendUpdateDto recommendUpdateDto) {

    }
}