package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.QSingleDiseaseStatisticHospital;
import com.qu.modules.web.mapper.QSingleDiseaseStatisticHospitalMapper;
import com.qu.modules.web.param.QSingleDiseaseTakeReportQuantityRankingParam;
import com.qu.modules.web.param.QSingleDiseaseTakeReportStatisticParam;
import com.qu.modules.web.param.QSingleDiseaseTakeStatisticAnalysisParam;
import com.qu.modules.web.service.IQSingleDiseaseStatisticHospitalService;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportQuantityRankingVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticPageVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeStatisticAnalysisVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 单病种统计院级表
 * @Author: jeecg-boot
 * @Date:   2022-04-21
 * @Version: V1.0
 */
@Service
public class QSingleDiseaseStatisticHospitalServiceImpl extends ServiceImpl<QSingleDiseaseStatisticHospitalMapper, QSingleDiseaseStatisticHospital> implements IQSingleDiseaseStatisticHospitalService {


    @Override
    public QSingleDiseaseTakeReportStatisticPageVo allSingleDiseaseReportStatistic(QSingleDiseaseTakeReportStatisticParam qSingleDiseaseTakeReportStatisticParam, Integer pageNo, Integer pageSize) {
        QSingleDiseaseTakeReportStatisticPageVo qSingleDiseaseTakeReportStatisticPageVo = new QSingleDiseaseTakeReportStatisticPageVo();
        Map<String, Object> params = new HashMap<>();
        params.put("startRow", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("categoryId", qSingleDiseaseTakeReportStatisticParam.getCategoryId());
        String dateStart = qSingleDiseaseTakeReportStatisticParam.getDateStart();
        String dateEnd = qSingleDiseaseTakeReportStatisticParam.getDateEnd();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM");
//        DateTime startDateTime = dateTimeFormatter.parseDateTime(dateStart);
//        startDateTime = startDateTime.dayOfMonth().withMinimumValue();
//        DateTime endDateTime = dateTimeFormatter.parseDateTime(dateEnd);
//        endDateTime = endDateTime.dayOfMonth().withMaximumValue().plusDays(1);

        params.put("dateStart", dateStart);
        params.put("dateEnd", dateEnd);
        Integer total = this.baseMapper.allSingleDiseaseReportStatisticCount(params);
        List<QSingleDiseaseTakeReportStatisticVo> allSingleDiseaseReportStatisticList = this.baseMapper.allSingleDiseaseReportStatistic(params);

        qSingleDiseaseTakeReportStatisticPageVo.setTotal(total);
        qSingleDiseaseTakeReportStatisticPageVo.setQSingleDiseaseTakeList(allSingleDiseaseReportStatisticList);
        return qSingleDiseaseTakeReportStatisticPageVo;
    }


//    {
//        Page<QSingleDiseaseStatisticHospital> page = new Page<>(pageNo, pageSize);
//        QSingleDiseaseTakeReportStatisticPageVo qSingleDiseaseTakeReportStatisticPageVo = new QSingleDiseaseTakeReportStatisticPageVo();
//        LambdaQueryWrapper<QSingleDiseaseStatisticHospital> lambda = new QueryWrapper<QSingleDiseaseStatisticHospital>().lambda();
//        String[] categoryIdList = qSingleDiseaseTakeReportStatisticParam.getCategoryId();
//        if(categoryIdList.length>0){
//            lambda.in(QSingleDiseaseStatisticHospital::getCategoryId,qSingleDiseaseTakeReportStatisticParam.getCategoryId());
//        }
//        lambda.ge(QSingleDiseaseStatisticHospital::getYearMonthRemark,qSingleDiseaseTakeReportStatisticParam.getDateStart());
//        lambda.le(QSingleDiseaseStatisticHospital::getYearMonthRemark,qSingleDiseaseTakeReportStatisticParam.getDateEnd());
//        lambda.groupBy()
//        IPage<QSingleDiseaseStatisticHospital> qSingleDiseaseStatisticHospitalIPage = this.page(page, lambda);
//        qSingleDiseaseTakeReportStatisticPageVo.setTotal(qSingleDiseaseStatisticHospitalIPage.getTotal());
//        qSingleDiseaseTakeReportStatisticPageVo.setQSingleDiseaseTakeList(qSingleDiseaseStatisticHospitalIPage.getRecords());
//        return qSingleDiseaseTakeReportStatisticPageVo;
//    }

    @Override
    public List<QSingleDiseaseTakeReportQuantityRankingVo> singleDiseaseReportQuantityRanking(QSingleDiseaseTakeReportQuantityRankingParam qSingleDiseaseTakeReportQuantityRankingParam) {
        return this.baseMapper.singleDiseaseReportQuantityRanking(qSingleDiseaseTakeReportQuantityRankingParam);
    }

    @Override
    public List<QSingleDiseaseTakeReportQuantityRankingVo> singleDiseaseReportWriteRanking(QSingleDiseaseTakeReportQuantityRankingParam qSingleDiseaseTakeReportQuantityRankingParam) {
        return this.baseMapper.singleDiseaseReportQuantityRanking(qSingleDiseaseTakeReportQuantityRankingParam);
    }

    @Override
    public List<QSingleDiseaseTakeStatisticAnalysisVo> singleDiseaseStatisticAnalysis(QSingleDiseaseTakeStatisticAnalysisParam qSingleDiseaseTakeStatisticAnalysisParam) {
        LambdaQueryWrapper<QSingleDiseaseStatisticHospital> lambda = new QueryWrapper<QSingleDiseaseStatisticHospital>().lambda();
        lambda.in(QSingleDiseaseStatisticHospital::getCategoryId,qSingleDiseaseTakeStatisticAnalysisParam.getCategoryId());
        lambda.ge(QSingleDiseaseStatisticHospital::getYearMonthRemark,qSingleDiseaseTakeStatisticAnalysisParam.getDateStart());
        lambda.le(QSingleDiseaseStatisticHospital::getYearMonthRemark,qSingleDiseaseTakeStatisticAnalysisParam.getDateEnd());
        List<QSingleDiseaseStatisticHospital> qSingleDiseaseStatisticHospitalList = this.baseMapper.selectList(lambda);
        List<QSingleDiseaseTakeStatisticAnalysisVo> qSingleDiseaseTakeStatisticAnalysisVoList = qSingleDiseaseStatisticHospitalList.stream().map(e -> QSingleDiseaseTakeStatisticAnalysisVo.builder()
                .categoryId(e.getCategoryId()).yearMonth(e.getYearMonthTitle())
                .completeReportCountryCount(e.getCompleteReportCount()).averageInHospitalDay(new BigDecimal(e.getAverageInHospitalDay()))
                .averageInHospitalFee(new BigDecimal(e.getAverageInHospitalFee())).mortality(e.getMortality()).complicationRate(e.getOperationComplicationRate()).build())
            .collect(Collectors.toList());

        return qSingleDiseaseTakeStatisticAnalysisVoList;
    }




}
