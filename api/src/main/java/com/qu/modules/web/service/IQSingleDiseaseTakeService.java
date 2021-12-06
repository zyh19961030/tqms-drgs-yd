package com.qu.modules.web.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.param.QSingleDiseaseTakeByDeptParam;
import com.qu.modules.web.param.QSingleDiseaseTakeByDoctorParam;
import com.qu.modules.web.param.QSingleDiseaseTakeNoNeedParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticDeptPermutationParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticOverviewLineParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticOverviewPieParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticSummaryParam;
import com.qu.modules.web.param.SingleDiseaseAnswerNavigationParam;
import com.qu.modules.web.param.SingleDiseaseAnswerParam;
import com.qu.modules.web.param.SingleDiseaseWaitUploadParam;
import com.qu.modules.web.param.SingleDiseaseExamineRecordParam;
import com.qu.modules.web.vo.QSingleDiseaseNameVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeByDoctorPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticDeptPermutationVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticDeptVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticOverviewLineVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticOverviewPieVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticSummaryVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticTrendVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeVo;
import com.qu.modules.web.vo.SingleDiseaseAnswerNavigationVo;
import com.qu.modules.web.vo.WorkbenchReminderVo;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date:   2021-04-02
 * @Version: V1.0
 */
public interface IQSingleDiseaseTakeService extends IService<QSingleDiseaseTake> {

    List<QSingleDiseaseTakeVo> singleDiseaseList(String name, String deptId);

    List<QSingleDiseaseNameVo> singleDiseaseNameList(String deptId);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDoctorList(QSingleDiseaseTakeByDoctorParam qSingleDiseaseTakeByDoctorParam, Integer pageNo, Integer pageSize,String deptId);

    Boolean setSingleDiseaseNoNeed(QSingleDiseaseTakeNoNeedParam qSingleDiseaseTakeNoNeedParam);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseWaitUploadList(SingleDiseaseWaitUploadParam singleDiseaseWaitUploadParam, String deptId, Integer pageNo, Integer pageSize);

    String setSingleDiseaseStatus(String[] ids, Integer status, String examineReason);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseRejectList(Integer pageNo, Integer pageSize);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDeptList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam, Integer pageNo, Integer pageSize, String deptId);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseExamineRecordAllList(SingleDiseaseExamineRecordParam singleDiseaseExamineRecordParam, Integer pageNo, Integer pageSize);

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

    List<SingleDiseaseAnswerNavigationVo> singleDiseaseAnswerNavigation(SingleDiseaseAnswerNavigationParam singleDiseaseAnswerNavigationParam);
}
