package org.musicplace.streaming.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.musicplace.streaming.domain.StreamingChatEntity;
import org.musicplace.streaming.domain.StreamingRole;
import org.musicplace.streaming.dto.StreamingChatSaveDto;
import org.musicplace.streaming.dto.StreamingSaveDto;
import org.musicplace.streaming.repository.StreamingChatRepository;
import org.musicplace.streaming.repository.StreamingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StreamingChatServiceTest {

    @Autowired
    private StreamingService streamingService;

    @Autowired
    private StreamingRepository streamingRepository;

    @Autowired
    private StreamingChatService streamingChatService;

    @Autowired
    private StreamingChatRepository streamingChatRepository;

    @AfterEach
    void setUp() {
        streamingRepository.deleteAll();
    }

    @Test
    void chatSave() {

        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        String writeUserId = "Test User Id";
        String writeUserNickname = "Test User Nickname";
        String chat = "Test User Chat";

        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        // when
        Long chatId = streamingChatService.chatSave(streamingId, StreamingChatSaveDto.builder()
                .chat(chat)
                .writeUserId(writeUserId)
                .writeUserNickname(writeUserNickname)
                .build());

        StreamingChatEntity streamingChatEntity = streamingChatRepository.findById(chatId).get();

        // then
        assertEquals(writeUserId, streamingChatEntity.getWriteUserId());
        assertEquals(writeUserNickname, streamingChatEntity.getWriteUserNickname());
        assertEquals(chat, streamingChatEntity.getChat());
    }

    @Test
    void chatFindAll() {

        // given
        String broadcastingTitle = "Test Broadcasting Title";
        String introduce = "Test Introduce";
        String streamerNickname = "Test Streamer Nickname";

        String writeUserId1 = "Test User Id1";
        String writeUserNickname1 = "Test User Nickname1";
        String chat1 = "Test User Chat1";

        String writeUserId2 = "Test User Id2";
        String writeUserNickname2 = "Test User Nickname2";
        String chat2 = "Test User Chat2";

        Long streamingId = streamingService.streamingSave(StreamingSaveDto.builder()
                .broadcastingTitle(broadcastingTitle)
                .introduce(introduce)
                .streamerNickname(streamerNickname)
                .build());

        streamingChatService.chatSave(streamingId, StreamingChatSaveDto.builder()
                .chat(chat1)
                .writeUserId(writeUserId1)
                .writeUserNickname(writeUserNickname1)
                .build());

        streamingChatService.chatSave(streamingId, StreamingChatSaveDto.builder()
                .chat(chat2)
                .writeUserId(writeUserId2)
                .writeUserNickname(writeUserNickname2)
                .build());

        // when
        List<StreamingChatEntity> streamingChatEntities =  streamingChatService.chatFindAll(streamingId);

        // when
        assertEquals(2, streamingChatEntities.size());

        assertEquals(writeUserId1, streamingChatEntities.get(0).getWriteUserId());
        assertEquals(writeUserNickname1, streamingChatEntities.get(0).getWriteUserNickname());
        assertEquals(chat1, streamingChatEntities.get(0).getChat());

        assertEquals(writeUserId2, streamingChatEntities.get(1).getWriteUserId());
        assertEquals(writeUserNickname2, streamingChatEntities.get(1).getWriteUserNickname());
        assertEquals(chat2, streamingChatEntities.get(1).getChat());
    }
}
