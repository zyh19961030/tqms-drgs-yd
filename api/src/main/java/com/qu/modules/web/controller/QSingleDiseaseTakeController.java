package com.qu.modules.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.qu.constant.QSingleDiseaseTakeConstant;
import com.qu.modules.web.param.QSingleDiseaseTakeByDeptParam;
import com.qu.modules.web.param.QSingleDiseaseTakeByDoctorParam;
import com.qu.modules.web.param.QSingleDiseaseTakeNoNeedParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticOverviewParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticParam;
import com.qu.modules.web.param.SingleDiseaseAnswerParam;
import com.qu.modules.web.param.SingleDiseaseRejectParam;
import com.qu.modules.web.service.IQSingleDiseaseTakeService;
import com.qu.modules.web.vo.QSingleDiseaseNameVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeByDoctorPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticDeptVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticOverviewVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeVo;
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
     * 按单病种填报
     */
    @AutoLog(value = "按单病种填报查询")
    @ApiOperation(value = "按单病种填报查询", notes = "按单病种填报查询")
    @GetMapping(value = "/singleDiseaseList")
    public Result<List<QSingleDiseaseTakeVo>> singleDiseaseList(@RequestParam(name = "name", required = false) String name) {
        Result<List<QSingleDiseaseTakeVo>> result = new Result<>();
        List<QSingleDiseaseTakeVo> list = qSingleDiseaseTakeService.singleDiseaseList(name);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 按医生填报查询-本科室单病种上报记录-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件
     */
    @AutoLog(value = "按医生填报查询-本科室单病种上报记录-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件")
    @ApiOperation(value = "按医生填报查询-本科室单病种上报记录-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件", notes = "按医生填报查询-本科室单病种上报记录-全院单病种上报统计-科室单病种上报统计-单病种指标统计-病种名称筛选条件")
    @GetMapping(value = "/singleDiseaseNameList")
    public Result<List<QSingleDiseaseNameVo>> singleDiseaseNameList() {
        Result<List<QSingleDiseaseNameVo>> result = new Result<>();
        List<QSingleDiseaseNameVo> list = qSingleDiseaseTakeService.singleDiseaseNameList();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 按医生填报查询
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
     * 单病种上报待审查询
     */
    @AutoLog(value = "单病种上报待审查询")
    @ApiOperation(value = "单病种上报待审查询", notes = "单病种上报待审查询")
    @GetMapping(value = "/singleDiseaseWaitUploadList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> singleDiseaseWaitUploadList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseWaitUploadList(pageNo, pageSize);
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
        //todo 为展会 审核通过直接已完成
        String msg = qSingleDiseaseTakeService.setSingleDiseaseStatus(ids, QSingleDiseaseTakeConstant.STATUS_COMPLETE,null);
//        String msg = qSingleDiseaseTakeService.setSingleDiseaseStatus(ids, QSingleDiseaseTakeConstant.STATUS_PASS_WAIT_UPLOAD,null);
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
     * 本科室单病种上报记录查询
     */
    @AutoLog(value = "本科室单病种上报记录查询")
    @ApiOperation(value = "本科室单病种上报记录查询", notes = "本科室单病种上报记录查询")
    @GetMapping(value = "/singleDiseaseByDeptList")
    public Result<QSingleDiseaseTakeByDoctorPageVo> singleDiseaseByDeptList(QSingleDiseaseTakeByDeptParam qSingleDiseaseTakeByDeptParam,
                                                                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<QSingleDiseaseTakeByDoctorPageVo> result = new Result<>();
        QSingleDiseaseTakeByDoctorPageVo list = qSingleDiseaseTakeService.singleDiseaseByDeptList(qSingleDiseaseTakeByDeptParam, pageNo, pageSize);
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
     * 全院单病种上报统计查询中-科室列表筛选条件
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
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Result<QSingleDiseaseTakeReportStatisticPageVo> result = new Result<>();
        //todo  加科室过滤
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
    public Result<List<QSingleDiseaseTakeReportStatisticDeptVo>> deptSingleDiseaseReportStatisticDept() {

        Result<List<QSingleDiseaseTakeReportStatisticDeptVo>> result = new Result<>();
        List<QSingleDiseaseTakeReportStatisticDeptVo> list = qSingleDiseaseTakeService.deptSingleDiseaseReportStatisticDept();
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
        Boolean flag = qSingleDiseaseTakeService.singleDiseaseStageAnswer(cookie,singleDiseaseAnswerParam);
        if(flag){
            result.setSuccess(false);
            result.setResult(flag);
        }else{
            result.setSuccess(false);
            result.setResult(flag);
        }
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
        Boolean flag = qSingleDiseaseTakeService.singleDiseaseAnswer(cookie,singleDiseaseAnswerParam);
        if(flag){
            result.setSuccess(false);
            result.setResult(flag);
        }else{
            result.setSuccess(false);
            result.setResult(flag);
        }
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
     * 全院单病种上报数量统计-查看图表-单病种上报数据概览
     */
    @AutoLog(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览")
    @ApiOperation(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览", notes = "全院单病种上报数量统计-查看图表-单病种上报数据概览")
    @GetMapping(value = "/allSingleDiseaseReportStatisticOverview")
    public Result<QSingleDiseaseTakeReportStatisticOverviewVo> allSingleDiseaseReportStatisticOverview(@Validated QSingleDiseaseTakeReportStatisticOverviewParam qSingleDiseaseTakeReportStatisticOverviewParam
                                                                                           /*@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize*/) {

        Result<QSingleDiseaseTakeReportStatisticOverviewVo> result = new Result<>();
        QSingleDiseaseTakeReportStatisticOverviewVo list = qSingleDiseaseTakeService.allSingleDiseaseReportStatisticOverview(qSingleDiseaseTakeReportStatisticOverviewParam);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }






    /*@AutoLog(value = "单病种总表-test")
    @ApiOperation(value = "单病种总表-test", notes = "单病种总表-test")
    @GetMapping(value = "/test")
    public Result<QSingleDiseaseTake> test() {
        Result<QSingleDiseaseTake> result = new Result<QSingleDiseaseTake>();
        try {
            List<QSingleDiseaseTake> list = qSingleDiseaseTakeService.list();
            for (int i = 0; i < list.size(); i++) {
                QSingleDiseaseTake l = list.get(i);
                String department = l.getDepartment();
                if ("儿科".equals(department)) {
                    l.setDepartmentId("07a3e1200a554dedac5b02bacb98b2e8");
                } else if ("外科".equals(department)) {
                    l.setDepartmentId("f6cf3a6e696f46f2a09dd764486c9f3e");
                } else if ("内科".equals(department)) {
                    l.setDepartmentId("61be392ea5984ce599547720814ebd30");
                } else if ("神经科".equals(department)) {
                    l.setDepartmentId("de92bf65d27743a38f05ee2e9472bc0b");
                } else {
                    log.error("-----------------");
                }
                *//*int i1 = RandomUtils.nextInt(0,100000);
                l.setInHospitalDay(i1);
                i1 = RandomUtils.nextInt(0,100000);
                l.setInHospitalFee(i1);
                i1 = RandomUtils.nextInt(0,100000);
                l.setDrugFee(i1);
                i1 = RandomUtils.nextInt(0,100000);
                l.setOperationTreatmentFee(i1);
                i1 = RandomUtils.nextInt(0,100000);
                l.setDisposableConsumable(i1);*//*
                qSingleDiseaseTakeService.updateById(l);
            }

            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }*/

    /**
     * 分页列表查询
     * @param qSingleDiseaseTake
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     *//*
	@AutoLog(value = "单病种总表-分页列表查询")
	@ApiOperation(value="单病种总表-分页列表查询", notes="单病种总表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<QSingleDiseaseTake>> queryPageList(QSingleDiseaseTake qSingleDiseaseTake,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<QSingleDiseaseTake>> result = new Result<IPage<QSingleDiseaseTake>>();
		QueryWrapper<QSingleDiseaseTake> queryWrapper = QueryGenerator.initQueryWrapper(qSingleDiseaseTake, req.getParameterMap());
		Page<QSingleDiseaseTake> page = new Page<QSingleDiseaseTake>(pageNo, pageSize);
		IPage<QSingleDiseaseTake> pageList = qSingleDiseaseTakeService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	*/

    /**
     * 添加
     *
     * @param qSingleDiseaseTake
     * @return
     *//*
    @AutoLog(value = "单病种总表-添加")
    @ApiOperation(value = "单病种总表-添加", notes = "单病种总表-添加")
    @PostMapping(value = "/add")
    public Result<QSingleDiseaseTake> add(*//*@RequestBody QSingleDiseaseTake qSingleDiseaseTake*//*) {
        Result<QSingleDiseaseTake> result = new Result<QSingleDiseaseTake>();
        try {
            List<QSingleDiseaseTake> list = qSingleDiseaseTakeService.list();
            for (int i = 0; i < list.size(); i++) {
                QSingleDiseaseTake l = list.get(i);
                l.setId(null);
                l.setPatientId("patientId1"+i);
                l.setPatientName("患者姓名1"+i);
                qSingleDiseaseTakeService.save(l*//*qSingleDiseaseTake*//*);
            }

            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    *//**
     *  编辑
     * @param qSingleDiseaseTake
     * @return
     *//*
	@AutoLog(value = "单病种总表-编辑")
	@ApiOperation(value="单病种总表-编辑", notes="单病种总表-编辑")
	@PutMapping(value = "/edit")
	public Result<QSingleDiseaseTake> edit(@RequestBody QSingleDiseaseTake qSingleDiseaseTake) {
		Result<QSingleDiseaseTake> result = new Result<QSingleDiseaseTake>();
		QSingleDiseaseTake qSingleDiseaseTakeEntity = qSingleDiseaseTakeService.getById(qSingleDiseaseTake.getId());
		if(qSingleDiseaseTakeEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = qSingleDiseaseTakeService.updateById(qSingleDiseaseTake);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}

	*//**
     *   通过id删除
     * @param id
     * @return
     *//*
	@AutoLog(value = "单病种总表-通过id删除")
	@ApiOperation(value="单病种总表-通过id删除", notes="单病种总表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<QSingleDiseaseTake> delete(@RequestParam(name="id",required=true) String id) {
		Result<QSingleDiseaseTake> result = new Result<QSingleDiseaseTake>();
		QSingleDiseaseTake qSingleDiseaseTake = qSingleDiseaseTakeService.getById(id);
		if(qSingleDiseaseTake==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = qSingleDiseaseTakeService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}

		return result;
	}

	*//**
     *  批量删除
     * @param ids
     * @return
     *//*
	@AutoLog(value = "单病种总表-批量删除")
	@ApiOperation(value="单病种总表-批量删除", notes="单病种总表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<QSingleDiseaseTake> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<QSingleDiseaseTake> result = new Result<QSingleDiseaseTake>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.qSingleDiseaseTakeService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

	*//**
     * 通过id查询
     * @param id
     * @return
     *//*
	@AutoLog(value = "单病种总表-通过id查询")
	@ApiOperation(value="单病种总表-通过id查询", notes="单病种总表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<QSingleDiseaseTake> queryById(@RequestParam(name="id",required=true) String id) {
		Result<QSingleDiseaseTake> result = new Result<QSingleDiseaseTake>();
		QSingleDiseaseTake qSingleDiseaseTake = qSingleDiseaseTakeService.getById(id);
		if(qSingleDiseaseTake==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(qSingleDiseaseTake);
			result.setSuccess(true);
		}
		return result;
	}

  *//**
     * 导出excel
     *
     * @param request
     * @param response
     *//*
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<QSingleDiseaseTake> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              QSingleDiseaseTake qSingleDiseaseTake = JSON.parseObject(deString, QSingleDiseaseTake.class);
              queryWrapper = QueryGenerator.initQueryWrapper(qSingleDiseaseTake, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<QSingleDiseaseTake> pageList = qSingleDiseaseTakeService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "单病种总表列表");
      mv.addObject(NormalExcelConstants.CLASS, QSingleDiseaseTake.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("单病种总表列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  *//**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     *//*
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<QSingleDiseaseTake> listQSingleDiseaseTakes = ExcelImportUtil.importExcel(file.getInputStream(), QSingleDiseaseTake.class, params);
              for (QSingleDiseaseTake qSingleDiseaseTakeExcel : listQSingleDiseaseTakes) {
                  qSingleDiseaseTakeService.save(qSingleDiseaseTakeExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listQSingleDiseaseTakes.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }*/

}
