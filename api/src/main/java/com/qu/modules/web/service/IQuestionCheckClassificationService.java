package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.QuestionCheckClassification;
import com.qu.modules.web.param.QuestionCheckClassificationAddParam;
import com.qu.modules.web.param.QuestionCheckClassificationUpdateParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.vo.QuestionCheckClassificationListVo;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.ResultBetter;

import java.util.List;

/**
 * @Description: 查检表分类表
 * @Author: jeecg-boot
 * @Date:   2023-04-27
 * @Version: V1.0
 */
public interface IQuestionCheckClassificationService extends IService<QuestionCheckClassification> {

    List<QuestionCheckClassification> selectByUserId(String userId);

    List<QuestionCheckClassificationListVo> queryList(Data data);

    void add(QuestionCheckClassificationAddParam param, Data data);

    ResultBetter<T> edit(QuestionCheckClassificationUpdateParam param, Data data);

    ResultBetter<T> delete(String id, Data data);
}
