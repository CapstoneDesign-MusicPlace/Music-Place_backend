package org.musicplace.playList.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.playList.domain.MusicEntity;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.MusicSaveDto;
import org.musicplace.playList.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final PLService plService;

    @Transactional
    public Long MusicSave(Long PLId, MusicSaveDto musicSaveDto) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        MusicEntity musicEntity = MusicEntity.builder()
                .title(musicSaveDto.getTitle())
                .singer(musicSaveDto.getSinger())
                .build();
        musicEntity.setPlEntity(plEntity);
        plEntity.getMusicEntities().add(musicEntity);
        musicRepository.save(musicEntity);
        return musicEntity.getMusic_id();
    }

    @Transactional
    public boolean MusicDelete(Long PLId, Long MusicId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        MusicEntity musicEntity = MusicFindById(plEntity,MusicId);
        checkMusicDeleteStatus(musicEntity);
        musicEntity.delete();
        return musicEntity.isDelete();
    }

    public List<MusicEntity> MusicFindAll(Long PLId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        List<MusicEntity> nonDeletedMusic = plEntity.getMusicEntities()
                .stream()
                .filter(music -> !music.isDelete())
                .toList();
        return nonDeletedMusic;
    }

    public MusicEntity MusicFindById(PLEntity plEntity, Long MusicId) {
        MusicEntity musicEntity = plEntity.getMusicEntities()
                .stream()
                .filter(music -> music.getMusic_id().equals(MusicId))
                .findFirst()
                .orElse(null);
        if (musicEntity == null) {
            throw new ExceptionHandler(ErrorCode.ID_NOT_FOUND);
        }
        return musicEntity;
    }

    public void checkMusicDeleteStatus(MusicEntity musicEntity) {
        if (musicEntity.isDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }
}
