package com.example.domain.user.repository;

import com.example.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Id 중복 체크
    Optional<User> findByMail(String mail);


}
