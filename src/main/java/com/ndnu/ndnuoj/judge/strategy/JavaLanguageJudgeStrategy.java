package com.ndnu.ndnuoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.ndnu.ndnuoj.model.entity.Question;
import com.ndnu.ndnuoj.model.enums.JudgeInfoMessageEnum;
import com.ndnu.ndnuoj.model.question.JudgeCase;
import com.ndnu.ndnuoj.model.question.JudgeConfig;
import com.ndnu.ndnuoj.judge.codesandbox.model.JudgeInfo;

import java.util.List;

/**
 * 默认判题策略
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        Long consumeTime = judgeInfo.getTime();
        Long consumeMemory = judgeInfo.getMemory();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfoResponse.setTime(consumeTime);
        judgeInfoResponse.setMemory(consumeMemory);

        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.WAITING;
        // 先判断沙箱执行的结果输出数量是否和预期输出数量相等
        if(inputList.size()!= outputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < judgeCaseList.size(); i++) {
            if(!outputList.get(i).equals(judgeCaseList.get(i).getOutput())) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 判断题目限制

        String questionJudgeConfig = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(questionJudgeConfig, JudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        if(consumeMemory > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
        }
        // todo 假设 java程序本身需要额外执行10秒
        long JAVA_PROGRAM_TIME_COST = 10000L;
        if(consumeMemory - JAVA_PROGRAM_TIME_COST > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }


}
