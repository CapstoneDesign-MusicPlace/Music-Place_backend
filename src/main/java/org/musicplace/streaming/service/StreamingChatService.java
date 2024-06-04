package org.musicplace.streaming.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.domain.StreamingChatEntity;
import org.musicplace.streaming.domain.StreamingEntity;
import org.musicplace.streaming.dto.ResponseStreamingChatDto;
import org.musicplace.streaming.dto.StreamingChatSaveDto;
import org.musicplace.streaming.repository.StreamingChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreamingChatService {

    private final StreamingChatRepository streamingChatRepository;

    private final StreamingService streamingService;

    @Transactional
    public Long chatSave(Long streamingId, StreamingChatSaveDto streamingChatSaveDto) {
        StreamingEntity streamingEntity = streamingService.streamingFindById(streamingId);
        StreamingChatEntity streamingChatEntity = StreamingChatEntity.builder()
                .chat(streamingChatSaveDto.getChat())
                .writeUserId(streamingChatSaveDto.getWriteUserId())
                .writeUserNickname(streamingChatSaveDto.getWriteUserNickname())
                .build();
        streamingChatEntity.setStreamingEntity(streamingEntity);
        streamingEntity.getChatEntities().add(streamingChatEntity);
        return streamingChatRepository.save(streamingChatEntity).getChatId();
    }

    public List<ResponseStreamingChatDto> chatFindAll(Long streamingId) {
        StreamingEntity streamingEntity = streamingService.streamingFindById(streamingId);
        return streamingEntity.getChatEntities().stream()
                .map(chatEntity -> ResponseStreamingChatDto.builder()
                        .chatId(chatEntity.getChatId())
                        .writeUserId(chatEntity.getWriteUserId())
                        .writeUserNickname(chatEntity.getWriteUserNickname())
                        .chat(chatEntity.getChat())
                        .build())
                .collect(Collectors.toList());
    }

}
