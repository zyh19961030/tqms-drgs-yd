package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qu.modules.web.entity.DrugRulesQuestion;
import com.qu.modules.web.mapper.DrugRulesQuestionMapper;
import com.qu.modules.web.service.IDrugRulesQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 药品规则问卷表
 * @Author: jeecg-boot
 * @Date:   2021-09-12
 * @Version: V1.0
 */
@Service
public class DrugRulesQuestionServiceImpl extends ServiceImpl<DrugRulesQuestionMapper, DrugRulesQuestion> implements IDrugRulesQuestionService {

    @Autowired
    DrugRulesQuestionMapper drugRulesQuestionMapper;

    @Override
    public List<DrugRulesQuestion> queryQuestion() {
        LambdaQueryWrapper<DrugRulesQuestion> lambda = new QueryWrapper<DrugRulesQuestion>().lambda();
        lambda.eq(DrugRulesQuestion::getDel, 0);
        List<DrugRulesQuestion> list = this.list(lambda);
        return list;
    }

    @Override
    public List<DrugRulesQuestion> queryQuestionByInput(String name) {
        List<DrugRulesQuestion> list = drugRulesQuestionMapper.queryQuestionByInput(name);
        return list;
    }
}
