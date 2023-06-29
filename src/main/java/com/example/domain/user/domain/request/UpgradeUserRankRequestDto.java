package com.example.domain.user.domain.request;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Getter
@AllArgsConstructor
public class UpgradeUserRankRequestDto {

    @NotBlank
    private Long id;

    @NotBlank
    private Role role;

    @NotBlank
    private Rank newRank;

}
