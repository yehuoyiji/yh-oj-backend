package com.ndnu.ndnuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ndnu.ndnuoj.model.entity.Question;
import com.ndnu.ndnuoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ndnu.ndnuoj.model.entity.User;
import com.ndnu.ndnuoj.model.question.QuestionQueryRequest;
import com.ndnu.ndnuoj.model.questionsubmit.QuestionSubmitAddRequest;
import com.ndnu.ndnuoj.model.questionsubmit.QuestionSubmitQueryRequest;
import com.ndnu.ndnuoj.model.vo.QuestionSubmitVO;
import com.ndnu.ndnuoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author ak
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-03-11 13:41:55
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
        long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);



    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit,  User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
