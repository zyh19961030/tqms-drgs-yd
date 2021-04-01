package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qu.modules.web.entity.Qsubject;

import java.util.List;
import java.util.Map;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date:   2021-03-19
 * @Version: V1.0
 */
public interface QsubjectMapper extends BaseMapper<Qsubject> {

    Integer selectSumCount(Integer quId);

    List<Qsubject> selectSubjectByQuId(Integer id);

    Qsubject selectIdByGroupIdsLike(Integer subId);

    int selectColumnNameCount(Map<String, Object> param);

    List<Qsubject> selectGroupQsubjectByQuId(Map<String, Object> groupParam);

}
