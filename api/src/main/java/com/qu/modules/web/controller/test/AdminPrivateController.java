package com.qu.modules.web.controller.test;

import com.alibaba.fastjson.JSON;
import com.qu.modules.web.param.AdminPrivateUpdateAnswerCheckAllTableParam;
import com.qu.modules.web.param.AdminPrivateUpdateTableDrugFeeParam;
import com.qu.modules.web.service.IAdminPrivateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "后台API")
@RestController
@RequestMapping("/j/admin")
public class AdminPrivateController {

    @Autowired
    private IAdminPrivateService adminPrivateService;


//    @ApiOperation(value = "修改题目时间TZ", notes = "修改题目时间TZ")
//    @PostMapping(value = "/updateTableDate")
//    public Result updateTableDate(@RequestBody AdminPrivateParam adminPrivateParam, HttpServletRequest request) {
//        if(!"a1q2".equals(adminPrivateParam.getName())){
//            return ResultFactory.fail();
//        }
//        log.info("-----------updateTableDate={}", JSON.toJSONString(adminPrivateParam));
//        boolean flag = adminPrivateService.updateTableDate(adminPrivateParam);
//        if(flag){
//            return  ResultFactory.success();
//        }
//        return  ResultFactory.fail("失败");
//    }
//
//    @ApiOperation(value = "处理药品费用", notes = "处理药品费用")
//    @PostMapping(value = "/updateTableDrugFee")
//    public Result updateTableDrugFee(@RequestBody AdminPrivateUpdateTableDrugFeeParam adminPrivateUpdateTableDrugFeeParam) {
//        if(!"s3w2".equals(adminPrivateUpdateTableDrugFeeParam.getName())){
//            return ResultFactory.fail();
//        }
//        log.info("-----------updateTableDrugFee={}", JSON.toJSONString(adminPrivateUpdateTableDrugFeeParam));
//        boolean flag = adminPrivateService.updateTableDrugFee(adminPrivateUpdateTableDrugFeeParam);
//        if(flag){
//            return  ResultFactory.success();
//        }
//        return  ResultFactory.fail("失败");
//    }
//
//
//    @ApiOperation(value = "更新选项分值", notes = "更新选项分值")
//    @PostMapping(value = "/updateOptionValue")
//    public Result updateOptionValue(@RequestBody AdminPrivateUpdateOptionValueParam adminPrivateUpdateOptionValueParam) {
//        if(!"d3e4".equals(adminPrivateUpdateOptionValueParam.getName())){
//            return ResultFactory.fail();
//        }
//        log.info("-----------updateTableDate={}", JSON.toJSONString(adminPrivateUpdateOptionValueParam));
//        return adminPrivateService.updateOptionValue(adminPrivateUpdateOptionValueParam);
//    }


//    @ApiOperation(value = "给子表添加del", notes = "给子表添加del")
//    @PostMapping(value = "/updateTableAddDel")
//    public Result updateTableAddDel(@RequestBody AdminPrivateUpdateTableAddDelFeeParam param) {
//        if(!"f4r5".equals(param.getName())){
//            return ResultFactory.fail();
//        }
//        log.info("-----------updateTableAddDel={}", JSON.toJSONString(param));
//        return adminPrivateService.updateTableAddDel(param);
//    }


    @ApiOperation(value = "给检查表子表添加answerStatus", notes = "给检查表子表添加answerStatus")
    @PostMapping(value = "/updateTableAddAnswerStatus")
    public Result updateTableAddAnswerStatus(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"f4r6".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateTableAddAnswerStatus={}", JSON.toJSONString(param));
        return adminPrivateService.updateTableAddAnswerStatus(param);
    }

    @ApiOperation(value = "给检查表子表answerStatus赋值", notes = "给检查表子表answerStatus赋值")
    @PostMapping(value = "/updateAnswerStatus")
    public Result updateAnswerStatus(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"f4r6".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateAnswerStatus={}", JSON.toJSONString(param));
        return adminPrivateService.updateAnswerStatus(param);
    }

    @ApiOperation(value = "给普通问卷和单病种子表添加answerStatus", notes = "给普通问卷和单病种子表添加answerStatus")
    @PostMapping(value = "/updateQuestionTableAddAnswerStatus")
    public Result updateQuestionTableAddAnswerStatus(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"f4r1".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateQuestionTableAddAnswerStatus={}", JSON.toJSONString(param));
        return adminPrivateService.updateQuestionTableAddAnswerStatus(param);
    }

    @ApiOperation(value = "给普通问卷子表answerStatus赋值", notes = "给普通问卷子表answerStatus赋值")
    @PostMapping(value = "/updateQuestionAnswerStatus")
    public Result updateQuestionAnswerStatus(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"f4r1".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateQuestionAnswerStatus={}", JSON.toJSONString(param));
        return adminPrivateService.updateQuestionAnswerStatus(param);
    }

    @ApiOperation(value = "给单病种问卷子表answerStatus赋值", notes = "给单病种问卷子表answerStatus赋值")
    @PostMapping(value = "/updateQSingleDiseaseTakeAnswerStatus")
    public Result updateQSingleDiseaseTakeAnswerStatus(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"f4r1".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateQSingleDiseaseTakeAnswerStatus={}", JSON.toJSONString(param));
        return adminPrivateService.updateQSingleDiseaseTakeAnswerStatus(param);
    }




    @ApiOperation(value = "给查检表总表caseId的历史数据赋值", notes = "给查检表总表caseId的历史数据赋值")
    @PostMapping(value = "/updateAnswerCheckCaseId")
    public Result updateAnswerCheckCaseId(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"ax2".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateAnswerCheckCaseId={}", JSON.toJSONString(param));
        return adminPrivateService.updateAnswerCheckCaseId(param);
    }

    @ApiOperation(value = "给answer总表outTime的历史数据赋值", notes = "给answer总表outTime的历史数据赋值")
    @PostMapping(value = "/updateAnswerOutTime")
    public Result updateAnswerOutTime(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"z1c2".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateAnswerOutTime={}", JSON.toJSONString(param));
        return adminPrivateService.updateAnswerOutTime(param);
    }

    @ApiOperation(value = "给已发布所有检查表添加痕迹", notes = "给已发布所有检查表添加痕迹")
    @PostMapping(value = "/updateTableAddMark")
    public Result updateTableAddMark(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"f4r1".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateTableAddDel={}", JSON.toJSONString(param));
        return adminPrivateService.updateTableAddMark(param);
    }


    @ApiOperation(value = "处理历史检查表统计明细数据已提交数据", notes = "处理历史检查表统计明细数据已提交数据")
    @PostMapping(value = "/updateAnswerCheckStatisticDetail")
    public Result updateAnswerCheckStatisticDetail(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"y1r3".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateAnswerCheckStatisticDetail={}", JSON.toJSONString(param));
        return adminPrivateService.updateAnswerCheckStatisticDetail(param);
    }

    @ApiOperation(value = "处理检查表总表与子表数据不一致问题,quId不传查所有问卷", notes = "处理检查表总表与子表数据不一致问题,quId不传查所有问卷")
    @PostMapping(value = "/updateAnswerCheckAllTable")
    public Result updateAnswerCheckAllTable(@RequestBody AdminPrivateUpdateAnswerCheckAllTableParam param) {
        if(!"r5t8".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateAnswerCheckAllTable={}", JSON.toJSONString(param));
        return adminPrivateService.updateAnswerCheckAllTable(param,false);
    }


    @ApiOperation(value = "处理检查表总表与子表数据不一致问题,quId不传查所有问卷_带填报人的", notes = "处理检查表总表与子表数据不一致问题,quId不传查所有问卷_带填报人的")
    @PostMapping(value = "/updateAnswerCheckAllTableAndTBR")
    public Result updateAnswerCheckAllTableAndTBR(@RequestBody AdminPrivateUpdateAnswerCheckAllTableParam param) {
        if(!"r59y".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateAnswerCheckAllTable={}", JSON.toJSONString(param));
        return adminPrivateService.updateAnswerCheckAllTable(param,true);
    }


    @ApiOperation(value = "处理查检表统计明细表没有数据问题,子表有数据但统计表没有问题,quId不传查所有问卷", notes = "处理查检表统计明细表没有数据问题,子表有数据但统计表没有问题,quId不传查所有问卷")
    @PostMapping(value = "/updateAnswerCheckStatisticDetailBySubtable")
    public Result updateAnswerCheckStatisticDetailBySubtable(@RequestBody AdminPrivateUpdateAnswerCheckAllTableParam param) {
        if(!"r5t2".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateAnswerCheckStatisticDetailBySubtable={}", JSON.toJSONString(param));
        return adminPrivateService.updateAnswerCheckStatisticDetailBySubtable(param);
    }


    @ApiOperation(value = "查询所有问卷子表数据是否缺失并自动添加缺少字段,quId不传查所有问卷", notes = "查询所有问卷子表数据是否缺失并自动添加缺少字段,quId不传查所有问卷")
    @PostMapping(value = "/selectQuestionAllTable")
    public Result selectQuestionAllTable(@RequestBody AdminPrivateUpdateAnswerCheckAllTableParam param) {
        if(!"r5z7".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------selectQuestionAllTable={}", JSON.toJSONString(param));
        return adminPrivateService.selectQuestionAllTable(param);
    }


    @ApiOperation(value = "查询所有问卷题目增加默认(填报人、填报科室名称、填报科室代码)三道题", notes = "查询所有问卷题目增加默认(填报人、填报科室名称、填报科室代码)三道题")
    @PostMapping(value = "/addQuestionSubject")
    public Result addQuestionSubject(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"r9u7".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------addQuestionSubject={}", JSON.toJSONString(param));
        return adminPrivateService.addQuestionSubject(param);
    }

    @ApiOperation(value = "给answerCheck总表passStatus的历史数据赋值", notes = "给answerCheck总表passStatus的历史数据赋值")
    @PostMapping(value = "/updateAnswerCheckPassStatus")
    public Result updateAnswerCheckPassStatus(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"z1x8".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateAnswerOutTime={}", JSON.toJSONString(param));
        return adminPrivateService.updateAnswerCheckPassStatus(param);
    }

}
