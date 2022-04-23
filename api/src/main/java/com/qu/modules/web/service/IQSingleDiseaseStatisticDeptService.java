package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.QSingleDiseaseStatisticDept;
import com.qu.modules.web.param.QSingleDiseaseTakeNumberListParam;
import com.qu.modules.web.param.QSingleDiseaseTakeStatisticAnalysisParam;
import com.qu.modules.web.param.QSingleDiseaseTakeStatisticDepartmentComparisonChartParam;
import com.qu.modules.web.vo.QSingleDiseaseTakeNumberVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeStatisticDepartmentComparisonChartVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeStatisticDepartmentComparisonVo;

import java.util.List;

/**
 * @Description: 单病种统计表
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
public interface IQSingleDiseaseStatisticDeptService extends IService<QSingleDiseaseStatisticDept> {

    void processData(String startDateString,String endDateString);

    void statisticLastMonth();

    List<QSingleDiseaseTakeStatisticDepartmentComparisonVo> singleDiseaseStatisticDepartmentComparison(QSingleDiseaseTakeStatisticAnalysisParam qSingleDiseaseTakeStatisticAnalysisParam);

    List<QSingleDiseaseTakeStatisticDepartmentComparisonChartVo> singleDiseaseStatisticDepartmentComparisonChart(QSingleDiseaseTakeStatisticDepartmentComparisonChartParam qSingleDiseaseTakeStatisticDepartmentComparisonChartParam);

    List<QSingleDiseaseTakeNumberVo> deptSingleDiseaseNumberList3(QSingleDiseaseTakeNumberListParam qSingleDiseaseTakeNumberListParam);

}
