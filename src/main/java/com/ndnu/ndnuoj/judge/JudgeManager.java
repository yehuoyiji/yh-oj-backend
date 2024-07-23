package com.ndnu.ndnuoj.judge;

import com.ndnu.ndnuoj.judge.strategy.DefaultJudgeStrategy;
import com.ndnu.ndnuoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.ndnu.ndnuoj.judge.strategy.JudgeContext;
import com.ndnu.ndnuoj.judge.strategy.JudgeStrategy;
import com.ndnu.ndnuoj.model.entity.QuestionSubmit;
import com.ndnu.ndnuoj.judge.codesandbox.model.JudgeInfo;
import org.springframework.stereotype.Service;

/**
 * 判题管理 简化调用
 */
@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
