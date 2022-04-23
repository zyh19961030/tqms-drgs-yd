package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qu.modules.web.entity.QSingleDiseaseStatisticDept;

import java.util.Date;

/**
 * @Description: 单病种统计表
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
public interface QSingleDiseaseStatisticDeptMapper extends BaseMapper<QSingleDiseaseStatisticDept> {

    Date selectMinOutTime();

}
