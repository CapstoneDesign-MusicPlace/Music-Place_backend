package org.musicplace.playList.repository;

import org.musicplace.playList.domain.PLEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PLRepository extends JpaRepository<PLEntity,Long> {
}
