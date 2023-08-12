package com.qu.modules.web.controller;

import com.qu.modules.web.service.IQSingleDiseaseTakeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/business/drgs")
public class QSingleDiseaseTakeController {
    @Autowired
    private IQSingleDiseaseTakeService qSingleDiseaseTakeService;


    /**
     * 读取燕达mongodb数据
     */
    @AutoLog(value = "读取燕达mongodb数据")
    @ApiOperation(value = "读取燕达mongodb数据", notes = "读取燕达mongodb数据")
    @GetMapping(value = "/readDrgsReportData")
    public String readDrgsReportData(){
        qSingleDiseaseTakeService.readQSingleDiseaseTake();
        return "success";
    }

}
