package com.qu.modules.web.service;

import com.qu.modules.web.entity.DrugRulesQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 药品规则问卷表
 * @Author: jeecg-boot
 * @Date:   2021-09-12
 * @Version: V1.0
 */
public interface IDrugRulesQuestionService extends IService<DrugRulesQuestion> {

    //展示药品规则问卷
    List<DrugRulesQuestion> queryQuestion();

    //根据输入内容搜索药品规则问卷
    List<DrugRulesQuestion> queryQuestionByInput(String name);
}
