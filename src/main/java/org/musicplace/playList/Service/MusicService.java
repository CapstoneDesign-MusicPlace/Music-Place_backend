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
        MusicEntity musicEntity = PLFindById(id);
        checkDeleteStatus(musicEntity);
        musicEntity.delete();
    }


    public MusicEntity PLFindById(Long id) {
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
