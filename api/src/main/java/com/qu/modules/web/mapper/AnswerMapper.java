package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.vo.AnswerVo;

import java.util.List;
import java.util.Map;

/**
 * @Description: 作答
 * @Author: jeecg-boot
 * @Date:   2021-03-28
 * @Version: V1.0
 */
public interface AnswerMapper extends BaseMapper<Answer> {

    int questionFillInListCount(Map<String, Object> params);

    List<AnswerVo> questionFillInList(Map<String, Object> params);

}
