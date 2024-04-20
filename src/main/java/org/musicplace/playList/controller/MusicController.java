package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.Service.MusicService;
import org.musicplace.playList.domain.MusicEntity;
import org.musicplace.playList.domain.PLEntity;
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
    public void MusicSave(@PathVariable Long PLId, @RequestBody MusicSaveDto musicSaveDto) {
        musicService.MusicSave(PLId, musicSaveDto);
    }

    @DeleteMapping("/{PLId}/{MusicId}")
    public void MusicDelete(@PathVariable Long PLId,@PathVariable Long MusicId) {
        musicService.MusicDelete(PLId, MusicId);
    }

    @GetMapping("/{PLId}")
    public List<MusicEntity> MusicFindAll(@PathVariable Long PLId){
        List<MusicEntity> AllMusic = musicService.MusicFindAll(PLId);
        return AllMusic;
    }
}
