package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.param.*;
import com.qu.modules.web.vo.*;

import java.util.List;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date:   2021-04-02
 * @Version: V1.0
 */
public interface IQSingleDiseaseTakeService extends IService<QSingleDiseaseTake> {

    List<QSingleDiseaseTakeVo> singleDiseaseList(String name, String deptId);

    List<QSingleDiseaseNameVo> singleDiseaseNameList(String deptId);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDoctorList(QSingleDiseaseTakeByDoctorParam qSingleDiseaseTakeByDoctorParam, Integer pageNo, Integer pageSize);

    Boolean setSingleDiseaseNoNeed(QSingleDiseaseTakeNoNeedParam qSingleDiseaseTakeNoNeedParam);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseWaitUploadList(Integer pageNo, Integer pageSize);

    String setSingleDiseaseStatus(String[] ids, Integer status, String examineReason);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseRejectList(Integer pageNo, Integer pageSize);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDeptList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam, Integer pageNo, Integer pageSize, String deptId);

    QSingleDiseaseTakeReportStatisticPageVo allSingleDiseaseReportStatistic(QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam, Integer pageNo, Integer pageSize);

    List<QSingleDiseaseTakeReportStatisticDeptVo> allSingleDiseaseReportStatisticDept();

    List<QSingleDiseaseTakeReportStatisticDeptVo> deptSingleDiseaseReportStatisticDept();


    void singleDiseaseStageAnswer(String cookie, SingleDiseaseAnswerParam singleDiseaseAnswerParam);

    void singleDiseaseAnswer(String cookie, SingleDiseaseAnswerParam singleDiseaseAnswerParam);

    String singleDiseaseAnswerQueryById(Integer id);

    List<QSingleDiseaseTakeReportStatisticOverviewLineVo> allSingleDiseaseReportStatisticOverviewLine(QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewLineParam);

    List<QSingleDiseaseTakeReportStatisticOverviewPieVo> allSingleDiseaseReportStatisticOverviewPie(QSingleDiseaseTakeReportStatisticOverviewPieParam qSingleDiseaseTakeReportStatisticOverviewParam);

    List<QSingleDiseaseTakeReportStatisticTrendVo> allSingleDiseaseReportStatisticTrend(QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewParam);

    List<QSingleDiseaseTakeReportStatisticDeptPermutationVo> allSingleDiseaseReportStatisticDeptPermutation(QSingleDiseaseTakeReportStatisticDeptPermutationParam qSingleDiseaseTakeReportStatisticDeptPermutationParam);

    List<QSingleDiseaseTakeReportStatisticSummaryVo> allSingleDiseaseReportStatisticSummary(QSingleDiseaseTakeReportStatisticSummaryParam qSingleDiseaseTakeReportStatisticSummaryParam);

    WorkbenchReminderVo workbenchReminder(String dept);

    List<SingleDiseaseAnswerNavigationVo> singleDiseaseAnswerNavigation(Integer id);
}
