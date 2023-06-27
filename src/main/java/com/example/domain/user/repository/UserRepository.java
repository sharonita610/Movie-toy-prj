package com.example.domain.user.repository;

import com.example.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    // Id 중복 체크
    boolean existsUserByUserId(String userId);

    // PK(Long) 이 아닌 String(ID)로 로그인 해서 메서드를 만듬
    Optional<User> findByUserId(String userId);

}
