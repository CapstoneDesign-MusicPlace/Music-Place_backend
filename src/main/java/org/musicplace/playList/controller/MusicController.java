package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.Service.MusicService;
import org.musicplace.playList.dto.MusicSaveDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playList/music")
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;

    @PostMapping
    public void MusicSave(@RequestBody MusicSaveDto musicSaveDto) {
        musicService.MusicSave(musicSaveDto);
    }

    @DeleteMapping("/{id}")
    public void MusicDelete(@PathVariable Long id) {
        musicService.MusicDelete(id);
    }
}
