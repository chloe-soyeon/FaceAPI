package kopo.junsu.dto.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuizDTO {
    private Long level;
    private Long quizNumber;
    private String correct;
    private String content;
}
