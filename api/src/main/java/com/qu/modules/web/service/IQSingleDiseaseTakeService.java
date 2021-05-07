package com.qu.modules.web.service;

import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.param.*;
import com.qu.modules.web.vo.QSingleDiseaseTakeByDoctorPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticDeptVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeVo;

import java.util.List;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date:   2021-04-02
 * @Version: V1.0
 */
public interface IQSingleDiseaseTakeService extends IService<QSingleDiseaseTake> {

    List<QSingleDiseaseTakeVo> singleDiseaseList(String name);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDoctorList(QSingleDiseaseTakeByDoctorParam qSingleDiseaseTakeByDoctorParam, Integer pageNo, Integer pageSize);

    Boolean setSingleDiseaseNoNeed(QSingleDiseaseTakeNoNeedParam qSingleDiseaseTakeNoNeedParam);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseWaitUploadList(Integer pageNo, Integer pageSize);

    String setSingleDiseaseStatus(String[] ids, Integer status, String examineReason);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseRejectList(Integer pageNo, Integer pageSize);

    QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDeptList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam, Integer pageNo, Integer pageSize);

    QSingleDiseaseTakeReportStatisticPageVo allSingleDiseaseReportStatistic(QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam, Integer pageNo, Integer pageSize);

    List<QSingleDiseaseTakeReportStatisticDeptVo> allSingleDiseaseReportStatisticDept();

    List<QSingleDiseaseTakeReportStatisticDeptVo> deptSingleDiseaseReportStatisticDept();


    Boolean singleDiseaseStageAnswer(String cookie, SingleDiseaseAnswerParam singleDiseaseAnswerParam);

    Boolean singleDiseaseAnswer(String cookie, SingleDiseaseAnswerParam singleDiseaseAnswerParam);

    String singleDiseaseAnswerQueryById(Integer id);
}
