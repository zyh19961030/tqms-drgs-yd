package com.qu.modules.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.TbFollowVisitTemplateConstant;
import com.qu.modules.web.entity.TbFollowVisitPatient;
import com.qu.modules.web.entity.TbFollowVisitPatientTemplate;
import com.qu.modules.web.entity.TbFollowVisitTemplate;
import com.qu.modules.web.mapper.TbFollowVisitPatientTemplateMapper;
import com.qu.modules.web.param.TbFollowVisitPatientTemplateListParam;
import com.qu.modules.web.service.ITbFollowVisitPatientService;
import com.qu.modules.web.service.ITbFollowVisitPatientTemplateService;
import com.qu.modules.web.service.ITbFollowVisitTemplateService;
import com.qu.modules.web.vo.TbFollowVisitPatientTemplateListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
}
