package com.example.domain.user.repository;

import com.example.domain.payment.domain.Payment;
import com.example.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Id 중복 체크
    Optional<User> findByMail(String mail);
    @Query("SELECT DISTINCT p FROM User u JOIN FETCH u.paymentList p JOIN FETCH p.seatList ps WHERE u.id = :userId")
    Optional<Payment> findPaymentListByUserId(@Param("userId") Long id);

}
