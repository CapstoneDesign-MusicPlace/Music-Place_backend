package org.musicplace.member.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.member.dto.SignInSaveDto;
import org.musicplace.member.service.SignInService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SignIn")
@RequiredArgsConstructor
public class SignInController {
    private final SignInService signInService;

    @PostMapping
    public void SignInSave(@RequestBody SignInSaveDto signInSaveDto) {
        signInService.SignInSave(signInSaveDto);
    }
}
