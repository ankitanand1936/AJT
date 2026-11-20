package com.learnforge.repository;

import com.learnforge.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {

    List<CourseModule> findAllByOrderByIdAsc();

    boolean existsByTitle(String title);
}
