package com.qu.modules.web.service;

import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.AnswerCheck;
import com.qu.modules.web.param.AnswerCheckAddParam;
import com.qu.modules.web.param.AnswerCheckDetailListExportParam;
import com.qu.modules.web.param.AnswerCheckDetailListParam;
import com.qu.modules.web.param.AnswerCheckListParam;
import com.qu.modules.web.param.AnswerMiniAppParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.vo.AnswerCheckDetailListVo;
import com.qu.modules.web.vo.AnswerCheckVo;

/**
 * @Description: 检查表问卷总表
 * @Author: jeecg-boot
 * @Date:   2022-07-30
 * @Version: V1.0
 */
public interface IAnswerCheckService extends IService<AnswerCheck> {

    IPage<AnswerCheckVo> checkQuestionFillInList(AnswerCheckListParam answerCheckListParam, Integer pageNo, Integer pageSize, Integer answerStatus);

    Result answer(String cookie, AnswerCheckAddParam answerCheckAddParam);

    Result answerByMiniApp(AnswerMiniAppParam answerParam);

    AnswerCheck queryById(String id);

    AnswerCheckDetailListVo detailList(AnswerCheckDetailListParam answerCheckDetailListParam, Data userId, Integer pageNo, Integer pageSize);

    void exportXlsDetailList(AnswerCheckDetailListExportParam answerCheckDetailListExportParam, HttpServletResponse response);

}
