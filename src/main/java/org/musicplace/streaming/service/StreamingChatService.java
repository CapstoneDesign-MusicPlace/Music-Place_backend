package org.musicplace.streaming.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.domain.StreamingChatEntity;
import org.musicplace.streaming.domain.StreamingEntity;
import org.musicplace.streaming.dto.StreamingChatSaveDto;
import org.musicplace.streaming.repository.StreamingChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamingChatService {

    private final StreamingChatRepository streamingChatRepository;

    private final StreamingService streamingService;

    @Transactional
    public void chatSave(Long streamingId, StreamingChatSaveDto streamingChatSaveDto) {
        StreamingEntity streamingEntity = streamingService.streamingFindById(streamingId);
        StreamingChatEntity streamingChatEntity = StreamingChatEntity.builder()
                .chat(streamingChatSaveDto.getChat())
                .writeUserId(streamingChatSaveDto.getWriteUserId())
                .writeUserNickname(streamingChatSaveDto.getWriteUserNickname())
                .build();
        streamingChatEntity.setStreamingEntity(streamingEntity);
        streamingEntity.getChatEntities().add(streamingChatEntity);
        streamingChatRepository.save(streamingChatEntity);
    }

    public List<StreamingChatEntity> chatFindAll(Long streamingId) {
        StreamingEntity streamingEntity = streamingService.streamingFindById(streamingId);
        return streamingEntity.getChatEntities();
    }

}
