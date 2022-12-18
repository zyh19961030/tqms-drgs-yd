package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.*;
import com.qu.modules.web.vo.AnswerAllDataVo;
import com.qu.modules.web.vo.AnswerMonthQuarterYearFillingInAndSubmitPageVo;
import com.qu.modules.web.vo.AnswerPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInAndSubmitPageVo;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultBetter;

public interface IAnswerService extends IService<Answer> {

    int createDynamicTable(String sql);

    int insertDynamicTable(String sql);

    Result answer(String cookie, AnswerParam answerParam);

//    String queryByQuId(Integer quId);

    AnswerPageVo questionFillInList(String quName, Integer pageNo, Integer pageSize);

    boolean withdrawEdit(Integer id);

    AnswerPatientFillingInAndSubmitPageVo patientFillingInList(String deptId, Integer pageNo, Integer pageSize);

    AnswerPatientFillingInAndSubmitPageVo patientSubmitList(String deptId, AnswerPatientSubmitParam answerPatientSubmitParam, Integer pageNo, Integer pageSize);

    AnswerMonthQuarterYearFillingInAndSubmitPageVo monthQuarterYearFillingInList(String deptId, String type,String month, Integer pageNo, Integer pageSize);

    AnswerMonthQuarterYearFillingInAndSubmitPageVo monthQuarterYearSubmitList(String deptId, AnswerMonthQuarterYearSubmitParam answerMonthQuarterYearSubmitParam,
                                                                              Integer pageNo, Integer pageSize);

    AnswerMonthQuarterYearFillingInAndSubmitPageVo answerQuestionFillInAndSubmitList(AnswerListParam answerListParam, Integer pageNo, Integer pageSize);


    boolean patientMonthQuarterYearFillingInDelete(Integer id);

    Answer queryById(String id);

    ResultBetter<AnswerAllDataVo> answerAllData(String deptId, AnswerAllDataParam param);

}
