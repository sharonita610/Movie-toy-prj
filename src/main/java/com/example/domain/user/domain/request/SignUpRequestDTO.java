package com.example.domain.user.domain.request;

import com.example.domain.user.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {

    @NotBlank
    private String userName;
    private LocalDate userBirthdate;
    private String userPhone;
    @NotBlank
    private String userId;
    @NotBlank
    private String userPw;


    public User toEntity(SignUpRequestDTO dto) {
        return User.builder()
                .userName(dto.getUserName())
                .userPhone(dto.getUserPhone())
                .userBirthdate(dto.getUserBirthdate())
                .userId(dto.getUserId())
                .userPw(dto.getUserPw())
                .build();
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
}
