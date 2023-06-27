package com.example.domain.user.controller;

import com.example.domain.user.domain.request.LoginRequestDTO;
import com.example.domain.user.domain.request.MyPageChangeInfoRequestDTO;
import com.example.domain.user.domain.request.SignUpRequestDTO;
import com.example.domain.user.domain.response.LoginResponseDTO;
import com.example.domain.user.domain.response.MyPageResponseDTO;
import com.example.domain.user.domain.response.SignUpResponseDTO;
import com.example.domain.user.service.UserService;
import com.example.global.DuplicateIdException;
import com.example.global.NoRegisteredArgumentsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("movieworld/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Validated @RequestBody SignUpRequestDTO dto, BindingResult result) {
        log.info("movieworld/users/signup : POST 요청- {}", dto);

        if (result.hasErrors()) {

            log.warn(toString());
            return ResponseEntity.badRequest().body(result.getFieldError());

        }

        try {

            SignUpResponseDTO signUpResponseDTO = userService.signUp(dto);
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
    public ResponseEntity<?> signIn(@Validated @RequestBody LoginRequestDTO dto, BindingResult result){

        log.info("movieworld/users/login : POST 요청 - {}", dto);

        LoginResponseDTO logedIn = userService.logIn(dto);

        return ResponseEntity.ok().body(logedIn);

    }

    // 마이페이지
    @GetMapping("/mypage/{usercode}")
    public ResponseEntity<?> myPage(@PathVariable Long userCode){

        log.info("movieworld/users/mypage/{usercode} : GET 요청");

        MyPageResponseDTO dto = userService.myPage(userCode);

        log.info("마이페이지 확인 시 리턴받는 dto - {}", dto);

        return ResponseEntity.ok().body(dto);

    }

    @PatchMapping("/mypage/{usercode}")
    public ResponseEntity<?> modifyMyInfo(@RequestBody MyPageChangeInfoRequestDTO dto, BindingResult result){

        log.info("movieworld/users/mypage/{usercode} : PATCH 요청 - {}", dto);

        userService.modifyMyInfo(dto);

        return ResponseEntity.ok().body("");

    }


    @DeleteMapping("mypage/{usercode}")
    public ResponseEntity<?> withdrawal(@PathVariable Long userCode){

        boolean flag = userService.withdrawal(userCode);

        if(!flag){
            log.warn("회원탈퇴가 정상적으로 이루어지지 않았습니다.");
        }

        return ResponseEntity.ok().body("회원 탈퇴가 정상적으로 처리되었습니다.");
    }


}
