package org.musicplace.playList.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.playList.domain.MusicEntity;
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

}
