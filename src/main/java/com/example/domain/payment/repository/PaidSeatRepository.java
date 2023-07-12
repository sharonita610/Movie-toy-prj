package com.example.domain.payment.repository;

import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.response.PaidSeatResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaidSeatRepository extends JpaRepository<PaidSeat, Long> {

//    List<PaidSeatResponseDto> findAllByPayId(Long payId);
}
