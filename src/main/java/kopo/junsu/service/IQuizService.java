package kopo.junsu.service;

import kopo.junsu.dto.object.QuizDTO;
import kopo.junsu.dto.request.AnswerDTO;

import java.util.List;

public interface IQuizService {
    List<QuizDTO> getQuizList();

    QuizDTO getQuiz(Long level, Long number);
    QuizDTO getQuiz(AnswerDTO request);
}
