package com.qu.modules.web.controller.test;

import com.alibaba.fastjson.JSON;
import com.qu.modules.web.param.AdminPrivateParam;
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

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "后台API")
@RestController
@RequestMapping("/j/admin")
public class AdminPrivateController {

    @Autowired
    private IAdminPrivateService adminPrivateService;


    @ApiOperation(value = "修改", notes = "修改")
    @PostMapping(value = "/updateTableDate")
    public Result updateTableDate(@RequestBody AdminPrivateParam adminPrivateParam, HttpServletRequest request) {
        if(!"a1q2".equals(adminPrivateParam.getName())){
            return ResultFactory.fail();
        }
        log.info("-----------answerParam={}", JSON.toJSONString(adminPrivateParam));
        boolean flag = adminPrivateService.updateTableDate(adminPrivateParam);
        if(flag){
            return  ResultFactory.success();
        }
        return  ResultFactory.fail("失败");
    }


}
