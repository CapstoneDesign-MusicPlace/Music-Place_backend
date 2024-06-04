package org.musicplace.streaming.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.domain.StreamingEntity;
import org.musicplace.streaming.domain.StreamingMusicEntity;
import org.musicplace.streaming.dto.ResponseStreamingMusicDto;
import org.musicplace.streaming.dto.StreamingMusicSaveDto;
import org.musicplace.streaming.repository.StreamingMusicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreamingMusicService {

    private final StreamingMusicRepository streamingMusicRepository;

    private final StreamingService streamingService;

    @Transactional
    public Long musicSave(Long streamingId, StreamingMusicSaveDto streamingMusicSaveDto) {
        StreamingEntity streamingEntity = streamingService.streamingFindById(streamingId);
        StreamingMusicEntity musicEntity = StreamingMusicEntity.builder()
                .streamingSinger(streamingMusicSaveDto.getStreamingSinger())
                .streamingTitle(streamingMusicSaveDto.getStreamingTitle())
                .build();
        musicEntity.setStreamingEntity(streamingEntity);
        streamingEntity.getMusicEntities().add(musicEntity);
        return streamingMusicRepository.save(musicEntity).getStreamingMusicId();
    }

    public List<ResponseStreamingMusicDto> musicFindAll(Long streamingId) {
        StreamingEntity streamingEntity = streamingService.streamingFindById(streamingId);

        return streamingEntity.getMusicEntities().stream()
                .map(musicEntity -> ResponseStreamingMusicDto.builder()
                        .streamingMusicId(musicEntity.getStreamingMusicId())
                        .streamingTitle(musicEntity.getStreamingTitle())
                        .streamingSinger(musicEntity.getStreamingSinger())
                        .build())
                .collect(Collectors.toList());
    }

}
