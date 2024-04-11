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

    @Transactional
    public void MusicSave(MusicSaveDto musicSaveDto) {
        musicRepository.save(MusicEntity.builder()
                .title(musicSaveDto.getTitle())
                .singer(musicSaveDto.getSinger())
                .build());
    }

    @Transactional
    public void MusicDelete(Long id) {
        MusicEntity musicEntity = MusicFindById(id);
        checkDeleteStatus(musicEntity);
        musicEntity.delete();
    }

    public List<MusicEntity> MusicFindAll() {
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

    private void checkDeleteStatus(MusicEntity musicEntity) {
        if (musicEntity.isDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }
}
