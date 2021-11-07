package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.vo.AnswerPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInVo;

import java.util.List;

public interface IAnswerService extends IService<Answer> {

    int createDynamicTable(String sql);

    int insertDynamicTable(String sql);

    Boolean answer(String cookie, AnswerParam answerParam);

    String queryByQuId(Integer quId);

    AnswerPageVo questionFillInList(Integer pageNo, Integer pageSize);

    String withdrawEdit(Integer id);

    List<AnswerPatientFillingInVo> patientFillingInList(String deptId);
}
