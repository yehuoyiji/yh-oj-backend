package com.ndnu.ndnuoj.judge.codesandbox.impl;

import com.ndnu.ndnuoj.judge.codesandbox.CodeSandBox;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ndnu.ndnuoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程调用代码沙箱
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
