package com.ndnu.ndnuoj.judge;

import cn.hutool.json.JSONUtil;
import com.ndnu.ndnuoj.common.ErrorCode;
import com.ndnu.ndnuoj.exception.BusinessException;
import com.ndnu.ndnuoj.judge.codesandbox.CodeSandBox;
import com.ndnu.ndnuoj.judge.codesandbox.CodeSandBoxFactory;
import com.ndnu.ndnuoj.judge.codesandbox.CodeSandBoxProxy;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.ndnu.ndnuoj.judge.strategy.JudgeContext;
import com.ndnu.ndnuoj.model.entity.Question;
import com.ndnu.ndnuoj.model.entity.QuestionSubmit;
import com.ndnu.ndnuoj.model.enums.QuestionSubmitStatusEnum;
import com.ndnu.ndnuoj.model.question.JudgeCase;
import com.ndnu.ndnuoj.judge.codesandbox.model.JudgeInfo;
import com.ndnu.ndnuoj.service.QuestionService;
import com.ndnu.ndnuoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeManager judgeManager;
    @Value("${codesandbox.type:example}")
    private String type;
    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        // 传入题目Id, 获取对应的题目信息、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 如果题目提交状态不为等待中，就不用重复执行
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中，请勿重复提交");
        }
        // 更改判题（题目提交）的状态为"判题中",防止重复执行
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean updateResult = questionSubmitService.updateById(updateQuestionSubmit);
        if(!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        // 调用代码沙箱，获取执行结果
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type); //通过工厂模式 创建代码沙箱实例
        codeSandBox = new CodeSandBoxProxy(codeSandBox);    // 通过代理模式 对判题前后信息进行输出
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> judgeCaseInputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(judgeCaseInputList)
                .build();
        // 执行代码沙箱
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);

        // 根据沙箱的执行结果，设置题目的判题状态和信息
        List<String> outputList = executeCodeResponse.getOutput();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(judgeCaseInputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        // 将代码沙箱运行结果进行判题 策略模式
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 修改数据库中的判题结果
        updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        updateQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        boolean update = questionSubmitService.updateById(updateQuestionSubmit);
        if(!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);
        return questionSubmitResult;
    }
}
