package org.musicplace.recommend.repository;

import org.musicplace.recommend.domain.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<RecommendEntity, String> {
}