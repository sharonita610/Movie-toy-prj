package com.example.domain.user.domain;

import com.example.domain.user.domain.request.LoginRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class LoginRequestDTO{
        @NotBlank
        private String userId;

        @NotBlank
        private String userPw;
    }
}
