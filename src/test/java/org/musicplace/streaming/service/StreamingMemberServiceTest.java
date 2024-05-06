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
        Long memberNum = streamingMemberService.memberSave(streamingId, StreamingMemberSaveDto.builder()
                        .streamingRole(streamingRole)
                        .streamingUserId(streamingUserId)
                        .build());

        StreamingMemberEntity streamingMemberEntity = streamingMemberRepository.findById(memberNum).get();

        // then
        assertEquals(streamingUserId, streamingMemberEntity.getStreamingUserId());
        assertEquals(streamingRole, streamingMemberEntity.getStreamingRole());
    }

    @Test
    void memberDelete() {
        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        String streamingUserId = "Test User Id";
        StreamingRole streamingRole = StreamingRole.host;

        // 스트리밍 세션 생성
        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        // 멤버 생성 및 저장
        Long memberNum = streamingMemberService.memberSave(streamingId, StreamingMemberSaveDto.builder()
                .streamingRole(streamingRole)
                .streamingUserId(streamingUserId)
                .build());

        // when
        // 멤버 삭제
        streamingMemberService.memberDelete(streamingId, memberNum);

        // 삭제된 멤버가 더 이상 존재하지 않는지 확인
        Optional<StreamingMemberEntity> deletedEntityOptional = streamingMemberRepository.findById(memberNum);

        // then
        assertFalse(deletedEntityOptional.isPresent());
    }

}
