package org.musicplace.playList.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.authorizaion.MemberAuthorizationUtil;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.service.SignInService;
import org.musicplace.playList.domain.OnOff;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.dto.PLUpdateDto;
import org.musicplace.playList.dto.ResponsePLDto;
import org.musicplace.playList.repository.PLRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PLService {

    private final PLRepository plRepository;
    private final SignInService signInService;

    @Transactional
    public Long PLsave(PLSaveDto plSaveDto) {
        String member_id = MemberAuthorizationUtil.getLoginMemberId();
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        signInService.CheckSignInDelete(signInEntity);
        PLEntity plEntity = plRepository.save(PLEntity.builder()
                .title(plSaveDto.getTitle())
                .onOff(plSaveDto.getOnOff())
                .comment(plSaveDto.getComment())
                .cover_img(plSaveDto.getCover_img())
                .nickname(signInEntity.getNickname())
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
        plEntity.PLUpdate(
                plUpdateDto.getTitle(),
                plUpdateDto.getOnOff(),
                plUpdateDto.getCover_img(),
                plUpdateDto.getComment());
    }

    @Transactional
    public void PLDelete(Long id) {
        PLEntity plEntity = PLFindById(id);
        CheckPLDeleteStatus(plEntity);
        plEntity.delete();
    }

    public List<ResponsePLDto> PLFindAll() {
        String member_id = MemberAuthorizationUtil.getLoginMemberId();
        SignInEntity signInEntity = signInService.SignInFindById(member_id);
        List<ResponsePLDto> nonDeletedPlayLists = signInEntity.getPlaylistEntities()
                .stream()
                .filter(plEntity -> !plEntity.isPLDelete())
                .map(plEntity -> ResponsePLDto.builder()
                        .playlist_id(plEntity.getPlaylist_id())
                        .nickname(plEntity.getNickname())
                        .PLTitle(plEntity.getPLTitle())
                        .cover_img(plEntity.getCover_img())
                        .onOff(plEntity.getOnOff())
                        .comment(plEntity.getComment())
                        .build())
                .collect(Collectors.toList());
        return nonDeletedPlayLists;
    }

    public List<ResponsePLDto> PLFindPublic() {
        List<PLEntity> playListAll = plRepository.findAll();

        List<ResponsePLDto> publicPlayLists = playListAll.stream()
                .filter(plEntity -> plEntity.getOnOff().equals(OnOff.Public))
                .map(plEntity -> ResponsePLDto.builder()
                        .playlist_id(plEntity.getPlaylist_id())
                        .PLTitle(plEntity.getPLTitle())
                        .cover_img(plEntity.getCover_img())
                        .onOff(plEntity.getOnOff())
                        .comment(plEntity.getComment())
                        .build())
                .collect(Collectors.toList());

        return publicPlayLists;
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
