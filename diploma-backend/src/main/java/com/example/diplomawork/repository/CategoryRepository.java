package com.example.diplomawork.repository;

import com.example.diplomawork.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
