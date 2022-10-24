package com.qu.modules.web.controller.test;

import com.alibaba.fastjson.JSON;
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


    @ApiOperation(value = "给子表添加del", notes = "给子表添加del")
    @PostMapping(value = "/updateTableAddDel")
    public Result updateTableAddDel(@RequestBody AdminPrivateUpdateTableDrugFeeParam param) {
        if(!"f4r5".equals(param.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------updateTableAddDel={}", JSON.toJSONString(param));
        return adminPrivateService.updateTableAddDel(param);
    }

}
