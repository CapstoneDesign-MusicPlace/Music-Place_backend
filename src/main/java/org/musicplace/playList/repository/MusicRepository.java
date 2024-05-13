package org.musicplace.playList.repository;

import org.musicplace.playList.domain.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<MusicEntity,Long> {
}
