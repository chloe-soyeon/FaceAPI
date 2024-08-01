package kopo.junsu.service.impl;


import kopo.junsu.dto.object.QuizDTO;
import kopo.junsu.dto.request.AnswerDTO;
import kopo.junsu.persistance.mapper.IQuizMapper;
import kopo.junsu.service.IQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService implements IQuizService {
    private final IQuizMapper quizMapper;

    @Override
    public List<QuizDTO> getQuizList() {
        List<QuizDTO> result = new ArrayList<>();
        try {
            result = quizMapper.getQuizList();

            for(QuizDTO dto : result){
                log.info("quizNumber:" + dto.getQuizNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            return result;
        }
    }

    @Override
    public QuizDTO getQuiz(Long level, Long number) {
        QuizDTO result = new QuizDTO();
        try {
            result = quizMapper.getQuiz(level,number);

        }catch (Exception e) {
            result.setQuizNumber(404L);
            result.setContent("데이터베이스에러 발생했습니다.");
        }finally {
            return result;
        }
    }

    @Override
    public QuizDTO getQuiz(AnswerDTO request) {
        QuizDTO result = new QuizDTO();
        try {
            Long level = request.getLevel();
            Long number = request.getNumber();

            result = quizMapper.getQuiz(level, number);
        } catch (Exception e) {
            e.printStackTrace();
            result.setQuizNumber(404L);
            result.setContent("데이터베이스에러 발생했습니다.");
        } finally {
            return result;
        }
    }
}

