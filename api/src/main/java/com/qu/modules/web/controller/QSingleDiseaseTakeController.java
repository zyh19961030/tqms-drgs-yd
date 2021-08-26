package com.qu.modules.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.qu.constant.Constant;
import com.qu.constant.QSingleDiseaseTakeConstant;
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
import com.qu.modules.web.param.SingleDiseaseRejectParam;
import com.qu.modules.web.param.SingleDiseaseWaitUploadParam;
import com.qu.modules.web.param.SingleDiseaseExamineRecordParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.pojo.Deps;
import com.qu.modules.web.service.IQSingleDiseaseTakeService;
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
import com.qu.util.DeptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        String deptId = data.getDeps().get(0).getId();
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
        String deptId = data.getDeps().get(0).getId();
        List<QSingleDiseaseNameVo> list = qSingleDiseaseTakeService.singleDiseaseNameList(deptId);
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
                                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseByDoctorList(qSingleDiseaseTakeByDoctorParam, pageNo, pageSize);
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
        String deptId = data.getDeps().get(0).getId();
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
                                                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseRejectList(pageNo, pageSize);
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
        String deptId = data.getDeps().get(0).getId();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseByDeptList(qSingleDiseaseTakeByDeptParam, pageNo, pageSize,deptId);
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
     * 全院单病种上报统计查询
     */
    @AutoLog(value = "全院单病种上报统计查询")
    @ApiOperation(value = "全院单病种上报统计查询", notes = "全院单病种上报统计查询")
    @GetMapping(value = "/allSingleDiseaseReportStatistic")
    public Result<QSingleDiseaseTakeReportStatisticPageVo> allSingleDiseaseReportStatistic(@Validated QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam,
                                                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Result<QSingleDiseaseTakeReportStatisticPageVo> result = new Result<>();
        QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseTakeService.allSingleDiseaseReportStatistic(qSingleDiseaseTakeReportStatisticParam, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报统计查询中-科室列表筛选条件---暂时废弃
     */
    @AutoLog(value = "全院单病种上报统计查询中-科室列表筛选条件")
    @ApiOperation(value = "全院单病种上报统计查询中-科室列表筛选条件", notes = "全院单病种上报统计查询中-科室列表筛选条件")
    @GetMapping(value = "/allSingleDiseaseReportStatisticDept")
    public Result<List<QSingleDiseaseTakeReportStatisticDeptVo>> allSingleDiseaseReportStatisticDept() {

        Result<List<QSingleDiseaseTakeReportStatisticDeptVo>> result = new Result<>();
        List<QSingleDiseaseTakeReportStatisticDeptVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticDept();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 科室单病种上报统计查询
     */
    @AutoLog(value = "科室单病种上报统计查询")
    @ApiOperation(value = "科室单病种上报统计查询", notes = "科室单病种上报统计查询")
    @GetMapping(value = "/deptSingleDiseaseReportStatistic")
    public Result<QSingleDiseaseTakeReportStatisticPageVo> deptSingleDiseaseReportStatistic(@Validated QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam,
                                                                                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                                            HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        Result<QSingleDiseaseTakeReportStatisticPageVo> result = new Result<>();
        //加科室过滤---
        String[] dept = qSingleDiseaseTakeReportStatisticParam.getDept();
        if(dept!=null){
            ArrayList<String> strings = Lists.newArrayList(dept);
            strings.add(data.getDeps().get(0).getId());
            dept= strings.toArray(new String[0]);
        }else{
            dept= new String[]{data.getDeps().get(0).getId()};
        }
        qSingleDiseaseTakeReportStatisticParam.setDept(dept);
        QSingleDiseaseTakeReportStatisticPageVo list = qSingleDiseaseTakeService.allSingleDiseaseReportStatistic(qSingleDiseaseTakeReportStatisticParam, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

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
        Deps deps = data.getDeps().get(0);
        List<QSingleDiseaseTakeReportStatisticDeptVo> list= Lists.newArrayList();
        QSingleDiseaseTakeReportStatisticDeptVo q= new QSingleDiseaseTakeReportStatisticDeptVo();
        q.setDepartmentId(deps.getId());
        q.setDepartment(deps.getDepName());
        list.add(q);
//        List<QSingleDiseaseTakeReportStatisticDeptVo> list = qSingleDiseaseTakeService.deptSingleDiseaseReportStatisticDept(deptId);
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
    public Result<Boolean> singleDiseaseAnswer(@RequestBody SingleDiseaseAnswerParam singleDiseaseAnswerParam, HttpServletRequest request) {
        Result<Boolean> result = new Result<>();
        String token = request.getHeader("token");
        String cookie = "JSESSIONID=" + token;
        log.info("-----------singleDiseaseAnswerParam={}", JSON.toJSONString(singleDiseaseAnswerParam));
        qSingleDiseaseTakeService.singleDiseaseAnswer(cookie,singleDiseaseAnswerParam);
        result.setSuccess(true);
        result.setResult(true);
        return result;
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
     * 全院单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据", notes = "全院单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据")
    @GetMapping(value = "/allSingleDiseaseReportStatisticOverviewLine")
    public Result<List<QSingleDiseaseTakeReportStatisticOverviewLineVo>> allSingleDiseaseReportStatisticOverviewLine(
            @Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewLineParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticOverviewLineVo>> result = new Result<>();
        List<QSingleDiseaseTakeReportStatisticOverviewLineVo> list = qSingleDiseaseTakeService
                .allSingleDiseaseReportStatisticOverviewLine(qSingleDiseaseTakeReportStatisticOverviewLineParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据", notes = "全院单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据")
    @GetMapping(value = "/allSingleDiseaseReportStatisticOverviewPie")
    public Result<List<QSingleDiseaseTakeReportStatisticOverviewPieVo>> allSingleDiseaseReportStatisticOverviewPie(
            @Validated QSingleDiseaseTakeReportStatisticOverviewPieParam qSingleDiseaseTakeReportStatisticOverviewParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticOverviewPieVo>> result = new Result<>();
        List<QSingleDiseaseTakeReportStatisticOverviewPieVo> list = qSingleDiseaseTakeService
                .allSingleDiseaseReportStatisticOverviewPie(qSingleDiseaseTakeReportStatisticOverviewParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报数量统计-查看图表-单病种数量上报趋势图
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种数量上报趋势图")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种数量上报趋势图", notes = "全院单病种上报数量统计-查看图表-单病种数量上报趋势图")
    @GetMapping(value = "/allSingleDiseaseReportStatisticTrend")
    public Result<List<QSingleDiseaseTakeReportStatisticTrendVo>> allSingleDiseaseReportStatisticTrend(@Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticTrendVo>> result = new Result<>();
        List<QSingleDiseaseTakeReportStatisticTrendVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticTrend(qSingleDiseaseTakeReportStatisticOverviewParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报数量统计-查看图表-科室上报单病种数量排列图
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-科室上报单病种数量排列图")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-科室上报单病种数量排列图", notes = "全院单病种上报数量统计-查看图表-科室上报单病种数量排列图")
    @GetMapping(value = "/allSingleDiseaseReportStatisticDeptPermutation")
    public Result<List<QSingleDiseaseTakeReportStatisticDeptPermutationVo>> allSingleDiseaseReportStatisticDeptPermutation(@Validated QSingleDiseaseTakeReportStatisticDeptPermutationParam qSingleDiseaseTakeReportStatisticDeptPermutationParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticDeptPermutationVo>> result = new Result<>();
        List<QSingleDiseaseTakeReportStatisticDeptPermutationVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticDeptPermutation(qSingleDiseaseTakeReportStatisticDeptPermutationParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 全院单病种上报数量统计-查看图表-各病种上报情况汇总表
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-各病种上报情况汇总表")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-各病种上报情况汇总表", notes = "全院单病种上报数量统计-查看图表-各病种上报情况汇总表")
    @GetMapping(value = "/allSingleDiseaseReportStatisticSummary")
    public Result<List<QSingleDiseaseTakeReportStatisticSummaryVo>> allSingleDiseaseReportStatisticSummary(@Validated QSingleDiseaseTakeReportStatisticSummaryParam qSingleDiseaseTakeReportStatisticSummaryParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticSummaryVo>> result = new Result<>();
        List<QSingleDiseaseTakeReportStatisticSummaryVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticSummary(qSingleDiseaseTakeReportStatisticSummaryParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }


    /**
     * 科室单病种上报数量统计-查看图表-单病种上报数据概览
     */
    /*@AutoLog(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览", notes = "科室单病种上报数量统计-查看图表-单病种上报数据概览")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticOverview")
    public Result<QSingleDiseaseTakeReportStatisticOverviewVo> deptSingleDiseaseReportStatisticOverview(@Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewParam
                                                                                           *//*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*//*) {

        Result<QSingleDiseaseTakeReportStatisticOverviewVo> result = new Result<>();
        QSingleDiseaseTakeReportStatisticOverviewVo list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticOverview(qSingleDiseaseTakeReportStatisticOverviewParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }*/

    /**
     * 科室单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据", notes = "科室单病种上报数量统计-查看图表-单病种上报数据概览-折线图数据")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticOverviewLine")
    public Result<List<QSingleDiseaseTakeReportStatisticOverviewLineVo>> deptSingleDiseaseReportStatisticOverviewLine(
            @Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewLineParam,HttpServletRequest request
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticOverviewLineVo>> result = new Result<>();
        //todo  加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String[] dept = qSingleDiseaseTakeReportStatisticOverviewLineParam.getDept();
        if(dept!=null){
            ArrayList<String> strings = Lists.newArrayList(dept);
            strings.add(data.getDeps().get(0).getId());
            dept= strings.toArray(new String[0]);
        }else{
            dept= new String[]{data.getDeps().get(0).getId()};
        }
        qSingleDiseaseTakeReportStatisticOverviewLineParam.setDept(dept);
        List<QSingleDiseaseTakeReportStatisticOverviewLineVo> list = qSingleDiseaseTakeService
                .allSingleDiseaseReportStatisticOverviewLine(qSingleDiseaseTakeReportStatisticOverviewLineParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 科室单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据", notes = "科室单病种上报数量统计-查看图表-单病种上报数据概览-饼图数据")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticOverviewPie")
    public Result<List<QSingleDiseaseTakeReportStatisticOverviewPieVo>> deptSingleDiseaseReportStatisticOverviewPie(
            @Validated QSingleDiseaseTakeReportStatisticOverviewPieParam qSingleDiseaseTakeReportStatisticOverviewParam,
            HttpServletRequest request
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticOverviewPieVo>> result = new Result<>();
        //todo  加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String[] dept = qSingleDiseaseTakeReportStatisticOverviewParam.getDept();
        if(dept!=null){
            ArrayList<String> strings = Lists.newArrayList(dept);
            strings.add(data.getDeps().get(0).getId());
            dept= strings.toArray(new String[0]);
        }else{
            dept= new String[]{data.getDeps().get(0).getId()};
        }
        qSingleDiseaseTakeReportStatisticOverviewParam.setDept(dept);

        List<QSingleDiseaseTakeReportStatisticOverviewPieVo> list = qSingleDiseaseTakeService
                .allSingleDiseaseReportStatisticOverviewPie(qSingleDiseaseTakeReportStatisticOverviewParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 科室单病种上报数量统计-查看图表-单病种数量上报趋势图
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-单病种数量上报趋势图")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-单病种数量上报趋势图", notes = "科室单病种上报数量统计-查看图表-单病种数量上报趋势图")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticTrend")
    public Result<List<QSingleDiseaseTakeReportStatisticTrendVo>> deptSingleDiseaseReportStatisticTrend(@Validated QSingleDiseaseTakeReportStatisticOverviewLineParam qSingleDiseaseTakeReportStatisticOverviewParam,
                                                                                                        HttpServletRequest request
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticTrendVo>> result = new Result<>();
        //todo  加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String[] dept = qSingleDiseaseTakeReportStatisticOverviewParam.getDept();
        if(dept!=null){
            ArrayList<String> strings = Lists.newArrayList(dept);
            strings.add(data.getDeps().get(0).getId());
            dept= strings.toArray(new String[0]);
        }else{
            dept= new String[]{data.getDeps().get(0).getId()};
        }
        qSingleDiseaseTakeReportStatisticOverviewParam.setDept(dept);
        List<QSingleDiseaseTakeReportStatisticTrendVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticTrend(qSingleDiseaseTakeReportStatisticOverviewParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 科室单病种上报数量统计-查看图表-科室上报单病种数量排列图
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-科室上报单病种数量排列图")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-科室上报单病种数量排列图", notes = "科室单病种上报数量统计-查看图表-科室上报单病种数量排列图")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticDeptPermutation")
    public Result<List<QSingleDiseaseTakeReportStatisticDeptPermutationVo>> deptSingleDiseaseReportStatisticDeptPermutation(@Validated QSingleDiseaseTakeReportStatisticDeptPermutationParam qSingleDiseaseTakeReportStatisticDeptPermutationParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticDeptPermutationVo>> result = new Result<>();
        List<QSingleDiseaseTakeReportStatisticDeptPermutationVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticDeptPermutation(qSingleDiseaseTakeReportStatisticDeptPermutationParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 科室单病种上报数量统计-查看图表-各病种上报情况汇总表
     */
    @AutoLog(value = "科室单病种上报数量统计-查看图表-各病种上报情况汇总表")
    @ApiOperation(value = "科室单病种上报数量统计-查看图表-各病种上报情况汇总表", notes = "科室单病种上报数量统计-查看图表-各病种上报情况汇总表")
    @GetMapping(value = "/deptSingleDiseaseReportStatisticSummary")
    public Result<List<QSingleDiseaseTakeReportStatisticSummaryVo>> deptSingleDiseaseReportStatisticSummary(@Validated QSingleDiseaseTakeReportStatisticSummaryParam qSingleDiseaseTakeReportStatisticSummaryParam,
                                                                                                            HttpServletRequest request
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<List<QSingleDiseaseTakeReportStatisticSummaryVo>> result = new Result<>();
        //todo  加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        qSingleDiseaseTakeReportStatisticSummaryParam.setDept(data.getDeps().get(0).getId());
        List<QSingleDiseaseTakeReportStatisticSummaryVo> list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticSummary(qSingleDiseaseTakeReportStatisticSummaryParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 工作台提醒
     */
    @AutoLog(value = "工作台提醒")
    @ApiOperation(value = "工作台提醒", notes = "工作台提醒")
    @GetMapping(value = "/workbenchReminder")
    public Result<WorkbenchReminderVo> workbenchReminder(HttpServletRequest request) {
        Result<WorkbenchReminderVo> result = new Result<>();
        //todo  加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String type = data.getDeps().get(0).getType();
        String dept=null;
        if(DeptUtil.isClinical(type)){
            dept=data.getDeps().get(0).getId();
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







}
