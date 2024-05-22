package org.musicplace.streaming.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.domain.StreamingEntity;
import org.musicplace.streaming.dto.StreamingSaveDto;
import org.musicplace.streaming.dto.StreamingUpdateDto;
import org.musicplace.streaming.service.StreamingService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/streaming")
public class StreamingController {
    private final StreamingService streamingService;

    @PostMapping
    public Long streamingSave(@RequestBody StreamingSaveDto streamingSaveDto, @PathVariable String member_id) {
        return streamingService.streamingSave(streamingSaveDto, member_id);
    }

    @PatchMapping("/{streamingId}")
    public void streamingUpdate(@PathVariable Long streamingId, @RequestBody StreamingUpdateDto streamingUpdateDto) {
        streamingService.streamingUpdate(streamingId, streamingUpdateDto);
    }

    @DeleteMapping("/{streamingId}")
    public void streamingDelete(@PathVariable Long streamingId) {
        streamingService.streamingDelete(streamingId);
    }

    @GetMapping
    public List<StreamingEntity> streamingFindAll() {
        return streamingService.streamingFindAll();
    }
}
