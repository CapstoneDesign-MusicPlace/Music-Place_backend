package org.musicplace.playList.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.musicplace.playList.domain.CommentEntity;
import org.musicplace.playList.domain.MusicEntity;
import org.musicplace.playList.domain.OnOff;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.CommentSaveDto;
import org.musicplace.playList.dto.MusicSaveDto;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.repository.PLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommentServiceTest {

    @Autowired
    private PLService plService;

    @Autowired
    private PLRepository plRepository;

    @Autowired
    private CommentService commentService;

    @Test
    void commentSave() {

        // given
        String PLTitle = "여름에 듣기 좋은 플리 모음";
        OnOff PLOnOff = OnOff.Public;
        String PLComment = "듣기만해도 시원해지는 노래들";

        Long PLId = plService.PLsave(PLSaveDto.builder()
                .title(PLTitle)
                .onOff(PLOnOff)
                .comment(PLComment)
                .build());

        String nickName = "주철깡";
        String comment = "와 좋은 노래!";

        commentService.CommentSave(PLId, CommentSaveDto.builder()
                .nickName(nickName)
                .comment(comment)
                .build());

        // when
        PLEntity plEntity = plRepository.findById(PLId).get();
        CommentEntity commentEntity = plEntity.getCommentEntities()
                .stream()
                .filter(cm -> cm.getComment_id().equals(PLId))
                .findFirst()
                .orElse(null);

        // then
        assertEquals(comment, commentEntity.getComment());
        assertEquals(nickName, commentEntity.getNickName());
        assertFalse(commentEntity.isDelete());
    }

    @Test
    void commentDelete() {

        // given
        String PLTitle = "여름에 듣기 좋은 플리 모음";
        OnOff PLOnOff = OnOff.Public;
        String PLComment = "듣기만해도 시원해지는 노래들";

        Long PLId = plService.PLsave(PLSaveDto.builder()
                .title(PLTitle)
                .onOff(PLOnOff)
                .comment(PLComment)
                .build());

        String nickName = "주철깡";
        String comment = "와 좋은 노래!";

        Long commentId = commentService.CommentSave(PLId, CommentSaveDto.builder()
                .nickName(nickName)
                .comment(comment)
                .build());

        // when
        boolean result =  commentService.CommentDelete(PLId,commentId);

        // then
        assertTrue(result);
    }

    @Test
    void commentFindAll() {

        // given
        String PLTitle = "여름에 듣기 좋은 플리 모음";
        OnOff PLOnOff = OnOff.Public;
        String PLComment = "듣기만해도 시원해지는 노래들";

        Long PLId = plService.PLsave(PLSaveDto.builder()
                .title(PLTitle)
                .onOff(PLOnOff)
                .comment(PLComment)
                .build());

        String nickName1 = "주철깡";
        String comment1 = "와 좋은 노래!";

        Long commentId = commentService.CommentSave(PLId, CommentSaveDto.builder()
                .nickName(nickName1)
                .comment(comment1)
                .build());

        String nickName2 = "주철깡";
        String comment2 = "와 좋은 노래!";

        commentService.CommentSave(PLId, CommentSaveDto.builder()
                .nickName(nickName2)
                .comment(comment2)
                .build());

        commentService.CommentDelete(PLId,commentId);

        // when
        List<CommentEntity> commentEntities = commentService.CommentFindAll(PLId);

        // then
        assertEquals(1, commentEntities.size());

        assertEquals(nickName2, commentEntities.get(0).getNickName());
        assertEquals(comment2, commentEntities.get(0).getComment());
        assertFalse(commentEntities.get(0).isDelete());
    }
}
