package org.musicplace.playList.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.service.SignInService;
import org.musicplace.playList.domain.OnOff;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.dto.PLUpdateDto;
import org.musicplace.playList.repository.PLRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PLService {

    private final PLRepository plRepository;
    private final SignInService signInService;

    @Transactional
    public Long PLsave(PLSaveDto plSaveDto, String member_id) {
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        signInService.CheckSignInDelete(signInEntity);
        PLEntity plEntity = plRepository.save(PLEntity.builder()
                .title(plSaveDto.getTitle())
                .onOff(plSaveDto.getOnOff())
                .comment(plSaveDto.getComment())
                .cover_img(plSaveDto.getCover_img())
                .build());
        signInEntity.getPlaylistEntities().add(plEntity);
        plEntity.SignInEntity(signInEntity);
        plRepository.save(plEntity);
        return plEntity.getPlaylist_id();
    }

    @Transactional
    public void PLUpdate(Long id, PLUpdateDto plUpdateDto) {
        PLEntity plEntity = PLFindById(id);
        CheckPLDeleteStatus(plEntity);
        plEntity.PLUpdate(plUpdateDto.getOnOff(),
                plUpdateDto.getCover_img(),
                plUpdateDto.getComment());
    }

    @Transactional
    public void PLDelete(Long id) {
        PLEntity plEntity = PLFindById(id);
        CheckPLDeleteStatus(plEntity);
        plEntity.delete();
    }

    public List<PLEntity> PLFindAll(String member_id) {
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        List<PLEntity> nonDeletedPlayLists = signInEntity.getPlaylistEntities()
                .stream()
                .filter(plEntity -> !plEntity.isPLDelete())
                .toList();
        return nonDeletedPlayLists;
    }

    public List<PLEntity> PLFindPublic() {
        List<PLEntity> PlayListAll = plRepository.findAll();
        List<PLEntity> PublicPlayLists = PlayListAll
                .stream()
                .filter(plEntity -> plEntity.getOnOff().equals(OnOff.Public))
                .toList();
        return PublicPlayLists;
    }

    public PLEntity PLFindById(Long id) {
        PLEntity plEntity = plRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        return plEntity;
    }

    public void CheckPLDeleteStatus(PLEntity plEntity) {
        if (plEntity.isPLDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }


}
