package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.QSingleDiseaseTakeConstant;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.mapper.QSingleDiseaseTakeMapper;
import com.qu.modules.web.param.QSingleDiseaseTakeByDeptParam;
import com.qu.modules.web.param.QSingleDiseaseTakeByDoctorParam;
import com.qu.modules.web.param.QSingleDiseaseTakeNoNeedParam;
import com.qu.modules.web.service.IQSingleDiseaseTakeService;
import com.qu.modules.web.vo.QSingleDiseaseTakeByDoctorPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            queryWrapper.in("status", qSingleDiseaseTakeByDoctorParam.getStatus());
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
            QSingleDiseaseTake qSingleDiseaseTake = new QSingleDiseaseTake();
            qSingleDiseaseTake.setId(qSingleDiseaseTakeNoNeedParam.getId());
            qSingleDiseaseTake.setExamineReason(qSingleDiseaseTakeNoNeedParam.getReason());
            qSingleDiseaseTake.setStatus(QSingleDiseaseTakeConstant.STATUS_NO_NEED);
            updateFlag = this.updateById(qSingleDiseaseTake);
        } catch (Exception e) {
            updateFlag = false;
            log.error(e.getMessage(), e);
        }
        return updateFlag;
    }

    @Override
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseWaitUploadList(Integer pageNo, Integer pageSize) {
        Page<QSingleDiseaseTake> page = new Page<>((pageNo - 1) * pageSize, pageSize);
        QueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", QSingleDiseaseTakeConstant.STATUS_WAIT_UPLOAD);
        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }

    @Override
    public String setSingleDiseaseStatus(String[] ids, Integer status) {
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
                this.updateById(qSingleDiseaseTake);
            }
            return null;
        }
        return "数组为空";
    }

    @Override
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseRejectList(Integer pageNo, Integer pageSize) {
        Page<QSingleDiseaseTake> page = new Page<>((pageNo - 1) * pageSize, pageSize);
        QueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", QSingleDiseaseTakeConstant.STATUS_REJECT);
        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }

    @Override
    public QSingleDiseaseTakeByDoctorPageVo singleDiseaseByDeptList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam, Integer pageNo, Integer pageSize) {
        //todo  添加科室操作
        Page<QSingleDiseaseTake> page = new Page<>((pageNo - 1) * pageSize, pageSize);
        QueryWrapper<QSingleDiseaseTake> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(qSingleDiseaseTakeByDeptParam.getDiseaseName())) {
            queryWrapper.like("icd_name", qSingleDiseaseTakeByDeptParam.getDiseaseName());
        }

        if (qSingleDiseaseTakeByDeptParam.getOutHospitalStartDate() != null) {
            queryWrapper.ge("out_time", qSingleDiseaseTakeByDeptParam.getOutHospitalStartDate());
        }

        if (qSingleDiseaseTakeByDeptParam.getOutHospitalEndDate() != null) {
            queryWrapper.le("out_time", qSingleDiseaseTakeByDeptParam.getOutHospitalEndDate());
        }

        if (qSingleDiseaseTakeByDeptParam.getStatus() != null) {
            queryWrapper.eq("status", qSingleDiseaseTakeByDeptParam.getStatus());
        }

        IPage<QSingleDiseaseTake> qSingleDiseaseTakeIPage = this.page(page, queryWrapper);
        QSingleDiseaseTakeByDoctorPageVo qsubjectlibPageVo = new QSingleDiseaseTakeByDoctorPageVo();
        qsubjectlibPageVo.setTotal(qSingleDiseaseTakeIPage.getTotal());
        qsubjectlibPageVo.setQSingleDiseaseTakeList(qSingleDiseaseTakeIPage.getRecords());
        return qsubjectlibPageVo;
    }
}
