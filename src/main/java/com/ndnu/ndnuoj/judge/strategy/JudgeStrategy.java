package com.ndnu.ndnuoj.judge.strategy;

import com.ndnu.ndnuoj.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
