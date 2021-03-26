package com.qu.config;

/**
 * 应用启动是初始化码表数据到Redis
 *
 * @author:闫润杰 2021-03-18 14:00:04
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RedisRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Boolean flag = true;
        if (flag) {
            log.info("----initRedis success!");
        } else {
            log.info("----initRedis fail!");
        }
    }
}
