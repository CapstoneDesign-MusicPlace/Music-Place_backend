package org.musicplace.playList.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.playList.domain.OnOff;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.dto.PLUpdateDto;
import org.musicplace.playList.repository.PLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PLService {

    private final PLRepository plRepository;

    @Transactional
    public void PLsave(PLSaveDto plSaveDto) {
        plRepository.save(PLEntity.builder()
                .title(plSaveDto.getTitle())
                .onOff(plSaveDto.getOnOff())
                .comment(plSaveDto.getComment())
                .cover_img(plSaveDto.getCover_img())
                .build());
    }

    @Transactional
    public void PLUpdate(Long id, PLUpdateDto plUpdateDto) {
        PLEntity plEntity = PLFindById(id);
        checkDeleteStatus(plEntity);
        plEntity.PLUpdate(plUpdateDto.getOnOff(),
                plUpdateDto.getCover_img(),
                plUpdateDto.getComment());
    }

    @Transactional
    public void PLDelete(Long id) {
        PLEntity plEntity = PLFindById(id);
        checkDeleteStatus(plEntity);
        plEntity.delete();
    }

    public List<PLEntity> PLFindAll() {
        List<PLEntity> PlayListAll = plRepository.findAll();
        List<PLEntity> nonDeletedPlayLists = new ArrayList<>();
        for(PLEntity pl : PlayListAll)  {
            if(!pl.isDelete()) {
                nonDeletedPlayLists.add(pl);
            }
        }
        return nonDeletedPlayLists;
    }

    public List<PLEntity> PLFindPublic() {
        List<PLEntity> PlayListAll = plRepository.findAll();
        List<PLEntity> PublicPlayLists = new ArrayList<>();
        for(PLEntity pl : PlayListAll)  {
            if(pl.getOnOff().equals(OnOff.Public)) {
                PublicPlayLists.add(pl);
            }
        }
        return PublicPlayLists;
    }

    public PLEntity PLFindById(Long id) {
        PLEntity plEntity = plRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        return plEntity;
    }

    private void checkDeleteStatus(PLEntity plEntity) {
        if (plEntity.isDelete()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }


}
