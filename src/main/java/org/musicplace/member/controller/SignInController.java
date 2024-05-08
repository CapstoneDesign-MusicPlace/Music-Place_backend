package org.musicplace.member.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.member.dto.SignInSaveDto;
import org.musicplace.member.dto.SignInUpdateDto;
import org.musicplace.member.service.SignInService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signIn")
@RequiredArgsConstructor
public class SignInController {
    private final SignInService signInService;

    @PostMapping
    public void SignInSave(@RequestBody SignInSaveDto signInSaveDto) {
        signInService.SignInSave(signInSaveDto);
    }

    @PatchMapping("/{member_id}")
    public void SignInUpdate(@PathVariable String member_id, @RequestBody SignInUpdateDto signInUpdateDto) {
        signInService.SignInUpdate(member_id, signInUpdateDto);
    }

    @DeleteMapping("/{member_id}")
    public void SignInDelete(@PathVariable String member_id) {
        signInService.SignInDelete(member_id);
    }

    @GetMapping("/{member_id}/{email}/pw")
    public String ForgetPw(@PathVariable String member_id, @PathVariable String email) {
        return signInService.ForgetPw(member_id, email);
    }

    @GetMapping("/{pw}/{email}/id")
    public String ForgetId(@PathVariable String pw, @PathVariable String email) {
        return signInService.ForgetId(pw, email);
    }

    @GetMapping("/{member_id}/sameid")
    public Boolean SignInCheckSameId(@PathVariable String member_id) {
        return signInService.SignInCheckSameId(member_id);
    }
}
