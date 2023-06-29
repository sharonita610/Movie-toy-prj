package com.example.domain.user.service;

import com.example.domain.user.domain.request.LoginRequestDto;
import com.example.domain.user.domain.request.SignUpRequestDto;
import com.example.domain.user.domain.request.UpdateUserRequestDto;
import com.example.domain.user.domain.response.LoginResponseDto;
import com.example.domain.user.domain.response.UserDetailResponseDto;
import com.example.domain.user.domain.response.SignUpResponseDto;
import com.example.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;


import java.time.LocalDate;

import static org.junit.Assert.*;

@SpringBootTest

@Transactional
@Rollback(value = true)
class UserServiceTest {


    @Autowired
    UserService userService;

    @BeforeEach
    void signUpBefore() {

        SignUpRequestDto dto = SignUpRequestDto.builder()
                .name("admin")
                .password("!23")
                .mail("admin")
                .build();
        userService.signUp(dto);

        System.out.println("\n\n\n");
        System.out.println("dto = " + dto);
    }

    @Test
    @DisplayName("회원가입이 정상적으로 이루어지고 비밀번호가 인코딩이 되어야한다.")
    void signUpPwTest() {

        // given
        SignUpRequestDto dto = SignUpRequestDto.builder()
                .name("나나1")
                .password("111111")
                .mail("admin")
                .build();


        // when
        SignUpResponseDto flag = userService.signUp(dto);

        // then
        assertEquals("나나1", flag.getName());

    }

    @Test
    @DisplayName("중복된 아이디로 회원가입을 시도하면 에러가 떠야한다.")
    void signUpTest() {

        // given
        SignUpRequestDto dto = SignUpRequestDto.builder()
                .name("admin")
                .password("!23")
                .mail("admin")
                .build();

        // when


        // then
        assertThrows(CustomException.class,
                () -> {
                    userService.signUp(dto);
                }
        );
    }


    @Test
    @DisplayName("로그인이 정상적으로 실행되어야한다.")
    void loginTest() {

        // given
        LoginRequestDto dto = LoginRequestDto.builder()
                .mail("admi")
                .password("!23")
                .build();


        // when
        LoginResponseDto logedIn = userService.logIn(dto);

        System.out.println("logedIn = " + logedIn);
        // then
        assertEquals("admin", logedIn.getName());

    }

    @Test
    @DisplayName("회원정보를 수정하면 db에 반영 되어야 한다.")
    void updateUsertest() {

        // given
        Long id = 2L;

        UpdateUserRequestDto dto = UpdateUserRequestDto.builder()
                .name("안녕")
                .password("22555555")
                .mail("hello1")
                .birthdate(LocalDate.parse("1999-10-10"))
                .phone("010-222-1111")
                .build();


        // when
        UserDetailResponseDto responseDto = userService.updateUser(dto, id);

        // then
        assertEquals("hello1", responseDto.getMail());
    }

    @Test
    @DisplayName("회원 아이디로 마이 페이지를 조회 할 수 있어야한다.")
    void myPageTest() {

        // given
        Long id = 1L;

        // when
        UserDetailResponseDto dto = userService.getUserDetail(id);
        System.out.println("dto = " + dto);


        // then
        assertEquals("admin", dto.getName());
    }

    @Test
    @DisplayName("회원 아이디로 회원 탈퇴를 할 수 있어야 한다.")
    void withdrawTest() {

        // given
        Long id = 1L;

        // when
        boolean flag = userService.signOut(id);

        // then
        assertTrue(flag);
    }

    @Test
    @DisplayName("잘못된 회원 코드로 회원 탈퇴를 시도하면 에러가 발생한다.")
    void withdrawFalseTest() {

        // given
        Long id = 6L;


        // then
        assertThrows(CustomException.class,
                () -> {
                    userService.signOut(id);
                }
        );

    }


}