package com.example.eduquizweb.controller;

import com.example.eduquizcommon.entity.Question;
import com.example.eduquizcommon.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/question")
public class QuestionController {


    private final QuestionRepository questionRepository;

    // Page for creating questions
    @GetMapping()
    public String showQuestionCreationForm(Model model) {
        List<Question> all = questionRepository.findAll();
        model.addAttribute("question", all);
        return "question_creation";
    }

    @PostMapping("/teacher/question")
    public String createQuestion(@ModelAttribute("question") Question question) {
        questionRepository.save(question);
        return "redirect:/teacher/question";
    }
}