package com.example.domain.user.domain.response;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageResponseDTO {

    private String userName;
    private LocalDate userBirthdate;
    private String userPhone;
    private String userId;
    private Rank userRank;


    public MyPageResponseDTO(User user) {
        this.userName = user.getUserName();
        this.userBirthdate = user.getUserBirthdate();
        this.userPhone = user.getUserPhone();
        this.userId = user.getId();
        this.userRank = user.getUserRank();
    }
}
