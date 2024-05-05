package org.musicplace.streaming.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.dto.StreamingMemberSaveDto;
import org.musicplace.streaming.service.StreamingMemberService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/streaming-member")
public class StreamingMemberController {

    private final StreamingMemberService streamingMemberService;

    @PostMapping("/{streamingId}")
    public String streamingMemberSave(@PathVariable Long streamingId, @RequestBody StreamingMemberSaveDto streamingMemberSaveDto) {
        return streamingMemberService.memeberSave(streamingId, streamingMemberSaveDto);
    }

    @PostMapping("/{streamingId}/{StreamingUserId}")
    public void memeberDelete(@PathVariable Long streamingId, @PathVariable String StreamingUserId) {
        streamingMemberService.memeberDelete(streamingId, StreamingUserId);
    }
}
