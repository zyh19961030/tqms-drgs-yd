package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.QSingleDiseaseStatisticDept;
import com.qu.modules.web.param.*;
import com.qu.modules.web.vo.*;

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

    QSingleDiseaseTakeReportStatisticPageVo allSingleDiseaseReportStatisticByDept(QSingleDiseaseTakeReportStatisticByDeptParam qSingleDiseaseTakeReportStatisticByDeptParam, Integer pageNo, Integer pageSize);

    List<QSingleDiseaseTakeStatisticAnalysisVo> singleDiseaseStatisticAnalysisByDept(QSingleDiseaseTakeStatisticAnalysisByDeptParam qSingleDiseaseTakeStatisticAnalysisByDeptParam);


}
