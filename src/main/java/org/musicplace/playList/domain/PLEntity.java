package org.musicplace.playList.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYLIST_ID")
    private Long playlist_id;
    @Column(name = "COVER_IMG", nullable = true)
    private String cover_img;
    @Column(name = "ONOFF", nullable = false)
    private OnOff onOff;
    @Column(name = "COMMENT", nullable = true)
    private String comment;


}
