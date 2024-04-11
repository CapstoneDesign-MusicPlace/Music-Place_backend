package org.musicplace.playList.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.playList.Service.CommentService;
import org.musicplace.playList.dto.CommentSaveDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playList/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void CommentSave(@RequestBody CommentSaveDto commentSaveDto) {
        commentService.CommentSave(commentSaveDto);
    }

    @DeleteMapping("/{id}")
    public void CommentDelete(@PathVariable Long id) {
        commentService.CommentDelete(id);
    }
}
