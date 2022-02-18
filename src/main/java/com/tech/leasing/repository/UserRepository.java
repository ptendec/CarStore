package com.tech.leasing.repository;

import com.tech.leasing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByPhone(String phone);
}
