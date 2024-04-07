package org.musicplace.playList.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity(name = "PLAYLIST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYLIST_ID", nullable = false)
    private Long playlist_id;

    @Column(name = "COVER_IMG", nullable = true)
    private String cover_img;

    @Column(name = "ONOFF", nullable = false)
    private OnOff onOff;

    @Column(name = "COMMENT", nullable = true)
    private String comment;


    @OneToMany(mappedBy = "MusicEntity", cascade = CascadeType.ALL)
    private List<MusicEntity> musicEntities;

}
