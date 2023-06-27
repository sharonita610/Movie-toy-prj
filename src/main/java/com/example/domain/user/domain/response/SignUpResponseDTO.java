package com.example.domain.user.domain.response;


import com.example.domain.user.domain.User;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponseDTO {

    private String userId;
    private String userName;

    public SignUpResponseDTO(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
    }
}
