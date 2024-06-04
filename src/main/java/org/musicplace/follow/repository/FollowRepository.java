package org.musicplace.follow.repository;

import org.musicplace.follow.domain.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
}
