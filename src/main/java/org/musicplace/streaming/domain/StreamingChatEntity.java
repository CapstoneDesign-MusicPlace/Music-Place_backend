package org.musicplace.streaming.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.musicplace.global.jpa.AuditInformation;

@Entity
@Getter
@Table(name = "STREAMINGCHAT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingChatEntity extends AuditInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ID", nullable = false)
    private Long chatId;

    @Column(name = "WRITE_USER_ID", nullable = false)
    @Comment("채팅 작성자 아이디")
    private String writeUserId;

    @Column(name = "WRITE_USER_NICKNAME", nullable = false)
    @Comment("채팅 작성자 닉네임")
    private String writeUserNickname;

    @Column(name = "CHAT", nullable = false)
    @Comment("채팅")
    private String chat;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STREAMING_ID")
    private StreamingEntity streamingEntity;

    @Builder
    public StreamingChatEntity(String writeUserId, String writeUserNickname, String chat) {
        this.writeUserId = writeUserId;
        this.writeUserNickname = writeUserNickname;
        this.chat = chat;
    }

}
