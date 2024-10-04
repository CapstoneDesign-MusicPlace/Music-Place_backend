package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.dto.ResponseMusicDto;
import org.musicplace.playList.service.MusicService;
import org.musicplace.playList.dto.MusicSaveDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/playList/music")
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;

    @PostMapping("/{PLId}")
    public Long musicSave(@PathVariable Long PLId, @RequestBody MusicSaveDto musicSaveDto) {
        return musicService.MusicSave(PLId, musicSaveDto);
    }

    @DeleteMapping("/{PLId}/{MusicId}")
    public boolean musicDelete(@PathVariable Long PLId, @PathVariable Long MusicId) {
        return musicService.MusicDelete(PLId, MusicId);
    }

    @GetMapping("/{PLId}")
    public List<ResponseMusicDto> MusicFindAll(@PathVariable Long PLId){
        List<ResponseMusicDto> AllMusic = musicService.MusicFindAll(PLId);
        return AllMusic;
    }
}
