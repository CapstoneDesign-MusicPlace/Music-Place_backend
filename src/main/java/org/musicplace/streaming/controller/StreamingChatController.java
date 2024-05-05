package org.musicplace.streaming.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.domain.StreamingChatEntity;
import org.musicplace.streaming.dto.StreamingChatSaveDto;
import org.musicplace.streaming.service.StreamingChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/streaming-chat")
public class StreamingChatController {

    private final StreamingChatService streamingChatService;

    @PostMapping("/{streamingId}")
    public void streamingChatSave(@PathVariable Long streamingId, @RequestBody StreamingChatSaveDto streamingChatSaveDto) {
        streamingChatService.chatSave(streamingId, streamingChatSaveDto);
    }

    @GetMapping("/{streamingId}")
    public List<StreamingChatEntity> streamingChatFindAll(@PathVariable Long streamingId) {
        return streamingChatService.chatFindAll(streamingId);
    }
}
