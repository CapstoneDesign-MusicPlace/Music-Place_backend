package org.musicplace.playList.Service;

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
    public void MusicSave(Long PLId, MusicSaveDto musicSaveDto) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        plEntity.MusicSave(MusicEntity.builder()
                .title(musicSaveDto.getTitle())
                .singer(musicSaveDto.getSinger())
                .build());
    }

    @Transactional
    public void MusicDelete(Long PLId, Long MusicId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        MusicEntity musicEntity = MusicFindById(MusicId);
        checkMusicDeleteStatus(musicEntity);
        musicEntity.delete();
    }

    public List<MusicEntity> MusicFindAll(Long PLId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        List<MusicEntity> AllMusic = musicRepository.findAll();
        List<MusicEntity> nonDeletedMusic = new ArrayList<>();
        for(MusicEntity Music : AllMusic)  {
            if(!Music.isDelete()) {
                nonDeletedMusic.add(Music);
            }
        }
        return nonDeletedMusic;
    }


    public MusicEntity MusicFindById(Long id) {
        MusicEntity musicEntity = musicRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        return musicEntity;
    }

    public void checkMusicDeleteStatus(MusicEntity musicEntity) {
        if (musicEntity.isDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }
}
