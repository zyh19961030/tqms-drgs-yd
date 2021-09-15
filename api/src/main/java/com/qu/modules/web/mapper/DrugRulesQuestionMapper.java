package com.qu.modules.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.qu.modules.web.entity.DrugRulesQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 药品规则问卷表
 * @Author: jeecg-boot
 * @Date:   2021-09-12
 * @Version: V1.0
 */
public interface DrugRulesQuestionMapper extends BaseMapper<DrugRulesQuestion> {

    //根据输入内容搜索药品规则问卷
    List<DrugRulesQuestion> queryQuestionByInput(String name);
}
