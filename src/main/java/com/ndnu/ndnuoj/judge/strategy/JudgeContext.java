package com.ndnu.ndnuoj.judge.strategy;

import com.ndnu.ndnuoj.model.entity.Question;
import com.ndnu.ndnuoj.model.entity.QuestionSubmit;
import com.ndnu.ndnuoj.model.question.JudgeCase;
import com.ndnu.ndnuoj.judge.codesandbox.model.JudgeInfo;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用来定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
