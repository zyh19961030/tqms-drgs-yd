package com.qu.modules.web.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultBetter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AnswerAllDataParam;
import com.qu.modules.web.param.AnswerListParam;
import com.qu.modules.web.param.AnswerMiniAppParam;
import com.qu.modules.web.param.AnswerMonthQuarterYearSubmitParam;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.param.AnswerPatientSubmitParam;
import com.qu.modules.web.vo.AnswerAllDataVo;
import com.qu.modules.web.vo.AnswerMonthQuarterYearFillingInAndSubmitPageVo;
import com.qu.modules.web.vo.AnswerPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInAndSubmitPageVo;

public interface IAnswerService extends IService<Answer> {

    int createDynamicTable(String sql);

    int insertDynamicTable(String sql);

    Result answer(String cookie, AnswerParam answerParam);

    Result answerByMiniApp(AnswerMiniAppParam answerMiniAppParam, String userId);

//    String queryByQuId(Integer quId);

    AnswerPageVo questionFillInList(String quName, Integer pageNo, Integer pageSize);

    boolean withdrawEdit(Integer id);

    AnswerPatientFillingInAndSubmitPageVo patientFillingInList(String deptId, Integer pageNo, Integer pageSize);

    AnswerPatientFillingInAndSubmitPageVo patientSubmitList(String deptId, AnswerPatientSubmitParam answerPatientSubmitParam, Integer pageNo, Integer pageSize);

    AnswerMonthQuarterYearFillingInAndSubmitPageVo monthQuarterYearFillingInList(String deptId, String type,String month, Integer pageNo, Integer pageSize);

    AnswerMonthQuarterYearFillingInAndSubmitPageVo monthQuarterYearSubmitList(String deptId, AnswerMonthQuarterYearSubmitParam answerMonthQuarterYearSubmitParam,
                                                                              Integer pageNo, Integer pageSize);

    AnswerMonthQuarterYearFillingInAndSubmitPageVo answerQuestionFillInAndSubmitList(AnswerListParam answerListParam, Integer pageNo, Integer pageSize, String userId);


    boolean patientMonthQuarterYearFillingInDelete(Integer id);

    Answer queryById(String id);

    ResultBetter<AnswerAllDataVo> answerAllData(String deptId, AnswerAllDataParam param);

}
