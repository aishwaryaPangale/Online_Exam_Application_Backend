package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Otp;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

//    Optional<Otp> findByEmail(String email); // Fetch OTP by Email
//    void deleteByEmail(String email); // Delete OTP after verification
	
    Optional<Otp> findTopByEmailOrderByExpiryTimeDesc(String email);  // Get only latest OTP

}
