package com.qu.task;

import com.qu.modules.web.service.IQSingleDiseaseStatisticDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 *
 *
 * @author: jesse
 * @createDate: 2021/6/28
 */
@Component
@Slf4j
public class QSingleDiseaseStatisticTask {

    @Autowired
    private IQSingleDiseaseStatisticDeptService qSingleDiseaseStatisticDeptService;


    // 每月15号凌晨十二点半执行
    @Scheduled(cron = "0 30 0 15 * ?")
//    @PostConstruct
    public void task() {
        String uuid= UUID.randomUUID().toString();
        log.info("{}",uuid);
        log.info("ScheduleTask start 单病种统计上个月数据：{}-----时间----->{}",uuid,new Date());
        qSingleDiseaseStatisticDeptService.statisticLastMonth();
        log.info("ScheduleTask end 单病种统计上个月数据：{}-----时间----->{}",uuid,new Date());
    }
}