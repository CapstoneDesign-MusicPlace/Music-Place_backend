package org.musicplace.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank
    private String email;
    @NotBlank
    private String pw;
    @NotBlank
    private String name;

}
