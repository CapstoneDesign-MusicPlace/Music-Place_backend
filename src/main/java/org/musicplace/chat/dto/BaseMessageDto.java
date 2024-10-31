package org.musicplace.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseMessageDto {
    private String roomId;
    private String username;
    private String message;
}