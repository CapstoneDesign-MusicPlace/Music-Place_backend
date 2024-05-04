package org.musicplace.playList.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.musicplace.playList.domain.MusicEntity;
import org.musicplace.playList.domain.OnOff;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.MusicSaveDto;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.repository.PLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MusicServiceTest {

    @Autowired
    private MusicService musicService;

    @Autowired
    private PLService plService;

    @Autowired
    private PLRepository plRepository;

    @Test
    void musicSave() {

        // given
        String PLTitle = "여름에 듣기 좋은 플리 모음";
        OnOff PLOnOff = OnOff.Public;
        String PLComment = "듣기만해도 시원해지는 노래들";

        Long PLId = plService.PLsave(PLSaveDto.builder()
                .title(PLTitle)
                .onOff(PLOnOff)
                .comment(PLComment)
                .build());

        String singer = "비비";
        String musicTitle = "밤양갱";
        Long musicId = musicService.MusicSave(PLId, MusicSaveDto.builder()
                .singer(singer)
                .title(musicTitle)
                .build());

        // when
        PLEntity plEntity = plRepository.findById(PLId).get();
        MusicEntity musicEntity = plEntity.getMusicEntities()
                .stream()
                .filter(music -> music.getMusic_id().equals(musicId))
                .findFirst()
                .orElse(null);

        // then
        assertEquals(musicTitle, musicEntity.getTitle());
        assertEquals(singer, musicEntity.getSinger());
        assertFalse(musicEntity.isDelete());
    }

    @Test
    void musicDelete() {

        // given
        String PLTitle = "여름에 듣기 좋은 플리 모음";
        OnOff PLOnOff = OnOff.Public;
        String PLComment = "듣기만해도 시원해지는 노래들";

        Long PLId = plService.PLsave(PLSaveDto.builder()
                .title(PLTitle)
                .onOff(PLOnOff)
                .comment(PLComment)
                .build());

        String singer = "비비";
        String musicTitle = "밤양갱";
        Long musicId = musicService.MusicSave(PLId, MusicSaveDto.builder()
                .singer(singer)
                .title(musicTitle)
                .build());

        // when
        boolean result =  musicService.MusicDelete(PLId,musicId);

        // then
        assertTrue(result);
    }

    @Test
    void musicFindAll() {

        // given
        String PLTitle = "여름에 듣기 좋은 플리 모음";
        OnOff PLOnOff = OnOff.Public;
        String PLComment = "듣기만해도 시원해지는 노래들";

        Long PLId = plService.PLsave(PLSaveDto.builder()
                .title(PLTitle)
                .onOff(PLOnOff)
                .comment(PLComment)
                .build());

        String singer1 = "비비";
        String musicTitle1 = "밤양갱";
        Long musicId1 = musicService.MusicSave(PLId, MusicSaveDto.builder()
                .singer(singer1)
                .title(musicTitle1)
                .build());

        String singer2 = "노라조";
        String musicTitle2 = "카래";
        musicService.MusicSave(PLId, MusicSaveDto.builder()
                .singer(singer2)
                .title(musicTitle2)
                .build());

        boolean result =  musicService.MusicDelete(PLId,musicId1);

        // when
        List<MusicEntity> musicEntityList = musicService.MusicFindAll(PLId);

        // then
        assertEquals(1, musicEntityList.size());

        assertEquals(singer2, musicEntityList.get(0).getSinger());
        assertEquals(musicTitle2, musicEntityList.get(0).getTitle());
        assertFalse(musicEntityList.get(0).isDelete());

    }
}
