package org.musicplace.streaming.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.musicplace.streaming.domain.StreamingEntity;
import org.musicplace.streaming.domain.StreamingMemberEntity;
import org.musicplace.streaming.domain.StreamingRole;
import org.musicplace.streaming.dto.StreamingMemberSaveDto;
import org.musicplace.streaming.dto.StreamingSaveDto;
import org.musicplace.streaming.repository.StreamingMemberRepository;
import org.musicplace.streaming.repository.StreamingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StreamingMemberServiceTest {

    @Autowired
    private StreamingService streamingService;

    @Autowired
    private StreamingRepository streamingRepository;

    @Autowired
    private StreamingMemberService streamingMemberService;

    @Autowired
    private StreamingMemberRepository streamingMemberRepository;

    @AfterEach
    void setUp() {
        streamingRepository.deleteAll();
    }

    @Test
    void memeberSave() {

        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        String streamingUserId = "Test Uset Id";
        StreamingRole streamingRole = StreamingRole.host;

        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        // when
        Long memberNum = streamingMemberService.memeberSave(streamingId, StreamingMemberSaveDto.builder()
                        .streamingRole(streamingRole)
                        .streamingUserId(streamingUserId)
                        .build());

        StreamingMemberEntity streamingMemberEntity = streamingMemberRepository.findById(memberNum).get();

        // then
        assertEquals(streamingUserId, streamingMemberEntity.getStreamingUserId());
        assertEquals(streamingRole, streamingMemberEntity.getStreamingRole());
    }

    @Test
    void memeberDelete() {

        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        String streamingUserId = "Test User Id";
        StreamingRole streamingRole = StreamingRole.host;

        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());
        Long memberNum = streamingMemberService.memeberSave(streamingId, StreamingMemberSaveDto.builder()
                .streamingRole(streamingRole)
                .streamingUserId(streamingUserId)
                .build());
        // when
        streamingMemberService.memeberDelete(streamingId, memberNum);

        StreamingMemberEntity deletedEntityOptional = streamingMemberRepository.findById(memberNum).get();

        // then
        assertFalse(deletedEntityOptional.equals(null));
    }
}
