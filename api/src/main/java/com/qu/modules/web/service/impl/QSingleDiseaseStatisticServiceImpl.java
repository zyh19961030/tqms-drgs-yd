package com.qu.modules.web.service.impl;

import com.qu.modules.web.entity.QSingleDiseaseStatistic;
import com.qu.modules.web.mapper.QSingleDiseaseStatisticMapper;
import com.qu.modules.web.service.IQSingleDiseaseStatisticService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 单病种统计表
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
@Service
public class QSingleDiseaseStatisticServiceImpl extends ServiceImpl<QSingleDiseaseStatisticMapper, QSingleDiseaseStatistic> implements IQSingleDiseaseStatisticService {

    @Override
    public void processData() {

    }


}
