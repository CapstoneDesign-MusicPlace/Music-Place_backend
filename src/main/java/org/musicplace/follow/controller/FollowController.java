package org.musicplace.follow.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.follow.domain.FollowEntity;
import org.musicplace.follow.dto.FollowSaveDto;
import org.musicplace.follow.dto.ResponseDto;
import org.musicplace.follow.service.FollowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping()
    public Long FollowSave(@RequestBody FollowSaveDto followSaveDto) {
        return followService.FollowSave(followSaveDto);
    }

    @DeleteMapping("/{follow_id}")
    public void FollowDelete(@PathVariable Long follow_id) {
        followService.followDelete(follow_id);
    }

    @GetMapping()
    public List<ResponseDto> followFindAll() {
        return followService.followFindAll();
    }
}
