package org.musicplace.playList.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.playList.domain.CommentEntity;
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
}
