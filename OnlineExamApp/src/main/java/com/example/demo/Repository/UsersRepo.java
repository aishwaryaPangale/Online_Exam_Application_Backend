package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Users;

import java.util.Optional;
import java.util.*;

public interface UsersRepo extends JpaRepository<Users, Long> {
	 Optional<Users> findByEmail(String email);
}

