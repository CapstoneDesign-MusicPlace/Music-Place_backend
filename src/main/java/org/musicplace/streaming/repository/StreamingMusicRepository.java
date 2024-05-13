package org.musicplace.streaming.repository;

import org.musicplace.streaming.domain.StreamingMusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamingMusicRepository extends JpaRepository<StreamingMusicEntity, Long> {
}
