package org.musicplace.playList.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.playList.domain.CommentEntity;
import org.musicplace.playList.domain.MusicEntity;
import org.musicplace.playList.dto.CommentSaveDto;
import org.musicplace.playList.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void CommentSave(CommentSaveDto commentSaveDto) {
        commentRepository.save(CommentEntity.builder()
                .comment(commentSaveDto.getComment())
                .nickName(commentSaveDto.getNickName())
                .build());
    }

    @Transactional
    public void CommentDelete(Long id) {
        CommentEntity commentEntity = CommentFindById(id);
        checkDeleteStatus(commentEntity);
        commentEntity.delete();
    }

    public CommentEntity CommentFindById(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        return commentEntity;
    }

    private void checkDeleteStatus(CommentEntity commentEntity) {
        if (commentEntity.isDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }
}
