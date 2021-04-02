package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.entity.Qoption;
import com.qu.modules.web.entity.Qsubjectlib;
import com.qu.modules.web.mapper.QSingleDiseaseTakeMapper;
import com.qu.modules.web.param.QSingleDiseaseTakeByDoctorParam;
import com.qu.modules.web.service.IQSingleDiseaseTakeService;
import com.qu.modules.web.vo.QSingleDiseaseTakeByDoctorPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeVo;
import com.qu.modules.web.vo.QsubjectlibPageVo;
import com.qu.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
        Page<QSingleDiseaseTake> page = new Page<>((pageNo - 1) * pageSize, pageSize);
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
            queryWrapper.eq("status", qSingleDiseaseTakeByDoctorParam.getStatus());
        }

        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }
}
