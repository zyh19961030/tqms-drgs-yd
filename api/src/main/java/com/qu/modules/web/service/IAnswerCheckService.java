package com.qu.modules.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.AnswerCheck;
import com.qu.modules.web.param.*;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.request.CheckQuestionHistoryStatisticDetailListExportRequest;
import com.qu.modules.web.request.CheckQuestionHistoryStatisticDetailListRequest;
import com.qu.modules.web.request.CheckQuestionHistoryStatisticRecordListRequest;
import com.qu.modules.web.vo.AnswerCheckDetailListVo;
import com.qu.modules.web.vo.AnswerCheckVo;
import com.qu.modules.web.vo.CheckQuestionHistoryStatisticRecordListVo;
import org.jeecg.common.api.vo.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 检查表问卷总表
 * @Author: jeecg-boot
 * @Date:   2022-07-30
 * @Version: V1.0
 */
public interface IAnswerCheckService extends IService<AnswerCheck> {

    IPage<AnswerCheckVo> checkQuestionFillInList(AnswerCheckListParam answerCheckListParam, Integer pageNo, Integer pageSize, Integer answerStatus);

    /**
     * 检查表管理_历史统计_上级督查和科室自查_填报记录分页列表
     * @param recordListRequest
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<CheckQuestionHistoryStatisticRecordListVo> checkQuestionHistoryStatisticRecordList(CheckQuestionHistoryStatisticRecordListRequest recordListRequest, Integer pageNo, Integer pageSize);

    Result answer(String cookie, AnswerCheckAddParam answerCheckAddParam);

    Result answerByMiniApp(AnswerMiniAppParam answerParam);

    AnswerCheck queryById(String id);

    AnswerCheckDetailListVo detailList(AnswerCheckDetailListParam answerCheckDetailListParam, Data userId, Integer pageNo, Integer pageSize);

    void exportXlsDetailList(AnswerCheckDetailListExportParam answerCheckDetailListExportParam, HttpServletResponse response);

    /**
     * 检查管理_历史统计_上级督查和科室自查_明细表格分页列表
     * @param listRequest
     * @param data
     * @param pageNo
     * @param pageSize
     * @return
     */
    AnswerCheckDetailListVo checkQuestionHistoryStatisticDetailList(CheckQuestionHistoryStatisticDetailListRequest listRequest, Data data, Integer pageNo, Integer pageSize);


    void exportXlsCheckQuestionHistoryStatisticDetailList(CheckQuestionHistoryStatisticDetailListExportRequest exportRequest, HttpServletResponse response);

}
