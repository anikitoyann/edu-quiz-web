package com.example.eduquizweb.controller;
import com.example.eduquizcommon.entity.Answer;
import com.example.eduquizcommon.service.AnswerService;
import com.example.eduquizcommon.service.QuestionOptionService;
import com.example.eduquizcommon.service.QuestionService;
import com.example.eduquizweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final QuestionOptionService questionOptionService;

    @GetMapping()
    public String answerPageList(ModelMap modelMap) {
        Integer totalScore = answerService.getTotalScore();
        modelMap.addAttribute("totalScore", totalScore);
        modelMap.addAttribute("answers", answerService.findAll());
        return "answer";
    }

    @PostMapping("/submitQuiz")
    public String submitQuiz(@RequestParam Map<String, String> formData, @AuthenticationPrincipal CurrentUser currentUser) {
        Answer answer = new Answer();
        answer.setUser(currentUser.getUser());

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();

            if (paramName.startsWith("answers[")) {
                Integer questionId = Integer.parseInt(paramName.substring("answers[".length(), paramName.indexOf("]")));
                Integer questionOptionId = Integer.parseInt(paramValue);
                answer.setQuestion(questionService.findById(questionId).orElse(null));
                answer.setQuestionOption(questionOptionService.findById(questionOptionId).orElse(null));
                answer.setDateTime(new Date());
                answerService.save(answer);
            }
        }

        return "redirect:/answer";
    }


    @GetMapping("/remove")
    public String removeQuiz(@RequestParam("id") int id) {
        answerService.deleteById(id);
        return "redirect:/answer";
    }}
