package com.example.eduquizweb.controller;

import com.example.eduquizcommon.entity.Answer;
import com.example.eduquizcommon.service.AnswerService;
import com.example.eduquizcommon.service.QuestionOptionService;
import com.example.eduquizcommon.service.QuestionService;
import com.example.eduquizweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final QuestionOptionService questionOptionService;

    @GetMapping()
    public String answerPageList(ModelMap modelMap) {
        modelMap.addAttribute("answers", answerService.findAll());
        return "answer";
    }

    @GetMapping("/remove")
    public String removeQuiz(@RequestParam("id") int id) {
        answerService.deleteById(id);
        return "redirect:/answer";
    }

    @GetMapping("/add")
    public String addAnswer(Model model) {
        model.addAttribute("questions", questionService.findAll());
        model.addAttribute("questionOption", questionOptionService.findAll());
        return "addAnswer";
    }

    @PostMapping("/add")
    public String addAnswer(@ModelAttribute Answer answer, @AuthenticationPrincipal CurrentUser currentUser) {
        answer.setUser(currentUser.getUser());
        answerService.save(answer);
        return "redirect:/answer";
    }
}
