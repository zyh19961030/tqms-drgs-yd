package com.qu.modules.web.service.impl;

import cn.hutool.core.date.*;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.QuestionConstant;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.mapper.AnswerMapper;
import com.qu.modules.web.mapper.DynamicTableMapper;
import com.qu.modules.web.mapper.QSingleDiseaseTakeMapper;
import com.qu.modules.web.mapper.QuestionMapper;
import com.qu.modules.web.param.AdminPrivateParam;
import com.qu.modules.web.service.IAdminPrivateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdminPrivateServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAdminPrivateService {

    @Resource
    private DynamicTableMapper dynamicTableMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QSingleDiseaseTakeMapper qSingleDiseaseTakeMapper;

    private static String[] parsePatterns = new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"};

    @Override
    public boolean updateTableDate(AdminPrivateParam adminPrivateParam) {
        //查出来所有的单病种表
        LambdaQueryWrapper<Question> lambda = new QueryWrapper<Question>().lambda();
        lambda.eq(Question::getQuStatus, QuestionConstant.QU_STATUS_RELEASE);
        lambda.eq(Question::getCategoryType, QuestionConstant.CATEGORY_TYPE_SINGLE_DISEASE);
        lambda.eq(Question::getDel, QuestionConstant.DEL_NORMAL);
//        lambda.ge(Question::getId,98);
        List<Question> questionList = questionMapper.selectList(lambda);
        StringBuffer sqlSelect = new StringBuffer();
        StringBuffer sqlAns = new StringBuffer();
        boolean dateUpdateFlag = false;
        for (Question question : questionList) {
            if (question != null) {
                sqlSelect.append("select * from `");
                sqlSelect.append(question.getTableName());
                sqlSelect.append("`");

//                sqlSelect.append("select * from `drgs_cc` where summary_mapping_table_id ='1651194077516_94DE4BF8-1C1C-4AF5-AA85-C7088899DA7D'");
//                Map<String, String> map = dynamicTableMapper.selectDynamicTableColumn(sqlSelect.toString());
                List<Map<String, String>> dataList = dynamicTableMapper.selectDynamicTableColumnList(sqlSelect.toString());
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, String> map = dataList.get(i);
                    String summary_mapping_table_id = map.get("summary_mapping_table_id");
                    if (map == null) {
                        log.info("question.getTableName() data is null---questionId-------{},{},{}", question.getTableName(), question.getId(),summary_mapping_table_id);
                        continue;
                    }
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String k = entry.getKey();
                        String v = String.valueOf(entry.getValue());
                        if (v.contains("T") && v.contains("Z")) {
                            try {
                                DateTime parse = DateUtil.parse(v, parsePatterns);
                                DateTime offset = parse.offset(DateField.HOUR, 8);
                                map.put(k, offset.toString(DatePattern.NORM_DATETIME_PATTERN));
                                dateUpdateFlag = true;
                            } catch (DateException e) {
                                log.info("v.contains T ,but parse error--->{}", v);
                            } catch (Exception e) {
                                log.error("map for exception", e);
                            }

                        }

                    }

                    if (!dateUpdateFlag) {
                        log.info("not dateUpdateFlag , continue question.getTableName() data is null---questionId--summary_mapping_table_id-----{},{},{}", question.getTableName(), question.getId(),summary_mapping_table_id);
                        continue;
                    }

                    sqlAns.append("update `" + question.getTableName() + "` set ");
//                    sqlAns.append("update `drgs_cc` set ");

                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String k = entry.getKey();
                        String v = String.valueOf(entry.getValue());
                        if (k == null || v == null) {
                            log.info("map key or value is null");
                            continue;
                        }
                        sqlAns.append("`");
                        sqlAns.append(k);
                        sqlAns.append("`");
                        sqlAns.append("=");
                        sqlAns.append("'");
                        sqlAns.append(v);
                        sqlAns.append("'");
                        sqlAns.append(",");
                    }
                    sqlAns.delete(sqlAns.length() - 1, sqlAns.length());
                    sqlAns.append(" where summary_mapping_table_id = '");
                    sqlAns.append(summary_mapping_table_id);
                    sqlAns.append("'");
                    log.info("-----update sqlAns:{}", sqlAns.toString());
                    dynamicTableMapper.updateDynamicTable(sqlAns.toString());

                    QSingleDiseaseTake qSingleDiseaseTake = new QSingleDiseaseTake();
                    qSingleDiseaseTake.setAnswerJson(JSON.toJSONString(map));
                    LambdaUpdateWrapper<QSingleDiseaseTake> qSingleDiseaseTakeLambdaUpdateWrapper = new UpdateWrapper<QSingleDiseaseTake>().lambda();
                    qSingleDiseaseTakeLambdaUpdateWrapper.eq(QSingleDiseaseTake::getSummaryMappingTableId, summary_mapping_table_id);
                    qSingleDiseaseTakeMapper.update(qSingleDiseaseTake, qSingleDiseaseTakeLambdaUpdateWrapper);

                    dateUpdateFlag =false;
                    sqlAns.setLength(0);
                }
            }
            sqlSelect.setLength(0);
        }
        return true;
    }
}
