package org.musicplace.playList.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.playList.domain.MusicEntity;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.MusicSaveDto;
import org.musicplace.playList.dto.ResponseMusicDto;
import org.musicplace.playList.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .vidioTitle(musicSaveDto.getVidioTitle())
                .vidioId(musicSaveDto.getVidioId())
                .vidioImage(musicSaveDto.getVidioImage())
                .build();
        musicEntity.setPlEntity(plEntity);
        plEntity.getMusicEntities().add(musicEntity);
        musicRepository.save(musicEntity);
        return musicEntity.getMusic_id();
    }

    @Transactional
    public boolean MusicDelete(Long PLId, List<Long> MusicIds) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        boolean allDeleted = true;
        for (Long musicId : MusicIds) {
            MusicEntity musicEntity = MusicFindById(plEntity, musicId);
            checkMusicDeleteStatus(musicEntity);
            musicEntity.delete();
            allDeleted &= musicEntity.isMusicDelete();
        }

        return allDeleted;
    }

    public List<ResponseMusicDto> MusicFindAll(Long PLId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);

        List<ResponseMusicDto> nonDeletedMusic = plEntity.getMusicEntities()
                .stream()
                .filter(music -> !music.isMusicDelete())
                .map(music -> ResponseMusicDto.builder()
                        .music_id(music.getMusic_id())
                        .vidioId(music.getVidioId())
                        .vidioImage(music.getVidioImage())
                        .vidioTitle(music.getVidioTitle())
                        .build())
                .collect(Collectors.toList()); // collect로 리스트로 변환

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
        if (musicEntity.isMusicDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }


}
