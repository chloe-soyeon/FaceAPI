package kopo.junsu.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerDTO {
    private Long level;
    private Long number;
    private String answer;
}
