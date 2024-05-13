package org.musicplace.streaming.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.musicplace.streaming.domain.StreamingMusicEntity;
import org.musicplace.streaming.dto.StreamingMusicSaveDto;
import org.musicplace.streaming.dto.StreamingSaveDto;
import org.musicplace.streaming.repository.StreamingMusicRepository;
import org.musicplace.streaming.repository.StreamingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StreamingMusicServiceTest {

    @Autowired
    private StreamingService streamingService;

    @Autowired
    private StreamingRepository streamingRepository;

    @Autowired
    private StreamingMusicService streamingMusicService;

    @Autowired
    private StreamingMusicRepository streamingMusicRepository;

    @AfterEach
    void setUp() {
        streamingRepository.deleteAll();
    }

    @Test
    void musicSave() {
        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        String musicTitle = "Test Music Title";
        String musicSinger = "Test Music Singer";

        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        // when
        Long musicId = streamingMusicService.musicSave(streamingId, StreamingMusicSaveDto.builder()
                        .streamingSinger(musicSinger)
                        .streamingTitle(musicTitle)
                        .build());

        StreamingMusicEntity streamingMusicEntity = streamingMusicRepository.findById(musicId).get();

        // then
        assertEquals(musicTitle, streamingMusicEntity.getStreamingTitle());
        assertEquals(musicSinger, streamingMusicEntity.getStreamingSinger());
    }

    @Test
    void musicFindAll() {

        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        String musicTitle1 = "Test Music Title1";
        String musicSinger1 = "Test Music Singer1";

        String musicTitle2 = "Test Music Title2";
        String musicSinger2 = "Test Music Singer2";

        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        streamingMusicService.musicSave(streamingId, StreamingMusicSaveDto.builder()
                .streamingSinger(musicSinger1)
                .streamingTitle(musicTitle1)
                .build());

        streamingMusicService.musicSave(streamingId, StreamingMusicSaveDto.builder()
                .streamingSinger(musicSinger2)
                .streamingTitle(musicTitle2)
                .build());

        // when
        List<StreamingMusicEntity> musicFindAll = streamingMusicService.musicFindAll(streamingId);

        // then
        assertEquals(2, musicFindAll.size());

        assertEquals(musicTitle1, musicFindAll.get(0).getStreamingTitle());
        assertEquals(musicSinger1, musicFindAll.get(0).getStreamingSinger());

        assertEquals(musicTitle2, musicFindAll.get(1).getStreamingTitle());
        assertEquals(musicSinger2, musicFindAll.get(1).getStreamingSinger());
    }
}
