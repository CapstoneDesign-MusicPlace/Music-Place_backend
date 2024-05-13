package org.musicplace.streaming.repository;

import org.musicplace.streaming.domain.StreamingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamingRepository extends JpaRepository<StreamingEntity, Long> {
}
