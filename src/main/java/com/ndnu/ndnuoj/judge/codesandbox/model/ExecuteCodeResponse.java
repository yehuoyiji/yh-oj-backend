package com.ndnu.ndnuoj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ExecuteCodeResponse {

    private List<String> output;

    private Integer status;

    private String message;

    private JudgeInfo judgeInfo;
}
