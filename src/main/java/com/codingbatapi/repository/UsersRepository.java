package com.codingbatapi.repository;

import com.codingbatapi.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    boolean existsByEmail(String email);
}
