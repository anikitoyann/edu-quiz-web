package com.example.eduquizweb.controller;

import com.example.eduquizcommon.entity.*;
import com.example.eduquizcommon.service.QuestionOptionService;
import com.example.eduquizcommon.service.QuestionService;
import com.example.eduquizcommon.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private  final QuestionOptionService questionOptionService;

    @GetMapping()
    public String quizPageList(@RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size, ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Quiz> all = quizService.findAll(pageable);
        int totalPages = all.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("question",questionService.findAll());
        modelMap.addAttribute("quizes", all);
        return "quiz";
    }

    @GetMapping("/{quizId}")
    public String singlePage(@PathVariable int quizId, ModelMap modelMap) {
        Optional<Quiz> byId = quizService.findById(quizId);
        if (byId.isPresent()) {
            Quiz quiz = byId.get();
            modelMap.addAttribute("quiz", quiz);
            List<Question>questions=questionService.findAllQuestionByQuiz_id(quizId);
            modelMap.addAttribute("questions", questions);
            Map<Integer, Answer> answers = new HashMap<>();
            for (Question question : questions) {
                Answer answer = new Answer();
                answer.setQuestion(question);
                answers.put(question.getId(), answer);
            }
            modelMap.addAttribute("answers", answers);
            Map<Question, List<QuestionOption>> questionOptionsMap = new HashMap<>();
            for (Question question : questions) {
                List<QuestionOption> options = questionOptionService.getAllQuestionOptionByQuestionId(question.getId());
                questionOptionsMap.put(question, options);
            }
            modelMap.addAttribute("questionOptionsMap", questionOptionsMap);
            return "singleQuiz";
        } else {
            return "redirect:/quiz";
        }
    }

    @GetMapping("/view/{questionId}")
    public String viewOptions(@PathVariable("questionId") int questionId, Model model) {
        List<QuestionOption> options = questionOptionService.getAllQuestionOptionByQuestionId(questionId);
        model.addAttribute("options", options);
        return "viewOptions";
    }

    @GetMapping("/remove")
    public String removeQuiz(@RequestParam("id") int id) {
        quizService.deleteById(id);
        return "redirect:/quiz";
    }
    @GetMapping("/add")
    public  String addQuiz(){
        return "addQuiz";
    }
    @PostMapping("/add")
    public String addQuiz(@ModelAttribute Quiz quiz) {
        quizService.save(quiz);
        return "redirect:/quiz";
    }
    @GetMapping("/updateQuiz")
    public String updateQuizPage() {
        return "updateQuiz";
    }

    @PostMapping("/updateQuiz")
    public String updateQuiz(@RequestParam("id") int id,
                            @ModelAttribute  Quiz quiz) {
        Optional<Quiz> byId = quizService.findById(id);
        if (byId.isPresent()) {
            Quiz updateQuiz = quizService.updateQuiz(quiz, byId);
            quizService.save(updateQuiz);
            return "redirect:/quiz";
        }
        return "redirect:/quiz";
    }
}
