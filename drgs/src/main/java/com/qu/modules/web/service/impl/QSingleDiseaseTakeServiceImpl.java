package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.*;
import com.qu.modules.web.mapper.*;
import com.qu.modules.web.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


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
        //获取视图中6天前上报的单病种数据集合
        String time_6 = formatter.format(date_6);
        calendar.add(Calendar.DATE, -1);
        Date date_7 = calendar.getTime();
        //获取视图中7天前上报的单病种数据集合
        String time_7 = formatter.format(date_7);
//        time_6 = "2023-07-09 00:01:00";
//        time_7 = "2019-01-01 00:01:00";
        log.info("读取数据的上报日期区间为："+time_7+" ——> "+time_6);
        //获取单病种数据
        List<DrgsReportData> drgsReportDatas = mongoTemplate.find(new Query(new Criteria().andOperator(Criteria.where("reportTime").gt(time_7),Criteria.where("reportTime").lte(time_6)).orOperator(Criteria.where("innerStatus").is(5),Criteria.where("innerStatus").is(6),Criteria.where("innerStatus").is(16))), DrgsReportData.class);
//        List<DrgsReportData> drgsReportDatas = mongoTemplate.find(new Query(Criteria.where("reportTime").gte(time_7)), DrgsReportData.class);
        for (DrgsReportData drgsReportData : drgsReportDatas) {
            Map<String, Object> reportContent = drgsReportData.getReportContent();
            //处理子表表名
            String diseaseType = reportContent.get("diseaseType").toString();
            if ("Cap-Adult".equals(diseaseType)) {
                diseaseType = "CapAdult";
            }
            //子表数据
            Map<String, Object> data;
            Map<String, Object> rawData;
            Map<String, Object> funcResult;
            //合并三份子表数据
            Map<String, Object> map = new HashMap<>();
            if (reportContent.get("rawData") != null) {
                rawData = (Map<String, Object>)reportContent.get("rawData");
                Set<String> rawDataKey = rawData.keySet();
                for (String key: rawDataKey) {
                    String value = rawData.get(key) + "";
                    if (!"".equals(value) && value.length() > 0) {
                        map.put(key, value);
                    }
                }
            }
            if (drgsReportData.getFuncResult() != null) {
                funcResult = (Map<String, Object>)drgsReportData.getFuncResult();
                Set<String> funcResultKey = funcResult.keySet();
                for (String key: funcResultKey) {
                    String value = funcResult.get(key) + "";
                    if (!"".equals(value) && value.length() > 0) {
                        map.put(key, value);
                    }
                }
            }
            //以data中数据为准
            if (reportContent.get("data") != null) {
                data = (Map<String, Object>)reportContent.get("data");
                Set<String> dataKey = data.keySet();
                for (String key: dataKey) {
                    String value = data.get(key) + "";
                    if (!"".equals(value) && value.length() > 0) {
                        map.put(key, value);
                    }
                }
            }
            Map<String, String> dataNew = new HashMap<>();
            if (!map.isEmpty()) {
                //患者姓名
                map.put("xm", drgsReportData.getPatientName());
                //获取TQMS中单病种子表的字段
                List<String> columnNameList = qsubjectMapper.querySubColumnNameByTableName("DRGS_" + diseaseType);
                //只解析存在于TQMS子表字段中的数据
                Set<String> mapKey = map.keySet();
                for (String key: mapKey) {
                    String value = map.get(key)+"";
                    if (columnNameList.contains(key) && !"".equals(value) && value.length() > 0){
                        //表字段加``
                        dataNew.put("`"+key+"`", value);
                    }
                }
                //填报时间
                String reportTime = drgsReportData.getReportTime();
                dataNew.put("answer_datetime", reportTime);
                //主表id
                String id = drgsReportData.getId();
                dataNew.put("summary_mapping_table_id", id);
            }
            //插入子表数据
            if (!dataNew.isEmpty()) {
                try {
                    genTableColumnMapper.insertQSingleDiseaseTake(dataNew, "drgs_"+diseaseType);
                    log.info("添加单病种子表drgs_"+diseaseType.toLowerCase()+"数据。caseId="+dataNew.get("`caseId`"));
                } catch (Exception e) {
                    //可能存在表字段超长问题，日志输出子表表名和mongodb中caseId
                    log.error(e.getMessage()+drgsReportData.getDiseaseTypes()+"~~"+drgsReportData.getCaseId()+"!!!!!", e);
                }
            }
        }
    }

}
