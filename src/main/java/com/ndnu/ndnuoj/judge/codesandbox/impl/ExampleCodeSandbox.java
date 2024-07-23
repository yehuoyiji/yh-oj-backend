package com.ndnu.ndnuoj.judge.codesandbox.impl;

import com.ndnu.ndnuoj.judge.codesandbox.CodeSandBox;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.ndnu.ndnuoj.model.enums.JudgeInfoMessageEnum;
import com.ndnu.ndnuoj.model.enums.QuestionSubmitStatusEnum;
import com.ndnu.ndnuoj.judge.codesandbox.model.JudgeInfo;

import java.util.List;

/**
 * 示例调用代码沙箱
 */
public class ExampleCodeSandbox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setOutput(inputList);
        response.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        response.setMessage("测试执行成功");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);
        response.setJudgeInfo(judgeInfo);
        return response;
    }


}
