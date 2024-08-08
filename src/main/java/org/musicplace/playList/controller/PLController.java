package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.dto.ResponsePLDto;
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

    @PostMapping("/{member_id}")
    public Long PLSave(@RequestBody PLSaveDto plSaveDto, @PathVariable String member_id) {
        return PLService.PLsave(plSaveDto, member_id);
    }

    @PatchMapping("/{pl_id}")
    public void PLUpdate(@PathVariable Long pl_id, @RequestBody PLUpdateDto plUpdateDto) {
        PLService.PLUpdate(pl_id, plUpdateDto);
    }

    @DeleteMapping("/{pl_id}")
    public void PLDelete(@PathVariable Long pl_id) {
        PLService.PLDelete(pl_id);
    }

    @GetMapping("/{member_id}")
    public List<ResponsePLDto> PLFindAll(@PathVariable String member_id) {
        List<ResponsePLDto> PlayListAll = PLService.PLFindAll(member_id);
        return PlayListAll;
    }

    @GetMapping("/public")
    public List<ResponsePLDto> PLFindPublic(){
        List<ResponsePLDto> PublicPlayList = PLService.PLFindPublic();
        return PublicPlayList;
    }

}
