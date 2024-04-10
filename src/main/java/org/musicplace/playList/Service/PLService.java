package org.musicplace.playList.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.playList.dto.PLSaveDto;
import org.musicplace.playList.repository.PLRepository;
import org.springframework.stereotype.Service;

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

    public void PLUpdate() {}

    public void PLDelete() {}

    public void PLFindAll() {}

    public void PLFind() {}


}
