package com.example.eduquizweb.controller;

import com.example.eduquizcommon.entity.Question;
import com.example.eduquizcommon.entity.Quiz;
import com.example.eduquizcommon.entity.Type;
import com.example.eduquizcommon.repository.QuestionRepository;
import com.example.eduquizcommon.service.QuestionService;
import com.example.eduquizcommon.service.QuizService;
import com.example.eduquizweb.security.CurrentUser;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/question")
public class QuestionController {


    private final QuestionService questionService;
    private  final QuizService quizService;
    @GetMapping()
    public String showQuestionCreationForm(Model model) {
        List<Question> all = questionService.findAll();
        model.addAttribute("question", all);
        model.addAttribute("quiz",quizService.findAll());
        List<Type> types = Arrays.asList(Type.values());
        model.addAttribute("types", types);
        return "question_creation";
    }

    @PostMapping()
    public String createQuestion(@ModelAttribute Question question) {
        questionService.save(question);
        return "redirect:/teacher/question";
    }
}