package com.ndnu.ndnuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ndnu.ndnuoj.annotation.AuthCheck;
import com.ndnu.ndnuoj.common.BaseResponse;
import com.ndnu.ndnuoj.common.ErrorCode;
import com.ndnu.ndnuoj.common.ResultUtils;
import com.ndnu.ndnuoj.constant.UserConstant;
import com.ndnu.ndnuoj.exception.BusinessException;

import com.ndnu.ndnuoj.model.dto.user.UserQueryRequest;
import com.ndnu.ndnuoj.model.entity.Question;
import com.ndnu.ndnuoj.model.entity.QuestionSubmit;
import com.ndnu.ndnuoj.model.entity.User;
import com.ndnu.ndnuoj.model.question.QuestionQueryRequest;
import com.ndnu.ndnuoj.model.questionsubmit.QuestionSubmitAddRequest;
import com.ndnu.ndnuoj.model.questionsubmit.QuestionSubmitQueryRequest;
import com.ndnu.ndnuoj.model.vo.QuestionSubmitVO;
import com.ndnu.ndnuoj.service.QuestionSubmitService;
import com.ndnu.ndnuoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 提交题目
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;


    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long questionId = questionSubmitAddRequest.getQuestionId();
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }

    /**
     * 分页获取用户列表（除管理员外, 普通用户只能看到非答案, 提交代码的公开信息）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        //从数据库中查询原始的提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        //返回脱敏信息
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }
}
