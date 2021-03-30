package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.param.QuestionEditParam;
import com.qu.modules.web.param.QuestionParam;
import com.qu.modules.web.param.UpdateDeptIdsParam;
import com.qu.modules.web.vo.QuestionPageVo;
import com.qu.modules.web.vo.QuestionVo;

/**
 * @Description: 问卷表
 * @Author: jeecg-boot
 * @Date:   2021-03-19
 * @Version: V1.0
 */
public interface IQuestionService extends IService<Question> {

    Question saveQuestion(QuestionParam questionParam);

    QuestionVo queryById(Integer id);

    Question updateQuestionById(QuestionEditParam questionEditParam);

    boolean removeQuestionById(Integer id);

    QuestionPageVo queryPageList(QuestionParam questionParam, Integer pageNo, Integer pageSize);

    QuestionPageVo questionFillInList(QuestionParam questionParam, Integer pageNo, Integer pageSize);

    Boolean updateDeptIdsParam(UpdateDeptIdsParam updateDeptIdsParam);

}
