package org.musicplace.playList.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.playList.domain.CommentEntity;
import org.musicplace.playList.domain.MusicEntity;
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
    public Long CommentSave(Long PLId, CommentSaveDto commentSaveDto) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        CommentEntity commentEntity = CommentEntity.builder()
                .comment(commentSaveDto.getComment())
                .nickName(commentSaveDto.getNickName())
                .build();
        commentEntity.setPlEntity(plEntity);
        plEntity.getCommentEntities().add(commentEntity);
        commentRepository.save(commentEntity);
        return commentEntity.getComment_id();
    }

    @Transactional
    public boolean CommentDelete(Long PLId,Long CommentId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        CommentEntity commentEntity = CommentFindById(plEntity, CommentId);
        CheckCommentDeleteStatus(commentEntity);
        commentEntity.delete();
        return commentEntity.isDelete();
    }

    public List<CommentEntity> CommentFindAll(Long PLId) {
        PLEntity plEntity = plService.PLFindById(PLId);
        plService.CheckPLDeleteStatus(plEntity);
        List<CommentEntity> nonDeletedComment = plEntity.getCommentEntities()
                .stream()
                .filter(comment -> !comment.isDelete())
                .toList();
        return nonDeletedComment;
    }

    public CommentEntity CommentFindById(PLEntity plEntity, Long CommentId) {
        CommentEntity commentEntity = plEntity.getCommentEntities()
                .stream()
                .filter(comment -> comment.getComment_id().equals(CommentId))
                .findFirst()
                .orElse(null);
        if (commentEntity == null) {
            throw new ExceptionHandler(ErrorCode.ID_NOT_FOUND);
        }
        return commentEntity;
    }

    public void CheckCommentDeleteStatus(CommentEntity commentEntity) {
        if (commentEntity.isDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }
}
