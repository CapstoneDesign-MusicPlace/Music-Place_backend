package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.Service.PLService;
import org.musicplace.playList.dto.PLSaveDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playList")
@RequiredArgsConstructor
public class PLController {
    private final PLService PLService;

    @PostMapping
    public PLSaveDto PLsave(@RequestBody PLSaveDto plSaveDto) {
        PLService.PLsave(plSaveDto);
        return plSaveDto;
    }


}
