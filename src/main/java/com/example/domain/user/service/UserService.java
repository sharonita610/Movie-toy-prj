package com.example.domain.user.service;

import com.example.domain.user.domain.request.MyPageChangeInfoRequestDTO;
import com.example.domain.user.domain.response.LoginResponseDTO;
import com.example.domain.user.domain.request.LoginRequestDTO;
import com.example.domain.user.domain.request.SignUpRequestDTO;
import com.example.domain.user.domain.response.MyPageResponseDTO;
import com.example.domain.user.domain.response.SignUpResponseDTO;
import com.example.domain.user.domain.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.DuplicateIdException;
import com.example.global.NoRegisteredArgumentsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    // 회원가입
    public SignUpResponseDTO signUp(final SignUpRequestDTO dto) {

        if (dto == null) {
            // 에러 타입을 확인하기 위해 사용자 정의 예외 생성
            throw new NoRegisteredArgumentsException("가입 정보가 없습니다");
        }

        String userId = dto.getUserId();

        if (userRepository.existsUserByUserId(userId)) {

            log.warn("Id 가 중복되었습니다. - {}", userId);
            throw new DuplicateIdException("중복된 Id 입니다. 다른 Id를 입력해주세요");

        }

        // 패스워드 인코딩
        String encoded = encoder.encode(dto.getUserPw());
        dto.setUserPw(encoded);

        // 유저 엔티티로 변환
        User user = dto.toEntity(dto);
        User saved = userRepository.save(user);

        log.info("회원가입이 정상적으로 처리되었습니다. -{}", saved);

        return new SignUpResponseDTO(saved);
    }


    // 로그인
    public LoginResponseDTO logIn(LoginRequestDTO dto) {

        if (dto == null) {
            log.warn("로그인 정보를 다시 확인해주세요");
            throw new RuntimeException();
        }

        User foundUser = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("가입된 회원이 아닙니다."));

        String password = dto.getUserPw();
        String encodedPassword = foundUser.getUserPw();

        if (!encoder.matches(password, encodedPassword)) {
            log.warn("비밀번호가 일치하지 않습니다.");
            throw new RuntimeException();
        }

        log.info("로그인이 정상적으로 처리되었습니다.");

        return new LoginResponseDTO();

    }

    public MyPageResponseDTO myPage(Long userCode) {

        User foundUser = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        return MyPageResponseDTO.builder()
                .userName(foundUser.getUserName())
                .userId(foundUser.getUserId())
                .userBirthdate(foundUser.getUserBirthdate())
                .userRank(foundUser.getUserRank())
                .userPhone(foundUser.getUserPhone()).build();


    }

    public void modifyMyInfo(MyPageChangeInfoRequestDTO dto) {





    }

    public boolean withdrawal(Long userCode) {

        User foundUser = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("삭제하고자 하는 사용자가 없습니다"));

        userRepository.delete(foundUser);

        return true;

    }

}
