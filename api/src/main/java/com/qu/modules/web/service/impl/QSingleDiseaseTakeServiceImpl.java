package com.qu.modules.web.service.impl;

import cn.hutool.core.date.DatePattern;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qu.constant.QSingleDiseaseTakeConstant;
import com.qu.constant.QsubjectConstant;
import com.qu.constant.QuestionConstant;
import com.qu.constant.TqmsQuotaCategoryConstant;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.entity.TqmsQuotaCategory;
import com.qu.modules.web.mapper.*;
import com.qu.modules.web.param.*;
import com.qu.modules.web.mapper.*;
import com.qu.modules.web.param.*;
import com.qu.modules.web.mapper.DynamicTableMapper;
import com.qu.modules.web.mapper.QSingleDiseaseTakeMapper;
import com.qu.modules.web.mapper.QsubjectMapper;
import com.qu.modules.web.mapper.QuestionMapper;
import com.qu.modules.web.mapper.TqmsQuotaCategoryMapper;
import com.qu.modules.web.param.QSingleDiseaseTakeByDeptParam;
import com.qu.modules.web.param.QSingleDiseaseTakeByDoctorParam;
import com.qu.modules.web.param.QSingleDiseaseTakeNoNeedParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticDeptPermutationParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticOverviewLineParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticOverviewPieParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticSummaryParam;
import com.qu.modules.web.param.SingleDiseaseAnswer;
import com.qu.modules.web.param.SingleDiseaseAnswerParam;
import com.qu.modules.web.pojo.JsonRootBean;
import com.qu.modules.web.service.IQSingleDiseaseTakeService;
import com.qu.modules.web.service.IQuestionService;
import com.qu.modules.web.vo.*;
import com.qu.modules.web.vo.*;
import com.qu.util.HttpClient;
import com.qu.util.HttpTools;
import com.qu.util.HttpTools.HttpData;
import com.qu.util.HttpTools.ResponseEntity;
import com.qu.util.PriceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.ibatis.binding.MapperMethod;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private QsubjectMapper qsubjectMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private DynamicTableMapper dynamicTableMapper;

    @Autowired
    private TqmsQuotaCategoryMapper tqmsQuotaCategoryMapper;

    @Value("${system.tokenUrl}")
    private String tokenUrl;

    @Value("${system.singleDiseaseReportUrl}")
    private String singleDiseaseReportUrl;

    private static String[] parsePatterns = new String[] { "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'","yyyyMMdd","yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss" };

    @Override
    public List<QSingleDiseaseTakeVo> singleDiseaseList(String name, String deptId) {
        LambdaQueryWrapper<Question> lambda = new QueryWrapper<Question>().lambda();
        if(StringUtils.isNotBlank(name)){
            lambda.like(Question::getQuName,name);
        }
        lambda.eq(Question::getQuStatus,"1");
        lambda.eq(Question::getCategoryType,"1");
        lambda.eq(Question::getDel,"0");
        //科室匹配 按单病种填报 问卷设置科室权限---
        if(StringUtils.isNotBlank(deptId)){
            lambda.like(Question::getDeptIds,deptId);
        }
        List<Question> questions = questionMapper.selectList(lambda);
        List<QSingleDiseaseTakeVo> qSingleDiseaseTakeVoList = questions.stream().map(q -> {
            QSingleDiseaseTakeVo qSingleDiseaseTakeVo = new QSingleDiseaseTakeVo();
            qSingleDiseaseTakeVo.setIcon(q.getIcon());
            qSingleDiseaseTakeVo.setDisease(q.getQuName());
            qSingleDiseaseTakeVo.setCategoryId(q.getCategoryId());
            qSingleDiseaseTakeVo.setQuId(q.getId());
            return qSingleDiseaseTakeVo;
        }).collect(Collectors.toList());
        return qSingleDiseaseTakeVoList;
    }

    @Override
    public List<QSingleDiseaseNameVo> singleDiseaseNameList(String deptId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_type",QuestionConstant.CATEGORY_TYPE_SINGLE_DISEASE);
        queryWrapper.eq("qu_Status",QuestionConstant.QU_STATUS_RELEASE);
        queryWrapper.eq("del",QuestionConstant.DEL_NORMAL);
        if(StringUtils.isNotBlank(deptId)){
            queryWrapper.like("dept_ids",deptId);
        }
        //科室匹配 按医生填报查询-本科室单病种上报记录-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件
        List<Question> questions = questionMapper.selectList(queryWrapper);
        List<QSingleDiseaseNameVo> qSingleDiseaseTakeVoList = questions.stream().map(q -> {
            QSingleDiseaseNameVo qSingleDiseaseTakeVo = new QSingleDiseaseNameVo();
            qSingleDiseaseTakeVo.setIcon(q.getIcon());
            qSingleDiseaseTakeVo.setDisease(q.getQuName());
            qSingleDiseaseTakeVo.setCategoryId(q.getCategoryId());
            return qSingleDiseaseTakeVo;
        }).collect(Collectors.toList());
        return qSingleDiseaseTakeVoList;
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

        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDoctorParam.getCategoryId())) {
            queryWrapper.like("category_id", qSingleDiseaseTakeByDoctorParam.getCategoryId());
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
        for (QSingleDiseaseTake record : qSingleDiseaseTakeIPage.getRecords()) {
            String dynamicTableName = record.getDynamicTableName();
            if(StringUtils.isNotBlank(dynamicTableName)){
                LambdaQueryWrapper<Question> questionQueryWrapper = new QueryWrapper<Question>().lambda();
                questionQueryWrapper.in(Question::getTableName, dynamicTableName);
//                questionQueryWrapper.eq(Question::getQuStatus,QuestionConstant.QU_STATUS_RELEASE);
                questionQueryWrapper.eq(Question::getQuStop,QuestionConstant.QU_STOP_NORMAL);
                questionQueryWrapper.eq(Question::getCategoryType,QuestionConstant.CATEGORY_TYPE_SINGLE_DISEASE);
                questionQueryWrapper.eq(Question::getDel,QuestionConstant.DEL_NORMAL);
                Question question = questionMapper.selectOne(questionQueryWrapper);
                record.setQuestionId(question.getId());
                record.setQuestionName(question.getQuName());
            }
        }
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
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseWaitUploadList(SingleDiseaseWaitUploadParam singleDiseaseWaitUploadParam, String deptId, Integer pageNo, Integer pageSize) {
        Page<QSingleDiseaseTake> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<QSingleDiseaseTake>().lambda();
        queryWrapper.eq(QSingleDiseaseTake::getStatus, QSingleDiseaseTakeConstant.STATUS_WAIT_UPLOAD);
        if(StringUtils.isNotBlank(deptId)){
            queryWrapper.eq(QSingleDiseaseTake::getAnswerDeptid, deptId);
        }
        if (StringUtils.isNotBlank(singleDiseaseWaitUploadParam.getCategoryId())) {
            queryWrapper.eq(QSingleDiseaseTake::getCategoryId, singleDiseaseWaitUploadParam.getCategoryId());
        }
        if (StringUtils.isNotBlank(singleDiseaseWaitUploadParam.getDoctorName())) {
            queryWrapper.like(QSingleDiseaseTake::getDoctorName, singleDiseaseWaitUploadParam.getDoctorName());
        }
        if (StringUtils.isNotBlank(singleDiseaseWaitUploadParam.getPatientName())) {
            queryWrapper.like(QSingleDiseaseTake::getPatientName, singleDiseaseWaitUploadParam.getPatientName());
        }
        if (StringUtils.isNotBlank(singleDiseaseWaitUploadParam.getCategoryId())) {
            queryWrapper.eq(QSingleDiseaseTake::getCategoryId, singleDiseaseWaitUploadParam.getCategoryId());
        }
        if (singleDiseaseWaitUploadParam.getInHospitalStartDate() != null) {
            queryWrapper.ge(QSingleDiseaseTake::getInTime, singleDiseaseWaitUploadParam.getInHospitalStartDate());
        }

        if (singleDiseaseWaitUploadParam.getInHospitalEndDate() != null) {
            queryWrapper.le(QSingleDiseaseTake::getInTime, singleDiseaseWaitUploadParam.getInHospitalEndDate());
        }

        if (singleDiseaseWaitUploadParam.getOutHospitalStartDate() != null) {
            queryWrapper.ge(QSingleDiseaseTake::getOutTime, singleDiseaseWaitUploadParam.getOutHospitalStartDate());
        }

        if (singleDiseaseWaitUploadParam.getOutHospitalEndDate() != null) {
            queryWrapper.le(QSingleDiseaseTake::getOutTime, singleDiseaseWaitUploadParam.getOutHospitalEndDate());
        }
        queryWrapper.eq(QSingleDiseaseTake::getStatus, QSingleDiseaseTakeConstant.STATUS_WAIT_UPLOAD);
        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);

        for (QSingleDiseaseTake record : qSingleDiseaseTakeIPage.getRecords()) {
            String dynamicTableName = record.getDynamicTableName();
            if(StringUtils.isNotBlank(dynamicTableName)){
                LambdaQueryWrapper<Question> questionQueryWrapper = new QueryWrapper<Question>().lambda();
                questionQueryWrapper.in(Question::getTableName, dynamicTableName);
//                questionQueryWrapper.eq(Question::getQuStatus,QuestionConstant.QU_STATUS_RELEASE);
                questionQueryWrapper.eq(Question::getQuStop,QuestionConstant.QU_STOP_NORMAL);
                questionQueryWrapper.eq(Question::getCategoryType,QuestionConstant.CATEGORY_TYPE_SINGLE_DISEASE);
                questionQueryWrapper.eq(Question::getDel,QuestionConstant.DEL_NORMAL);
                Question question = questionMapper.selectOne(questionQueryWrapper);

                record.setQuestionId(question.getId());
                record.setQuestionName(question.getQuName());
            }
        }
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
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDeptList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam, Integer pageNo, Integer pageSize, String deptId) {
        Page<QSingleDiseaseTake> page = new Page<>(pageNo, pageSize);
        QueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDeptParam.getCategoryId())) {
            queryWrapper.like("category_id", qSingleDiseaseTakeByDeptParam.getCategoryId());
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
        //todo  添加科室操作---
        if (StringUtils.isNotBlank(deptId)) {
            queryWrapper.in("tqms_dept", deptId);
        }

        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }

    @Override
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseExamineRecordAllList(SingleDiseaseExamineRecordParam singleDiseaseExamineRecordParam, Integer pageNo, Integer pageSize) {
        Page<QSingleDiseaseTake> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<QSingleDiseaseTake> lambda = new QueryWrapper<QSingleDiseaseTake>().lambda();
        if (StringUtils.isNotBlank(singleDiseaseExamineRecordParam.getCategoryId())) {
            lambda.like(QSingleDiseaseTake::getCategoryId, singleDiseaseExamineRecordParam.getCategoryId());
        }

        if (StringUtils.isNotBlank(singleDiseaseExamineRecordParam.getPatientName())) {
            lambda.like(QSingleDiseaseTake::getPatientName, singleDiseaseExamineRecordParam.getPatientName());
        }

        if (singleDiseaseExamineRecordParam.getOutHospitalStartDate() != null) {
            lambda.ge(QSingleDiseaseTake::getOutTime, singleDiseaseExamineRecordParam.getOutHospitalStartDate());
        }

        if (singleDiseaseExamineRecordParam.getOutHospitalEndDate() != null) {
            lambda.le(QSingleDiseaseTake::getOutTime, singleDiseaseExamineRecordParam.getOutHospitalEndDate());
        }

        if (StringUtils.isNotBlank(singleDiseaseExamineRecordParam.getDoctorName())) {
            lambda.like(QSingleDiseaseTake::getDoctorName, singleDiseaseExamineRecordParam.getDoctorName());
        }

        if (StringUtils.isNotBlank(singleDiseaseExamineRecordParam.getMainDiagnosis())) {
            lambda.like(QSingleDiseaseTake::getMainDiagnosis, singleDiseaseExamineRecordParam.getMainDiagnosis());
        }

        if (singleDiseaseExamineRecordParam.getWriteTimeStartDate() != null) {
            lambda.ge(QSingleDiseaseTake::getWriteTime, singleDiseaseExamineRecordParam.getWriteTimeStartDate());
        }

        if (singleDiseaseExamineRecordParam.getWriteTimeEndDate() != null) {
            lambda.le(QSingleDiseaseTake::getWriteTime, singleDiseaseExamineRecordParam.getWriteTimeEndDate());
        }

        if (singleDiseaseExamineRecordParam.getStatus() != null) {
            lambda.in(QSingleDiseaseTake::getStatus, singleDiseaseExamineRecordParam.getStatus());
        }

        if (StringUtils.isNotBlank(singleDiseaseExamineRecordParam.getDeptId())) {
            lambda.eq(QSingleDiseaseTake::getTqmsDept, singleDiseaseExamineRecordParam.getDeptId());
        }

        lambda.gt(QSingleDiseaseTake::getStatus, QSingleDiseaseTakeConstant.STATUS_WAIT_UPLOAD);
        lambda.ne(QSingleDiseaseTake::getStatus, QSingleDiseaseTakeConstant.STATUS_NO_NEED);

        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, lambda);
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
        params.put("categoryId", qSingleDiseaseTakeReportStatisticParam.getCategoryId());
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
            countParams.put("categoryId", qSingleDiseaseTakeReportStatisticParam.getCategoryId());
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

            LambdaQueryWrapper<Question> questionQueryWrapper = new QueryWrapper<Question>().lambda();
            questionQueryWrapper.in(Question::getTableName, qSingleDiseaseTakeReportStatisticVo.getDynamicTableName());
//            questionQueryWrapper.eq(Question::getQuStatus,QuestionConstant.QU_STATUS_RELEASE);
            questionQueryWrapper.eq(Question::getQuStop,QuestionConstant.QU_STOP_NORMAL);
            questionQueryWrapper.eq(Question::getCategoryType,QuestionConstant.CATEGORY_TYPE_SINGLE_DISEASE);
            questionQueryWrapper.eq(Question::getDel,QuestionConstant.DEL_NORMAL);
            Question question = questionMapper.selectOne(questionQueryWrapper);
//            Question question = questionMapper.selectById(qSingleDiseaseTakeReportStatisticVo.getQuestionId());
            qSingleDiseaseTakeReportStatisticVo.setDisease(question.getQuName());
            qSingleDiseaseTakeReportStatisticVo.setCategoryId(question.getCategoryId());
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

    @Override
    public void singleDiseaseStageAnswer(String cookie, SingleDiseaseAnswerParam singleDiseaseAnswerParam) {
        saveAnswer(cookie, singleDiseaseAnswerParam,QSingleDiseaseTakeConstant.ANSWER_STATUS_NOT_SUBMIT,QSingleDiseaseTakeConstant.STATUS_WAIT_WRITE_GOING);
    }

    private void saveAnswer(String cookie, SingleDiseaseAnswerParam singleDiseaseAnswerParam,Integer answerStatus,Integer status) {
        //解析token
        String res = HttpClient.doPost(tokenUrl, cookie, null);
        JsonRootBean jsonRootBean = JSON.parseObject(res, JsonRootBean.class);
        String answer = "";
        String answerName = "";
        String answerDeptid = "";
        String answerDeptname = "";
        if (jsonRootBean != null) {
            if (jsonRootBean.getData() != null) {
                answer = jsonRootBean.getData().getTbUser().getId();
                answerName = jsonRootBean.getData().getTbUser().getUserName();
                answerDeptid = jsonRootBean.getData().getDeps().get(0).getId();
                answerDeptname = jsonRootBean.getData().getDeps().get(0).getDepName();
            }
        }
        SingleDiseaseAnswer[] answersArray = singleDiseaseAnswerParam.getAnswers();

        QSingleDiseaseTake qSingleDiseaseTake = this.getById(singleDiseaseAnswerParam.getId());
        if(qSingleDiseaseTake==null){
            qSingleDiseaseTake = new QSingleDiseaseTake();
        }
//        QSingleDiseaseTake qSingleDiseaseTake = this.getById(singleDiseaseAnswerParam.getId());
        qSingleDiseaseTake.setId(singleDiseaseAnswerParam.getId());
        qSingleDiseaseTake.setAnswerJson(JSON.toJSONString(answersArray));
        qSingleDiseaseTake.setAnswerStatus(answerStatus);
        qSingleDiseaseTake.setStatus(status);
        qSingleDiseaseTake.setAnswer(answer);
        qSingleDiseaseTake.setAnswerName(answerName);
        qSingleDiseaseTake.setAnswerTime(new Date());
        qSingleDiseaseTake.setAnswerDeptid(answerDeptid);
        qSingleDiseaseTake.setAnswerDeptname(answerDeptname);
        qSingleDiseaseTake.setQuestionId(singleDiseaseAnswerParam.getQuId());
        qSingleDiseaseTake.setWriteTime(new Date());

        Map<String, String> mapCache = new HashMap<>();
        for (SingleDiseaseAnswer a : answersArray) {
            if(StringUtils.isNotBlank(a.getBindValue())){
                mapCache.put(a.getSubColumnName(), a.getBindValue());
            }else{
                mapCache.put(a.getSubColumnName(), a.getSubValue());
            }
        }
        LambdaQueryWrapper<Qsubject> lambdaPatientName = new QueryWrapper<Qsubject>().lambda();
        lambdaPatientName.eq(Qsubject::getColumnName,"xm");
        lambdaPatientName.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        Qsubject qsubject = qsubjectMapper.selectOne(lambdaPatientName);
        if(qsubject!=null){
            String patientName = mapCache.get(qsubject.getColumnName());
            qSingleDiseaseTake.setPatientName(patientName);
        }

        LambdaQueryWrapper<Qsubject> lambdaPatientGender = new QueryWrapper<Qsubject>().lambda();
        lambdaPatientGender.eq(Qsubject::getColumnName,"CM-0-2-1-2");
        lambdaPatientGender.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaPatientGender);
        if(qsubject!=null){
            qSingleDiseaseTake.setPatientGender(mapCache.get(qsubject.getColumnName()));
        }

        LambdaQueryWrapper<Qsubject> lambdaBirthday = new QueryWrapper<Qsubject>().lambda();
        lambdaBirthday.eq(Qsubject::getColumnName,"CM-0-2-1-1");
        lambdaBirthday.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaBirthday);
        if(qsubject!=null){
            String birthday = mapCache.get(qsubject.getColumnName());
            if(StringUtils.isNotBlank(birthday)){
                qSingleDiseaseTake.setAge(String.valueOf(DateUtil.ageOfNow(birthday)));
            }
        }

        LambdaQueryWrapper<Qsubject> lambdaInTime = new QueryWrapper<Qsubject>().lambda();
        lambdaInTime.eq(Qsubject::getColumnName,"CM-0-2-4-1");
        lambdaInTime.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaInTime);
        if(qsubject!=null){
            String dateInTimeString = mapCache.get(qsubject.getColumnName());
            if(StringUtils.isNotBlank(dateInTimeString)){
                Date dateInTime = DateUtil.parse(dateInTimeString).toJdkDate();
                qSingleDiseaseTake.setInTime(dateInTime);
            }
        }

        LambdaQueryWrapper<Qsubject> lambdaOutTime = new QueryWrapper<Qsubject>().lambda();
        lambdaOutTime.eq(Qsubject::getColumnName,"CM-0-2-4-2");
        lambdaOutTime.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaOutTime);
        if(qsubject!=null){
            String dateOutTimeString = mapCache.get(qsubject.getColumnName());
            if(StringUtils.isNotBlank(dateOutTimeString)){
                Date dateOutTime = DateUtil.parse(dateOutTimeString).toJdkDate();
                qSingleDiseaseTake.setOutTime(dateOutTime);
            }
        }

        LambdaQueryWrapper<Qsubject> lambdaDoctor = new QueryWrapper<Qsubject>().lambda();
        lambdaDoctor.eq(Qsubject::getColumnName,"CM-0-1-1-3");
        lambdaDoctor.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaDoctor);
        if (qsubject != null) {
            qSingleDiseaseTake.setDoctorName(mapCache.get(qsubject.getColumnName()));
        }

        LambdaQueryWrapper<Qsubject> lambdaDept = new QueryWrapper<Qsubject>().lambda();
        lambdaDept.eq(Qsubject::getColumnName,"CM-0-1-1-5");
        lambdaDept.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaDept);
        if(qsubject!=null){
            qSingleDiseaseTake.setDepartment(mapCache.get(qsubject.getColumnName()));
        }

        LambdaQueryWrapper<Qsubject> lambdaIdCard = new QueryWrapper<Qsubject>().lambda();
        lambdaIdCard.eq(Qsubject::getColumnName,"IDCard");
        lambdaIdCard.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaIdCard);
        if(qsubject!=null){
            qSingleDiseaseTake.setIdCard(mapCache.get(qsubject.getColumnName()));
        }

        LambdaQueryWrapper<Qsubject> lambdaInHospitalDay = new QueryWrapper<Qsubject>().lambda();
        lambdaInHospitalDay.eq(Qsubject::getColumnName,"CM-4-1");
        lambdaInHospitalDay.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaInHospitalDay);
        if (qsubject != null) {
            String inHospitalDayString = mapCache.get(qsubject.getColumnName());
            if(StringUtils.isNotBlank(inHospitalDayString)){
                if(inHospitalDayString.contains("天")){
                    inHospitalDayString = inHospitalDayString.replaceAll("天","");
                }
                qSingleDiseaseTake.setInHospitalDay(Integer.parseInt(inHospitalDayString));
            }
        }


        LambdaQueryWrapper<Qsubject> lambdaInHospitalFee = new QueryWrapper<Qsubject>().lambda();
        lambdaInHospitalFee.eq(Qsubject::getColumnName,"CM-6-1");
        lambdaInHospitalFee.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaInHospitalFee);
        if(qsubject!=null){
            String inHospitalFee = mapCache.get(qsubject.getColumnName());
            if(StringUtils.isNotBlank(inHospitalFee)){
                qSingleDiseaseTake.setInHospitalFee(PriceUtil.toFenInt(new BigDecimal(inHospitalFee)));
            }
        }

        LambdaQueryWrapper<Qsubject> lambdaOperationTreatmentFee = new QueryWrapper<Qsubject>().lambda();
        lambdaOperationTreatmentFee.eq(Qsubject::getColumnName,"CM-6-13");
        lambdaOperationTreatmentFee.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaOperationTreatmentFee);
        if(qsubject!=null){
            String operationTreatmentFeeString = mapCache.get(qsubject.getColumnName());
            if(StringUtils.isNotBlank(operationTreatmentFeeString)){
                qSingleDiseaseTake.setOperationTreatmentFee(PriceUtil.toFenInt(new BigDecimal(operationTreatmentFeeString)));
            }
        }

        LambdaQueryWrapper<Qsubject> lambdaDisposableConsumable = new QueryWrapper<Qsubject>().lambda();
        lambdaDisposableConsumable.eq(Qsubject::getColumnName,"CM-6-29");
        lambdaDisposableConsumable.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaDisposableConsumable);
        Integer operationDisposableMaterialFee=0;
        if(qsubject!=null){
            String operationDisposableMaterialFeeString = mapCache.get(qsubject.getColumnName())==null?"0":mapCache.get(qsubject.getColumnName());
            operationDisposableMaterialFee= PriceUtil.toFenInt(new BigDecimal(operationDisposableMaterialFeeString));
        }

        LambdaQueryWrapper<Qsubject> lambdaExaminationDisposableConsumable = new QueryWrapper<Qsubject>().lambda();
        lambdaExaminationDisposableConsumable.eq(Qsubject::getColumnName,"CM-6-27");
        lambdaExaminationDisposableConsumable.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaExaminationDisposableConsumable);
        Integer examinationDisposableMaterialFee=0;
        if(qsubject!=null){
            String examinationDisposableMaterialFeeString = mapCache.get(qsubject.getColumnName())==null?"0":mapCache.get(qsubject.getColumnName());
            examinationDisposableMaterialFee = PriceUtil.toFenInt(new BigDecimal(examinationDisposableMaterialFeeString));
        }

        LambdaQueryWrapper<Qsubject> lambdaTreatmentDisposableConsumable = new QueryWrapper<Qsubject>().lambda();
        lambdaTreatmentDisposableConsumable.eq(Qsubject::getColumnName,"CM-6-28");
        lambdaTreatmentDisposableConsumable.eq(Qsubject::getQuId,singleDiseaseAnswerParam.getQuId());
        qsubject = qsubjectMapper.selectOne(lambdaTreatmentDisposableConsumable);
        Integer treatmentDisposableMaterialFee=0;
        if(qsubject!=null){
            String treatmentDisposableMaterialFeeString = mapCache.get(qsubject.getColumnName())==null?"0":mapCache.get(qsubject.getColumnName());
            treatmentDisposableMaterialFee = PriceUtil.toFenInt(new BigDecimal(treatmentDisposableMaterialFeeString));
        }

        qSingleDiseaseTake.setDisposableConsumable(treatmentDisposableMaterialFee+examinationDisposableMaterialFee+operationDisposableMaterialFee);

        Question question = questionMapper.selectById(singleDiseaseAnswerParam.getQuId());
        qSingleDiseaseTake.setDynamicTableName(question.getTableName());
        qSingleDiseaseTake.setCategoryId(Long.parseLong(question.getCategoryId()));
        //todo   categoryId 从Int改为Long
//        qSingleDiseaseTake.setCategoryId(Integer.parseInt(question.getCategoryId()));
        boolean insertOrUpdate = qSingleDiseaseTake.getId() != null && qSingleDiseaseTake.getId() != 0;
        if (insertOrUpdate) {
            this.qSingleDiseaseTakeMapper.updateById(qSingleDiseaseTake);
        } else {
            this.qSingleDiseaseTakeMapper.insert(qSingleDiseaseTake);
        }

        //插入答案表
        StringBuffer sqlAns = new StringBuffer();
        if (insertOrUpdate) {
            sqlAns.append("update `" + question.getTableName() + "` set ");
            List<Qsubject> subjectList = qsubjectMapper.selectSubjectByQuId(singleDiseaseAnswerParam.getQuId());
            for (int i = 0; i < subjectList.size(); i++) {
                Qsubject qsubjectDynamicTable = subjectList.get(i);
                String subType = qsubjectDynamicTable.getSubType();
                Integer del = qsubjectDynamicTable.getDel();
                if (QuestionConstant.SUB_TYPE_GROUP.equals(subType) || QuestionConstant.SUB_TYPE_TITLE.equals(subType)
                        || QuestionConstant.DEL_DELETED.equals(del) || mapCache.get(qsubjectDynamicTable.getColumnName())==null
                        || StringUtils.isBlank(mapCache.get(qsubjectDynamicTable.getColumnName()))) {
                    continue;
                }
                sqlAns.append("`");
                sqlAns.append(qsubjectDynamicTable.getColumnName());
                sqlAns.append("`");
                sqlAns.append("=");
                sqlAns.append("'");
                sqlAns.append(mapCache.get(qsubjectDynamicTable.getColumnName()));
                sqlAns.append("'");
                sqlAns.append(",");
            }
            sqlAns.delete(sqlAns.length()-1,sqlAns.length());
            sqlAns.append(" where id = ");
            sqlAns.append(qSingleDiseaseTake.getTableId());
            log.info("-----insert sqlAns:{}", sqlAns.toString());
            dynamicTableMapper.updateDynamicTable(sqlAns.toString());
        }else {
            sqlAns.append("insert into `" + question.getTableName() + "` (");

            List<Qsubject> subjectList = qsubjectMapper.selectSubjectByQuId(singleDiseaseAnswerParam.getQuId());
            for (int i = 0; i < subjectList.size(); i++) {
                Qsubject qsubjectDynamicTable = subjectList.get(i);
                String subType = qsubjectDynamicTable.getSubType();
                Integer del = qsubjectDynamicTable.getDel();
                if (QuestionConstant.SUB_TYPE_GROUP.equals(subType) || QuestionConstant.SUB_TYPE_TITLE.equals(subType)
                        || QuestionConstant.DEL_DELETED.equals(del) || mapCache.get(qsubjectDynamicTable.getColumnName())==null
                        || StringUtils.isBlank(mapCache.get(qsubjectDynamicTable.getColumnName()))) {
                    continue;
                }
                sqlAns.append("`");
                sqlAns.append(qsubjectDynamicTable.getColumnName());
                sqlAns.append("`");
                sqlAns.append(",");
            }
            sqlAns.delete(sqlAns.length()-1,sqlAns.length());

            sqlAns.append(") values (");
            for (int i = 0; i < subjectList.size(); i++) {
                Qsubject qsubjectDynamicTable = subjectList.get(i);
                String subType = qsubjectDynamicTable.getSubType();
                Integer del = qsubjectDynamicTable.getDel();
                if (QuestionConstant.SUB_TYPE_GROUP.equals(subType) || QuestionConstant.SUB_TYPE_TITLE.equals(subType)
                        || QuestionConstant.DEL_DELETED.equals(del) || mapCache.get(qsubjectDynamicTable.getColumnName())==null) {
                    continue;
                }
                sqlAns.append("'");
                sqlAns.append(mapCache.get(qsubjectDynamicTable.getColumnName()));
                sqlAns.append("'");
                sqlAns.append(",");
            }
            sqlAns.delete(sqlAns.length()-1,sqlAns.length());

            sqlAns.append(")");
            log.info("-----insert sqlAns:{}", sqlAns.toString());
            dynamicTableMapper.insertDynamicTable(sqlAns.toString());

            StringBuffer sqlSelect = new StringBuffer();
            sqlSelect.append("select * from  `");
            sqlSelect.append(question.getTableName());
            sqlSelect.append("` where ");
            for (int i = 0; i < subjectList.size(); i++) {
                Qsubject qsubjectDynamicTable = subjectList.get(i);
                String subType = qsubjectDynamicTable.getSubType();
                Integer del = qsubjectDynamicTable.getDel();
                if (QuestionConstant.SUB_TYPE_GROUP.equals(subType) || QuestionConstant.SUB_TYPE_TITLE.equals(subType)
                        || QuestionConstant.DEL_DELETED.equals(del) || mapCache.get(qsubjectDynamicTable.getColumnName())==null) {
                    continue;
                }
                sqlSelect.append("`");
                sqlSelect.append(qsubjectDynamicTable.getColumnName());
                sqlSelect.append("`");
                sqlSelect.append("=");
                sqlSelect.append("'");
                sqlSelect.append(mapCache.get(qsubjectDynamicTable.getColumnName()));
                sqlSelect.append("'");
                sqlSelect.append(" and ");
            }
            int i = sqlSelect.lastIndexOf(" and ");
            sqlSelect.delete(i,sqlSelect.length());
            log.info("-----select sqlSelect:{}", sqlSelect.toString());
            Map<String, String> stringStringMap = dynamicTableMapper.selectDynamicTableColumn(sqlSelect.toString());
            qSingleDiseaseTake.setTableId(String.valueOf(stringStringMap.get("id")));
            this.qSingleDiseaseTakeMapper.updateById(qSingleDiseaseTake);
        }
    }


    @Override
    public void singleDiseaseAnswer(String cookie, SingleDiseaseAnswerParam singleDiseaseAnswerParam) {
        saveAnswer(cookie, singleDiseaseAnswerParam,QSingleDiseaseTakeConstant.ANSWER_STATUS_SUBMIT,QSingleDiseaseTakeConstant.STATUS_WAIT_UPLOAD);
    }

    @Override
    public String singleDiseaseAnswerQueryById(Integer id) {
        String answer = null;

        QSingleDiseaseTake qSingleDiseaseTake = qSingleDiseaseTakeMapper.selectById(id);
        String answerJson = (String) qSingleDiseaseTake.getAnswerJson();
        List<SingleDiseaseAnswer> singleDiseaseAnswerList = JSON.parseArray(answerJson, SingleDiseaseAnswer.class);
        Map<String, SingleDiseaseAnswer> mapCache = new HashMap<>();
        if(singleDiseaseAnswerList!=null && !singleDiseaseAnswerList.isEmpty()){
            for (SingleDiseaseAnswer a : singleDiseaseAnswerList) {
                mapCache.put(a.getSubColumnName(), a);
            }
        }
        String dynamicTableName = qSingleDiseaseTake.getDynamicTableName();
        LambdaQueryWrapper<Question> questionQueryWrapper = new QueryWrapper<Question>().lambda();
        questionQueryWrapper.in(Question::getTableName, dynamicTableName);
//        questionQueryWrapper.eq(Question::getQuStatus,QuestionConstant.QU_STATUS_RELEASE);
        questionQueryWrapper.eq(Question::getQuStop,QuestionConstant.QU_STOP_NORMAL);
        questionQueryWrapper.eq(Question::getCategoryType,QuestionConstant.CATEGORY_TYPE_SINGLE_DISEASE);
        questionQueryWrapper.eq(Question::getDel,QuestionConstant.DEL_NORMAL);
        Question question = questionMapper.selectOne(questionQueryWrapper);


        StringBuffer sqlAns = new StringBuffer();
        if (question != null) {
            sqlAns.append("select * from `");
            sqlAns.append(question.getTableName());
            sqlAns.append("` where id =");
            sqlAns.append(qSingleDiseaseTake.getTableId());
            Map<String,String> map = dynamicTableMapper.selectDynamicTableColumn(sqlAns.toString());
//            String s = "select * from q_single_disease_take where id =20 ";
//            Map<String, String> map = dynamicTableMapper.selectDynamicTableColumn(s);
            if(map==null){
                return null;
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                QueryWrapper<Qsubject> wrapper = new QueryWrapper<Qsubject>();
                if("id".equals(entry.getKey())){
                    continue;
                }
                wrapper.eq("column_name", entry.getKey());
                wrapper.eq("qu_id", question.getId());
                wrapper.eq("del", "0");
                Qsubject qsubject = qsubjectMapper.selectOne(wrapper);
                if(qsubject==null){
                    continue;
                }
                SingleDiseaseAnswer singleDiseaseAnswer = new SingleDiseaseAnswer();
                singleDiseaseAnswer.setSubColumnName(qsubject.getColumnName());
                singleDiseaseAnswer.setSubValue(String.valueOf(entry.getValue()));
                mapCache.put(qsubject.getColumnName(), singleDiseaseAnswer);
            }
            List<SingleDiseaseAnswer> resList = new ArrayList<>(mapCache.values());
            answer = JSON.toJSONString(resList);
        }

        return answer;
    }

    @Override
    public List<QSingleDiseaseTakeReportStatisticOverviewLineVo> allSingleDiseaseReportStatisticOverviewLine(QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewLineParam) {
        String dateType = qSingleDiseaseTakeReportStatisticOverviewLineParam.getDateType();
        String dateStart = qSingleDiseaseTakeReportStatisticOverviewLineParam.getDateStart();
        String dateEnd = qSingleDiseaseTakeReportStatisticOverviewLineParam.getDateEnd();
        Date startDate;
        Date endDate;
        if (QSingleDiseaseTakeConstant.DATE_TYPE_YEARLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfYear().withMaximumValue().plusDays(1).toDate();
        } else if (QSingleDiseaseTakeConstant.DATE_TYPE_MONTHLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfMonth().withMaximumValue().plusDays(1).toDate();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.plusDays(1).toDate();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("dateStart", startDate);
        params.put("dateEnd", endDate);
        String[] dept = qSingleDiseaseTakeReportStatisticOverviewLineParam.getDept();
        String[] categoryId = qSingleDiseaseTakeReportStatisticOverviewLineParam.getCategoryId();
        params.put("dept", dept);
        params.put("dateType", dateType);
        params.put("categoryId", categoryId);
        params.put("status", QSingleDiseaseTakeConstant.STATUS_COMPLETE);
        List<QSingleDiseaseTakeReportStatisticOverviewLineVo> overviewLineVoList =  this.qSingleDiseaseTakeMapper.allSingleDiseaseReportStatisticOverviewLine(params);
        return overviewLineVoList;
    }

    @Override
    public List<QSingleDiseaseTakeReportStatisticOverviewPieVo> allSingleDiseaseReportStatisticOverviewPie(QSingleDiseaseTakeReportStatisticOverviewPieParam qSingleDiseaseTakeReportStatisticOverviewPieParam) {
        String dateType = qSingleDiseaseTakeReportStatisticOverviewPieParam.getDateType();
        String dateStr = qSingleDiseaseTakeReportStatisticOverviewPieParam.getDate();
        Date date;
        if (QSingleDiseaseTakeConstant.DATE_TYPE_YEARLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStr);
            date = startDateTime.dayOfMonth().withMinimumValue().toDate();
        } else if (QSingleDiseaseTakeConstant.DATE_TYPE_MONTHLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStr);
            date = startDateTime.dayOfMonth().withMinimumValue().toDate();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStr);
            date = startDateTime.toDate();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("date", dateStr);
        String[] dept = qSingleDiseaseTakeReportStatisticOverviewPieParam.getDept();
        String[] categoryId = qSingleDiseaseTakeReportStatisticOverviewPieParam.getCategoryId();
        params.put("dept", dept);
        params.put("dateType", dateType);
        params.put("categoryId", categoryId);
        params.put("status", QSingleDiseaseTakeConstant.STATUS_COMPLETE);

        List<QSingleDiseaseTakeReportStatisticOverviewPieVo> overviewPieVoList =  this.qSingleDiseaseTakeMapper.allSingleDiseaseReportStatisticOverviewPie(params);
//        overviewPieVoList.stream().count()
        int sum = overviewPieVoList.stream().mapToInt(q->Integer.parseInt(q.getNumber())).sum();
        for (QSingleDiseaseTakeReportStatisticOverviewPieVo qSingleDiseaseTakeReportStatisticOverviewPieVo : overviewPieVoList) {
            int i = Integer.parseInt(qSingleDiseaseTakeReportStatisticOverviewPieVo.getNumber());
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            String percentage = numberFormat.format((float) i / (float) sum * 100) + "%";
            qSingleDiseaseTakeReportStatisticOverviewPieVo.setPercentage(percentage);
        }
        return overviewPieVoList;
    }

    @Override
    public List<QSingleDiseaseTakeReportStatisticTrendVo> allSingleDiseaseReportStatisticTrend(QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewParam) {
        String dateType = qSingleDiseaseTakeReportStatisticOverviewParam.getDateType();
        String dateStart = qSingleDiseaseTakeReportStatisticOverviewParam.getDateStart();
        String dateEnd = qSingleDiseaseTakeReportStatisticOverviewParam.getDateEnd();
        Date startDate;
        Date endDate;
        if (QSingleDiseaseTakeConstant.DATE_TYPE_YEARLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfYear().withMaximumValue().plusDays(1).toDate();
        } else if (QSingleDiseaseTakeConstant.DATE_TYPE_MONTHLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfMonth().withMaximumValue().plusDays(1).toDate();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.plusDays(1).toDate();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("dateStart", startDate);
        params.put("dateEnd", endDate);
        String[] dept = qSingleDiseaseTakeReportStatisticOverviewParam.getDept();
        String[] categoryId = qSingleDiseaseTakeReportStatisticOverviewParam.getCategoryId();
        params.put("dept", dept);
        params.put("dateType", dateType);
        params.put("categoryId", categoryId);
        params.put("status", QSingleDiseaseTakeConstant.STATUS_COMPLETE);
        List<QSingleDiseaseTakeReportStatisticTrendVo> overviewLineVoList =  this.qSingleDiseaseTakeMapper.allSingleDiseaseReportStatisticTrend(params);
        return overviewLineVoList;
    }


    @Override
    public List<QSingleDiseaseTakeReportStatisticDeptPermutationVo> allSingleDiseaseReportStatisticDeptPermutation(QSingleDiseaseTakeReportStatisticDeptPermutationParam qSingleDiseaseTakeReportStatisticDeptPermutationParam) {
        String dateType = qSingleDiseaseTakeReportStatisticDeptPermutationParam.getDateType();
        String dateStart = qSingleDiseaseTakeReportStatisticDeptPermutationParam.getDateStart();
        String dateEnd = qSingleDiseaseTakeReportStatisticDeptPermutationParam.getDateEnd();
        Date startDate;
        Date endDate;
        if (QSingleDiseaseTakeConstant.DATE_TYPE_YEARLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfYear().withMaximumValue().plusDays(1).toDate();
        } else if (QSingleDiseaseTakeConstant.DATE_TYPE_MONTHLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfMonth().withMaximumValue().plusDays(1).toDate();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.plusDays(1).toDate();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("dateStart", startDate);
        params.put("dateEnd", endDate);
        String[] categoryId = qSingleDiseaseTakeReportStatisticDeptPermutationParam.getCategoryId();
        params.put("dateType", dateType);
        params.put("categoryId", categoryId);
        params.put("status", QSingleDiseaseTakeConstant.STATUS_COMPLETE);
        List<QSingleDiseaseTakeReportStatisticDeptPermutationVo> overviewLineVoList =  this.qSingleDiseaseTakeMapper.allSingleDiseaseReportStatisticDeptPermutation(params);
        return overviewLineVoList;
    }

    @Override
    public List<QSingleDiseaseTakeReportStatisticSummaryVo> allSingleDiseaseReportStatisticSummary(QSingleDiseaseTakeReportStatisticSummaryParam qSingleDiseaseTakeReportStatisticSummaryParam){
        String dateType = qSingleDiseaseTakeReportStatisticSummaryParam.getDateType();
        String dateStart = qSingleDiseaseTakeReportStatisticSummaryParam.getDateStart();
        String dateEnd = qSingleDiseaseTakeReportStatisticSummaryParam.getDateEnd();
        Date startDate;
        Date endDate;
        if (QSingleDiseaseTakeConstant.DATE_TYPE_YEARLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfYear().withMaximumValue().plusDays(1).toDate();
        } else if (QSingleDiseaseTakeConstant.DATE_TYPE_MONTHLY.equals(dateType)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.dayOfMonth().withMinimumValue().toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.dayOfMonth().withMaximumValue().plusDays(1).toDate();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
            startDate = startDateTime.toDate();
            DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
            endDate = endDateTime.plusDays(1).toDate();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("dateStart", startDate);
        params.put("dateEnd", endDate);
        String dept = qSingleDiseaseTakeReportStatisticSummaryParam.getDept();
        params.put("dept", dept);
        params.put("dateType", dateType);
        params.put("status", QSingleDiseaseTakeConstant.STATUS_COMPLETE);
        List<QSingleDiseaseTakeReportStatisticSummaryVo> overviewLineVoList =  this.qSingleDiseaseTakeMapper.allSingleDiseaseReportStatisticSummary(params);
        return overviewLineVoList;
    }

    @Override
    public WorkbenchReminderVo workbenchReminder(String dept) {
        Integer notWriteCount = this.qSingleDiseaseTakeMapper.workbenchReminderNotWriteCount(dept);
        Integer rejectCount = this.qSingleDiseaseTakeMapper.workbenchReminderRejectCount(dept);
        return WorkbenchReminderVo.builder().notWriteCount(notWriteCount).rejectCount(rejectCount).build();
    }


    @Override
    public void  runSingleDiseaseTakeReport() {
        LambdaQueryWrapper<QSingleDiseaseTake> lambda = new QueryWrapper<QSingleDiseaseTake>().lambda();
        lambda.eq(QSingleDiseaseTake::getStatus,QSingleDiseaseTakeConstant.STATUS_PASS_WAIT_UPLOAD);
        List<QSingleDiseaseTake> qSingleDiseaseTakeList = this.list(lambda);
        List<String> dynamicTableNameList = qSingleDiseaseTakeList.stream().map(QSingleDiseaseTake::getDynamicTableName).collect(Collectors.toList());

        LambdaQueryWrapper<Question> questionQueryWrapper = new QueryWrapper<Question>().lambda();
        questionQueryWrapper.in(Question::getTableName, dynamicTableNameList);
        questionQueryWrapper.eq(Question::getQuStatus,"1");
        questionQueryWrapper.eq(Question::getCategoryType,"1");
        questionQueryWrapper.eq(Question::getDel,"0");
        List<Question> questionList = questionMapper.selectList(questionQueryWrapper);
        List<Integer> questionIds = questionList.stream().map(Question::getId).collect(Collectors.toList());
//        Map<Integer, Question> questionMap = questionList.stream().collect(Collectors.toMap(Question::getId, Function.identity()));

        LambdaQueryWrapper<Qsubject> qsubjectQueryWrapper = new QueryWrapper<Qsubject>().lambda();
        qsubjectQueryWrapper.in(Qsubject::getQuId, questionIds);
        qsubjectQueryWrapper.eq(Qsubject::getDel, "0");
        List<Qsubject> qsubjectList = qsubjectMapper.selectList(qsubjectQueryWrapper);

        Map<String, Qsubject> qsubjectMap= Maps.newConcurrentMap();
        qsubjectList.forEach(q->{
            if(q.getColumnName()==null){
                return;
            }
            String key = String.format("%s%s", q.getQuId(), q.getColumnName());
            qsubjectMap.put(key,q);
        });
//        Map<String, List<Qsubject>> qsubjectMap = qsubjectList.stream().collect(Collectors.toMap(Qsubject::getColumnName, q->Lists.newArrayList(q),
//                (List<Qsubject> n1, List<Qsubject> n2) -> {
//            n1.addAll(n2);
//            return n1;
//        }));

        LambdaQueryWrapper<TqmsQuotaCategory> queryWrapper = new QueryWrapper<TqmsQuotaCategory>().lambda();
        queryWrapper.eq(TqmsQuotaCategory::getIsSingleDisease, TqmsQuotaCategoryConstant.IS_SINGLE_DISEASE);
        List<TqmsQuotaCategory> quotaCategoryList = tqmsQuotaCategoryMapper.selectList(queryWrapper);
        Map<Long, TqmsQuotaCategory> quotaCategoryMap = quotaCategoryList.stream().collect(Collectors.toMap(TqmsQuotaCategory::getCategoryId, Function.identity()));

        qSingleDiseaseTakeList.stream().forEach(qSingleDiseaseTake->{
            String answerJson = (String) qSingleDiseaseTake.getAnswerJson();
            List<SingleDiseaseAnswer> singleDiseaseAnswerList = JSON.parseArray(answerJson, SingleDiseaseAnswer.class);
            if (singleDiseaseAnswerList != null && !singleDiseaseAnswerList.isEmpty()) {
                Map<String, String> mapCache = new HashMap<>();
                for (SingleDiseaseAnswer a : singleDiseaseAnswerList) {

                    String key = String.format("%s%s", qSingleDiseaseTake.getQuestionId(), a.getSubColumnName());
                    Qsubject qsubject = qsubjectMap.get(key);
                    String subType = qsubject.getSubType();
                    String subValue = a.getSubValue();
                    if(QsubjectConstant.SUB_TYPE_MULTIPLE_CHOICE.equals(subType)){
                        a.setSubValue(JSON.toJSONString(subValue.split("\\$")));
                    }else if (QsubjectConstant.SUB_TYPE_DATE.equals(subType)){
                        cn.hutool.core.date.DateTime parse = DateUtil.parse(subValue, parsePatterns);
                        if (a.getSubColumnName().equals("STK-1-4-3-2-1") || a.getSubColumnName().equals("STK-3-3-2-1-1")
                        || a.getSubColumnName().equals("STK-1-5-2-1") || a.getSubColumnName().equals("STK-1-4-4-3")
                        || a.getSubColumnName().equals("CM-0-2-4-1") || a.getSubColumnName().equals("CM-0-2-4-2")
                        || a.getSubColumnName().equals("CM-0-2-5-2") || a.getSubColumnName().equals("CM-0-2-5-1")
                        || a.getSubColumnName().equals("Cap-Adult-4-2-3") || a.getSubColumnName().equals("Cap-Adult-4-1-3")
                        || a.getSubColumnName().equals("Cap-Adult-4-2-4") || a.getSubColumnName().equals("Cap-Adult-4-4")
                        || a.getSubColumnName().equals("Cap-Adult-6-2-5-1") || a.getSubColumnName().equals("Cap-Adult-6-2-4-2")
                        || a.getSubColumnName().equals("CM-0-2-3-2") || a.getSubColumnName().equals("STK-1-4-2-2-1")
                        || a.getSubColumnName().equals("STK-1-4-5-9") || a.getSubColumnName().equals("STK-1-4-1-1-1")
                        || a.getSubColumnName().equals("STK-1-3-3-1") || a.getSubColumnName().equals("STK-1-2-1-2-1")
                        || a.getSubColumnName().equals("CM-0-2-6-1") || a.getSubColumnName().equals("CM-1-4-1")
                        || a.getSubColumnName().equals("CM-1-6-1") || a.getSubColumnName().equals("CM-0-2-6-2")
                        || a.getSubColumnName().equals("STK-1-1-1-2") || a.getSubColumnName().equals("CM-0-2-2-2")) {
                            a.setSubValue(parse.toString(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                        } else {
                            a.setSubValue(parse.toString(DatePattern.NORM_DATE_PATTERN));
                        }

                    }else if (QsubjectConstant.SUB_TYPE_TIME.equals(subType)){
                        if (a.getSubColumnName().equals("Knee-5-1-4") || a.getSubColumnName().equals("Cap-3-2-4")
                        || a.getSubColumnName().equals("TN-2-2-2") || a.getSubColumnName().equals("TN-2-2-3")) {
                            cn.hutool.core.date.DateTime parse = DateUtil.parse(subValue, parsePatterns);
                            a.setSubValue(parse.toString(DatePattern.NORM_DATETIME_PATTERN));
                        } else {
                            cn.hutool.core.date.DateTime parse = DateUtil.parse(subValue, parsePatterns);
                            a.setSubValue(parse.toString(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                        }
                    } else if (QsubjectConstant.SUB_TYPE_RESULT.equals(subType)) {
                        if (a.getSubColumnName().equals("Cap-3-5-2")) {
                            cn.hutool.core.date.DateTime parse = DateUtil.parse(subValue, parsePatterns);
                            a.setSubValue(parse.toString(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                        }
                    }
                    if (a.getSubColumnName().equals("STK-9-1-7")) {
                        if (a.getSubValue().equals("a")) {
                            a.setSubValue("y");
                        } else  if (a.getSubValue().equals("b")){
                            a.setSubValue("n");
                        }
                    }
                    if (qSingleDiseaseTake.getQuestionId().equals(100)){
                        if (a.getSubColumnName().equals("CM-0-1-4-1")) {
                            if (a.getSubValue().equals("a")) {
                                a.setSubValue("c");
                            } else  if (a.getSubValue().equals("b")){
                                a.setSubValue("d");
                            } if (a.getSubValue().equals("c")) {
                                a.setSubValue("e");
                            } else if(a.getSubValue().equals("d")) {
                                a.setSubValue("h");
                            }
                        }
                    }
                    if (qSingleDiseaseTake.getQuestionId().equals(138)) {
                        if (a.getSubColumnName().equals("CM-0-1-4-2") || a.getSubColumnName().equals("CM-0-1-4-1")) {
                            String value = a.getSubValue();
                            String s = value.substring(2,3);
                            a.setSubValue(s);
                        }
                    }
                    if (qSingleDiseaseTake.getQuestionId().equals(80)) {
                        if (a.getSubColumnName().equals("Cap-Adult-8-3-2")) {
                            String value = a.getSubValue();
                            String s = value.substring(2,3);
                            a.setSubValue(s);
                        }
                    }
                    if (qSingleDiseaseTake.getQuestionId().equals(103)) {
                        if (a.getSubColumnName().equals("CM-2-2")) {
                            String value = a.getSubValue();
                            String s = value.substring(2,3);
                            a.setSubValue(s);
                        }
                    }
                    if (qSingleDiseaseTake.getQuestionId().equals(115) || qSingleDiseaseTake.getQuestionId().equals(122)
                    || qSingleDiseaseTake.getQuestionId().equals(100)) {
                        if (a.getSubColumnName().equals("CM-0-2-2-1") || a.getSubColumnName().equals("CM-0-2-3-1")) {
                            if (a.getSubValue().length() >= 8 && !a.getSubValue().equals("[\"\"]")){
                                String s = a.getSubValue().substring(5, 8);
                                a.setSubValue(s);
                            }
                        }
                    }
                    mapCache.put(a.getSubColumnName(), a.getSubValue());
                    if (qSingleDiseaseTake.getQuestionId().equals(115) || qSingleDiseaseTake.getQuestionId().equals(122)
                            || qSingleDiseaseTake.getQuestionId().equals(100) || qSingleDiseaseTake.getQuestionId().equals(150)) {
                        if (a.getSubColumnName().equals("CM-0-2-2-1") || a.getSubColumnName().equals("CM-0-2-3-1")) {
                            if (a.getSubValue().length() < 8 && !a.getSubValue().equals("UTD")){
                                mapCache.remove(a.getSubColumnName());
                            }
                        } else if (a.getSubColumnName().equals("TN-1-4-2-1") || a.getSubColumnName().equals("TN-3-2-2-1")) {
                            mapCache.remove(a.getSubColumnName());
                        }
                    }
                    if (qSingleDiseaseTake.getQuestionId().equals(99)) {
                        if (a.getSubColumnName().equals("CM-0-2-2-1") || a.getSubColumnName().equals("ALL-0-2-3-1")) {
                            if (a.getSubValue().length() < 5 && !a.getSubValue().equals("UTD")) {
                                mapCache.remove(a.getSubColumnName());
                            }
                        }
                    }
                    if (qSingleDiseaseTake.getQuestionId().equals(106)) {
                        if (a.getSubColumnName().equals("CM-0-2-2-1") || a.getSubColumnName().equals("APL-0-2-3-1")) {
                            if (a.getSubValue().length() < 5 && !a.getSubValue().equals("UTD")) {
                                mapCache.remove(a.getSubColumnName());
                            }
                        }
                    }
                }
                singleDiseaseReportUrl = String.format(singleDiseaseReportUrl,quotaCategoryMap.get(qSingleDiseaseTake.getCategoryId()).getDiseaseType());
                HttpData data = HttpData.instance();
                data.setPostEntity(new StringEntity(JSON.toJSONString(mapCache), ContentType.APPLICATION_JSON));
                mapCache.clear();
                // 接口调用并返回结果
                ResponseEntity responseEntity = null;
                try {
                    responseEntity = HttpTools.post(singleDiseaseReportUrl, data);
                    if (responseEntity.isOk()) {
                        log.info("sync businessSync success.{}", responseEntity);
                        JSONObject jsonObject = JSON.parseObject(responseEntity.getContent());
                        Integer status = jsonObject.getInteger("status");
                        if (Objects.equals(HttpStatus.OK.value(), status)) {
                            JSONObject obj = jsonObject.getJSONObject("data");
                            Integer objStatus = obj.getInteger("status");
                            if (Objects.equals(objStatus, 20) && obj.containsKey("signed") && StringUtils.isNotBlank(obj.getString("signed"))) {
                                String signed = obj.getString("signed");
                            }
                        }
                    } else {
                        log.info("sync businessSync fail.{}", responseEntity);
                    }
                } catch (IOException e) {
                    log.error("国家上报定时器报错-->",e);
                }
                log.info("qSingleDiseaseTake上报id-->{},国家上报接口响应：{}",qSingleDiseaseTake.getId(),responseEntity);
            }
        });
    }

    @Override
    public List<SingleDiseaseAnswerNavigationVo> singleDiseaseAnswerNavigation(SingleDiseaseAnswerNavigationParam singleDiseaseAnswerNavigationParam) {
        List<SingleDiseaseAnswerNavigationVo> res = new ArrayList<>();

        Integer id = singleDiseaseAnswerNavigationParam.getId();
        Integer questionId = singleDiseaseAnswerNavigationParam.getQuestionId();

        LambdaQueryWrapper<Question> questionQueryWrapper = new QueryWrapper<Question>().lambda();
        questionQueryWrapper.in(Question::getId, questionId);
//        questionQueryWrapper.eq(Question::getQuStatus,QuestionConstant.QU_STATUS_RELEASE);
        questionQueryWrapper.eq(Question::getQuStop,QuestionConstant.QU_STOP_NORMAL);
//        questionQueryWrapper.eq(Question::getCategoryType,QuestionConstant.CATEGORY_TYPE_SINGLE_DISEASE);
        questionQueryWrapper.eq(Question::getDel,QuestionConstant.DEL_NORMAL);
        Question question = questionMapper.selectOne(questionQueryWrapper);
        if(question==null){
            log.info("question is null---id-->{}<---questionId-->{}", id,questionId);
            return null;
        }

        LambdaQueryWrapper<Qsubject> qsubjectLambdaQueryWrapper = new QueryWrapper<Qsubject>().lambda();
        qsubjectLambdaQueryWrapper.eq(Qsubject::getQuId, question.getId());
        qsubjectLambdaQueryWrapper.eq(Qsubject::getSubType, QsubjectConstant.SUB_TYPE_GROUP);
        qsubjectLambdaQueryWrapper.eq(Qsubject::getDel, QsubjectConstant.DEL_NORMAL);
        qsubjectLambdaQueryWrapper.orderByAsc(Qsubject::getOrderNum);
        List<Qsubject> qsubjectList = qsubjectMapper.selectList(qsubjectLambdaQueryWrapper);

        QSingleDiseaseTake qSingleDiseaseTake = this.getById(id);
        Map<String, String> mapCache = new HashMap<>();
        if(qSingleDiseaseTake!=null){
            String answerJson = (String) qSingleDiseaseTake.getAnswerJson();
            List<SingleDiseaseAnswer> singleDiseaseAnswerList = JSON.parseArray(answerJson, SingleDiseaseAnswer.class);
            if(singleDiseaseAnswerList!=null && !singleDiseaseAnswerList.isEmpty()){
                for (SingleDiseaseAnswer a : singleDiseaseAnswerList) {
                    if(StringUtils.isNotBlank(a.getBindValue())){
                        mapCache.put(a.getSubColumnName(), a.getBindValue());
                    }else{
                        mapCache.put(a.getSubColumnName(), a.getSubValue());
                    }
                }
            }
        }

        qsubjectList.forEach(qsubject -> {
            String[] qsubjectIds = qsubject.getGroupIds().split(",");
            LambdaQueryWrapper<Qsubject> lambda = new QueryWrapper<Qsubject>().lambda();
            lambda.eq(Qsubject::getQuId, question.getId());
            lambda.in(Qsubject::getId, qsubjectIds);
            lambda.eq(Qsubject::getDel, QsubjectConstant.DEL_NORMAL);
            lambda.ne(Qsubject::getSubType, QsubjectConstant.SUB_TYPE_TITLE);
            List<Qsubject> qsubjects = qsubjectMapper.selectList(lambda);
            AtomicReference<Integer> alreadyAnswerCount = new AtomicReference<>(0);
            AtomicReference<Integer> notAnswerCount = new AtomicReference<>(0);
            qsubjects.forEach(q -> {
                String value = mapCache.get(q.getColumnName());
                if(StringUtils.isBlank(value) || "请选择".equals(value)){
                    notAnswerCount.getAndSet(notAnswerCount.get() + 1);
                }else{
                    alreadyAnswerCount.getAndSet(alreadyAnswerCount.get() + 1);
                }
            });
            SingleDiseaseAnswerNavigationVo build = SingleDiseaseAnswerNavigationVo.builder()
                    .alreadyAnswerCount(alreadyAnswerCount.get())
//                    .notAnswerCount(notAnswerCount.get())
                    .questionCount(notAnswerCount.get()+alreadyAnswerCount.get())
                    .groupId(qsubject.getId())
                    .groupName(qsubject.getSubName())
                    .build();
            res.add(build);
        });
        return res;
    }
}
