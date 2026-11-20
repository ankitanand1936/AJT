package com.learnforge.repository;

import com.learnforge.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByLessonIdOrderByIdAsc(Long lessonId);
}
