package com.example.domain.user.service;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.Role;
import com.example.domain.user.domain.request.UpgradeUserRankRequestDto;
import com.example.domain.user.domain.request.UpdateUserRequestDto;
import com.example.domain.user.domain.response.LoginResponseDto;
import com.example.domain.user.domain.request.LoginRequestDto;
import com.example.domain.user.domain.request.SignUpRequestDto;
import com.example.domain.user.domain.response.UserDetailResponseDto;
import com.example.domain.user.domain.response.SignUpResponseDto;
import com.example.domain.user.domain.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.global.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public SignUpResponseDto signUp(final SignUpRequestDto dto) {

        isDuplicateMail(dto.getMail());

        encodePassword(dto);

        User saved = userRepository.save(dto.toEntity(dto));

        return new SignUpResponseDto(saved);

    }

    public LoginResponseDto logIn(LoginRequestDto dto) {

        User user = findByEmail(dto.getMail());

        checkPassword(dto, user.getPassword());

        return new LoginResponseDto(user);

    }

    public UserDetailResponseDto getUserDetail(Long id) {

        return getUserDetailResponseDto(id);

    }

    public UserDetailResponseDto updateUser(UpdateUserRequestDto dto, Long id) {

        updateUserInfo(dto, findById(id));

        return getUserDetailResponseDto(id);

    }

    public boolean signOut(Long id) {

        userRepository.delete(findById(id));

        return true;

    }

    public boolean upgradeUserRank(UpgradeUserRankRequestDto dto) {

        User user = findById(dto.getId());

        return user.upgradeRank(checkRoleAndRank(dto.getRole(), dto.getNewRank(), user.getRank()));

    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND.getMessage(), USER_NOT_FOUND));

    }

    private User findByEmail(String mail) {
        return userRepository.findByMail(mail)
                .orElseThrow(() -> new CustomException(MAIL_NOT_FOUND.getMessage(), MAIL_NOT_FOUND));

    }

    private void checkPassword(LoginRequestDto dto, String password) {

        if (!encoder.matches(dto.getPassword(), password)) {
            throw new CustomException(WRONG_PASSWORD.getMessage(), WRONG_PASSWORD);
        }
    }

    private void isDuplicateMail(String mail) {
        userRepository.findByMail(mail)
                .ifPresent(x -> {
                    throw new CustomException(DUPLICATE_MAIL.getMessage(), DUPLICATE_MAIL);
                });
    }

    private void encodePassword(SignUpRequestDto dto) {
        String encoded = encoder.encode(dto.getPassword());
        dto.setUserPw(encoded);
    }

    private UserDetailResponseDto getUserDetailResponseDto(Long id) {
        System.out.println("id = " + id);
        return userRepository.findById(id).map(UserDetailResponseDto::new)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND.getMessage(), USER_NOT_FOUND));
    }

    private void updateUserInfo(UpdateUserRequestDto dto, User foundUser) {
        String encoded = encoder.encode(dto.getPassword());
        foundUser.updateUser(dto);
    }


    private static Rank checkRoleAndRank(Role role, Rank newRank, Rank rawRank) {

        if (!role.equals(Role.ADMIN)) {
            throw new CustomException(NOT_ALLOWED.getMessage(), NOT_ALLOWED);

        } else if (rawRank.equals(newRank)) {
            throw new CustomException(SAME_RANK.getMessage(), SAME_RANK);

        }
        return newRank;
    }
}
