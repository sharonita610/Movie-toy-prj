package com.example.domain.user.domain.response;

import com.example.domain.user.domain.Rank;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {


    private String userName;
    private Rank userRank = Rank.STANDARD;
    private String userPhone;




}
