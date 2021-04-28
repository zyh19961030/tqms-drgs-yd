package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticDeptVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeReportStatisticVo;
import com.qu.modules.web.vo.QSingleDiseaseTakeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date: 2021-04-02
 * @Version: V1.0
 */
public interface QSingleDiseaseTakeMapper extends BaseMapper<QSingleDiseaseTake> {

    List<QSingleDiseaseTakeVo> singleDiseaseList(@Param("name") String name);

    Integer allSingleDiseaseReportStatisticCount(Map<String, Object> params);

//        List<Map<String,Object>> allSingleDiseaseReportStatistic(Map<String, Object> params);
    List<QSingleDiseaseTakeReportStatisticVo> allSingleDiseaseReportStatistic(Map<String, Object> params);

    Integer countSql(Map<String, Object> params);

    Map<String, Object> countAvgSql(Map<String, Object> countParams);

    List<QSingleDiseaseTakeReportStatisticDeptVo> selectDept();

}