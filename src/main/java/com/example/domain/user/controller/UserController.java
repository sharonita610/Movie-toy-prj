package com.example.domain.user.controller;

import com.example.domain.user.domain.request.LoginRequestDto;
import com.example.domain.user.domain.request.UpgradeUserRankRequestDto;
import com.example.domain.user.domain.request.UpdateUserRequestDto;
import com.example.domain.user.domain.request.SignUpRequestDto;
import com.example.domain.user.domain.response.UserDetailResponseDto;
import com.example.domain.user.domain.response.SignUpResponseDto;
import com.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Validated @RequestBody SignUpRequestDto dto, BindingResult result) {
            SignUpResponseDto signUpDto = userService.signUp(dto);
            return ResponseEntity.ok().body(signUpDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@Validated @RequestBody LoginRequestDto dto){
        return ResponseEntity.ok().body(userService.logIn(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponseDto> getUserDetail(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getDetail(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDetailResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDto dto){
        return ResponseEntity.ok().body(userService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> signOut(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.signOut(id));
    }

    @PatchMapping
    public ResponseEntity<Boolean> upgradeRank(@Validated UpgradeUserRankRequestDto dto){
        return ResponseEntity.ok().body(userService.upgradeRank(dto));
    }

}
