package com.qu.modules.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.qu.constant.Constant;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.param.AnswerPatientSubmitParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.IAnswerService;
import com.qu.modules.web.vo.AnswerPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInAndSubmitPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "答案")
@RestController
@RequestMapping("/business/answer")
public class AnswerController {

    @Autowired
    private IAnswerService answerService;

//    @ApiOperation(value = "创建动态表", notes = "创建动态表")
//    @GetMapping(value = "/createDynamicTable")
//    public Result<Integer> createDynamicTable() {
//        Result<Integer> result = new Result<Integer>();
//        StringBuffer sql = new StringBuffer();
//        sql.append("CREATE TABLE `abc` (");
//        sql.append("`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',");
//        sql.append("`aaa` varchar(200),");
//        sql.append(" PRIMARY KEY (`id`)");
//        sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='aaa';");
//        answerService.createDynamicTable(sql.toString());
//        result.setSuccess(true);
//        result.setResult(1);
//        return result;
//    }
//
//    @ApiOperation(value = "insert table", notes = "insert table")
//    @GetMapping(value = "/insertDynamicTable")
//    public Result<Integer> insertDynamicTable() {
//        Result<Integer> result = new Result<Integer>();
//        StringBuffer sql = new StringBuffer();
//        sql.append("insert into abc (aaa) values  ('123654');");
//        answerService.insertDynamicTable(sql.toString());
//        result.setSuccess(true);
//        result.setResult(1);
//        return result;
//    }

    @ApiOperation(value = "答题", notes = "答题")
    @PostMapping(value = "/answer")
    public Result answer(@RequestBody AnswerParam answerParam, HttpServletRequest request) {
        String token = request.getHeader("token");
        String cookie = "JSESSIONID=" + token;
        log.info("-----------answerParam={}", JSON.toJSONString(answerParam));
        return answerService.answer(cookie, answerParam);
    }

    @ApiOperation(value = "答题回显-废弃", notes = "答题回显-废弃")
    @GetMapping(value = "/queryByQuId")
    public Result<String> queryByQuId(@RequestParam Integer quId) {
        Result<String> result = new Result<String>();
        String answer = answerService.queryByQuId(quId);
        result.setSuccess(true);
        result.setResult(answer);
        return result;
    }

    @ApiOperation(value = "问卷填报记录分页列表", notes = "问卷填报记录分页列表")
    @GetMapping(value = "/questionFillInList")
    public Result<AnswerPageVo> questionFillInList(@RequestParam(name = "quName", required = false) String quName,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        Result<AnswerPageVo> result = new Result<AnswerPageVo>();
        AnswerPageVo answerPageVo = answerService.questionFillInList(quName,pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(answerPageVo);
        return result;
    }

    @ApiOperation(value = "问卷填报记录-通过id查询", notes = "问卷填报记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<Answer> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<Answer> result = new Result<Answer>();
        Answer answer = answerService.getById(id);
        if (answer == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(answer);
            result.setSuccess(true);
        }
        return result;
    }

    @ApiOperation(value = "撤回编辑", notes = "撤回编辑")
    @GetMapping(value = "/withdrawEdit")
    public Result withdrawEdit(@RequestParam Integer id) {
        boolean b = answerService.withdrawEdit(id);
        if(b){
            return ResultFactory.success();
        }else{
            return ResultFactory.fail("操作失败！");
        }
    }

    /**
     * 患者登记表-填报中
     */
    @AutoLog(value = "患者登记表-填报中")
    @ApiOperation(value = "患者登记表-填报中", notes = "患者登记表-填报中")
    @GetMapping(value = "/patientFillingInList")
    public Result<AnswerPatientFillingInAndSubmitPageVo> patientFillingInList(HttpServletRequest request,
                                                                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<AnswerPatientFillingInAndSubmitPageVo> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getDeps().get(0).getId();
        AnswerPatientFillingInAndSubmitPageVo list = answerService.patientFillingInList(deptId, pageNo,pageSize );
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 患者登记表-已提交
     */
    @AutoLog(value = "患者登记表-已提交")
    @ApiOperation(value = "患者登记表-已提交", notes = "患者登记表-已提交")
    @GetMapping(value = "/patientSubmitList")
    public Result<AnswerPatientFillingInAndSubmitPageVo> patientSubmitList(AnswerPatientSubmitParam answerPatientSubmitParam, HttpServletRequest request,
                                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<AnswerPatientFillingInAndSubmitPageVo> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getDeps().get(0).getId();
        AnswerPatientFillingInAndSubmitPageVo list = answerService.patientSubmitList(deptId,answerPatientSubmitParam,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 菜单月度汇总传，定期汇总传-填报中
     */
    @AutoLog(value = "菜单月度汇总传，定期汇总传-填报中")
    @ApiOperation(value = "菜单月度汇总传，定期汇总传-填报中", notes = "菜单月度汇总传，定期汇总传-填报中")
    @GetMapping(value = "/monthQuarterYearList")
    public Result<AnswerPatientFillingInAndSubmitPageVo> monthQuarterYearList(HttpServletRequest request,
                                                                              @RequestParam(name = "type")@ApiParam("菜单月度汇总传0，定期汇总传1") String type,
                                                                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<AnswerPatientFillingInAndSubmitPageVo> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getDeps().get(0).getId();
        AnswerPatientFillingInAndSubmitPageVo list = answerService.monthQuarterYearList(deptId,type, pageNo,pageSize );
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }


}
