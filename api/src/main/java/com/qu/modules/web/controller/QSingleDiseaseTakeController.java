package com.qu.modules.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qu.constant.Constant;
import com.qu.constant.QSingleDiseaseTakeConstant;
import com.qu.modules.web.entity.Getdzbxx;
import com.qu.modules.web.entity.TbDep;
import com.qu.modules.web.entity.TbUser;
import com.qu.modules.web.param.*;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.*;
import com.qu.modules.web.vo.*;
import com.qu.util.DeptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date: 2021-04-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "单病种总表")
@RestController
@RequestMapping("/business/qSingleDiseaseTake")
public class QSingleDiseaseTakeController {
    @Autowired
    private IQSingleDiseaseTakeService qSingleDiseaseTakeService;

    @Autowired
    private IQSingleDiseaseStatisticHospitalService qSingleDiseaseStatisticHospitalService;

    @Autowired
    private IQSingleDiseaseStatisticDeptService qSingleDiseaseStatisticDeptService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private ITbUserService tbUserService;

    @Autowired
    private ITbDepService tbDepService;

    @Autowired
    private ReadDrgsReportDataervice readDrgsReportDataervice;


    /**
     * 按单病种填报(新增上报)
     */
    @AutoLog(value = "按单病种填报查询")
    @ApiOperation(value = "按单病种填报查询", notes = "按单病种填报查询")
    @GetMapping(value = "/singleDiseaseList")
    public Result<List<QSingleDiseaseTakeVo>> singleDiseaseList(@RequestParam(name = "name", required = false) String name,HttpServletRequest request) {
        Result<List<QSingleDiseaseTakeVo>> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        List<QSingleDiseaseTakeVo> list = qSingleDiseaseTakeService.singleDiseaseList(name,deptId);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 按医生填报查询(待填报)-本科室单病种上报记录(科室上报记录)-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件
     */
    @AutoLog(value = "按医生填报查询-本科室单病种上报记录-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件")
    @ApiOperation(value = "按医生填报查询-本科室单病种上报记录-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件", notes = "按医生填报查询-本科室单病种上报记录-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件")
    @GetMapping(value = "/singleDiseaseNameList")
    public Result<List<QSingleDiseaseNameVo>> singleDiseaseNameList(HttpServletRequest request) {
        Result<List<QSingleDiseaseNameVo>> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        TbDep tbDep = new TbDep();
        if (org.apache.commons.lang.StringUtils.isNotBlank(deptId)) {
            tbDep = tbDepService.getById(deptId);
        }
        String type = tbDep.getType();
        List<QSingleDiseaseNameVo> list = qSingleDiseaseTakeService.singleDiseaseNameList(deptId,type);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 按医生填报查询(待填报)
     */
    @AutoLog(value = "按医生填报查询")
    @ApiOperation(value = "按医生填报查询", notes = "按医生填报查询")
    @GetMapping(value = "/singleDiseaseByDoctorList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> singleDiseaseByDoctorList(QSingleDiseaseTakeByDoctorParam qSingleDiseaseTakeByDoctorParam,
                                                                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                              HttpServletRequest request) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseByDoctorList(qSingleDiseaseTakeByDoctorParam, pageNo, pageSize,deptId);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 设为无需填报
     */
    @AutoLog(value = "设为无需填报")
    @ApiOperation(value = "设为无需填报", notes = "设为无需填报")
    @PostMapping(value = "/setSingleDiseaseNoNeed")
    public Result<Boolean> setSingleDiseaseNoNeed(@RequestBody @Validated QSingleDiseaseTakeNoNeedParam qSingleDiseaseTakeNoNeedParam) {
        Result<Boolean> result = new Result<>();
        Boolean flag = qSingleDiseaseTakeService.setSingleDiseaseNoNeed(qSingleDiseaseTakeNoNeedParam);
        result.setSuccess(true);
        result.setResult(flag);
        return result;
    }

    /**
     * 单病种上报待审查询(院级审核)
     */
    @AutoLog(value = "单病种上报待审查询-院级审核")
    @ApiOperation(value = "单病种上报待审查询-院级审核", notes = "单病种上报待审查询-院级审核")
    @GetMapping(value = "/singleDiseaseWaitUploadList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> singleDiseaseWaitUploadList(SingleDiseaseWaitUploadParam singleDiseaseWaitUploadParam,
                                                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseWaitUploadList(singleDiseaseWaitUploadParam,singleDiseaseWaitUploadParam.getDeptId(),pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 单病种上报待审查询(科级审核)
     */
    @AutoLog(value = "单病种上报待审查询-科级审核")
    @ApiOperation(value = "单病种上报待审查询-科级审核", notes = "单病种上报待审查询-科级审核")
    @GetMapping(value = "/deptSingleDiseaseWaitUploadList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> deptSingleDiseaseWaitUploadList(SingleDiseaseWaitUploadParam singleDiseaseWaitUploadParam,
                                                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                                    HttpServletRequest request) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        //加科室过滤
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseWaitUploadList(singleDiseaseWaitUploadParam,deptId,pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 单病种审核通过
     */
    @AutoLog(value = "单病种审核通过")
    @ApiOperation(value = "单病种审核通过", notes = "单病种审核通过")
    @PostMapping(value = "/setSingleDiseasePass")
    public Result<Boolean> setSingleDiseasePass(@RequestBody String[] ids) {
        Result<Boolean> result = new Result<>();
        //审核通过直接已完成
//        String msg = qSingleDiseaseTakeService.setSingleDiseaseStatus(ids, QSingleDiseaseTakeConstant.STATUS_COMPLETE,null);
        String msg = qSingleDiseaseTakeService.setSingleDiseaseStatus(ids, QSingleDiseaseTakeConstant.STATUS_PASS_WAIT_UPLOAD,null);
        if (StringUtils.isBlank(msg)) {
            result.setSuccess(true);
            result.setResult(true);
            return result;
        } else {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage(msg);
            return result;
        }
    }

    /**
     * 单病种驳回
     */
    @AutoLog(value = "单病种驳回")
    @ApiOperation(value = "单病种驳回", notes = "单病种驳回")
    @PostMapping(value = "/setSingleDiseaseReject")
    public Result<Boolean> setSingleDiseaseReject(@RequestBody SingleDiseaseRejectParam singleDiseaseRejectParam) {
        Result<Boolean> result = new Result<>();
        String msg = qSingleDiseaseTakeService.setSingleDiseaseStatus(singleDiseaseRejectParam.getIds(), QSingleDiseaseTakeConstant.STATUS_REJECT,singleDiseaseRejectParam.getExamineReason());
        if (StringUtils.isBlank(msg)) {
            result.setSuccess(true);
            result.setResult(true);
            return result;
        } else {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage(msg);
            return result;
        }
    }

    /**
     * 单病种驳回待处理查询
     */
    @AutoLog(value = "单病种驳回待处理查询")
    @ApiOperation(value = "单病种驳回待处理查询", notes = "单病种驳回待处理查询")
    @GetMapping(value = "/singleDiseaseRejectList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> singleDiseaseRejectList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                            HttpServletRequest request) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseRejectList(pageNo, pageSize,deptId);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 本科室单病种上报记录查询-科室上报记录
     */
    @AutoLog(value = "本科室单病种上报记录查询-科室上报记录")
    @ApiOperation(value = "本科室单病种上报记录查询-科室上报记录", notes = "本科室单病种上报记录查询-科室上报记录")
    @GetMapping(value = "/singleDiseaseByDeptList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> singleDiseaseByDeptList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam,
                                                                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                            HttpServletRequest request) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseByDeptList(qSingleDiseaseTakeByDeptParam, pageNo, pageSize,deptId);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报记录查询-全院上报记录菜单中-科室筛选条件列表接口
     */
    @AutoLog(value = "全院单病种上报记录查询-全院上报记录菜单中-科室筛选条件列表接口")
    @ApiOperation(value = "全院单病种上报记录查询-全院上报记录菜单中-科室筛选条件列表接口", notes = "全院单病种上报记录查询-全院上报记录菜单中-科室筛选条件列表接口")
    @GetMapping(value = "/singleDiseaseAllListDeptCondition")
    public Result<List<TbDep>> singleDiseaseAllListDeptCondition() {
        Result<List<TbDep>> result = new Result<>();
        List<TbDep> list = qSingleDiseaseTakeService.singleDiseaseAllListDeptCondition();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报记录查询-全院上报记录
     */
    @AutoLog(value = "全院单病种上报记录查询-全院上报记录")
    @ApiOperation(value = "全院单病种上报记录查询-全院上报记录", notes = "全院单病种上报记录查询-全院上报记录")
    @GetMapping(value = "/singleDiseaseAllList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> singleDiseaseAllList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam,
                                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseByDeptList(qSingleDiseaseTakeByDeptParam, pageNo, pageSize,qSingleDiseaseTakeByDeptParam.getDeptId());
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种审核记录查询-全院审核记录
     */
    @AutoLog(value = "全院单病种审核记录查询-全院审核记录")
    @ApiOperation(value = "全院单病种审核记录查询-全院审核记录", notes = "全院单病种审核记录查询-全院审核记录")
    @GetMapping(value = "/singleDiseaseExamineRecordAllList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> singleDiseaseExamineRecordAllList(SingleDiseaseExamineRecordParam singleDiseaseExamineRecordParam,
                                                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseExamineRecordAllList(singleDiseaseExamineRecordParam, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }


    /**
     * 单病种阶段性保存
     */
    @AutoLog(value = "单病种阶段性保存")
    @ApiOperation(value = "单病种阶段性保存", notes = "单病种阶段性保存")
    @PostMapping(value = "/singleDiseaseStageAnswer")
    public Result<Boolean> singleDiseaseStageAnswer(@RequestBody SingleDiseaseAnswerParam singleDiseaseAnswerParam, HttpServletRequest request) {
        Result<Boolean> result = new Result<>();
        String token = request.getHeader("token");
        String cookie = "JSESSIONID=" + token;
        log.info("-----------singleDiseaseAnswerParam={}", JSON.toJSONString(singleDiseaseAnswerParam));
        qSingleDiseaseTakeService.singleDiseaseStageAnswer(cookie,singleDiseaseAnswerParam);
        result.setSuccess(true);
        result.setResult(true);
        return result;
    }

    /**
     * 单病种填报
     */
    @AutoLog(value = "单病种填报")
    @ApiOperation(value = "单病种填报", notes = "单病种填报")
    @PostMapping(value = "/singleDiseaseAnswer")
    public Result singleDiseaseAnswer(@RequestBody SingleDiseaseAnswerParam singleDiseaseAnswerParam, HttpServletRequest request) {
        String token = request.getHeader("token");
        String cookie = "JSESSIONID=" + token;
        log.info("-----------singleDiseaseAnswerParam={}", JSON.toJSONString(singleDiseaseAnswerParam));
        try {
            qSingleDiseaseTakeService.singleDiseaseAnswer(cookie, singleDiseaseAnswerParam);
            return ResultFactory.success();
        } catch (Exception e) {
            log.error("单病种填报报错--->",e);
            return ResultFactory.fail("操作失败！");
        }

    }


    /**
     * 单病种回显
     */
    @AutoLog(value = "单病种回显")
    @ApiOperation(value = "单病种回显", notes = "单病种回显")
    @GetMapping(value = "/singleDiseaseAnswerQueryById")
    public Result<String> singleDiseaseAnswerQueryById(@RequestParam @ApiParam("单病种总表id,从医生填报查询中获得的id") Integer id) {
        Result<String> result = new Result<>();
        String answer = qSingleDiseaseTakeService.singleDiseaseAnswerQueryById(id);
        result.setSuccess(true);
        result.setResult(answer);
        return result;
    }

    /**
     * 科室单病种上报统计(科室单病种数量统计_科室筛选)查询
     */
    @AutoLog(value = "科室单病种上报统计(科室单病种数量统计_科室筛选)查询")
    @ApiOperation(value = "科室单病种上报统计(科室单病种数量统计_科室筛选)查询", notes = "科室单病种上报统计(科室单病种数量统计_科室筛选)查询", response = QSingleDiseaseTakeReportStatisticPageVo.class)
    @GetMapping(value = "/allSingleDiseaseReportStatisticByDept")
    public Result<QSingleDiseaseTakeReportStatisticPageVo> allSingleDiseaseReportStatisticByDept(@Validated QSingleDiseaseTakeReportStatisticByDeptParam qSingleDiseaseTakeReportStatisticByDeptParam,
                                                                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                                                 HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        TbDep tbDep = new TbDep();
        if (org.apache.commons.lang.StringUtils.isNotBlank(deptId)) {
            tbDep = tbDepService.getById(deptId);
        }
        String type = tbDep.getType();
        QSingleDiseaseTakeStatisticAnalysisByDeptConditionParam qSingleDiseaseTakeStatisticAnalysisByDeptConditionParam = new QSingleDiseaseTakeStatisticAnalysisByDeptConditionParam();
        List <TbDep> tbDepList = questionService.singleDiseaseStatisticAnalysisByDeptCondition(qSingleDiseaseTakeStatisticAnalysisByDeptConditionParam,deptId,type);
        List<String> deptIdList = tbDepList.stream().map(TbDep::getId).distinct().collect(Collectors.toList());
        Result<QSingleDiseaseTakeReportStatisticPageVo> result = new Result<>();
        QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseStatisticDeptService.allSingleDiseaseReportStatisticByDept(qSingleDiseaseTakeReportStatisticByDeptParam, pageNo, pageSize,deptIdList);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 科室单病种上报统计(科室单病种数量统计_科室筛选)查询_导出
     */
    @AutoLog(value = "科室单病种上报统计(科室单病种数量统计_科室筛选)查询_导出")
    @ApiOperation(value = "科室单病种上报统计(科室单病种数量统计_科室筛选)查询_导出", notes = "科室单病种上报统计(科室单病种数量统计_科室筛选)查询_导出", response = QSingleDiseaseTakeReportStatisticPageVo.class)
    @GetMapping(value = "/allSingleDiseaseReportStatisticByDeptExport")
    public ModelAndView allSingleDiseaseReportStatisticByDeptExport(@Validated QSingleDiseaseTakeReportStatisticByDeptExportParam qSingleDiseaseTakeReportStatisticByDeptExportParam) {

        String userId = qSingleDiseaseTakeReportStatisticByDeptExportParam.getUserId();
        TbUser tbUser = tbUserService.getById(userId);
        if(StringUtils.isBlank(userId)  || tbUser==null){
            return null;
        }
        TbDep tbDep = tbDepService.getById(tbUser.getDepid());
        if(tbDep==null){
            return null;
        }
        String deptId = tbDep.getId();
        String type = tbDep.getType();
        QSingleDiseaseTakeStatisticAnalysisByDeptConditionParam qSingleDiseaseTakeStatisticAnalysisByDeptConditionParam = new QSingleDiseaseTakeStatisticAnalysisByDeptConditionParam();
        List <TbDep> tbDepList = questionService.singleDiseaseStatisticAnalysisByDeptCondition(qSingleDiseaseTakeStatisticAnalysisByDeptConditionParam,deptId,type);
        List<String> deptIdList = tbDepList.stream().map(TbDep::getId).distinct().collect(Collectors.toList());

        QSingleDiseaseTakeReportStatisticByDeptParam qSingleDiseaseTakeReportStatisticByDeptParam = new QSingleDiseaseTakeReportStatisticByDeptParam();
        BeanUtils.copyProperties(qSingleDiseaseTakeReportStatisticByDeptExportParam,qSingleDiseaseTakeReportStatisticByDeptParam);
        List<QSingleDiseaseTakeReportStatisticVo> exportList = Lists.newArrayList();
        for (int i = 0; ; i++) {
            QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseStatisticDeptService.allSingleDiseaseReportStatisticByDept(qSingleDiseaseTakeReportStatisticByDeptParam, i+1, 500,deptIdList);
            List<QSingleDiseaseTakeReportStatisticVo> qSingleDiseaseTakeList = list.getQSingleDiseaseTakeList();
            if(qSingleDiseaseTakeList.isEmpty()){
                break;
            }
            exportList.addAll(qSingleDiseaseTakeList);
        }
        String fileName = String.valueOf(System.currentTimeMillis());
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, fileName);
        mv.addObject(NormalExcelConstants.CLASS, QSingleDiseaseTakeReportStatisticVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("单病种上报统计列表", "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }


    /**
     * 科室单病种上报统计(科室单病种数量统计_科室筛选)-点击某个病种-科室筛选条件
     */
    @AutoLog(value = "科室单病种上报统计(科室单病种数量统计_科室筛选)-点击某个病种-科室筛选条件")
    @ApiOperation(value = "科室单病种上报统计(科室单病种数量统计_科室筛选)-点击某个病种-科室筛选条件", notes = "科室单病种上报统计(科室单病种数量统计_科室筛选)-点击某个病种-科室筛选条件", response = TbDep.class)
    @GetMapping(value = "/singleDiseaseStatisticAnalysisByDeptCondition")
    public Result<List<TbDep>> singleDiseaseStatisticAnalysisByDeptCondition(@Validated QSingleDiseaseTakeStatisticAnalysisByDeptConditionParam qSingleDiseaseTakeStatisticAnalysisByDeptConditionParam,
                                                                             HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        TbDep tbDep = new TbDep();
        if (org.apache.commons.lang.StringUtils.isNotBlank(deptId)) {
            tbDep = tbDepService.getById(deptId);
        }
        String type = tbDep.getType();
        List <TbDep> tbDepList = questionService.singleDiseaseStatisticAnalysisByDeptCondition(qSingleDiseaseTakeStatisticAnalysisByDeptConditionParam,deptId,type);
        Result<List<TbDep>> result = new Result<>();
        result.setSuccess(true);
        result.setResult(tbDepList);
        return result;
    }

    /**
     * 科室单病种上报统计(科室单病种数量统计_科室筛选)-点击某个病种-统计分析
     */
    @AutoLog(value = "科室单病种上报统计(科室单病种数量统计_科室筛选)-点击某个病种-统计分析")
    @ApiOperation(value = "科室单病种上报统计(科室单病种数量统计_科室筛选)-点击某个病种-统计分析", notes = "科室单病种上报统计(科室单病种数量统计_科室筛选)-点击某个病种-统计分析", response = QSingleDiseaseTakeStatisticAnalysisTableVo.class)
    @GetMapping(value = "/singleDiseaseStatisticAnalysisByDept")
    public Result<QSingleDiseaseTakeStatisticAnalysisTableVo> singleDiseaseStatisticAnalysisByDept(@Validated QSingleDiseaseTakeStatisticAnalysisByDeptParam qSingleDiseaseTakeStatisticAnalysisByDeptParam) {

        Result<QSingleDiseaseTakeStatisticAnalysisTableVo> result = new Result<>();
        List<QSingleDiseaseTakeStatisticAnalysisVo> list = qSingleDiseaseStatisticDeptService.singleDiseaseStatisticAnalysisByDept(qSingleDiseaseTakeStatisticAnalysisByDeptParam);

        List<LinkedHashMap<String,String>> fieldItems = Lists.newArrayList();
        LinkedHashMap<String, String> fieldItem = Maps.newLinkedHashMap();
        fieldItems.add(fieldItem);
        fieldItem.put("fieldTxt","name1");

        List<LinkedHashMap<String,String>> singleDataList = Lists.newArrayList();
        LinkedHashMap<String, String> singleData = Maps.newLinkedHashMap();
        singleDataList.add(singleData);
        singleData.put("name1","上报例数");
        LinkedHashMap<String, String> singleData2 = Maps.newLinkedHashMap();
        singleDataList.add(singleData2);
        singleData2.put("name1","平均住院日");
        LinkedHashMap<String, String> singleData3 = Maps.newLinkedHashMap();
        singleDataList.add(singleData3);
        singleData3.put("name1","平均住院费用");
        LinkedHashMap<String, String> singleData4 = Maps.newLinkedHashMap();
        singleDataList.add(singleData4);
        singleData4.put("name1","死亡率");
        LinkedHashMap<String, String> singleData5 = Maps.newLinkedHashMap();
        singleDataList.add(singleData5);
        singleData5.put("name1","手术并发症发生率");

        for (int i = 0; i < list.size(); i++) {
            QSingleDiseaseTakeStatisticAnalysisVo qSingleDiseaseTakeStatisticAnalysisVo = list.get(i);
            LinkedHashMap<String, String> fieldItemTemp = Maps.newLinkedHashMap();
            fieldItems.add(fieldItemTemp);
            fieldItemTemp.put("fieldTxt",qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth());

            for (int j = 0; j < singleDataList.size(); j++) {
                LinkedHashMap<String, String> stringStringLinkedHashMap = singleDataList.get(j);
                if(j==0){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.valueOf(qSingleDiseaseTakeStatisticAnalysisVo.getCompleteReportCountryCount()));
                }else if(j==1){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.valueOf(qSingleDiseaseTakeStatisticAnalysisVo.getAverageInHospitalDay()));
                }else if(j==2){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.valueOf(qSingleDiseaseTakeStatisticAnalysisVo.getAverageInHospitalFee()));
                }else if(j==3){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.format("%s%%",qSingleDiseaseTakeStatisticAnalysisVo.getMortality()));
                }else if(j==4){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.format("%s%%",qSingleDiseaseTakeStatisticAnalysisVo.getComplicationRate()));
                }
            }
        }

        QSingleDiseaseTakeStatisticAnalysisTableVo build = QSingleDiseaseTakeStatisticAnalysisTableVo.builder().fieldItems(fieldItems).singleDataList(singleDataList).build();
        result.setSuccess(true);
        result.setResult(build);
        return result;
    }



    /**
     * 全院单病种上报统计(全院单病种数量统计_单病种筛选)查询
     */
    @AutoLog(value = "全院单病种上报统计(全院单病种数量统计_单病种筛选)查询")
    @ApiOperation(value = "全院单病种上报统计(全院单病种数量统计_单病种筛选)查询", notes = "全院单病种上报统计(全院单病种数量统计_单病种筛选)查询",response = QSingleDiseaseTakeReportStatisticPageVo.class)
    @GetMapping(value = "/allSingleDiseaseReportStatistic")
    public Result<QSingleDiseaseTakeReportStatisticPageVo> allSingleDiseaseReportStatistic(@Validated QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam,
                                                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                                           HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        TbDep tbDep = new TbDep();
        if (org.apache.commons.lang.StringUtils.isNotBlank(deptId)) {
            tbDep = tbDepService.getById(deptId);
        }
        String type = tbDep.getType();
        Result<QSingleDiseaseTakeReportStatisticPageVo> result = new Result<>();
        QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseStatisticHospitalService.allSingleDiseaseReportStatistic(qSingleDiseaseTakeReportStatisticParam, pageNo, pageSize,deptId,type);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报统计(全院单病种数量统计_单病种筛选)查询_导出
     */
    @AutoLog(value = "全院单病种上报统计(全院单病种数量统计_单病种筛选)查询_导出")
    @ApiOperation(value = "全院单病种上报统计(全院单病种数量统计_单病种筛选)查询_导出", notes = "全院单病种上报统计(全院单病种数量统计_单病种筛选)查询_导出",response = QSingleDiseaseTakeReportStatisticPageVo.class)
    @GetMapping(value = "/allSingleDiseaseReportStatisticExport")
    public ModelAndView allSingleDiseaseReportStatisticExport(@Validated QSingleDiseaseTakeReportStatisticExportParam qSingleDiseaseTakeReportStatisticExportParam) {
        String userId = qSingleDiseaseTakeReportStatisticExportParam.getUserId();
        TbUser tbUser = tbUserService.getById(userId);
        if(StringUtils.isBlank(userId)  || tbUser==null){
            return null;
        }
        TbDep tbDep = tbDepService.getById(tbUser.getDepid());
        if(tbDep==null){
            return null;
        }
        String deptId = tbDep.getId();
        String type = tbDep.getType();
        QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam = new QSingleDiseaseTakeReportStatisticParam();
        BeanUtils.copyProperties(qSingleDiseaseTakeReportStatisticExportParam,qSingleDiseaseTakeReportStatisticParam);
        List<QSingleDiseaseTakeReportStatisticVo> exportList = Lists.newArrayList();
        for (int i = 0; ; i++) {
            QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseStatisticHospitalService.allSingleDiseaseReportStatistic(qSingleDiseaseTakeReportStatisticParam, i+1, 500,deptId,type);
            List<QSingleDiseaseTakeReportStatisticVo> qSingleDiseaseTakeList = list.getQSingleDiseaseTakeList();
            if(qSingleDiseaseTakeList.isEmpty()){
                break;
            }
            exportList.addAll(qSingleDiseaseTakeList);
        }
        String fileName = String.valueOf(System.currentTimeMillis());
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, fileName);
        mv.addObject(NormalExcelConstants.CLASS, QSingleDiseaseTakeReportStatisticVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("全院单病种上报统计列表", "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 全院单病种上报数量统计-查看图表-单病种上报例数排名
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种上报例数排名")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种上报例数排名", notes = "全院单病种上报数量统计-查看图表-单病种上报例数排名")
    @GetMapping(value = "/singleDiseaseReportQuantityRanking")
    public Result<QSingleDiseaseTakeReportQuantityRankingVo> singleDiseaseReportQuantityRanking(@Validated QSingleDiseaseTakeReportQuantityRankingParam qSingleDiseaseTakeReportQuantityRankingParam) {

        Result<QSingleDiseaseTakeReportQuantityRankingVo> result = new Result<>();
        List<QSingleDiseaseTakeReportQuantityRankingVo> list = qSingleDiseaseStatisticHospitalService.singleDiseaseReportQuantityRanking(qSingleDiseaseTakeReportQuantityRankingParam);
//        List<QSingleDiseaseTakeReportQuantityRankingVo> list = Lists.newArrayList();
//        for (int i = 0; i < 6; i++) {
//            QSingleDiseaseTakeReportQuantityRankingVo build = QSingleDiseaseTakeReportQuantityRankingVo.builder().disease("病种" + (i + 1)).number(new BigDecimal(i + "0").intValue()).build();
//            list.add(build);
//        }

        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报数量统计-查看图表-单病种填报率排名
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种填报率排名")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种填报率排名", notes = "全院单病种上报数量统计-查看图表-单病种填报率排名")
    @GetMapping(value = "/singleDiseaseReportWriteRanking")
    public Result<QSingleDiseaseTakeReportQuantityRankingVo> singleDiseaseReportWriteRanking(@Validated QSingleDiseaseTakeReportQuantityRankingParam qSingleDiseaseTakeReportQuantityRankingParam) {

        Result<QSingleDiseaseTakeReportQuantityRankingVo> result = new Result<>();
        List<QSingleDiseaseTakeReportQuantityRankingVo> list = qSingleDiseaseStatisticHospitalService.singleDiseaseReportWriteRanking(qSingleDiseaseTakeReportQuantityRankingParam);
//        List<QSingleDiseaseTakeReportQuantityRankingVo> list = Lists.newArrayList();
//        for (int i = 0; i < 6; i++) {
//            QSingleDiseaseTakeReportQuantityRankingVo build = QSingleDiseaseTakeReportQuantityRankingVo.builder().disease("病种" + (i + 1)).number(new BigDecimal(i + "3")).build();
//            list.add(build);
//        }
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报数量统计-点击某个病种-统计分析
     */
    @AutoLog(value = "全院单病种上报数量统计-点击某个病种-统计分析")
    @ApiOperation(value = "全院单病种上报数量统计-点击某个病种-统计分析", notes = "全院单病种上报数量统计-点击某个病种-统计分析")
    @GetMapping(value = "/singleDiseaseStatisticAnalysis")
    public Result<QSingleDiseaseTakeStatisticAnalysisTableVo> singleDiseaseStatisticAnalysis(@Validated QSingleDiseaseTakeStatisticAnalysisParam qSingleDiseaseTakeStatisticAnalysisParam) {

        Result<QSingleDiseaseTakeStatisticAnalysisTableVo> result = new Result<>();
        List<QSingleDiseaseTakeStatisticAnalysisVo> list = qSingleDiseaseStatisticHospitalService.singleDiseaseStatisticAnalysis(qSingleDiseaseTakeStatisticAnalysisParam);
//        ArrayList<QSingleDiseaseTakeStatisticAnalysisVo> list = Lists.newArrayList();
//        for (int i = 0; i < 6; i++) {
//            QSingleDiseaseTakeStatisticAnalysisVo build = QSingleDiseaseTakeStatisticAnalysisVo.builder().categoryId("11111").yearMonth(String.format("2022年%s月",i+1)).completeReportCountryCount(2+i)
//                    .averageInHospitalDay(new BigDecimal(String.format("1%s.%s2",i,i+2)).add(new BigDecimal(i))).averageInHospitalFee(new BigDecimal("94564.26").add(new BigDecimal(i)))
//                    .mortality(String.format("0.5%s%%",i)).complicationRate(String.format("1.2%s%%",i)).build();
//            list.add(build);
//        }

        List<LinkedHashMap<String,String>> fieldItems = Lists.newArrayList();
        LinkedHashMap<String, String> fieldItem = Maps.newLinkedHashMap();
        fieldItems.add(fieldItem);
        fieldItem.put("fieldTxt","name1");

        List<LinkedHashMap<String,String>> singleDataList = Lists.newArrayList();
        LinkedHashMap<String, String> singleData = Maps.newLinkedHashMap();
        singleDataList.add(singleData);
        singleData.put("name1","上报例数");
        LinkedHashMap<String, String> singleData2 = Maps.newLinkedHashMap();
        singleDataList.add(singleData2);
        singleData2.put("name1","平均住院日");
        LinkedHashMap<String, String> singleData3 = Maps.newLinkedHashMap();
        singleDataList.add(singleData3);
        singleData3.put("name1","平均住院费用");
        LinkedHashMap<String, String> singleData4 = Maps.newLinkedHashMap();
        singleDataList.add(singleData4);
        singleData4.put("name1","死亡率");
        LinkedHashMap<String, String> singleData5 = Maps.newLinkedHashMap();
        singleDataList.add(singleData5);
        singleData5.put("name1","手术并发症发生率");

        for (int i = 0; i < list.size(); i++) {
            QSingleDiseaseTakeStatisticAnalysisVo qSingleDiseaseTakeStatisticAnalysisVo = list.get(i);
            LinkedHashMap<String, String> fieldItemTemp = Maps.newLinkedHashMap();
            fieldItems.add(fieldItemTemp);
            fieldItemTemp.put("fieldTxt",qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth());

            for (int j = 0; j < singleDataList.size(); j++) {
                LinkedHashMap<String, String> stringStringLinkedHashMap = singleDataList.get(j);
                if(j==0){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.valueOf(qSingleDiseaseTakeStatisticAnalysisVo.getCompleteReportCountryCount()));
                }else if(j==1){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.valueOf(qSingleDiseaseTakeStatisticAnalysisVo.getAverageInHospitalDay()));
                }else if(j==2){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.valueOf(qSingleDiseaseTakeStatisticAnalysisVo.getAverageInHospitalFee()));
                }else if(j==3){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.format("%s%%",qSingleDiseaseTakeStatisticAnalysisVo.getMortality()));
                }else if(j==4){
                    stringStringLinkedHashMap.put(qSingleDiseaseTakeStatisticAnalysisVo.getYearMonth(),String.format("%s%%",qSingleDiseaseTakeStatisticAnalysisVo.getComplicationRate()));
                }
            }
        }

        QSingleDiseaseTakeStatisticAnalysisTableVo build = QSingleDiseaseTakeStatisticAnalysisTableVo.builder().fieldItems(fieldItems).singleDataList(singleDataList).build();
        result.setSuccess(true);
        result.setResult(build);
        return result;
    }

    /**
     * 全院单病种上报数量统计-点击某个病种-科室对比
     */
    @AutoLog(value = "全院单病种上报数量统计-点击某个病种-科室对比")
    @ApiOperation(value = "全院单病种上报数量统计-点击某个病种-科室对比", notes = "全院单病种上报数量统计-点击某个病种-科室对比",response = QSingleDiseaseTakeStatisticDepartmentComparisonVo.class)
    @GetMapping(value = "/singleDiseaseStatisticDepartmentComparison")
    public Result<QSingleDiseaseTakeStatisticDepartmentComparisonVo> singleDiseaseStatisticDepartmentComparison(@Validated QSingleDiseaseTakeStatisticAnalysisParam qSingleDiseaseTakeStatisticAnalysisParam) {

        Result<QSingleDiseaseTakeStatisticDepartmentComparisonVo> result = new Result<>();
        List<QSingleDiseaseTakeStatisticDepartmentComparisonVo> list = qSingleDiseaseStatisticDeptService.singleDiseaseStatisticDepartmentComparison(qSingleDiseaseTakeStatisticAnalysisParam);
//        ArrayList<QSingleDiseaseTakeStatisticDepartmentComparisonVo> list = Lists.newArrayList();
//        for (int i = 0; i < 8; i++) {
//            QSingleDiseaseTakeStatisticDepartmentComparisonVo build = QSingleDiseaseTakeStatisticDepartmentComparisonVo.builder().deptName("内科"+i).needWriteCount(20).completeWriteCount(2).notWriteCount(18).completeReportCountryRate("5.26%")
//                    .averageInHospitalDay(new BigDecimal(String.format("1%s.%s2",i,i+2)).add(new BigDecimal(i))).averageInHospitalFee(new BigDecimal("894564.26")).complicationRate("1.21%").mortality("0.57%")
//                    .averageDrugFee(new BigDecimal("1856.12")).averageOperationFee(new BigDecimal("9856.12")).averageDisposableConsumableFee(new BigDecimal("1586.82"))
//                    .build();
//            list.add(build);
//        }
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报数量统计-点击某个病种-科室对比-五个图表
     */
    @AutoLog(value = "全院单病种上报数量统计-点击某个病种-科室对比-五个图表")
    @GetMapping(value = "/singleDiseaseStatisticDepartmentComparisonChart")
    @ApiOperation(value = "全院单病种上报数量统计-点击某个病种-科室对比-五个图表", notes = "全院单病种上报数量统计-点击某个病种-科室对比-五个图表",response = QSingleDiseaseTakeStatisticDepartmentComparisonChartVo.class)
    public Result<QSingleDiseaseTakeStatisticDepartmentComparisonChartVo> singleDiseaseStatisticDepartmentComparisonChart(@Validated QSingleDiseaseTakeStatisticDepartmentComparisonChartParam qSingleDiseaseTakeStatisticDepartmentComparisonChartParam) {
        Result<QSingleDiseaseTakeStatisticDepartmentComparisonChartVo> result = new Result<>();
        List<QSingleDiseaseTakeStatisticDepartmentComparisonChartVo> list = qSingleDiseaseStatisticDeptService.singleDiseaseStatisticDepartmentComparisonChart(qSingleDiseaseTakeStatisticDepartmentComparisonChartParam);
//        ArrayList<QSingleDiseaseTakeStatisticDepartmentComparisonChartVo> list = Lists.newArrayList();
//        for (int i = 0; i < 8; i++) {
//            ArrayList<QSingleDiseaseTakeStatisticDepartmentComparisonChartInDeptVo> objects = Lists.newArrayList();
//            for (int j = 0; j < 5; j++) {
//                QSingleDiseaseTakeStatisticDepartmentComparisonChartInDeptVo build = QSingleDiseaseTakeStatisticDepartmentComparisonChartInDeptVo.builder().deptName("科室" + j).number(new BigDecimal(j + "3").toPlainString()).build();
//                objects.add(build);
//            }
//            QSingleDiseaseTakeStatisticDepartmentComparisonChartVo build = QSingleDiseaseTakeStatisticDepartmentComparisonChartVo.builder()
//                    .yearMonth(String.format("2022年%s月",i+1))
//                    .deptList(objects)
//                    .build();
//            list.add(build);
//        }
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

//    /**
//     * 菜单-科室单病种上报例数列表
//     */
//    @AutoLog(value = "菜单-科室单病种上报例数列表")
//    @ApiOperation(value = "菜单-科室单病种上报例数列表", notes = "菜单-科室单病种上报例数列表")
//    @GetMapping(value = "/deptSingleDiseaseNumberList")
//    public Result<QSingleDiseaseTakeNumberVo> deptSingleDiseaseNumberList(@Validated QSingleDiseaseTakeNumberListParam qSingleDiseaseTakeNumberListParam) {
//        Result<QSingleDiseaseTakeNumberVo> result = new Result<>();
////        QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseTakeService.allSingleDiseaseReportStatistic(qSingleDiseaseTakeReportStatisticParam, pageNo, pageSize);
//        ArrayList<QSingleDiseaseTakeNumberListVo> list = Lists.newArrayList();
//        for (int i = 0; i < 8; i++) {
//            QSingleDiseaseTakeNumberListVo build = QSingleDiseaseTakeNumberListVo.builder()
//                    .deptName("内科").disease("病种"+i).number(new BigDecimal(i + "3").toPlainString())
//                    .build();
//            list.add(build);
//        }
//        for (int i = 0; i < 8; i++) {
//            QSingleDiseaseTakeNumberListVo build = QSingleDiseaseTakeNumberListVo.builder()
//                    .deptName("外科").disease("病种"+i).number(new BigDecimal(i + "6").toPlainString())
//                    .build();
//            list.add(build);
//        }
//        Map<String, List<JSONObject>> resMap = list.stream().collect(Collectors.toMap(QSingleDiseaseTakeNumberListVo::getDeptName, q->{
//                    JSONObject o = (JSONObject)JSON.toJSON(q);
//                    o.remove("deptName");
//                    ArrayList<JSONObject> qSingleDiseaseTakeNumberListVos = Lists.newArrayList();
//                    qSingleDiseaseTakeNumberListVos.add(o);
//                    return qSingleDiseaseTakeNumberListVos;
//                },
//                (List<JSONObject> n1, List<JSONObject> n2) -> {
//                    n1.addAll(n2);
//                    return n1;
//                }));
//        result.setSuccess(true);
//        result.setResult(resMap);
//        return result;
//    }

//    /**
//     * 菜单-科室单病种上报例数列表2
//     */
//    @AutoLog(value = "菜单-科室单病种上报例数列表2")
//    @ApiOperation(value = "菜单-科室单病种上报例数列表2", notes = "菜单-科室单病种上报例数列表2",response = QSingleDiseaseTakeNumberListVo.class)
//    @GetMapping(value = "/deptSingleDiseaseNumberList2")
//    public Result<QSingleDiseaseTakeNumberListVo> deptSingleDiseaseNumberList2(@Validated QSingleDiseaseTakeNumberListParam qSingleDiseaseTakeNumberListParam) {
//        Result<QSingleDiseaseTakeNumberListVo> result = new Result<>();
////        QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseTakeService.allSingleDiseaseReportStatistic(qSingleDiseaseTakeReportStatisticParam, pageNo, pageSize);
//        ArrayList<QSingleDiseaseTakeNumberListVo> list = Lists.newArrayList();
//        for (int i = 0; i < 8; i++) {
//            QSingleDiseaseTakeNumberListVo build = QSingleDiseaseTakeNumberListVo.builder()
//                    .deptName("内科").disease("病种"+i).number(new BigDecimal(i + "3").toPlainString())
//                    .build();
//            list.add(build);
//        }
//        for (int i = 0; i < 8; i++) {
//            QSingleDiseaseTakeNumberListVo build = QSingleDiseaseTakeNumberListVo.builder()
//                    .deptName("外科").disease("病种"+i).number(new BigDecimal(i + "6").toPlainString())
//                    .build();
//            list.add(build);
//        }
//
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
//    }

    /**
     * 菜单-科室单病种上报例数列表3
     */
    @AutoLog(value = "菜单-科室单病种上报例数列表3")
    @ApiOperation(value = "菜单-科室单病种上报例数列表3", notes = "菜单-科室单病种上报例数列表3",response = QSingleDiseaseTakeNumberVo.class)
    @GetMapping(value = "/deptSingleDiseaseNumberList3")
    public Result<QSingleDiseaseTakeNumberVo> deptSingleDiseaseNumberList3(@Validated QSingleDiseaseTakeNumberListParam qSingleDiseaseTakeNumberListParam) {
        Result<QSingleDiseaseTakeNumberVo> result = new Result<>();
        List<QSingleDiseaseTakeNumberVo> resList = qSingleDiseaseStatisticDeptService.deptSingleDiseaseNumberList3(qSingleDiseaseTakeNumberListParam);
        result.setSuccess(true);
        result.setResult(resList);
        return result;
    }

    /**
     * 菜单-新增-科室数量统计列表
     */
    @AutoLog(value = "菜单-新增-科室数量统计列表")
    @ApiOperation(value = "菜单-新增-科室数量统计列表", notes = "菜单-新增-科室数量统计列表",response = DepartmentQuantityStatisticsVo.class)
    @GetMapping(value = "/departmentQuantityStatistics")
    public Result<DepartmentQuantityStatisticsVo> departmentQuantityStatistics(@Validated DepartmentQuantityStatisticsParam departmentQuantityStatisticsParam) {
        Result<DepartmentQuantityStatisticsVo> result = new Result<>();
        List<DepartmentQuantityStatisticsVo> resList = qSingleDiseaseTakeService.departmentQuantityStatistics(departmentQuantityStatisticsParam);
        result.setSuccess(true);
        result.setResult(resList);
        return result;
    }

    /**
     * 菜单-新增-科室数量统计列表-导出
     */
    @AutoLog(value = "菜单-新增-科室数量统计列表-导出")
    @ApiOperation(value = "菜单-新增-科室数量统计列表-导出", notes = "菜单-新增-科室数量统计列表-导出",response = DepartmentQuantityStatisticsVo.class)
    @GetMapping(value = "/departmentQuantityStatisticsExport")
    public ModelAndView departmentQuantityStatisticsExport(
            @Validated DepartmentQuantityStatisticsParam departmentQuantityStatisticsParam){
        List<DepartmentQuantityStatisticsVo> exportList = qSingleDiseaseTakeService.departmentQuantityStatistics(departmentQuantityStatisticsParam);
        String fileName = String.valueOf(System.currentTimeMillis());
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, fileName);
        mv.addObject(NormalExcelConstants.CLASS, DepartmentQuantityStatisticsVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("科室数量统计列表", "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }



    /**
     * 全院单病种上报统计查询中-科室列表筛选条件---废弃
     */
    @AutoLog(value = "全院单病种上报统计查询中-科室列表筛选条件")
    @ApiOperation(value = "全院单病种上报统计查询中-科室列表筛选条件---废弃", notes = "全院单病种上报统计查询中-科室列表筛选条件---废弃")
    @GetMapping(value = "/allSingleDiseaseReportStatisticDept")
    public Result<List<QSingleDiseaseTakeReportStatisticDeptVo>> allSingleDiseaseReportStatisticDept() {
//        Result<List<QSingleDiseaseTakeReportStatisticDeptVo>> result = new Result<>();
//        List<QSingleDiseaseTakeReportStatisticDeptVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticDept();
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

//    /**
//     * 科室单病种上报统计查询---注释 会有全院替代
//     */
//    @AutoLog(value = "科室单病种上报统计查询")
//    @ApiOperation(value = "科室单病种上报统计查询", notes = "科室单病种上报统计查询")
//    @GetMapping(value = "/deptSingleDiseaseReportStatistic")
//    public Result<QSingleDiseaseTakeReportStatisticPageVo> deptSingleDiseaseReportStatistic(@Validated QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam,
//                                                                                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
//                                                                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
//                                                                                            HttpServletRequest request) {
//        // 暂时保存，会有用的
//        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
//        Result<QSingleDiseaseTakeReportStatisticPageVo> result = new Result<>();
//        //加科室过滤---
//        String[] dept = qSingleDiseaseTakeReportStatisticParam.getDept();
//        if(dept!=null){
//            ArrayList<String> strings = Lists.newArrayList(dept);
//            strings.add(data.getTbUser().getDepId(););
//            dept= strings.toArray(new String[0]);
//        }else{
//            dept= new String[]{data.getTbUser().getDepId()};
//        }
//        qSingleDiseaseTakeReportStatisticParam.setDept(dept);
//        QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseTakeService.allSingleDiseaseReportStatistic(qSingleDiseaseTakeReportStatisticParam, pageNo, pageSize);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
//    }

    /**
     * 科室单病种上报统计查询中-科室列表筛选条件
     */
    @AutoLog(value = "科室单病种上报统计查询中-科室列表筛选条件")
    @ApiOperation(value = "科室单病种上报统计查询中-科室列表筛选条件", notes = "科室单病种上报统计查询中-科室列表筛选条件")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticDept")
    public Result<List<QSingleDiseaseTakeReportStatisticDeptVo>> deptSingleDiseaseReportStatisticDept(HttpServletRequest request) {

        Result<List<QSingleDiseaseTakeReportStatisticDeptVo>> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        TbDep tbDep = new TbDep();
        if (org.apache.commons.lang.StringUtils.isNotBlank(deptId)) {
            tbDep = tbDepService.getById(deptId);
        }
        List<QSingleDiseaseTakeReportStatisticDeptVo> list= Lists.newArrayList();
        QSingleDiseaseTakeReportStatisticDeptVo q= new QSingleDiseaseTakeReportStatisticDeptVo();
        q.setDepartmentId(tbDep.getId());
        q.setDepartment(tbDep.getDepname());
        list.add(q);
//        List<QSingleDiseaseTakeReportStatisticDeptVo> list = qSingleDiseaseTakeService.deptSingleDiseaseReportStatisticDept(deptId);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }



    /**
     * 全院单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据-0329废弃
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据-0329废弃")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据-0329废弃", notes = "全院单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据-0329废弃")
    @GetMapping(value = "/allSingleDiseaseReportStatisticOverviewLine")
    public Result<List<QSingleDiseaseTakeReportStatisticOverviewLineVo>> allSingleDiseaseReportStatisticOverviewLine(
            @Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewLineParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticOverviewLineVo>> result = new Result<>();
//        List<QSingleDiseaseTakeReportStatisticOverviewLineVo> list = qSingleDiseaseTakeService
//                .allSingleDiseaseReportStatisticOverviewLine(qSingleDiseaseTakeReportStatisticOverviewLineParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 全院单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据-0329废弃
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据-0329废弃")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据-0329废弃", notes = "全院单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据-0329废弃")
    @GetMapping(value = "/allSingleDiseaseReportStatisticOverviewPie")
    public Result<List<QSingleDiseaseTakeReportStatisticOverviewPieVo>> allSingleDiseaseReportStatisticOverviewPie(
            @Validated QSingleDiseaseTakeReportStatisticOverviewPieParam qSingleDiseaseTakeReportStatisticOverviewParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticOverviewPieVo>> result = new Result<>();
//        List<QSingleDiseaseTakeReportStatisticOverviewPieVo> list = qSingleDiseaseTakeService
//                .allSingleDiseaseReportStatisticOverviewPie(qSingleDiseaseTakeReportStatisticOverviewParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 全院单病种上报数量统计-查看图表-单病种数量上报趋势图-0329废弃
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种数量上报趋势图-0329废弃")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种数量上报趋势图-0329废弃", notes = "全院单病种上报数量统计-查看图表-单病种数量上报趋势图-0329废弃")
    @GetMapping(value = "/allSingleDiseaseReportStatisticTrend")
    public Result<List<QSingleDiseaseTakeReportStatisticTrendVo>> allSingleDiseaseReportStatisticTrend(@Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticTrendVo>> result = new Result<>();
//        List<QSingleDiseaseTakeReportStatisticTrendVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticTrend(qSingleDiseaseTakeReportStatisticOverviewParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 全院单病种上报数量统计-查看图表-科室上报单病种数量排列图-0329废弃
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-科室上报单病种数量排列图-0329废弃")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-科室上报单病种数量排列图-0329废弃", notes = "全院单病种上报数量统计-查看图表-科室上报单病种数量排列图-0329废弃")
    @GetMapping(value = "/allSingleDiseaseReportStatisticDeptPermutation")
    public Result<List<QSingleDiseaseTakeReportStatisticDeptPermutationVo>> allSingleDiseaseReportStatisticDeptPermutation(@Validated QSingleDiseaseTakeReportStatisticDeptPermutationParam qSingleDiseaseTakeReportStatisticDeptPermutationParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticDeptPermutationVo>> result = new Result<>();
//        List<QSingleDiseaseTakeReportStatisticDeptPermutationVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticDeptPermutation(qSingleDiseaseTakeReportStatisticDeptPermutationParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 全院单病种上报数量统计-查看图表-各病种上报情况汇总表-0329废弃
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-各病种上报情况汇总表-0329废弃")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-各病种上报情况汇总表-0329废弃", notes = "全院单病种上报数量统计-查看图表-各病种上报情况汇总表-0329废弃")
    @GetMapping(value = "/allSingleDiseaseReportStatisticSummary")
    public Result<List<QSingleDiseaseTakeReportStatisticSummaryVo>> allSingleDiseaseReportStatisticSummary(@Validated QSingleDiseaseTakeReportStatisticSummaryParam qSingleDiseaseTakeReportStatisticSummaryParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticSummaryVo>> result = new Result<>();
//        List<QSingleDiseaseTakeReportStatisticSummaryVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticSummary(qSingleDiseaseTakeReportStatisticSummaryParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }




    /**
     * 科室单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据-0329废弃
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据-0329废弃")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据-0329废弃", notes = "科室单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据-0329废弃")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticOverviewLine")
    public Result<List<QSingleDiseaseTakeReportStatisticOverviewLineVo>> deptSingleDiseaseReportStatisticOverviewLine(
            @Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewLineParam,HttpServletRequest request
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticOverviewLineVo>> result = new Result<>();
//        // 加科室过滤---
//        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
//        String[] dept = qSingleDiseaseTakeReportStatisticOverviewLineParam.getDept();
//        if(dept!=null){
//            ArrayList<String> strings = Lists.newArrayList(dept);
//            strings.add(data.getTbUser().getDepId());
//            dept= strings.toArray(new String[0]);
//        }else{
//            dept= new String[]{data.getTbUser().getDepId()};
//        }
//        qSingleDiseaseTakeReportStatisticOverviewLineParam.setDept(dept);
//        List<QSingleDiseaseTakeReportStatisticOverviewLineVo> list = qSingleDiseaseTakeService
//                .allSingleDiseaseReportStatisticOverviewLine(qSingleDiseaseTakeReportStatisticOverviewLineParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 科室单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据-0329废弃
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据-0329废弃")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据-0329废弃", notes = "科室单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据-0329废弃")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticOverviewPie")
    public Result<List<QSingleDiseaseTakeReportStatisticOverviewPieVo>> deptSingleDiseaseReportStatisticOverviewPie(
            @Validated QSingleDiseaseTakeReportStatisticOverviewPieParam qSingleDiseaseTakeReportStatisticOverviewParam,
            HttpServletRequest request
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticOverviewPieVo>> result = new Result<>();
//        // 加科室过滤---
//        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
//        String[] dept = qSingleDiseaseTakeReportStatisticOverviewParam.getDept();
//        if(dept!=null){
//            ArrayList<String> strings = Lists.newArrayList(dept);
//            strings.add(data.getTbUser().getDepId());
//            dept= strings.toArray(new String[0]);
//        }else{
//            dept= new String[]{data.getTbUser().getDepId()};
//        }
//        qSingleDiseaseTakeReportStatisticOverviewParam.setDept(dept);
//
//        List<QSingleDiseaseTakeReportStatisticOverviewPieVo> list = qSingleDiseaseTakeService
//                .allSingleDiseaseReportStatisticOverviewPie(qSingleDiseaseTakeReportStatisticOverviewParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 科室单病种上报数量统计-查看图表-单病种数量上报趋势图-0329废弃
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-单病种数量上报趋势图-0329废弃")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-单病种数量上报趋势图-0329废弃", notes = "科室单病种上报数量统计-查看图表-单病种数量上报趋势图-0329废弃")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticTrend")
    public Result<List<QSingleDiseaseTakeReportStatisticTrendVo>> deptSingleDiseaseReportStatisticTrend(@Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewParam,
                                                                                                        HttpServletRequest request
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticTrendVo>> result = new Result<>();
//        // 加科室过滤---
//        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
//        String[] dept = qSingleDiseaseTakeReportStatisticOverviewParam.getDept();
//        if(dept!=null){
//            ArrayList<String> strings = Lists.newArrayList(dept);
//            strings.add(data.getTbUser().getDepId());
//            dept= strings.toArray(new String[0]);
//        }else{
//            dept= new String[]{data.getTbUser().getDepId()};
//        }
//        qSingleDiseaseTakeReportStatisticOverviewParam.setDept(dept);
//        List<QSingleDiseaseTakeReportStatisticTrendVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticTrend(qSingleDiseaseTakeReportStatisticOverviewParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 科室单病种上报数量统计-查看图表-科室上报单病种数量排列图-0329废弃
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-科室上报单病种数量排列图-0329废弃")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-科室上报单病种数量排列图-0329废弃", notes = "科室单病种上报数量统计-查看图表-科室上报单病种数量排列图-0329废弃")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticDeptPermutation")
    public Result<List<QSingleDiseaseTakeReportStatisticDeptPermutationVo>> deptSingleDiseaseReportStatisticDeptPermutation(@Validated QSingleDiseaseTakeReportStatisticDeptPermutationParam qSingleDiseaseTakeReportStatisticDeptPermutationParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticDeptPermutationVo>> result = new Result<>();
//        List<QSingleDiseaseTakeReportStatisticDeptPermutationVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticDeptPermutation(qSingleDiseaseTakeReportStatisticDeptPermutationParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 科室单病种上报数量统计-查看图表-各病种上报情况汇总表-0329废弃
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-各病种上报情况汇总表-0329废弃")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-各病种上报情况汇总表-0329废弃", notes = "科室单病种上报数量统计-查看图表-各病种上报情况汇总表-0329废弃")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticSummary")
    public Result<List<QSingleDiseaseTakeReportStatisticSummaryVo>> deptSingleDiseaseReportStatisticSummary(@Validated QSingleDiseaseTakeReportStatisticSummaryParam qSingleDiseaseTakeReportStatisticSummaryParam,
                                                                                                            HttpServletRequest request
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

//        Result<List<QSingleDiseaseTakeReportStatisticSummaryVo>> result = new Result<>();
//        // 加科室过滤---
//        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
//        qSingleDiseaseTakeReportStatisticSummaryParam.setDept(data.getTbUser().getDepId());
//        List<QSingleDiseaseTakeReportStatisticSummaryVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticSummary(qSingleDiseaseTakeReportStatisticSummaryParam);
//        result.setSuccess(true);
//        result.setResult(list);
//        return result;
        return null;
    }

    /**
     * 工作台提醒
     */
    @AutoLog(value = "工作台提醒")
    @ApiOperation(value = "工作台提醒", notes = "工作台提醒")
    @GetMapping(value = "/workbenchReminder")
    public Result<WorkbenchReminderVo> workbenchReminder(HttpServletRequest request) {
        Result<WorkbenchReminderVo> result = new Result<>();
        // 加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getTbUser().getDepId();
        TbDep tbDep = new TbDep();
        if (org.apache.commons.lang.StringUtils.isNotBlank(deptId)) {
            tbDep = tbDepService.getById(deptId);
        }
        String type = tbDep.getType();
        String dept=null;
        if(DeptUtil.isClinical(type)){
            dept=deptId;
        }
        WorkbenchReminderVo workbenchReminderVo = qSingleDiseaseTakeService.workbenchReminder(dept);
        if(DeptUtil.isClinical(type)){
            workbenchReminderVo.setClinical(0);
        }else{
            workbenchReminderVo.setClinical(1);
        }
        result.setSuccess(true);
        result.setResult(workbenchReminderVo);
        return result;
    }

    /**
     * 单病种填报增加分组导航
     */
    @AutoLog(value = "单病种填报增加分组导航")
    @ApiOperation(value = "单病种填报增加分组导航", notes = "单病种填报增加分组导航")
    @GetMapping(value = "/singleDiseaseAnswerNavigation")
    public Result<List<SingleDiseaseAnswerNavigationVo>> singleDiseaseAnswerNavigation(@Validated SingleDiseaseAnswerNavigationParam singleDiseaseAnswerNavigationParam) {
        Result<List<SingleDiseaseAnswerNavigationVo>> result = new Result<>();
        List<SingleDiseaseAnswerNavigationVo> singleDiseaseAnswerNavigationVoList = qSingleDiseaseTakeService.singleDiseaseAnswerNavigation(singleDiseaseAnswerNavigationParam);
        result.setSuccess(true);
        result.setResult(singleDiseaseAnswerNavigationVoList);
        return result;
    }

//    /**
//     * 上报失败记录
//     */
//    @AutoLog(value = "上报失败记录")
//    @ApiOperation(value = "上报失败记录", notes = "上报失败记录")
//    @GetMapping(value = "/reportFailureRecordPage")
//    public Result<String> reportFailureRecordPage(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//                                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
//        Result<String> result = new Result<>();
//        List<ReportFailureRecordParameterVo> reportFailureRecordParameterVoList = qSingleDiseaseTakeService.queryErrorQuestion(pageNo, pageSize);
//        result.setSuccess(true);
//        result.setResult(reportFailureRecordParameterVoList);
//        return result;
//    }

    /**
     * 上报失败记录页面
     */
    @AutoLog(value = "上报失败记录页面")
    @ApiOperation(value = "上报失败记录页面", notes = "上报失败记录页面")
    @GetMapping(value = "/reportFailureRecordPage")
    public Result<String> diseaseNameQuery (@RequestParam(value = "name", required =  false) String name,
                                            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Result<String> result = new Result<>();
        ReportFailureRecordParameterPageVo reportFailureRecordParameterPageVo = new ReportFailureRecordParameterPageVo();
        result.setSuccess(true);
        if (name != null && !name.isEmpty()) {
            List<ReportFailureRecordParameterVo> reportFailureRecordParameterVoList = qSingleDiseaseTakeService.queryErrorQuestionByName(name, pageNo, pageSize);
            Integer count = qSingleDiseaseTakeService.pageDataCountByName(name);
            reportFailureRecordParameterPageVo.setTotal(count);
            reportFailureRecordParameterPageVo.setReportFailureRecordParameterVoList(reportFailureRecordParameterVoList);
            result.setResult(reportFailureRecordParameterPageVo);
        } else {
            List<ReportFailureRecordParameterVo> reportFailureRecordParameterVoList = qSingleDiseaseTakeService.queryErrorQuestion(pageNo, pageSize);
            Integer count = qSingleDiseaseTakeService.pageDataCount();
            reportFailureRecordParameterPageVo.setTotal(count);
            reportFailureRecordParameterPageVo.setReportFailureRecordParameterVoList(reportFailureRecordParameterVoList);
            result.setResult(reportFailureRecordParameterPageVo);
        }
        return result;
    }


    /**
     * 工作台-单病种上报数量统计
     */
    @AutoLog(value = "工作台-单病种上报数量统计")
    @ApiOperation(value = "工作台-单病种上报数量统计", notes = "工作台-单病种上报数量统计")
    @GetMapping(value = "/singleDiseaseReportCount")
    public Result<SingleDiseaseReportCountVo> singleDiseaseReportCount(HttpServletRequest request) {
        Result<SingleDiseaseReportCountVo> result = new Result<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        if(data==null){
            return ResultFactory.fail("登录过期,请重新登录");
        }
        String deptId = data.getTbUser().getDepId();
        TbDep tbDep = new TbDep();
        if (org.apache.commons.lang.StringUtils.isNotBlank(deptId)) {
            tbDep = tbDepService.getById(deptId);
        }
        String type = tbDep.getType();
        String dept=null;
        if(DeptUtil.isClinical(type)){
            dept=data.getTbUser().getDepId();
        }
        SingleDiseaseReportCountVo singleDiseaseReportCount = qSingleDiseaseTakeService.singleDiseaseReportCount(dept);
//        if(DeptUtil.isClinical(type)){
//            singleDiseaseReportCount.setClinical(0);
//        }else{
//            singleDiseaseReportCount.setClinical(1);
//        }
        result.setSuccess(true);
        result.setResult(singleDiseaseReportCount);
        return result;
    }

    /**
     * 单病种数据接收
     */
    @AutoLog(value = "单病种数据接收")
    @ApiOperation(value = "单病种数据接收", notes = "单病种数据接收")
    @PostMapping(value = "/singleDiseaseTakeRecsiveSave")
    public void runSingleDiseaseTakeRecsiveSave(@Valid @RequestBody Map<String, Object> map) {
        qSingleDiseaseTakeService.runSingleDiseaseTakeRecsiveSave(map);
    }

    /**
     * 单病种视图接收
     */
    @AutoLog(value = "单病种视图接收")
    @ApiOperation(value = "单病种视图接收", notes = "单病种视图接收")
    @GetMapping(value = "/getdzbxx")
    public void getdzbxx() {
        qSingleDiseaseTakeService.getGetdzbxxInsertDrgs();
    }

    /**
     * 读取燕达mongodb数据
     */
    @AutoLog(value = "读取燕达mongodb数据")
    @ApiOperation(value = "读取燕达mongodb数据", notes = "读取燕达mongodb数据")
    @GetMapping(value = "/readDrgsReportData")
    public String readDrgsReportData(){
        readDrgsReportDataervice.readQSingleDiseaseTake();
        return "success";
    }

}
