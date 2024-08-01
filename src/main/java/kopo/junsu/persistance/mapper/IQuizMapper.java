package kopo.junsu.persistance.mapper;

import kopo.junsu.dto.object.QuizDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface IQuizMapper {
    @Select("SELECT LEVEL, QUIZ_NUMBER FROM QUIZ ORDER BY LEVEL, QUIZ_NUMBER ASC")
    List<QuizDTO> getQuizList() throws Exception;

    QuizDTO getQuiz(@Param("level") Long level, @Param("number")Long number) throws Exception;
}
