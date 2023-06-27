package com.example.domain.user.domain.response;

import com.example.domain.user.domain.Rank;
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




}
