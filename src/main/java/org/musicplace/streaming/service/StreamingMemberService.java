package org.musicplace.streaming.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.domain.StreamingEntity;
import org.musicplace.streaming.domain.StreamingMemberEntity;
import org.musicplace.streaming.dto.StreamingMemberSaveDto;
import org.musicplace.streaming.repository.StreamingMemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreamingMemberService {

    private final StreamingMemberRepository streamingMemberRepository;

    private final StreamingService streamingService;

    @Transactional
    public Long memeberSave(Long streamingId, StreamingMemberSaveDto streamingMemberSaveDto) {
        StreamingEntity streamingEntity = streamingService.streamingFindById(streamingId);
        StreamingMemberEntity streamingMemberEntity = StreamingMemberEntity.builder()
                .streamingRole(streamingMemberSaveDto.getStreamingRole())
                .streamingUserId(streamingMemberSaveDto.getStreamingUserId())
                .build();
        streamingMemberEntity.setStreamingEntity(streamingEntity);
        streamingEntity.getMemberEntities().add(streamingMemberEntity);
        return streamingMemberRepository.save(streamingMemberEntity).getMemberNum();
    }

    @Transactional
    public void memeberDelete(Long streamingId, Long memberNum) {
        StreamingEntity streamingEntity = streamingService.streamingFindById(streamingId);
        StreamingMemberEntity streamingMemberEntity = streamingEntity.getMemberEntities().stream()
                .filter(member->member.getMemberNum().equals(memberNum))
                .findFirst()
                .orElse(null);
        streamingMemberRepository.delete(streamingMemberEntity);
    }
}
