package com.example.eduquizweb.controller;

import com.example.eduquizcommon.entity.Quiz;
import com.example.eduquizcommon.service.QuestionService;
import com.example.eduquizcommon.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;

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
        modelMap.addAttribute("quizes", all);
        return "quiz";
    }

    @GetMapping("/{quizId}")
    public String singlePage(@PathVariable int quizId, ModelMap modelMap) {
        Optional<Quiz> byId = quizService.findById(quizId);
        if (byId.isPresent()) {
            Quiz quiz = byId.get();
            modelMap.addAttribute("quiz", quiz);
            modelMap.addAttribute("question", questionService.findAllByQuiz_id(quizId));
            return "singleQuiz";
        } else {
            return "redirect:/quiz";
        }
    }
}





