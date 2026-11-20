package com.learnforge.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Table: lessons
 * modules -> lessons (one-to-many), lessons -> questions (one-to-many),
 * lessons -> quiz_results (one-to-many).
 * The quiz of a lesson is simply the set of its questions (there is no quizzes table).
 */
@Entity
@Table(name = "lessons",
        uniqueConstraints = @UniqueConstraint(name = "uk_lesson_module_title", columnNames = {"module_id", "title"}))
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private CourseModule module;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "lesson_order", nullable = false)
    private int lessonOrder;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "lesson")
    private List<QuizResult> quizResults = new ArrayList<>();

    public Lesson() {
    }

    public Lesson(CourseModule module, String title, String content, int lessonOrder) {
        this.module = module;
        this.title = title;
        this.content = content;
        this.lessonOrder = lessonOrder;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CourseModule getModule() { return module; }
    public void setModule(CourseModule module) { this.module = module; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getLessonOrder() { return lessonOrder; }
    public void setLessonOrder(int lessonOrder) { this.lessonOrder = lessonOrder; }
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
    public List<QuizResult> getQuizResults() { return quizResults; }
    public void setQuizResults(List<QuizResult> quizResults) { this.quizResults = quizResults; }
}
