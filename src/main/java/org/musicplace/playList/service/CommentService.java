package org.musicplace.playList.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.playList.domain.CommentEntity;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.CommentSaveDto;
import org.musicplace.playList.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PLService plService;

    @Transactional
    public void CommentSave(Long PLId, CommentSaveDto commentSaveDto) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        CommentEntity commentEntity = CommentEntity.builder()
                .comment(commentSaveDto.getComment())
                .nickName(commentSaveDto.getNickName())
                .build();
        commentEntity.setPlEntity(plEntity);
        plEntity.getCommentEntities().add(commentEntity);
        commentRepository.save(commentEntity);

    }

    @Transactional
    public void CommentDelete(Long PLId,Long CommentId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        CommentEntity commentEntity = CommentFindById(CommentId);
        CheckCommentDeleteStatus(commentEntity);
        commentEntity.delete();
    }

    public List<CommentEntity> CommentFindAll(Long PLId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        List<CommentEntity> AllComment = commentRepository.findAll();
        List<CommentEntity> nonDeletedComment = new ArrayList<>();
        for(CommentEntity Comment : AllComment)  {
            if(!Comment.isDelete()) {
                nonDeletedComment.add(Comment);
            }
        }
        return nonDeletedComment;
    }

    public CommentEntity CommentFindById(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        return commentEntity;
    }

    public void CheckCommentDeleteStatus(CommentEntity commentEntity) {
        if (commentEntity.isDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }
}
