package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.service.CommentService;
import org.musicplace.playList.domain.CommentEntity;
import org.musicplace.playList.dto.CommentSaveDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/playList/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{PLId}")
    public Long CommentSave(@PathVariable Long PLId, @RequestBody CommentSaveDto commentSaveDto) {
        return commentService.CommentSave(PLId,commentSaveDto);
    }

    @DeleteMapping("/{PLId}/{CommentId}")
    public boolean CommentDelete(@PathVariable Long PLId, @PathVariable Long CommentId) {
        return commentService.CommentDelete(PLId, CommentId);
    }

    @GetMapping("/{PLId}")
    public List<CommentEntity> CommentFindAll(@PathVariable Long PLId){
        List<CommentEntity> AllComment = commentService.CommentFindAll(PLId);
        return AllComment;
    }
}
