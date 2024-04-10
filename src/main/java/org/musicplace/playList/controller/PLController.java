package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.Service.PLService;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.dto.PLUpdateDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playList")
@RequiredArgsConstructor
public class PLController {
    private final PLService PLService;

    @PostMapping
    public PLSaveDto PLSave(@RequestBody PLSaveDto plSaveDto) {
        PLService.PLsave(plSaveDto);
        return plSaveDto;
    }

    @PatchMapping("/{id}")
    public void PLUpdate(@PathVariable Long id, @RequestBody PLUpdateDto plUpdateDto) {
        PLService.PLUpdate(id, plUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void PLDelete(@PathVariable Long id) {
        PLService.PLDelete(id);
    }



}
