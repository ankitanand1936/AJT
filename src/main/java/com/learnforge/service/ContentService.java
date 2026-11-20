package com.learnforge.service;

import com.learnforge.entity.CourseModule;
import com.learnforge.entity.Lesson;
import com.learnforge.entity.Question;
import com.learnforge.repository.CourseModuleRepository;
import com.learnforge.repository.LessonRepository;
import com.learnforge.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** Reading and adding modules, lessons and questions. */
@Service
@Transactional
public class ContentService {

    private final CourseModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final QuestionRepository questionRepository;

    public ContentService(CourseModuleRepository moduleRepository, LessonRepository lessonRepository,
                          QuestionRepository questionRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.questionRepository = questionRepository;
    }

    // ---------- reading ----------

    @Transactional(readOnly = true)
    public List<CourseModule> getAllModules() {
        return moduleRepository.findAllByOrderByIdAsc();
    }

    @Transactional(readOnly = true)
    public CourseModule getModule(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found"));
    }

    @Transactional(readOnly = true)
    public Lesson getLesson(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestions(Long lessonId) {
        return questionRepository.findByLessonIdOrderByIdAsc(lessonId);
    }

    // ---------- adding (admin) ----------

    public CourseModule addModule(String title, String description) {
        title = clean(title);
        description = clean(description);
        if (title.isEmpty() || description.isEmpty()) {
            throw new IllegalArgumentException("Module title and description are required.");
        }
        if (moduleRepository.existsByTitle(title)) {
            throw new IllegalArgumentException("A module with this title already exists.");
        }
        return moduleRepository.save(new CourseModule(title, description));
    }

    public Lesson addLesson(Long moduleId, String title, String content, Integer lessonOrder) {
        title = clean(title);
        content = clean(content);
        if (title.isEmpty() || content.isEmpty()) {
            throw new IllegalArgumentException("Lesson title and content are required.");
        }
        CourseModule module = getModule(moduleId);
        if (lessonRepository.existsByModuleIdAndTitle(moduleId, title)) {
            throw new IllegalArgumentException("This module already has a lesson with that title.");
        }
        int order = (lessonOrder != null) ? lessonOrder : module.getLessons().size() + 1;
        return lessonRepository.save(new Lesson(module, title, content, order));
    }

    public Question addQuestion(Long lessonId, String text, String a, String b, String c, String d,
                                String correctOption) {
        text = clean(text);
        a = clean(a);
        b = clean(b);
        c = clean(c);
        d = clean(d);
        if (text.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
            throw new IllegalArgumentException("The question and all four options (A, B, C, D) are required.");
        }
        String correct = clean(correctOption).toUpperCase();
        if (!correct.matches("[ABCD]")) {
            throw new IllegalArgumentException("The correct option must be A, B, C or D.");
        }
        Lesson lesson = getLesson(lessonId);
        return questionRepository.save(new Question(lesson, text, a, b, c, d, correct));
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
