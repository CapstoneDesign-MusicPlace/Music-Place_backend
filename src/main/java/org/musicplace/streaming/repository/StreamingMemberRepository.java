package org.musicplace.streaming.repository;

import org.musicplace.streaming.domain.StreamingMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamingMemberRepository extends JpaRepository<StreamingMemberEntity, Long> {
}
