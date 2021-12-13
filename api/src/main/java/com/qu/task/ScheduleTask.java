package com.qu.task;

import com.qu.modules.web.service.IQSingleDiseaseTakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 *
 *
 * @author: jesse
 * @createDate: 2021/6/28
 */
@Component
@Slf4j
public class ScheduleTask {

    @Autowired
    private IQSingleDiseaseTakeService qSingleDiseaseTakeService;

    // 每天凌晨1点执行
//    @Scheduled(cron = "0 0 1 * * ?")
//    @PostConstruct
    public void task() {
        qSingleDiseaseTakeService.runSingleDiseaseTakeReport();
        log.info("每天凌晨1点执行一次单病种上报：" + new Date());
    }
}