package kopo.junsu.controller;

import kopo.junsu.dto.object.QuizDTO;
import kopo.junsu.persistance.mapper.IQuizMapper;
import kopo.junsu.service.IQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final IQuizService mainService;
    @GetMapping(value = "main")
    public String main(ModelMap model) {
        List<QuizDTO> result = mainService.getQuizList();
        model.addAttribute("quizList", result);
        return "/main";
    }
}
