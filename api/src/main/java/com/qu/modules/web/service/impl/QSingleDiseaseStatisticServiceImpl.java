package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.QSingleDiseaseStatistic;
import com.qu.modules.web.mapper.QSingleDiseaseStatisticMapper;
import com.qu.modules.web.service.IQSingleDiseaseStatisticService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        Date date = this.baseMapper.selectMinOutTime();
        DateTime dateTime = new DateTime(date);
        DateTime startOfDay = dateTime.withTimeAtStartOfDay();
        DateTime endDay = dateTime.plusMonths(1);





    }


}
