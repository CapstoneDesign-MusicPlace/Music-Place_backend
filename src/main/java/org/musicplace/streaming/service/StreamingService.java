package org.musicplace.streaming.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.service.SignInService;
import org.musicplace.streaming.domain.StreamingEntity;
import org.musicplace.streaming.dto.StreamingSaveDto;
import org.musicplace.streaming.dto.StreamingUpdateDto;
import org.musicplace.streaming.repository.StreamingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;
    private final SignInService signInService;

    @Transactional
    public Long streamingSave(StreamingSaveDto streamingSaveDto, String member_id) {
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        signInService.CheckSignInDelete(signInEntity);
        StreamingEntity streamingEntity = StreamingEntity.builder()
                .broadcastingTitle(streamingSaveDto.getBroadcastingTitle())
                .introduce(streamingSaveDto.getIntroduce())
                .streamerNickname(streamingSaveDto.getStreamerNickname())
                .build();
        signInEntity.getStreamingEntities().add(streamingEntity);
        streamingEntity.SignInEntity(signInEntity);
        streamingRepository.save(streamingEntity);
        return streamingEntity.getStreaming_id();
    }

    @Transactional
    public void streamingUpdate(Long streamingId, StreamingUpdateDto streamingUpdateDto) {
        StreamingEntity streamingEntity = streamingFindById(streamingId);
        streamingEntity.StreamingUpdate(streamingUpdateDto.getBroadcastingTitle(), streamingUpdateDto.getIntroduce());
    }

    @Transactional
    public void streamingDelete(Long streamingId) {
        StreamingEntity streamingEntity = streamingFindById(streamingId);
        streamingRepository.delete(streamingEntity);
    }

    public List<StreamingEntity> streamingFindAll() {
        List<StreamingEntity> streamingEntityList = streamingRepository.findAll();
        return streamingEntityList;
    }

    public StreamingEntity streamingFindById(Long streamingId) {
        StreamingEntity streamingEntity = streamingRepository.findById(streamingId)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        return streamingEntity;
    }
}
