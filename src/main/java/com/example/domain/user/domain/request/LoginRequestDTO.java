package com.example.domain.user.domain.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class LoginRequestDTO {

    @NotBlank
    private String userId;

    @NotBlank
    private String userPw;
}
