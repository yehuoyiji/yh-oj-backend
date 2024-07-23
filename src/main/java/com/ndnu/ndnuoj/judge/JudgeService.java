package com.ndnu.ndnuoj.judge;

import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.ndnu.ndnuoj.model.entity.QuestionSubmit;
import com.ndnu.ndnuoj.model.vo.QuestionSubmitVO;

public interface JudgeService {

    QuestionSubmit doJudge(Long questionSubmitId);
}
