package com.learnforge.controller;

import com.learnforge.entity.CourseModule;
import com.learnforge.entity.Lesson;
import com.learnforge.entity.Question;
import com.learnforge.entity.QuizResult;
import com.learnforge.entity.User;
import com.learnforge.repository.CompletionRepository;
import com.learnforge.service.CertificateService;
import com.learnforge.service.ContentService;
import com.learnforge.service.QuizService;
import com.learnforge.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** All pages for logged-in students: dashboard, modules, lessons, quizzes, results, certificate. */
@Controller
public class StudentController {

    private final UserService userService;
    private final ContentService contentService;
    private final QuizService quizService;
    private final CertificateService certificateService;
    private final CompletionRepository completionRepository;

    public StudentController(UserService userService, ContentService contentService, QuizService quizService,
                             CertificateService certificateService, CompletionRepository completionRepository) {
        this.userService = userService;
        this.contentService = contentService;
        this.quizService = quizService;
        this.certificateService = certificateService;
        this.completionRepository = completionRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        User user = userService.getByEmail(principal.getName());
        int totalQuizLessons = completionRepository.countLessonsWithQuiz();
        int stillToPass = completionRepository.countLessonsStillToPass(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("modules", contentService.getAllModules());
        model.addAttribute("passedPerModule", completionRepository.countPassedLessonsPerModule(user.getId()));
        model.addAttribute("totalQuizLessons", totalQuizLessons);
        model.addAttribute("passedLessons", totalQuizLessons - stillToPass);
        model.addAttribute("certificate", certificateService.findForUser(user.getId()).orElse(null));
        return "dashboard";
    }

    @GetMapping("/modules/{id}")
    public String module(@PathVariable Long id, Principal principal, Model model) {
        User user = userService.getByEmail(principal.getName());
        CourseModule module = contentService.getModule(id);

        model.addAttribute("module", module);
        model.addAttribute("passedIds", completionRepository.findPassedLessonIds(user.getId()));
        return "module";
    }

    @GetMapping("/lessons/{id}")
    public String lesson(@PathVariable Long id, Principal principal, Model model) {
        User user = userService.getByEmail(principal.getName());
        Lesson lesson = contentService.getLesson(id);

        model.addAttribute("lesson", lesson);
        model.addAttribute("completed", completionRepository.findPassedLessonIds(user.getId()).contains(id));
        return "lesson";
    }

    @GetMapping("/lessons/{id}/quiz")
    public String quiz(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Lesson lesson = contentService.getLesson(id);
        List<Question> questions = contentService.getQuestions(id);
        if (questions.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "This lesson does not have a quiz yet.");
            return "redirect:/lessons/" + id;
        }

        model.addAttribute("lesson", lesson);
        model.addAttribute("questions", questions);
        model.addAttribute("passPercentage", QuizService.PASS_PERCENTAGE);
        return "quiz";
    }

    @PostMapping("/lessons/{id}/quiz")
    public String submitQuiz(@PathVariable Long id,
                             @RequestParam Map<String, String> params,
                             Principal principal, Model model) {
        User user = userService.getByEmail(principal.getName());
        Lesson lesson = contentService.getLesson(id);
        List<Question> questions = contentService.getQuestions(id);
        if (questions.isEmpty()) {
            return "redirect:/lessons/" + id;
        }

        QuizResult result = quizService.submitQuiz(user, lesson, questions, params);

        // the student's chosen letter for every question, to show the answer review
        Map<Long, String> answers = new HashMap<>();
        for (Question question : questions) {
            answers.put(question.getId(), params.get("q_" + question.getId()));
        }

        model.addAttribute("lesson", lesson);
        model.addAttribute("result", result);
        model.addAttribute("questions", questions);
        model.addAttribute("answers", answers);
        model.addAttribute("passPercentage", QuizService.PASS_PERCENTAGE);
        model.addAttribute("certificate", certificateService.findForUser(user.getId()).orElse(null));
        return "quiz-result";
    }

    @GetMapping("/results")
    public String results(Principal principal, Model model) {
        User user = userService.getByEmail(principal.getName());
        model.addAttribute("results", quizService.getResultsForUser(user.getId()));
        return "results";
    }

    @GetMapping("/certificate")
    public String certificate(Principal principal, Model model) {
        User user = userService.getByEmail(principal.getName());
        int totalQuizLessons = completionRepository.countLessonsWithQuiz();
        int stillToPass = completionRepository.countLessonsStillToPass(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("certificate", certificateService.issueIfEligible(user).orElse(null));
        model.addAttribute("totalQuizLessons", totalQuizLessons);
        model.addAttribute("passedLessons", totalQuizLessons - stillToPass);
        return "certificate";
    }
}
