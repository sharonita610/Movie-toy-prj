package com.example.domain.user.service;

import com.example.domain.user.domain.request.UserUpdateRequestDto;
import com.example.domain.user.domain.response.LoginResponseDto;
import com.example.domain.user.domain.request.LoginRequestDto;
import com.example.domain.user.domain.request.SignUpRequestDto;
import com.example.domain.user.domain.response.UserDetailResponseDto;
import com.example.domain.user.domain.response.SignUpResponseDto;
import com.example.domain.user.domain.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.DuplicateIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    // 회원가입
    public SignUpResponseDto signUp(final SignUpRequestDto dto) {

        String mail = dto.getMail();

        isDuplicateMail(mail);

        encodePassword(dto);

        User saved = userRepository.save(dto.toEntity(dto));

        return new SignUpResponseDto(saved);

    }

    // 로그인
    public LoginResponseDto logIn(LoginRequestDto dto) {

        User foundUser = findByEmail(dto);

        checkPassword(dto, foundUser);

        return new LoginResponseDto(foundUser);

    }

    // 마이페이지
    public UserDetailResponseDto userDetail(Long userCode) {

        UserDetailResponseDto userDetail = userRepository.findById(userCode).map(UserDetailResponseDto::new)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        return userDetail;

    }


    public void userUpdate(UserUpdateRequestDto dto) {





    }

    public boolean withdrawal(Long userCode) {

        User foundUser = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("삭제하고자 하는 사용자가 없습니다"));

        userRepository.delete(foundUser);

        return true;

    }

    private User findByEmail(LoginRequestDto dto) {
        return userRepository.findByMail(dto.getMail())
                .orElseThrow(() -> new RuntimeException("다시 확인하세요"));

    }

    private void checkPassword(LoginRequestDto dto, User foundUser) {
        String password = dto.getPassword();
        String encodedPassword = foundUser.getPassword();

        if (!encoder.matches(password, encodedPassword)) {
            log.warn("비밀번호가 일치하지 않습니다.");
            throw new RuntimeException();
        }
    }

    private void isDuplicateMail(String mail) {
        userRepository.findByMail(mail)
                .ifPresent( x -> {
                    throw new DuplicateIdException();
                });
    }

    private void encodePassword(SignUpRequestDto dto) {
        String encoded = encoder.encode(dto.getPassword());
        dto.setUserPw(encoded);
    }

}
