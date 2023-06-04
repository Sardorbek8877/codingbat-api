package com.codingbatapi.repository;

import com.codingbatapi.entity.Answer;
import com.codingbatapi.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}
