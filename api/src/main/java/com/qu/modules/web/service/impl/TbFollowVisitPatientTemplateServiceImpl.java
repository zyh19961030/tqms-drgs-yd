package com.qu.modules.web.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.TbFollowVisitPatientConstant;
import com.qu.constant.TbFollowVisitPatientRecordConstant;
import com.qu.constant.TbFollowVisitPatientTemplateConstant;
import com.qu.constant.TbFollowVisitTemplateConstant;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.entity.TbFollowVisitPatient;
import com.qu.modules.web.entity.TbFollowVisitPatientRecord;
import com.qu.modules.web.entity.TbFollowVisitPatientTemplate;
import com.qu.modules.web.entity.TbFollowVisitTemplate;
import com.qu.modules.web.mapper.TbFollowVisitPatientTemplateMapper;
import com.qu.modules.web.param.TbFollowVisitPatientTemplateAllPatientListParam;
import com.qu.modules.web.param.TbFollowVisitPatientTemplateListParam;
import com.qu.modules.web.service.IQuestionService;
import com.qu.modules.web.service.ITbFollowVisitPatientRecordService;
import com.qu.modules.web.service.ITbFollowVisitPatientService;
import com.qu.modules.web.service.ITbFollowVisitPatientTemplateService;
import com.qu.modules.web.service.ITbFollowVisitTemplateService;
import com.qu.modules.web.vo.TbFollowVisitPatientTemplateAllPatientListVo;
import com.qu.modules.web.vo.TbFollowVisitPatientTemplateHistoryVo;
import com.qu.modules.web.vo.TbFollowVisitPatientTemplateInfoVo;
import com.qu.modules.web.vo.TbFollowVisitPatientTemplateListVo;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @Description: 随访患者模板总记录表
 * @Author: jeecg-boot
 * @Date:   2023-02-23
 * @Version: V1.0
 */
@Service
public class TbFollowVisitPatientTemplateServiceImpl extends ServiceImpl<TbFollowVisitPatientTemplateMapper, TbFollowVisitPatientTemplate> implements ITbFollowVisitPatientTemplateService {

    @Autowired
    private ITbFollowVisitTemplateService tbFollowVisitTemplateService;

    @Autowired
    private ITbFollowVisitPatientService tbFollowVisitPatientService;

    @Lazy
    @Autowired
    private ITbFollowVisitPatientRecordService tbFollowVisitPatientRecordService;


    @Autowired
    private IQuestionService questionService;


    @Override
    public IPage<TbFollowVisitPatientTemplateListVo> queryPageList(Page<TbFollowVisitPatientTemplate> page, TbFollowVisitPatientTemplateListParam param) {
        String name = param.getName();
        List<Integer> templateIdList = null;
        if(StringUtils.isNotBlank(name)){
            //查询随访模板
            LambdaQueryWrapper<TbFollowVisitTemplate> templateLambdaQueryWrapper = new QueryWrapper<TbFollowVisitTemplate>().lambda();
            templateLambdaQueryWrapper.like(TbFollowVisitTemplate::getName,name);
            templateLambdaQueryWrapper.eq(TbFollowVisitTemplate::getDelState, TbFollowVisitTemplateConstant.DEL_NORMAL);
            List<TbFollowVisitTemplate> templateList = tbFollowVisitTemplateService.list(templateLambdaQueryWrapper);
            templateIdList = templateList.stream().map(TbFollowVisitTemplate::getId).distinct().collect(Collectors.toList());
        }

        String patientName = param.getPatientName();
        List<Integer> patientIdList = null;
        if(StringUtils.isNotBlank(patientName)){
            //查询随访模板
            LambdaQueryWrapper<TbFollowVisitPatient> patientLambdaQueryWrapper = new QueryWrapper<TbFollowVisitPatient>().lambda();
            patientLambdaQueryWrapper.like(TbFollowVisitPatient::getName,name);
            List<TbFollowVisitPatient> patientList = tbFollowVisitPatientService.list(patientLambdaQueryWrapper);
            patientIdList = patientList.stream().map(TbFollowVisitPatient::getId).distinct().collect(Collectors.toList());
        }

        String diagnosis = param.getDiagnosis();
        List<Integer> diagnosisPatientIdList = null;
        if(StringUtils.isNotBlank(diagnosis)){
            //查询随访模板
            LambdaQueryWrapper<TbFollowVisitPatient> patientLambdaQueryWrapper = new QueryWrapper<TbFollowVisitPatient>().lambda();
            patientLambdaQueryWrapper.like(TbFollowVisitPatient::getDiagnosis,diagnosis);
            List<TbFollowVisitPatient> patientList = tbFollowVisitPatientService.list(patientLambdaQueryWrapper);
            diagnosisPatientIdList = patientList.stream().map(TbFollowVisitPatient::getId).distinct().collect(Collectors.toList());
        }

        LambdaQueryWrapper<TbFollowVisitPatientTemplate> lambda = new QueryWrapper<TbFollowVisitPatientTemplate>().lambda();
        if(CollectionUtil.isNotEmpty(templateIdList)){
            lambda.in(TbFollowVisitPatientTemplate::getFollowVisitTemplateId,templateIdList);
        }
        if(CollectionUtil.isNotEmpty(patientIdList)){
            lambda.in(TbFollowVisitPatientTemplate::getFollowVisitPatientId,patientIdList);
        }
        if(CollectionUtil.isNotEmpty(diagnosisPatientIdList)){
            lambda.in(TbFollowVisitPatientTemplate::getFollowVisitPatientId,diagnosisPatientIdList);
        }
        Date startCreateTime = param.getStartCreateTime();
        if(startCreateTime!=null){
            lambda.ge(TbFollowVisitPatientTemplate::getCreateTime,startCreateTime);
        }
        Date endCreateTime = param.getEndCreateTime();
        if(endCreateTime!=null){
            lambda.lt(TbFollowVisitPatientTemplate::getCreateTime,endCreateTime);
        }
        Integer status = param.getStatus();
        if(status!=null && status>0){
            lambda.eq(TbFollowVisitPatientTemplate::getStatus,status);
        }
        IPage<TbFollowVisitPatientTemplate> iPage = this.page(page, lambda);
        List<TbFollowVisitPatientTemplate> records = iPage.getRecords();
        if(CollectionUtil.isEmpty(records)){
            return new Page<>();
        }

        List<Integer> recordPatientIdList = records.stream().map(TbFollowVisitPatientTemplate::getFollowVisitPatientId).distinct().collect(Collectors.toList());
        Collection<TbFollowVisitPatient> tbFollowVisitPatientList = tbFollowVisitPatientService.listByIds(recordPatientIdList);
        Map<Integer, TbFollowVisitPatient> followVisitPatientMap = tbFollowVisitPatientList.stream().collect(Collectors.toMap(TbFollowVisitPatient::getId, Function.identity()));

        List<Integer> recordTemplateIdList = records.stream().map(TbFollowVisitPatientTemplate::getFollowVisitTemplateId).distinct().collect(Collectors.toList());
        Collection<TbFollowVisitTemplate> tbFollowVisitTemplateList = tbFollowVisitTemplateService.listByIds(recordTemplateIdList);
        Map<Integer, TbFollowVisitTemplate> followVisitTemplateMap = tbFollowVisitTemplateList.stream().collect(Collectors.toMap(TbFollowVisitTemplate::getId, Function.identity()));

        List<TbFollowVisitPatientTemplateListVo> resList = records.stream().map(r -> {
            TbFollowVisitPatientTemplateListVo vo = new TbFollowVisitPatientTemplateListVo();
            BeanUtils.copyProperties(r, vo);
            TbFollowVisitPatient tbFollowVisitPatient = followVisitPatientMap.get(r.getFollowVisitPatientId());
            if(Objects.nonNull(tbFollowVisitPatient)){
                BeanUtils.copyProperties(tbFollowVisitPatient,vo);
            }
            TbFollowVisitTemplate tbFollowVisitTemplate = followVisitTemplateMap.get(r.getFollowVisitTemplateId());
            if(Objects.nonNull(tbFollowVisitTemplate)){
                vo.setFollowVisitTemplate(tbFollowVisitTemplate.getName());
            }
            vo.setId(r.getId());
            return vo;
        }).collect(Collectors.toList());

        Page<TbFollowVisitPatientTemplateListVo> resPage = new Page<>();
        resPage.setRecords(resList);
        resPage.setTotal(iPage.getTotal());
        resPage.setCurrent(iPage.getCurrent());
        return resPage;
    }

    @Override
    public boolean stopFollowVisit(Integer id) {
        TbFollowVisitPatientTemplate byId = this.getById(id);
        if(byId==null){
            return false;
        }
        Date date = new Date();
        byId.setStatus(TbFollowVisitPatientTemplateConstant.STATUS_STOP);
        byId.setUpdateTime(date);
        this.updateById(byId);

        TbFollowVisitPatientRecord emptyEntity = new TbFollowVisitPatientRecord();
        LambdaUpdateWrapper<TbFollowVisitPatientRecord> lambda = new UpdateWrapper<TbFollowVisitPatientRecord>().lambda();
        lambda.eq(TbFollowVisitPatientRecord::getFollowVisitPatientTemplateId, id)
                .eq(TbFollowVisitPatientRecord::getStatus, TbFollowVisitPatientConstant.STATUS_WAIT)
                .set(TbFollowVisitPatientRecord::getStatus, TbFollowVisitPatientConstant.STATUS_STOP);
        tbFollowVisitPatientRecordService.update(emptyEntity, lambda);
        return true;
    }

    @Override
    public TbFollowVisitPatientTemplateInfoVo info(Integer id) {
        TbFollowVisitPatientTemplate byId = this.getById(id);
        if(byId==null){
            return null;
        }
        TbFollowVisitPatientTemplateInfoVo vo = new TbFollowVisitPatientTemplateInfoVo();

        TbFollowVisitPatient patient = tbFollowVisitPatientService.getById(byId.getFollowVisitPatientId());
        BeanUtils.copyProperties(patient,vo);

        Integer followVisitTemplateId = byId.getFollowVisitTemplateId();
        TbFollowVisitTemplate template = tbFollowVisitTemplateService.getById(followVisitTemplateId);
        if(template!=null && template.getDelState().equals(TbFollowVisitTemplateConstant.DEL_NORMAL)){
            vo.setFollowVisitTemplate(template.getName());
            vo.setDateStartType(template.getDateStartType());
        }
        vo.setFollowVisitCountNumber(byId.getFollowVisitCountNumber());

        LambdaQueryWrapper<TbFollowVisitPatientRecord> patientRecordLambdaQueryWrapper = new QueryWrapper<TbFollowVisitPatientRecord>().lambda();
        patientRecordLambdaQueryWrapper.eq(TbFollowVisitPatientRecord::getFollowVisitPatientTemplateId,id);
        patientRecordLambdaQueryWrapper.eq(TbFollowVisitPatientRecord::getStatus, TbFollowVisitPatientRecordConstant.STATUS_COMPLETED);
        List<TbFollowVisitPatientRecord> patientRecordList = tbFollowVisitPatientRecordService.list(patientRecordLambdaQueryWrapper);
        vo.setAlreadyFollowVisitCountNumber(patientRecordList.size());

        List<Integer> questionIdList = patientRecordList.stream().map(TbFollowVisitPatientRecord::getQuestionId).distinct().collect(Collectors.toList());
        Collection<Question> questionList = questionService.listByIds(questionIdList);
        Map<Integer, Question> questionMap = questionList.stream().collect(Collectors.toMap(Question::getId, Function.identity()));


        List<TbFollowVisitPatientTemplateHistoryVo> historyVoList = patientRecordList.stream().map(p -> {
            TbFollowVisitPatientTemplateHistoryVo historyVo = new TbFollowVisitPatientTemplateHistoryVo();
            BeanUtils.copyProperties(p,historyVo);
            Question question = questionMap.get(p.getQuestionId());
            if(Objects.nonNull(question)){
                historyVo.setQuestionName(question.getQuName());
            }
            return historyVo;
        }).collect(Collectors.toList());
        vo.setHistoryRecord(historyVoList);

        LambdaQueryWrapper<TbFollowVisitPatientRecord> followVisitPatientRecordLambdaQueryWrapper = new QueryWrapper<TbFollowVisitPatientRecord>().lambda();
        followVisitPatientRecordLambdaQueryWrapper.eq(TbFollowVisitPatientRecord::getFollowVisitPatientTemplateId,id);
        followVisitPatientRecordLambdaQueryWrapper.eq(TbFollowVisitPatientRecord::getStatus, TbFollowVisitPatientRecordConstant.STATUS_WAIT);
        followVisitPatientRecordLambdaQueryWrapper.orderByDesc(TbFollowVisitPatientRecord::getFollowVisitNumber);
        List<TbFollowVisitPatientRecord> nextPatientRecordList = tbFollowVisitPatientRecordService.list(followVisitPatientRecordLambdaQueryWrapper);
        TbFollowVisitPatientRecord tbFollowVisitPatientRecord = nextPatientRecordList.get(0);
        vo.setFollowVisitTime(tbFollowVisitPatientRecord.getFollowVisitTime());

        vo.setStatus(byId.getStatus());
        vo.setCreateTime(byId.getCreateTime());

        return vo;
    }

    @Override
    public IPage<TbFollowVisitPatientTemplateAllPatientListVo> allPatientList(Page<TbFollowVisitPatientTemplate> page, TbFollowVisitPatientTemplateAllPatientListParam param) {
        String name = param.getName();
        List<Integer> templateIdList = null;
        if(StringUtils.isNotBlank(name)){
            //查询随访模板
            LambdaQueryWrapper<TbFollowVisitTemplate> templateLambdaQueryWrapper = new QueryWrapper<TbFollowVisitTemplate>().lambda();
            templateLambdaQueryWrapper.like(TbFollowVisitTemplate::getName,name);
            templateLambdaQueryWrapper.eq(TbFollowVisitTemplate::getDelState, TbFollowVisitTemplateConstant.DEL_NORMAL);
            List<TbFollowVisitTemplate> templateList = tbFollowVisitTemplateService.list(templateLambdaQueryWrapper);
            templateIdList = templateList.stream().map(TbFollowVisitTemplate::getId).distinct().collect(Collectors.toList());
        }

        String patientName = param.getPatientName();
        List<Integer> patientIdList = null;
        if(StringUtils.isNotBlank(patientName)){
            //查询随访模板
            LambdaQueryWrapper<TbFollowVisitPatient> patientLambdaQueryWrapper = new QueryWrapper<TbFollowVisitPatient>().lambda();
            patientLambdaQueryWrapper.like(TbFollowVisitPatient::getName,name);
            List<TbFollowVisitPatient> patientList = tbFollowVisitPatientService.list(patientLambdaQueryWrapper);
            patientIdList = patientList.stream().map(TbFollowVisitPatient::getId).distinct().collect(Collectors.toList());
        }

        String diagnosis = param.getDiagnosis();
        List<Integer> diagnosisPatientIdList = null;
        if(StringUtils.isNotBlank(diagnosis)){
            //查询随访模板
            LambdaQueryWrapper<TbFollowVisitPatient> patientLambdaQueryWrapper = new QueryWrapper<TbFollowVisitPatient>().lambda();
            patientLambdaQueryWrapper.like(TbFollowVisitPatient::getDiagnosis,diagnosis);
            List<TbFollowVisitPatient> patientList = tbFollowVisitPatientService.list(patientLambdaQueryWrapper);
            diagnosisPatientIdList = patientList.stream().map(TbFollowVisitPatient::getId).distinct().collect(Collectors.toList());
        }

        String deptId = param.getDeptId();
        List<Integer> deptIdList = null;
        if(StringUtils.isNotBlank(deptId)){
            //查询随访模板
            LambdaQueryWrapper<TbFollowVisitPatient> patientLambdaQueryWrapper = new QueryWrapper<TbFollowVisitPatient>().lambda();
            patientLambdaQueryWrapper.like(TbFollowVisitPatient::getDeptId,deptId);
            List<TbFollowVisitPatient> patientList = tbFollowVisitPatientService.list(patientLambdaQueryWrapper);
            deptIdList = patientList.stream().map(TbFollowVisitPatient::getId).distinct().collect(Collectors.toList());
        }

        LambdaQueryWrapper<TbFollowVisitPatientTemplate> lambda = new QueryWrapper<TbFollowVisitPatientTemplate>().lambda();
        if(CollectionUtil.isNotEmpty(templateIdList)){
            lambda.in(TbFollowVisitPatientTemplate::getFollowVisitTemplateId,templateIdList);
        }
        if(CollectionUtil.isNotEmpty(patientIdList)){
            lambda.in(TbFollowVisitPatientTemplate::getFollowVisitPatientId,patientIdList);
        }
        if(CollectionUtil.isNotEmpty(deptIdList)){
            lambda.in(TbFollowVisitPatientTemplate::getFollowVisitPatientId,deptIdList);
        }
        if(CollectionUtil.isNotEmpty(diagnosisPatientIdList)){
            lambda.in(TbFollowVisitPatientTemplate::getFollowVisitPatientId,diagnosisPatientIdList);
        }
        Date startCreateTime = param.getStartCreateTime();
        if(startCreateTime!=null){
            lambda.ge(TbFollowVisitPatientTemplate::getCreateTime,startCreateTime);
        }
        Date endCreateTime = param.getEndCreateTime();
        if(endCreateTime!=null){
            lambda.lt(TbFollowVisitPatientTemplate::getCreateTime,endCreateTime);
        }
        Integer status = param.getStatus();
        if(status!=null && status>0){
            lambda.eq(TbFollowVisitPatientTemplate::getStatus,status);
        }
        IPage<TbFollowVisitPatientTemplate> iPage = this.page(page, lambda);
        List<TbFollowVisitPatientTemplate> records = iPage.getRecords();
        if(CollectionUtil.isEmpty(records)){
            return new Page<>();
        }

        List<Integer> recordPatientIdList = records.stream().map(TbFollowVisitPatientTemplate::getFollowVisitPatientId).distinct().collect(Collectors.toList());
        Collection<TbFollowVisitPatient> tbFollowVisitPatientList = tbFollowVisitPatientService.listByIds(recordPatientIdList);
        Map<Integer, TbFollowVisitPatient> followVisitPatientMap = tbFollowVisitPatientList.stream().collect(Collectors.toMap(TbFollowVisitPatient::getId, Function.identity()));

        List<Integer> recordTemplateIdList = records.stream().map(TbFollowVisitPatientTemplate::getFollowVisitTemplateId).distinct().collect(Collectors.toList());
        Collection<TbFollowVisitTemplate> tbFollowVisitTemplateList = tbFollowVisitTemplateService.listByIds(recordTemplateIdList);
        Map<Integer, TbFollowVisitTemplate> followVisitTemplateMap = tbFollowVisitTemplateList.stream().collect(Collectors.toMap(TbFollowVisitTemplate::getId, Function.identity()));

        List<TbFollowVisitPatientTemplateAllPatientListVo> resList = records.stream().map(r -> {
            TbFollowVisitPatientTemplateAllPatientListVo vo = new TbFollowVisitPatientTemplateAllPatientListVo();
            BeanUtils.copyProperties(r, vo);
            TbFollowVisitPatient tbFollowVisitPatient = followVisitPatientMap.get(r.getFollowVisitPatientId());
            if(Objects.nonNull(tbFollowVisitPatient)){
                BeanUtils.copyProperties(tbFollowVisitPatient,vo);
            }
            TbFollowVisitTemplate tbFollowVisitTemplate = followVisitTemplateMap.get(r.getFollowVisitTemplateId());
            if(Objects.nonNull(tbFollowVisitTemplate)){
                vo.setFollowVisitTemplate(tbFollowVisitTemplate.getName());
            }
            vo.setId(r.getId());
            return vo;
        }).collect(Collectors.toList());

        Page<TbFollowVisitPatientTemplateAllPatientListVo> resPage = new Page<>();
        resPage.setRecords(resList);
        resPage.setTotal(iPage.getTotal());
        resPage.setCurrent(iPage.getCurrent());
        return resPage;
    }



}
