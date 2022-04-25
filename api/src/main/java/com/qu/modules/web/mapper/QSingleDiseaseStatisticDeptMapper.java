package com.qu.modules.web.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qu.modules.web.entity.QSingleDiseaseStatisticDept;
import com.qu.modules.web.param.QSingleDiseaseTakeStatisticAnalysisParam;
import com.qu.modules.web.vo.QSingleDiseaseTakeStatisticDepartmentComparisonVo;

/**
 * @Description: 单病种统计表
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
public interface QSingleDiseaseStatisticDeptMapper extends BaseMapper<QSingleDiseaseStatisticDept> {

    Date selectMinOutTime();

    List<QSingleDiseaseTakeStatisticDepartmentComparisonVo> singleDiseaseStatisticDepartmentComparison(@Param("qSingleDiseaseTakeStatisticAnalysisParam")QSingleDiseaseTakeStatisticAnalysisParam qSingleDiseaseTakeStatisticAnalysisParam);


}
