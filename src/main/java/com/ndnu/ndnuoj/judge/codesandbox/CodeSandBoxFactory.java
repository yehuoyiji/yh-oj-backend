package com.ndnu.ndnuoj.judge.codesandbox;

import com.ndnu.ndnuoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.ndnu.ndnuoj.judge.codesandbox.impl.RemoteCodeSandBox;
import com.ndnu.ndnuoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 工厂模式
 * 代码沙箱工程(根据字符串参数创建指定的代码沙箱示例)
 */
public class CodeSandBoxFactory {

    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();

            case "remote":
                return new RemoteCodeSandBox();

            case "thirdParty":
                return new ThirdPartyCodeSandbox();

            default:
                return new ExampleCodeSandbox();
        }
    }
}
