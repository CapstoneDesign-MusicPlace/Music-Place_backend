package org.musicplace.streaming.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.musicplace.streaming.domain.StreamingEntity;
import org.musicplace.streaming.dto.StreamingSaveDto;
import org.musicplace.streaming.dto.StreamingUpdateDto;
import org.musicplace.streaming.repository.StreamingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StreamingServiceTest {

    @Autowired
    private StreamingService streamingService;

    @Autowired
    private StreamingRepository streamingRepository;

    @AfterEach
    void setUp() {
        streamingRepository.deleteAll();
    }

    @Test
    void streamingSave() {

        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        // when
        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        StreamingEntity streamingEntity = streamingRepository.findById(streamingId).get();

        // then
        assertEquals(broadcastingTitle, streamingEntity.getBroadcastingTitle());
        assertEquals(introduce, streamingEntity.getIntroduce());
        assertEquals(streamerNickname, streamingEntity.getStreamerNickname());
    }

    @Test
    void streamingUpdate() {

        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        String updateBroadcastingTitle = "Update Broadcasting Title";
        String updateIntroduce = "Update Introduce";

        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        // when
        streamingService.streamingUpdate(streamingId, StreamingUpdateDto.builder()
                .broadcastingTitle(updateBroadcastingTitle)
                .introduce(updateIntroduce)
                .build()
        );

        // then
        StreamingEntity streamingEntity = streamingRepository.findById(streamingId).get();

        assertEquals(updateBroadcastingTitle, streamingEntity.getBroadcastingTitle());
        assertEquals(updateIntroduce, streamingEntity.getIntroduce());
        assertEquals(streamerNickname, streamingEntity.getStreamerNickname());
    }

    @Test
    void streamingDelete() {

        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        // when
        streamingService.streamingDelete(streamingId);

        Optional<StreamingEntity> deletedEntityOptional = streamingRepository.findById(streamingId);

        // then
        assertFalse(deletedEntityOptional.isPresent());
    }

    @Test
    void streamingFindAll() {

        // given
        String broadcastingTitle1 = "Test Broadcasting Title1";
        String introduce1 = "Test Introduce1";
        String streamerNickname1 = "Test Streamer Nickname1";

        String broadcastingTitle2 = "Test Broadcasting Title2";
        String introduce2 = "Test Introduce2";
        String streamerNickname2 = "Test Streamer Nickname2";

        streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle1)
                .introduce(introduce1)
                .streamerNickname(streamerNickname1)
                .build());

        streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle2)
                .introduce(introduce2)
                .streamerNickname(streamerNickname2)
                .build());

        // when
        List<StreamingEntity> streamingEntityList = streamingService.streamingFindAll();

        // then
        assertEquals(2, streamingEntityList.size());

        assertEquals(broadcastingTitle1, streamingEntityList.get(0).getBroadcastingTitle());
        assertEquals(introduce1, streamingEntityList.get(0).getIntroduce());
        assertEquals(streamerNickname1, streamingEntityList.get(0).getStreamerNickname());

        assertEquals(broadcastingTitle2, streamingEntityList.get(1).getBroadcastingTitle());
        assertEquals(introduce2, streamingEntityList.get(1).getIntroduce());
        assertEquals(streamerNickname2, streamingEntityList.get(1).getStreamerNickname());
    }
}
