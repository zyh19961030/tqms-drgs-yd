package com.qu.modules.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.SingleEnterQuestion;
import com.qu.modules.web.param.IdIntegerParam;
import com.qu.modules.web.param.SingleEnterQuestionAddParam;
import com.qu.modules.web.param.SingleEnterQuestionListParam;
import com.qu.modules.web.vo.SingleEnterQuestionListVo;
import org.jeecg.common.api.vo.ResultBetter;

/**
 * @Description: 录入表单表
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
public interface ISingleEnterQuestionService extends IService<SingleEnterQuestion> {

    void add(SingleEnterQuestionAddParam param);

    IPage<SingleEnterQuestionListVo> queryPageList(Page<SingleEnterQuestion> page, SingleEnterQuestionListParam param);

    ResultBetter delete(IdIntegerParam param);

}
