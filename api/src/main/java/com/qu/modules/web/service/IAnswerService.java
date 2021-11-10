package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.param.AnswerPatientSubmitParam;
import com.qu.modules.web.vo.AnswerPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInAndSubmitPageVo;

public interface IAnswerService extends IService<Answer> {

    int createDynamicTable(String sql);

    int insertDynamicTable(String sql);

    Boolean answer(String cookie, AnswerParam answerParam);

    String queryByQuId(Integer quId);

    AnswerPageVo questionFillInList(String quName, Integer pageNo, Integer pageSize);

    boolean withdrawEdit(Integer id);

    AnswerPatientFillingInAndSubmitPageVo patientFillingInList(String deptId, Integer pageNo, Integer pageSize);

    AnswerPatientFillingInAndSubmitPageVo patientSubmitList(String deptId, AnswerPatientSubmitParam answerPatientSubmitParam, Integer pageNo, Integer pageSize);

    AnswerPatientFillingInAndSubmitPageVo monthQuarterYearList(String deptId, String type,Integer pageNo, Integer pageSize);
}
