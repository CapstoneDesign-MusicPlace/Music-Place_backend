package org.musicplace.streaming.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.domain.StreamingMusicEntity;
import org.musicplace.streaming.dto.StreamingMusicSaveDto;
import org.musicplace.streaming.service.StreamingMusicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/streaming-music")
public class StreamingMusicController {

    private final StreamingMusicService streamingMusicService;

    @PostMapping("/{streamingId}")
    public Long musicSave(@PathVariable Long streamingId, @RequestBody StreamingMusicSaveDto streamingMusicSaveDto) {
        return streamingMusicService.musicSave(streamingId,streamingMusicSaveDto);
    }

    @GetMapping("/{streamingId}")
    public List<StreamingMusicEntity> musicFindAll(@PathVariable Long streamingId) {
        return streamingMusicService.musicFindAll(streamingId);
    }
}
