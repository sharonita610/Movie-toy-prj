package com.example.domain.user.service;

import com.example.domain.user.domain.request.LoginRequestDTO;
import com.example.domain.user.domain.request.SignUpRequestDTO;
import com.example.domain.user.domain.response.LoginResponseDTO;
import com.example.domain.user.domain.response.MyPageResponseDTO;
import com.example.domain.user.domain.response.SignUpResponseDTO;
import com.example.global.DuplicateIdException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserServiceTest {


    @Autowired
    UserService userService;



    @Test
    @DisplayName("중복된 아이디로 회원가입을 시도하면 에러가 뜨는가?")
    void signUpTest() {

        // given
        String id = "admin";
        SignUpRequestDTO dto = SignUpRequestDTO.builder()
                .userName("dkdk")
                .userPw("!23")
                .userId(id).build();


        // when


        // then
        assertThrows(DuplicateIdException.class,
                () -> {userService.signUp(dto);}
        );
    }


    @Test
    @DisplayName("회원가입이 정상적으로 이루어지고 비밀번호가 인코딩 되는가?")
    void signUpPwTest() {

        // given

        SignUpRequestDTO dto = SignUpRequestDTO.builder()
                .userName("김코코")
                .userPw("1234")
                .userId("coco")
                .userPhone("1111")
                .build();


        // when
        SignUpResponseDTO flag = userService.signUp(dto);

        // then
        System.out.println("flag = " + flag);

    }

    @Test
    @DisplayName("로그인이 정상적으로 실행되는가?")
    void loginTest() {

        // given

        LoginRequestDTO dto = LoginRequestDTO.builder()
                .userPw("1234")
                .userId("coco")
                .build();


        // when
        LoginResponseDTO logedIn = userService.logIn(dto);

        // then
//        Assertions.assertThat();
        System.out.println("flag = " + logedIn);

    }

    @Test
    @DisplayName("회원 아이디로 마이 페이지를 조회 할 수 있어야한다")
    void myPageTest() {

        // given

        Long id = 1L;


        // when
        MyPageResponseDTO dto = userService.myPage(id);
        System.out.println("dto = " + dto);


        // then
        assertEquals("홍길동", dto.getUserName());
    }

    @Test
    @DisplayName("회원 아이디로 회원 탈퇴를 할 수 있어야 한다.")
    void withdrawTest() {

        // given
        Long id = 6L;


        // when
        boolean flag = userService.withdrawal(id);

        if(!flag){
            System.out.println("flag = " + flag);
        }

        // then
        assertTrue(flag);
    }

    @Test
    @DisplayName("잘못된 회원 코드로 회원 탈퇴를 할 수 없어야 한다.")
    void withdrawFalseTest() {

        // given
        Long id = 6L;


        // when
        boolean flag = userService.withdrawal(id);

        if(!flag){
            System.out.println("flag = " + flag);
        }

        // then
        assertFalse(flag);
    }

}