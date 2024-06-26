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

    @PostMapping("/{member_id}")
    public Long FollowSave(@RequestBody FollowSaveDto followSaveDto, @PathVariable String member_id) {
        return followService.FollowSave(followSaveDto, member_id);
    }

    @DeleteMapping("/{member_id}/{follow_id}")
    public void FollowDelete(@PathVariable Long follow_id) {
        followService.followDelete(follow_id);
    }

    @GetMapping("/{member_id}")
    public List<ResponseDto> followFindAll(@PathVariable String member_id) {
        return followService.followFindAll(member_id);
    }
}
