package com.qu.modules.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qu.constant.*;
import com.qu.modules.web.entity.*;
import com.qu.modules.web.mapper.*;
import com.qu.modules.web.param.*;
import com.qu.modules.web.pojo.JsonRootBean;
import com.qu.modules.web.service.*;
import com.qu.modules.web.vo.*;
import com.qu.util.*;
import com.qu.util.HttpTools.HttpData;
import com.qu.util.HttpTools.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.jeecg.common.util.UUIDGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date: 2021-04-02
 * @Version: V1.0
 */
@Slf4j
@Service
public class QSingleDiseaseTakeServiceImpl extends ServiceImpl<QSingleDiseaseTakeMapper, QSingleDiseaseTake> implements IQSingleDiseaseTakeService {

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private QsubjectMapper qsubjectMapper;

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public void readQSingleDiseaseTake() {
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -5);
        Date date_6 = calendar.getTime();
        String time_6 = formatter.format(date_6);
        calendar.add(Calendar.DATE, -1);
        Date date_7 = calendar.getTime();
        String time_7 = formatter.format(date_7);
        //获取视图中6天前上报的单病种数据集合
//        time_6 = "2019-01-01 00:00:00";
//        time_7 = "2023-06-09 00:00:00";
        //获取单病种数据
        List<DrgsReportData> drgsReportDatas = mongoTemplate.find(new Query(new Criteria().andOperator(Criteria.where("reportTime").gt(time_6),Criteria.where("reportTime").lte(time_7))), DrgsReportData.class);
//        List<DrgsReportData> drgsReportDatas = mongoTemplate.find(new Query(Criteria.where("reportTime").gte(time_6)), DrgsReportData.class);
        for (DrgsReportData drgsReportData : drgsReportDatas) {
            Map<String, Object> reportContent = drgsReportData.getReportContent();
            //子表表名
            String diseaseType = reportContent.get("diseaseType").toString();
            if ("Cap-Adult".equals(diseaseType)) {
                diseaseType = "CapAdult";
            }
            //子表数据
            Map<String, Object> data = (Map<String, Object>)reportContent.get("data");
            //合并三份子表数据
            Map<String, Object> rawData;
            if (reportContent.get("rawData") != null) {
                rawData = (Map<String, Object>)reportContent.get("rawData");
                Set<String> rawDataKey = rawData.keySet();
                for (String key: rawDataKey) {
                    String value = rawData.get(key) + "";
                    if (!"".equals(value) && value.length() > 0) {
                        data.put(key, value);
                    }
                }
            }
            Map<String, Object> funcResult;
            if (drgsReportData.getFuncResult() != null) {
                funcResult = (Map<String, Object>)drgsReportData.getFuncResult();
                Set<String> funcResultKey = funcResult.keySet();
                for (String key: funcResultKey) {
                    String value = funcResult.get(key) + "";
                    if (!"".equals(value) && value.length() > 0) {
                        data.put(key, value);
                    }
                }
            }
            Map<String, String> dataNew = new HashMap<>();
            if (!data.isEmpty()) {
                //患者姓名
                data.put("xm", drgsReportData.getPatientName());
                //填报时间
                data.put("answer_datetime", drgsReportData.getReportTime());
                //主表id
                String id = drgsReportData.getId();
                data.put("summary_mapping_table_id", id);

                //获取TQMS中单病种子表的字段
                List<String> columnNameList = qsubjectMapper.querySubColumnNameByTableName("DRGS_" + diseaseType);
                //只解析存在于TQMS子表字段中的数据
                Set<String> dataKey = data.keySet();
                for (String key: dataKey) {
                    String value = data.get(key)+"";
                    if (columnNameList.contains(key) && !"".equals(value) && value.length() > 0){
                        //表字段加``
                        dataNew.put("`"+key+"`", value);
                    }
                }
            }
            //插入子表数据
            if (!dataNew.isEmpty()) {
                try {
                    genTableColumnMapper.insertQSingleDiseaseTake(dataNew, "drgs_"+diseaseType);
                    log.info("添加单病种子表drgs_"+diseaseType.toLowerCase()+"数据。");
                } catch (Exception e) {
                    //可能存在表字段超长问题，日志输出子表表名和mongodb中caseId
                    log.error(e.getMessage()+drgsReportData.getDiseaseTypes()+"~~"+drgsReportData.getCaseId()+"!!!!!", e);
                }
            }
        }
    }

}
