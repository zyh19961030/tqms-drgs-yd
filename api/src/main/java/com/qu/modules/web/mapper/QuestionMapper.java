package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qu.modules.web.entity.Question;

import java.util.List;
import java.util.Map;

/**
 * @Description: 问卷表
 * @Author: jeecg-boot
 * @Date:   2021-03-19
 * @Version: V1.0
 */
public interface QuestionMapper extends BaseMapper<Question> {

    int queryPageListCount(Map<String, Object> params);

    List<Question> queryPageList(Map<String, Object> params);

    int questionFillInListCount(Map<String, Object> params);

    List<Question> questionFillInList(Map<String, Object> params);

}
