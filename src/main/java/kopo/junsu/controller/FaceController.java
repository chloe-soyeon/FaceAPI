package kopo.junsu.controller;

import kopo.junsu.dto.object.QuizDTO;
import kopo.junsu.dto.request.AnswerDTO;
import kopo.junsu.dto.response.CorrectDTO;
import kopo.junsu.service.IQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = "/face")
@Controller
@RequiredArgsConstructor
@Slf4j
public class FaceController {
    private final IQuizService quizService;

    @GetMapping(value = "/exam")
    public String face(@RequestParam("level") Long level, @RequestParam("number") Long number, ModelMap model) {

        QuizDTO pDTO = new QuizDTO();
        pDTO.setLevel(level);
        pDTO.setQuizNumber(number);
        log.info("level:"+level);
        log.info("number:"+number);
        QuizDTO rDTO = Optional.ofNullable(quizService.getQuiz(level,number)).orElseGet(QuizDTO::new);
        model.addAttribute("quiz", rDTO);

        return "/face/exam";
    }

    @PostMapping(value = "/grading")
    @ResponseBody
    public CorrectDTO grading(@RequestBody AnswerDTO request) {
        CorrectDTO msg = new CorrectDTO();
        boolean correct;

        QuizDTO quiz = quizService.getQuiz(request);
        log.info("quiz : DB에서 가져오는 정답 " + quiz.getCorrect());
        log.info("Face API에서 가져오는 정답 :" + request.getAnswer());
        correct = quiz.getCorrect().equals(request.getAnswer().trim().toUpperCase());
        log.info( "퀴즈의 정답이 맞는지 : " + correct);

        msg.setCorrect(correct);
        return msg;
    }
    @GetMapping(value = "/correct")
    public String correct(){
        return "/face/correct";
    }
    @GetMapping(value = "/wrong")
    public String wrong(){
        return "/face/wrong";
    }
}
