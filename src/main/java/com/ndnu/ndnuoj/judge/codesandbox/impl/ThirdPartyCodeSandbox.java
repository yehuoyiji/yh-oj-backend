package com.ndnu.ndnuoj.judge.codesandbox.impl;

import com.ndnu.ndnuoj.judge.codesandbox.CodeSandBox;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 调用第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
