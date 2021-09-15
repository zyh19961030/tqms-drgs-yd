package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qu.modules.web.entity.DrugRulesOption;
import com.qu.modules.web.mapper.DrugRulesOptionMapper;
import com.qu.modules.web.service.IDrugRulesOptionService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 药品规则答案表
 * @Author: jeecg-boot
 * @Date:   2021-09-12
 * @Version: V1.0
 */
@Service
public class DrugRulesOptionServiceImpl extends ServiceImpl<DrugRulesOptionMapper, DrugRulesOption> implements IDrugRulesOptionService {

    @Override
    public List<DrugRulesOption> queryOption(Integer subjectId) {
        LambdaQueryWrapper<DrugRulesOption> lambda = new QueryWrapper<DrugRulesOption>().lambda();
        lambda.in(DrugRulesOption::getDrugRulesSubjectId, subjectId);
        lambda.in(DrugRulesOption::getDel, 0);
        List<DrugRulesOption> list = this.list(lambda);
        return list;
    }
}
