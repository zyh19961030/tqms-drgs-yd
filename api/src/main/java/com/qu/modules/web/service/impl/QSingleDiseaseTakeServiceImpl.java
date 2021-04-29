package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.qu.constant.QSingleDiseaseTakeConstant;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.mapper.QSingleDiseaseTakeMapper;
import com.qu.modules.web.param.QSingleDiseaseTakeByDeptParam;
import com.qu.modules.web.param.QSingleDiseaseTakeByDoctorParam;
import com.qu.modules.web.param.QSingleDiseaseTakeNoNeedParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticParam;
import com.qu.modules.web.service.IQSingleDiseaseTakeService;
import com.qu.modules.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date: 2021-04-02
 * @Version: V1.0
 */
@Slf4j
@Service
public class QSingleDiseaseTakeServiceImpl extends ServiceImpl<QSingleDiseaseTakeMapper, QSingleDiseaseTake> implements IQSingleDiseaseTakeService {

    @Autowired
    private QSingleDiseaseTakeMapper qSingleDiseaseTakeMapper;

    @Override
    public List<QSingleDiseaseTakeVo> singleDiseaseList(String name) {
        return qSingleDiseaseTakeMapper.singleDiseaseList(name);
    }


    @Override
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDoctorList(QSingleDiseaseTakeByDoctorParam qSingleDiseaseTakeByDoctorParam, Integer pageNo, Integer pageSize) {
        Page<QSingleDiseaseTake> page = new Page<>(pageNo, pageSize);
        QueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDoctorParam.getPatientName())) {
            queryWrapper.like("patient_name", qSingleDiseaseTakeByDoctorParam.getPatientName());
        }

        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDoctorParam.getDoctorName())) {
            queryWrapper.like("doctor_name", qSingleDiseaseTakeByDoctorParam.getDoctorName());
        }

        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDoctorParam.getDiseaseName())) {
            queryWrapper.like("icd_name", qSingleDiseaseTakeByDoctorParam.getDiseaseName());
        }

        if (qSingleDiseaseTakeByDoctorParam.getInHospitalStartDate() != null) {
            queryWrapper.ge("in_time", qSingleDiseaseTakeByDoctorParam.getInHospitalStartDate());
        }

        if (qSingleDiseaseTakeByDoctorParam.getInHospitalEndDate() != null) {
            queryWrapper.le("in_time", qSingleDiseaseTakeByDoctorParam.getInHospitalEndDate());
        }

        if (qSingleDiseaseTakeByDoctorParam.getOutHospitalStartDate() != null) {
            queryWrapper.ge("out_time", qSingleDiseaseTakeByDoctorParam.getOutHospitalStartDate());
        }

        if (qSingleDiseaseTakeByDoctorParam.getOutHospitalEndDate() != null) {
            queryWrapper.le("out_time", qSingleDiseaseTakeByDoctorParam.getOutHospitalEndDate());
        }

        if (qSingleDiseaseTakeByDoctorParam.getStatus() != null) {
            queryWrapper.in("status", qSingleDiseaseTakeByDoctorParam.getStatus());
        }

        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDoctorParam.getMainDiagnosis())) {
            queryWrapper.like("main_diagnosis", qSingleDiseaseTakeByDoctorParam.getMainDiagnosis());
        }

        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }

    @Override
    public Boolean setSingleDiseaseNoNeed(QSingleDiseaseTakeNoNeedParam qSingleDiseaseTakeNoNeedParam) {
        boolean updateFlag = true;
        try {
            Integer id = qSingleDiseaseTakeNoNeedParam.getId();
            QSingleDiseaseTake byId = this.getById(id);
            List<Integer> statusList = Lists.newArrayList(QSingleDiseaseTakeConstant.STATUS_WAIT_WRITE, QSingleDiseaseTakeConstant.STATUS_WAIT_WRITE_GOING, QSingleDiseaseTakeConstant.STATUS_REJECT);
            if (statusList.contains(byId.getStatus())) {
                QSingleDiseaseTake qSingleDiseaseTake = new QSingleDiseaseTake();
                qSingleDiseaseTake.setId(id);
                qSingleDiseaseTake.setReportNoReason(qSingleDiseaseTakeNoNeedParam.getReason());
                qSingleDiseaseTake.setReportNoReasonId(qSingleDiseaseTakeNoNeedParam.getReasonId());
                qSingleDiseaseTake.setReportNoReasonNote(qSingleDiseaseTakeNoNeedParam.getReasonNote());
                qSingleDiseaseTake.setReportStatus(QSingleDiseaseTakeConstant.REPORT_STATUS_NO_NEED);
                qSingleDiseaseTake.setStatus(QSingleDiseaseTakeConstant.STATUS_NO_NEED);
                updateFlag = this.updateById(qSingleDiseaseTake);
            } else {
                updateFlag = false;
            }
        } catch (Exception e) {
            updateFlag = false;
            log.error(e.getMessage(), e);
        }
        return updateFlag;
    }

    @Override
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseWaitUploadList(Integer pageNo, Integer pageSize) {
        Page<QSingleDiseaseTake> page = new Page<>(pageNo, pageSize);
        QueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", QSingleDiseaseTakeConstant.STATUS_WAIT_UPLOAD);
        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }

    @Override
    public String setSingleDiseaseStatus(String[] ids, Integer status, String examineReason) {
        if (ids != null && ids.length > 0) {
            StringBuffer msg = new StringBuffer();
            for (int i = 0; i < ids.length; i++) {
                QSingleDiseaseTake byId = this.getById(ids[i]);
                if (!byId.getStatus().equals(QSingleDiseaseTakeConstant.STATUS_WAIT_UPLOAD)) {
                    msg.append(byId.getPatientName()).append(",");
                }

            }

            String s = msg.toString();
            if (StringUtils.isNotBlank(s)) {
                msg.deleteCharAt(msg.lastIndexOf(","));
                msg.append("状态不正确，请核实");
                s = msg.toString();
                return s;
            }
            for (String id : ids) {
                QSingleDiseaseTake qSingleDiseaseTake = new QSingleDiseaseTake();
                qSingleDiseaseTake.setId(Integer.parseInt(id));
                qSingleDiseaseTake.setStatus(status);
                if (StringUtils.isNotBlank(examineReason)) {
                    qSingleDiseaseTake.setExamineReason(examineReason);
                }
                this.updateById(qSingleDiseaseTake);
            }
            return null;
        }
        return "数组为空";
    }

    @Override
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseRejectList(Integer pageNo, Integer pageSize) {
        Page<QSingleDiseaseTake> page = new Page<>(pageNo, pageSize);
        QueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", QSingleDiseaseTakeConstant.STATUS_REJECT_AND_COUNTRY_REJECT);
        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }

    @Override
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDeptList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam, Integer pageNo, Integer pageSize) {
        //todo  添加科室操作
        Page<QSingleDiseaseTake> page = new Page<>(pageNo, pageSize);
        QueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDeptParam.getDiseaseName())) {
            queryWrapper.like("icd_name", qSingleDiseaseTakeByDeptParam.getDiseaseName());
        }

        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDeptParam.getPatientName())) {
            queryWrapper.like("patient_name", qSingleDiseaseTakeByDeptParam.getPatientName());
        }

        if (qSingleDiseaseTakeByDeptParam.getOutHospitalStartDate() != null) {
            queryWrapper.ge("out_time", qSingleDiseaseTakeByDeptParam.getOutHospitalStartDate());
        }

        if (qSingleDiseaseTakeByDeptParam.getOutHospitalEndDate() != null) {
            queryWrapper.le("out_time", qSingleDiseaseTakeByDeptParam.getOutHospitalEndDate());
        }

        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDeptParam.getDoctorName())) {
            queryWrapper.like("doctor_name", qSingleDiseaseTakeByDeptParam.getDoctorName());
        }

        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDeptParam.getMainDiagnosis())) {
            queryWrapper.like("main_diagnosis", qSingleDiseaseTakeByDeptParam.getMainDiagnosis());
        }

        if (qSingleDiseaseTakeByDeptParam.getWriteTimeStartDate() != null) {
            queryWrapper.ge("write_time", qSingleDiseaseTakeByDeptParam.getWriteTimeStartDate());
        }

        if (qSingleDiseaseTakeByDeptParam.getWriteTimeEndDate() != null) {
            queryWrapper.le("write_time", qSingleDiseaseTakeByDeptParam.getWriteTimeEndDate());
        }

        if (qSingleDiseaseTakeByDeptParam.getStatus() != null) {
            queryWrapper.in("status", qSingleDiseaseTakeByDeptParam.getStatus());
        }

        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }

    @Override
    public QSingleDiseaseTakeReportStatisticPageVo allSingleDiseaseReportStatistic(QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam, Integer pageNo, Integer pageSize) {
        QSingleDiseaseTakeReportStatisticPageVo qSingleDiseaseTakeReportStatisticPageVo = new QSingleDiseaseTakeReportStatisticPageVo();
        Map<String, Object> params = new HashMap<>();
        params.put("startRow", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("diseaseName", qSingleDiseaseTakeReportStatisticParam.getDiseaseName());
        String dateType = qSingleDiseaseTakeReportStatisticParam.getDateType();
        String dateStart = qSingleDiseaseTakeReportStatisticParam.getDateStart();
        String dateEnd = qSingleDiseaseTakeReportStatisticParam.getDateEnd();
        Date startDate;
        Date endDate;
        Date samePeriodStartDateTime;
        Date samePeriodEndDateTime;
        Date lastCycleStartDateTime;
        Date lastCycleEndDateTime;
        params.put("dateType", dateType);
        if (QSingleDiseaseTakeConstant.DATE_TYPE_YEARLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfYear().withMaximumValue().plusDays(1).toDate();

            int years = Years.yearsBetween(startDateTime, endDateTime).getYears() + 1;
            samePeriodStartDateTime = startDateTime.minusYears(years).toDate();
            samePeriodEndDateTime = startDateTime.toDate();
            lastCycleStartDateTime = startDateTime.minusYears(years).toDate();
            lastCycleEndDateTime = startDateTime.toDate();
        } else if (QSingleDiseaseTakeConstant.DATE_TYPE_MONTHLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfMonth().withMaximumValue().plusDays(1).toDate();

            int months = Months.monthsBetween(startDateTime, endDateTime).getMonths() + 1;
            samePeriodStartDateTime = startDateTime.dayOfMonth().withMinimumValue().minusYears(1).toDate();
            samePeriodEndDateTime = endDateTime.dayOfMonth().withMaximumValue().plusDays(1).minusYears(1).toDate();
            lastCycleStartDateTime = startDateTime.minusMonths(months).toDate();
            lastCycleEndDateTime = startDateTime.toDate();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.plusDays(1).toDate();

            int days = Days.daysBetween(startDateTime, endDateTime).getDays() + 1;
            samePeriodStartDateTime = startDateTime.minusMonths(1).toDate();
            samePeriodEndDateTime = endDateTime.plusDays(1).minusMonths(1).toDate();
            lastCycleStartDateTime = startDateTime.minusDays(days).toDate();
            lastCycleEndDateTime = startDateTime.toDate();
        }
        params.put("dateStart", startDate);
        params.put("dateEnd", endDate);
        params.put("dept", qSingleDiseaseTakeReportStatisticParam.getDept());
        params.put("deptShow", qSingleDiseaseTakeReportStatisticParam.getDeptShow());
        Integer total = qSingleDiseaseTakeMapper.allSingleDiseaseReportStatisticCount(params);
        List<QSingleDiseaseTakeReportStatisticVo> allSingleDiseaseReportStatisticList = qSingleDiseaseTakeMapper.allSingleDiseaseReportStatistic(params);
//        List<Map<String, Object>> resList = qSingleDiseaseTakeMapper.allSingleDiseaseReportStatistic(params);
        for (int i = 0; i < allSingleDiseaseReportStatisticList.size(); i++) {
            QSingleDiseaseTakeReportStatisticVo qSingleDiseaseTakeReportStatisticVo = allSingleDiseaseReportStatisticList.get(i);
            Map<String, Object> countParams = new HashMap<>();
            countParams.put("disease", qSingleDiseaseTakeReportStatisticVo.getDisease());
            countParams.put("dept", qSingleDiseaseTakeReportStatisticVo.getDept());
            countParams.put("status", QSingleDiseaseTakeConstant.STATUS_NO_NEED);
            countParams.put("dateType", dateType);
            countParams.put("dateStart", startDate);
            countParams.put("dateEnd", endDate);

            Integer inHospitalCount = qSingleDiseaseTakeReportStatisticVo.getInHospitalCount();
            Integer noNeedWriteCount = qSingleDiseaseTakeMapper.countSql(countParams);
            qSingleDiseaseTakeReportStatisticVo.setNoNeedWriteCount(noNeedWriteCount);
            Integer needWriteCount = inHospitalCount - noNeedWriteCount;
            qSingleDiseaseTakeReportStatisticVo.setNeedWriteCount(needWriteCount);

            countParams.put("status", QSingleDiseaseTakeConstant.STATUS_WAIT_WRITE);
            Integer notWriteCount = qSingleDiseaseTakeMapper.countSql(countParams);
            qSingleDiseaseTakeReportStatisticVo.setNotWriteCount(notWriteCount);
            Integer completeWriteCount = needWriteCount - notWriteCount;
            qSingleDiseaseTakeReportStatisticVo.setCompleteWriteCount(completeWriteCount);

            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            String hospitalWriteRate = numberFormat.format((float) completeWriteCount / (float) needWriteCount * 100) + "%";
            qSingleDiseaseTakeReportStatisticVo.setHospitalWriteRate(hospitalWriteRate);

            countParams.put("status", QSingleDiseaseTakeConstant.STATUS_COMPLETE);
            Integer completeReportCountryCount = qSingleDiseaseTakeMapper.countSql(countParams);
            qSingleDiseaseTakeReportStatisticVo.setCompleteReportCountryCount(completeReportCountryCount);
            String completeReportCountryRate = numberFormat.format((float) completeReportCountryCount / (float) needWriteCount * 100) + "%";
            qSingleDiseaseTakeReportStatisticVo.setCompleteReportCountryRate(completeReportCountryRate);

            //同期数量
            countParams.put("dateStart", samePeriodStartDateTime);
            countParams.put("dateEnd", samePeriodEndDateTime);
            Integer samePeriodReportCount = qSingleDiseaseTakeMapper.countSql(countParams);
            Float s = samePeriodReportCount == 0 ? (float) completeReportCountryCount * 100 : (float) completeReportCountryCount - (float) samePeriodReportCount / (float) samePeriodReportCount * 100;
            String samePeriodReportRate = "增长" + numberFormat.format(s) + "%";
            qSingleDiseaseTakeReportStatisticVo.setSamePeriodReportCount(samePeriodReportCount);
            qSingleDiseaseTakeReportStatisticVo.setSamePeriodReportRate(samePeriodReportRate);
            //上期数量
            countParams.put("dateStart", lastCycleStartDateTime);
            countParams.put("dateEnd", lastCycleEndDateTime);
            Integer lastCycleReportCount = qSingleDiseaseTakeMapper.countSql(countParams);
            Float l = lastCycleReportCount == 0 ? (float) completeReportCountryCount * 100 : (float) completeReportCountryCount - (float) lastCycleReportCount / (float) lastCycleReportCount * 100;
            String lastCycleReportRate = "增长" + numberFormat.format(l) + "%";
            qSingleDiseaseTakeReportStatisticVo.setLastCycleReportCount(lastCycleReportCount);
            qSingleDiseaseTakeReportStatisticVo.setLastCycleReportRate(lastCycleReportRate);

            countParams.put("dateStart", startDate);
            countParams.put("dateEnd", endDate);
            Map<String, Object> map = qSingleDiseaseTakeMapper.countAvgSql(countParams);
            BigDecimal avgInHospitalDay = (BigDecimal) map.get("avgInHospitalDay");
            BigDecimal avgInHospitalFee = (BigDecimal) map.get("avgInHospitalFee");
            BigDecimal avgDrugFee = (BigDecimal) map.get("avgDrugFee");
            BigDecimal avgOperationTreatmentFee = (BigDecimal) map.get("avgOperationTreatmentFee");
            BigDecimal avgDisposableConsumable = (BigDecimal) map.get("avgDisposableConsumable");
            qSingleDiseaseTakeReportStatisticVo.setAverageInHospitalDay(avgInHospitalDay);
            qSingleDiseaseTakeReportStatisticVo.setAverageInHospitalFee(avgInHospitalFee);
            qSingleDiseaseTakeReportStatisticVo.setAverageDrugFee(avgDrugFee);
            qSingleDiseaseTakeReportStatisticVo.setAverageOperationFee(avgOperationTreatmentFee);
            qSingleDiseaseTakeReportStatisticVo.setAverageDisposableConsumableFee(avgDisposableConsumable);
        }
        qSingleDiseaseTakeReportStatisticPageVo.setTotal(total);
        qSingleDiseaseTakeReportStatisticPageVo.setQSingleDiseaseTakeList(allSingleDiseaseReportStatisticList);
        return qSingleDiseaseTakeReportStatisticPageVo;
    }


    @Override
    public List<QSingleDiseaseTakeReportStatisticDeptVo> allSingleDiseaseReportStatisticDept() {
        return qSingleDiseaseTakeMapper.selectDept();
    }

    @Override
    public List<QSingleDiseaseTakeReportStatisticDeptVo> deptSingleDiseaseReportStatisticDept() {
        List<QSingleDiseaseTakeReportStatisticDeptVo> list = Lists.newArrayList();
        QSingleDiseaseTakeReportStatisticDeptVo q= new QSingleDiseaseTakeReportStatisticDeptVo();
        q.setDepartmentId("test");
        q.setDepartment("测试科室");
        list.add(q);
        return list;
    }
}
