package org.musicplace.recommend.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.service.SignInService;
import org.musicplace.recommend.dto.RecommendSaveDto;
import org.musicplace.recommend.service.RecommendService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {
    private final SignInService signInService;
    private final RecommendService recommendService;

    @PostMapping("/{member_id}")
    public Long RecommendSave(@RequestBody RecommendSaveDto recommendSaveDto, @PathVariable String member_id) {
        return recommendService.RecommendSave(recommendSaveDto ,member_id);
    }
}