package com.learnforge.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Direct SQL (JdbcTemplate) queries that calculate lesson completion.
 *
 * There is no progress table: a lesson is completed when quiz_results contains
 * a row with passed = TRUE for the student and that lesson.
 * Only lessons that actually have questions (i.e. have a quiz) are counted.
 */
@Repository
public class CompletionRepository {

    private static final String LESSON_HAS_QUIZ =
            "EXISTS (SELECT 1 FROM questions q WHERE q.lesson_id = l.id)";

    private final JdbcTemplate jdbcTemplate;

    public CompletionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Ids of lessons the student has passed. */
    public Set<Long> findPassedLessonIds(Long userId) {
        List<Long> ids = jdbcTemplate.queryForList(
                "SELECT DISTINCT lesson_id FROM quiz_results WHERE user_id = ? AND passed = TRUE",
                Long.class, userId);
        return new HashSet<>(ids);
    }

    /** Number of lessons that have a quiz. */
    public int countLessonsWithQuiz() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM lessons l WHERE " + LESSON_HAS_QUIZ, Integer.class);
        return count == null ? 0 : count;
    }

    /** Number of quiz lessons the student has NOT passed yet (0 = eligible for the certificate). */
    public int countLessonsStillToPass(Long userId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM lessons l WHERE " + LESSON_HAS_QUIZ
                        + " AND NOT EXISTS (SELECT 1 FROM quiz_results r"
                        + " WHERE r.lesson_id = l.id AND r.user_id = ? AND r.passed = TRUE)",
                Integer.class, userId);
        return count == null ? 0 : count;
    }

    /** For each module id: how many of its lessons the student has passed. */
    public Map<Long, Integer> countPassedLessonsPerModule(Long userId) {
        List<Map.Entry<Long, Integer>> rows = jdbcTemplate.query(
                "SELECT l.module_id, COUNT(DISTINCT l.id) FROM lessons l"
                        + " JOIN quiz_results r ON r.lesson_id = l.id"
                        + " WHERE r.user_id = ? AND r.passed = TRUE GROUP BY l.module_id",
                (rs, rowNum) -> Map.entry(rs.getLong(1), rs.getInt(2)),
                userId);
        Map<Long, Integer> result = new HashMap<>();
        for (Map.Entry<Long, Integer> row : rows) {
            result.put(row.getKey(), row.getValue());
        }
        return result;
    }
}
