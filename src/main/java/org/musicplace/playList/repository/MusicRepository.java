package org.musicplace.playList.repository;

import org.musicplace.playList.domain.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<MusicEntity,Long> {
}
