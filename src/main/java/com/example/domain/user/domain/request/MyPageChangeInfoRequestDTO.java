package com.example.domain.user.domain.request;

import com.example.domain.user.domain.Rank;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageChangeInfoRequestDTO {


    private String userName;
    private LocalDate userBirthdate;
    private String userPhone;
    private String userId;
    private String userPw;


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserBirthdate(LocalDate userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
}
