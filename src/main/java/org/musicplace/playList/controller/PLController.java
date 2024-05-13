package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.service.PLService;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.dto.PLUpdateDto;
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
@RequestMapping("/playList")
@RequiredArgsConstructor
public class PLController {
    private final PLService PLService;

    @PostMapping
    public Long PLSave(@RequestBody PLSaveDto plSaveDto) {
        return PLService.PLsave(plSaveDto);
    }

    @PatchMapping("/{id}")
    public void PLUpdate(@PathVariable Long id, @RequestBody PLUpdateDto plUpdateDto) {
        PLService.PLUpdate(id, plUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void PLDelete(@PathVariable Long id) {
        PLService.PLDelete(id);
    }

    @GetMapping
    public List<PLEntity> PLFindAll(){
        List<PLEntity> PlayListAll = PLService.PLFindAll();
        return PlayListAll;
    }

    @GetMapping("/public")
    public List<PLEntity> PLFindPublic(){
        List<PLEntity> PublicPlayList = PLService.PLFindPublic();
        return PublicPlayList;
    }

}
