package org.musicplace.playList.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.musicplace.playList.domain.OnOff;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.dto.PLUpdateDto;
import org.musicplace.playList.repository.PLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PLServiceTest {

    @Autowired
    private PLService plService;

    @Autowired
    private PLRepository plRepository;

    @BeforeEach
    void setUp() {
        plRepository.deleteAll();
    }

    @Test
    @DisplayName("PlayList Save")
    void PLsave() {

        // given
        String title = "여름에 듣기 좋은 플리 모음";
        OnOff onOff = OnOff.Public;
        String comment = "듣기만해도 시원해지는 노래들";

        Long Id = plService.PLsave(PLSaveDto.builder()
                .title(title)
                .onOff(onOff)
                .comment(comment)
                .build());

        // when
        PLEntity plEntity = plRepository.findById(Id).get();

        // then
        assertEquals(title, plEntity.getPLTitle());
        assertEquals(comment, plEntity.getComment());
        assertEquals(onOff, plEntity.getOnOff());
        assertNull(plEntity.getCover_img());
        assertFalse(plEntity.isDelete());
    }

    @Test
    @DisplayName("PlayList Update")
    void PLUpdate() {

        // given
        String title1 = "여름에 듣기 좋은 플리 모음";
        OnOff onOff1 = OnOff.Public;
        String comment1 = "듣기만해도 시원해지는 노래들";

        OnOff onOff2 = OnOff.Private;
        String comment2 = "매미소리 말고 노래 듣자";

        Long Id = plService.PLsave(PLSaveDto.builder()
                .title(title1)
                .onOff(onOff1)
                .comment(comment1)
                .build());

        plService.PLUpdate(Id, PLUpdateDto.builder()
                .onOff(onOff2)
                .comment(comment2)
                .build());

        // when
        PLEntity plEntity = plRepository.findById(Id).get();

        // then
        assertEquals(title1, plEntity.getPLTitle());
        assertEquals(comment2, plEntity.getComment());
        assertEquals(onOff2, plEntity.getOnOff());
        assertNull(plEntity.getCover_img());
        assertFalse(plEntity.isDelete());
    }

    @Test
    @DisplayName("PlayList Delete")
    void PLDelete() {

        // given
        String title = "여름에 듣기 좋은 플리 모음";
        OnOff onOff = OnOff.Public;
        String comment = "듣기만해도 시원해지는 노래들";

        Long Id =plService.PLsave(PLSaveDto.builder()
                .title(title)
                .onOff(onOff)
                .comment(comment)
                .build());

        plService.PLDelete(Id);

        // when
        Optional<PLEntity> plEntityOptional = plRepository.findById(Id);

        // then
        assertTrue(plEntityOptional.isPresent());
    }

    @Test
    @DisplayName("PlayList FindAll")
    void PLFindAll() {
        // given
        String title1 = "여름에 듣기 좋은 플리 모음";
        OnOff onOff1 = OnOff.Public;
        String comment1 = "듣기만해도 시원해지는 노래들";

        String title2 = "봄에 듣기 좋은 플리 모음";
        OnOff onOff2 = OnOff.Private;
        String comment2 = "듣기만해도 따뜻해지는 노래들";

        plService.PLsave(PLSaveDto.builder()
                .title(title1)
                .onOff(onOff1)
                .comment(comment1)
                .build());

        plService.PLsave(PLSaveDto.builder()
                .title(title2)
                .onOff(onOff2)
                .comment(comment2)
                .build());

        // when
        List<PLEntity> playlists = plService.PLFindAll();

        // then
        assertEquals(2, playlists.size());

        assertEquals(title1, playlists.get(0).getPLTitle());
        assertEquals(onOff1, playlists.get(0).getOnOff());
        assertEquals(comment1, playlists.get(0).getComment());
        assertFalse(playlists.get(0).isDelete());

        assertEquals(title2, playlists.get(1).getPLTitle());
        assertEquals(onOff2, playlists.get(1).getOnOff());
        assertEquals(comment2, playlists.get(1).getComment());
        assertFalse(playlists.get(1).isDelete());
    }


    @Test
    @DisplayName("PlayList FindPublicAll")
    void PLFindPublic() {

        // given
        String title1 = "여름에 듣기 좋은 플리 모음";
        OnOff onOff1 = OnOff.Public;
        String comment1 = "듣기만해도 시원해지는 노래들";

        String title2 = "봄에 듣기 좋은 플리 모음";
        OnOff onOff2 = OnOff.Private;
        String comment2 = "듣기만해도 따뜻해지는 노래들";

        plService.PLsave(PLSaveDto.builder()
                .title(title1)
                .onOff(onOff1)
                .comment(comment1)
                .build());

        plService.PLsave(PLSaveDto.builder()
                .title(title2)
                .onOff(onOff2)
                .comment(comment2)
                .build());

        // when
        List<PLEntity> PublicPlaylists = plService.PLFindPublic();

        // then
        assertEquals(1, PublicPlaylists.size());
        assertEquals(title1, PublicPlaylists.get(0).getPLTitle());
        assertEquals(comment1, PublicPlaylists.get(0).getComment());
        assertFalse(PublicPlaylists.get(0).isDelete());
        assertEquals(onOff1, PublicPlaylists.get(0).getOnOff());
        assertNotEquals(OnOff.Private, PublicPlaylists.get(0).getOnOff());
    }
}
