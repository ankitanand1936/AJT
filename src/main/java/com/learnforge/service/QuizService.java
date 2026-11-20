package com.learnforge.service;

import com.learnforge.entity.Lesson;
import com.learnforge.entity.Question;
import com.learnforge.entity.QuizResult;
import com.learnforge.entity.User;
import com.learnforge.repository.QuizResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/** Marks a quiz, saves the attempt in quiz_results and issues the certificate when due. */
@Service
public class QuizService {

    /** A quiz is passed with at least this percentage. */
    public static final int PASS_PERCENTAGE = 60;

    private final QuizResultRepository quizResultRepository;
    private final CertificateService certificateService;

    public QuizService(QuizResultRepository quizResultRepository, CertificateService certificateService) {
        this.quizResultRepository = quizResultRepository;
        this.certificateService = certificateService;
    }

    /**
     * @param answers request parameters; the answer to question 7 is in the parameter "q_7" (A, B, C or D)
     */
    @Transactional
    public QuizResult submitQuiz(User user, Lesson lesson, List<Question> questions, Map<String, String> answers) {
        int score = 0;
        for (Question question : questions) {
            String chosen = answers.get("q_" + question.getId());
            if (chosen != null && chosen.equalsIgnoreCase(question.getCorrectOption())) {
                score++;
            }
        }
        int total = questions.size();
        boolean passed = total > 0 && score * 100 >= PASS_PERCENTAGE * total;

        QuizResult result = quizResultRepository.save(new QuizResult(user, lesson, score, total, passed));

        if (passed) {
            certificateService.issueIfEligible(user);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<QuizResult> getResultsForUser(Long userId) {
        return quizResultRepository.findByUserIdOrderByAttemptedAtDesc(userId);
    }
}
