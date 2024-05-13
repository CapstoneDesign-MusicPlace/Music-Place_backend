package org.musicplace.streaming.repository;

import org.musicplace.streaming.domain.StreamingChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamingChatRepository extends JpaRepository<StreamingChatEntity, Long> {
}
