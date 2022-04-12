package com.qu.modules.web.service;

import com.qu.modules.web.entity.QSingleDiseaseStatistic;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 单病种统计表
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
public interface IQSingleDiseaseStatisticService extends IService<QSingleDiseaseStatistic> {

    void processData();

}
