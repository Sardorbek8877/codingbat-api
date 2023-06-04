package com.codingbatapi.repository;

import com.codingbatapi.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

    boolean existsByName(String name);
}
