package com.example.kinoprokatrest.repositories;

import com.example.kinoprokatrest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    public User getUserByUsername(String username);

    boolean existsByUsername(String username);
}
