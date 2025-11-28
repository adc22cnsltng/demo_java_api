package com.demoproject.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demoproject.demo.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
