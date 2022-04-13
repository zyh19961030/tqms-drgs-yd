package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qu.modules.web.entity.QSingleDiseaseStatistic;

import java.util.Date;

/**
 * @Description: 单病种统计表
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
public interface QSingleDiseaseStatisticMapper extends BaseMapper<QSingleDiseaseStatistic> {

    Date selectMinOutTime();

}
