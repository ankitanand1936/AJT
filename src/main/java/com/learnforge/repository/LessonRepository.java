package com.learnforge.repository;

import com.learnforge.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    boolean existsByModuleIdAndTitle(Long moduleId, String title);
}
