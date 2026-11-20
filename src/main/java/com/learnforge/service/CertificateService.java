package com.learnforge.service;

import com.learnforge.entity.Certificate;
import com.learnforge.entity.User;
import com.learnforge.repository.CertificateRepository;
import com.learnforge.repository.CompletionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final CompletionRepository completionRepository;

    public CertificateService(CertificateRepository certificateRepository,
                              CompletionRepository completionRepository) {
        this.certificateRepository = certificateRepository;
        this.completionRepository = completionRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Certificate> findForUser(Long userId) {
        return certificateRepository.findByUserId(userId);
    }

    /**
     * Issues the certificate if the student has passed the quiz of every lesson
     * (completion is calculated from quiz_results with JdbcTemplate).
     * Returns the existing or new certificate, or empty if the student is not eligible yet.
     */
    @Transactional
    public Optional<Certificate> issueIfEligible(User user) {
        Optional<Certificate> existing = certificateRepository.findByUserId(user.getId());
        if (existing.isPresent()) {
            return existing;
        }
        boolean hasQuizzes = completionRepository.countLessonsWithQuiz() > 0;
        boolean allPassed = completionRepository.countLessonsStillToPass(user.getId()) == 0;
        if (!hasQuizzes || !allPassed) {
            return Optional.empty();
        }
        String code = "LF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return Optional.of(certificateRepository.save(new Certificate(user, code)));
    }
}
