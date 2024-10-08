package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.dto.ResponsePLDto;
import org.musicplace.playList.service.PLService;
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

    @PostMapping()
    public Long plsave(@RequestBody PLSaveDto plSaveDto) {
        return PLService.PLsave(plSaveDto);
    }

    @PatchMapping("/{pl_id}")
    public void plupdate(@PathVariable Long pl_id, @RequestBody PLUpdateDto plUpdateDto) {
        PLService.PLUpdate(pl_id, plUpdateDto);
    }

    @DeleteMapping("/{pl_id}")
    public void pldelete(@PathVariable Long pl_id) {
        PLService.PLDelete(pl_id);
    }

    @GetMapping()
    public List<ResponsePLDto> plfindall() {
        List<ResponsePLDto> PlayListAll = PLService.PLFindAll();
        return PlayListAll;
    }

    @GetMapping("/public")
    public List<ResponsePLDto> PLFindPublic(){
        List<ResponsePLDto> PublicPlayList = PLService.PLFindPublic();
        return PublicPlayList;
    }

    @GetMapping("/count")
    public Long PLCount() {
        return PLService.PLCount();
    }

    @GetMapping("/other/{otherMemberId}")
    public List<ResponsePLDto> getOtherUserPL(@PathVariable String otherMemberId) {
        return PLService.getOtherUserPL(otherMemberId);
    }

}
