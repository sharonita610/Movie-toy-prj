package com.example.domain.user.domain.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class LoginRequestDto {

    @NotBlank
    private String mail;

    @NotBlank
    private String password;
}
