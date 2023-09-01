package com.example.eduquizweb.controller;

import com.example.eduquizcommon.entity.QuestionOption;
import com.example.eduquizcommon.service.QuestionOptionService;
import com.example.eduquizcommon.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("")
    public String createOption(@ModelAttribute QuestionOption questionOption) {
        questionOptionService.save(questionOption);
        return "redirect:/option";
    }


    //konkret harci tarberakner@ ditel
    @GetMapping("/view/{questionId}")
    public String viewOptions(@PathVariable("questionId") int questionId, Model model) {
        List<QuestionOption> options = questionOptionService.getQuestionOptionByQuestionId(questionId);
        model.addAttribute("options", options);
        return "viewOptions";
    }
    }