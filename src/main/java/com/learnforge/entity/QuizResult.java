package com.learnforge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Table: quiz_results
 * users -> quiz_results and lessons -> quiz_results (both one-to-many).
 * Every attempt is stored as its own row. A lesson is "completed" when at least
 * one row with passed = TRUE exists for the user and the lesson.
 */
@Entity
@Table(name = "quiz_results")
public class QuizResult {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "total_questions", nullable = false)
    private int totalQuestions;

    @Column(name = "passed", nullable = false)
    private boolean passed;

    @Column(name = "attempted_at", nullable = false)
    private LocalDateTime attemptedAt;

    public QuizResult() {
    }

    public QuizResult(User user, Lesson lesson, int score, int totalQuestions, boolean passed) {
        this.user = user;
        this.lesson = lesson;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.passed = passed;
        this.attemptedAt = LocalDateTime.now();
    }

    /** Percentage score (not stored in the database). */
    public int getPercentage() {
        return totalQuestions == 0 ? 0 : Math.round(score * 100f / totalQuestions);
    }

    /** Readable date for JSP pages (not stored in the database). */
    public String getAttemptedAtText() {
        return attemptedAt == null ? "" : attemptedAt.format(FORMAT);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
    public LocalDateTime getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; }
}
