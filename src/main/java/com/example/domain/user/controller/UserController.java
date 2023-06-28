package com.example.domain.user.controller;

import com.example.domain.user.domain.request.LoginRequestDto;
import com.example.domain.user.domain.request.UserUpdateRequestDto;
import com.example.domain.user.domain.request.SignUpRequestDto;
import com.example.domain.user.domain.response.LoginResponseDto;
import com.example.domain.user.domain.response.UserDetailResponseDto;
import com.example.domain.user.domain.response.SignUpResponseDto;
import com.example.domain.user.service.UserService;
import com.example.global.DuplicateIdException;
import com.example.global.NoRegisteredArgumentsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Validated @RequestBody SignUpRequestDto dto, BindingResult result) {
        log.info("movieworld/users/signup : POST 요청- {}", dto);

        if (result.hasErrors()) {

            log.warn(toString());
            return ResponseEntity.badRequest().body(result.getFieldError());

        }

        try {

            SignUpResponseDto signUpResponseDTO = userService.signUp(dto);
            return ResponseEntity.ok().body(signUpResponseDTO);

        } catch (NoRegisteredArgumentsException e) {

            log.warn("필수 가입 정보를 입력받지 못했습니다.");
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (DuplicateIdException e) {

            log.warn("중복된 Id 입니다.");
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@Validated @RequestBody LoginRequestDto dto){

        log.info("movieworld/users/login : POST 요청 - {}", dto);

        LoginResponseDto logedIn = userService.logIn(dto);

        return ResponseEntity.ok().body(logedIn);

    }

    // 마이페이지
    @GetMapping("/{usercode}")
    public ResponseEntity<?> userDetail(@PathVariable Long userCode){

        log.info("movieworld/users/mypage/{usercode} : GET 요청");

        UserDetailResponseDto dto = userService.userDetail(userCode);

        log.info("마이페이지 확인 시 리턴받는 dto - {}", dto);

        return ResponseEntity.ok().body(dto);

    }

    @PatchMapping("/{usercode}")
    public ResponseEntity<?> userUpdate(@RequestBody UserUpdateRequestDto dto, BindingResult result){

        log.info("movieworld/users/mypage/{usercode} : PATCH 요청 - {}", dto);

        userService.userUpdate(dto);

        return ResponseEntity.ok().body("");

    }


    @DeleteMapping("{usercode}")
    public ResponseEntity<?> withdrawal(@PathVariable Long userCode){

        boolean flag = userService.withdrawal(userCode);

        if(!flag){
            log.warn("회원탈퇴가 정상적으로 이루어지지 않았습니다.");
        }

        return ResponseEntity.ok().body("회원 탈퇴가 정상적으로 처리되었습니다.");
    }


}
