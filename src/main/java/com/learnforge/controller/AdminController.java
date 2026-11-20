package com.learnforge.controller;

import com.learnforge.entity.Lesson;
import com.learnforge.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Simple admin panel: add modules, lessons and MCQ questions, and view them. */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ContentService contentService;

    public AdminController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public String panel(Model model) {
        model.addAttribute("modules", contentService.getAllModules());
        return "admin";
    }

    @PostMapping("/modules")
    public String addModule(@RequestParam String title,
                            @RequestParam String description,
                            RedirectAttributes redirectAttributes) {
        try {
            contentService.addModule(title, description);
            redirectAttributes.addFlashAttribute("message", "Module added.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/lessons")
    public String addLesson(@RequestParam Long moduleId,
                            @RequestParam String title,
                            @RequestParam String content,
                            @RequestParam(required = false) Integer lessonOrder,
                            RedirectAttributes redirectAttributes) {
        try {
            contentService.addLesson(moduleId, title, content, lessonOrder);
            redirectAttributes.addFlashAttribute("message", "Lesson added.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/lessons/{id}")
    public String lessonDetails(@PathVariable Long id, Model model) {
        Lesson lesson = contentService.getLesson(id);
        model.addAttribute("lesson", lesson);
        model.addAttribute("questions", contentService.getQuestions(id));
        return "admin-lesson";
    }

    @PostMapping("/lessons/{id}/questions")
    public String addQuestion(@PathVariable Long id,
                              @RequestParam String questionText,
                              @RequestParam String optionA,
                              @RequestParam String optionB,
                              @RequestParam String optionC,
                              @RequestParam String optionD,
                              @RequestParam String correctOption,
                              RedirectAttributes redirectAttributes) {
        try {
            contentService.addQuestion(id, questionText, optionA, optionB, optionC, optionD, correctOption);
            redirectAttributes.addFlashAttribute("message", "Question added.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/lessons/" + id;
    }
}
