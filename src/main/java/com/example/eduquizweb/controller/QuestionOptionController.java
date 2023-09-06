package com.example.eduquizweb.controller;
import com.example.eduquizcommon.entity.Question;
import com.example.eduquizcommon.entity.QuestionOption;
import com.example.eduquizcommon.service.QuestionOptionService;
import com.example.eduquizcommon.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/option")
@RequiredArgsConstructor
public class QuestionOptionController {

    private final QuestionOptionService questionOptionService;
    private final QuestionService questionService;

    @GetMapping()
    public String showCreateOptionForm(Model model) {
        model.addAttribute("question",questionService.findAll());
        return "createOption";
    }

    @PostMapping()
    public String createOption(@ModelAttribute QuestionOption questionOption, @RequestParam("question.id") int questionId, @RequestParam("isCorrect") boolean isCorrect) {
        Question question = questionService.findById(questionId).orElse(null);
        if (question != null) {
            questionOption.setQuestion(question);
            questionOption.setCorrect(isCorrect);
            questionOptionService.save(questionOption);

            return "redirect:/option";
        } else {
            return "redirect:/error";
        }
    }

    }