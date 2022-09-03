package com.qu.modules.web.service.impl;

import cn.hutool.core.date.*;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.qu.constant.QoptionConstant;
import com.qu.constant.QsubjectConstant;
import com.qu.constant.QuestionConstant;
import com.qu.modules.web.entity.*;
import com.qu.modules.web.mapper.*;
import com.qu.modules.web.param.AdminPrivateParam;
import com.qu.modules.web.param.AdminPrivateUpdateOptionValueParam;
import com.qu.modules.web.param.AdminPrivateUpdateTableDrugFeeParam;
import com.qu.modules.web.service.IAdminPrivateService;
import com.qu.modules.web.service.IOptionService;
import com.qu.util.PriceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminPrivateServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAdminPrivateService {

    @Resource
    private DynamicTableMapper dynamicTableMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QsubjectMapper qsubjectMapper;

    @Resource
    private OptionMapper optionMapper;
    @Autowired
    private IOptionService optionService;

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


    @Override
    public boolean updateTableDrugFee(AdminPrivateUpdateTableDrugFeeParam adminPrivateUpdateTableDrugFeeParam) {
        //查出来所有的单病种表
        LambdaQueryWrapper<QSingleDiseaseTake> lambda = new QueryWrapper<QSingleDiseaseTake>().lambda();
        List<QSingleDiseaseTake> qSingleDiseaseTakeList = qSingleDiseaseTakeMapper.selectList(lambda);
        StringBuilder sqlSelect = new StringBuilder();
        boolean dateUpdateFlag = false;
        for (QSingleDiseaseTake qSingleDiseaseTake : qSingleDiseaseTakeList) {
            sqlSelect.setLength(0);
            if (qSingleDiseaseTake != null) {
                String dynamicTableName = qSingleDiseaseTake.getDynamicTableName();
                String summaryMappingTableId = qSingleDiseaseTake.getSummaryMappingTableId();
                if(StringUtils.isAnyBlank(dynamicTableName,summaryMappingTableId)){
                    log.info("continue qSingleDiseaseTake.dynamicTableName or summaryMappingTableId is null---qSingleDiseaseTakeId-------{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
                    continue;
                }
                sqlSelect.append("select * from `");
                sqlSelect.append(dynamicTableName);
                sqlSelect.append("`");
                sqlSelect.append(" where summary_mapping_table_id = '");
                sqlSelect.append(summaryMappingTableId);
                sqlSelect.append("'");
                Map<String,String> map = dynamicTableMapper.selectDynamicTableColumn(sqlSelect.toString());
                if (map == null || map.isEmpty()) {
                    log.info("continue sqlAns map is null---qSingleDiseaseTakeId-------{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
                    continue;
                }

                //住院天数
//                String inHospitalDayString = map.get("CM-4-1");
//                if(StringUtils.isNotBlank(inHospitalDayString)){
//                    if(inHospitalDayString.contains("天")){
//                        inHospitalDayString = inHospitalDayString.replaceAll("天","");
//                    }
//                    qSingleDiseaseTake.setInHospitalDay(Integer.parseInt(inHospitalDayString));
//                }
//
//                String inHospitalFee = map.get("CM-6-1");
//                if(StringUtils.isNotBlank(inHospitalFee)){
//                    qSingleDiseaseTake.setInHospitalFee(inHospitalFee);
//                }
//
//                String operationTreatmentFeeString = map.get("CM-6-13");
//                if(StringUtils.isNotBlank(operationTreatmentFeeString)){
//                    qSingleDiseaseTake.setOperationTreatmentFee(operationTreatmentFeeString);
//                }
//
//                String operationDisposableMaterialFeeString = map.get("CM-6-29");
//                if(StringUtils.isBlank(operationDisposableMaterialFeeString)){
//                    log.info("continue CM-6-29 is null---qSingleDiseaseTakeId-------{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
//                    operationDisposableMaterialFeeString="0";
//                }
//                Integer operationDisposableMaterialFee= PriceUtil.toFenInt(new BigDecimal(operationDisposableMaterialFeeString));
//                String examinationDisposableMaterialFeeString = map.get("CM-6-27");
//                if(StringUtils.isBlank(examinationDisposableMaterialFeeString)){
//                    log.info("continue CM-6-27 is null---qSingleDiseaseTakeId-------{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
//                    examinationDisposableMaterialFeeString="0";
//                }
//                Integer examinationDisposableMaterialFee = PriceUtil.toFenInt(new BigDecimal(examinationDisposableMaterialFeeString));
//                String treatmentDisposableMaterialFeeString = map.get("CM-6-28");
//                if(StringUtils.isBlank(treatmentDisposableMaterialFeeString)){
//                    log.info("continue CM-6-28 is null---qSingleDiseaseTakeId-------{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
//                    treatmentDisposableMaterialFeeString="0";
//                }
//                Integer treatmentDisposableMaterialFee = PriceUtil.toFenInt(new BigDecimal(treatmentDisposableMaterialFeeString));
//                qSingleDiseaseTake.setDisposableConsumable(PriceUtil.toYuanStr(treatmentDisposableMaterialFee+examinationDisposableMaterialFee+operationDisposableMaterialFee));


                String westernMedicineFeeString = map.get("CM-6-18");
                if(StringUtils.isBlank(westernMedicineFeeString)){
                    log.info("continue CM-6-18 is null---qSingleDiseaseTakeId-------{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
                    westernMedicineFeeString="0";
                }
                Integer westernMedicineFee= PriceUtil.toFenInt(new BigDecimal(westernMedicineFeeString));

                String chineseMedicineFeeString = map.get("CM-6-20");
                if(StringUtils.isBlank(chineseMedicineFeeString)){
                    log.info("continue CM-6-18 is null---qSingleDiseaseTakeId-------{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
                    chineseMedicineFeeString="0";
                }
                Integer chineseMedicineFee = PriceUtil.toFenInt(new BigDecimal(chineseMedicineFeeString));

                String chineseHerbalMedicineFeeString = map.get("CM-6-21");
                if(StringUtils.isBlank(chineseHerbalMedicineFeeString)){
                    log.info("continue CM-6-18 is null---qSingleDiseaseTakeId-------{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
                    chineseHerbalMedicineFeeString="0";
                }
                Integer chineseHerbalMedicineFee = PriceUtil.toFenInt(new BigDecimal(chineseHerbalMedicineFeeString));

                qSingleDiseaseTake.setDrugFee(PriceUtil.toYuanStr(westernMedicineFee + chineseMedicineFee + chineseHerbalMedicineFee));
                dateUpdateFlag = qSingleDiseaseTakeMapper.updateById(qSingleDiseaseTake)>0;

                if (!dateUpdateFlag) {
                    log.info("not dateUpdateFlag , continue qSingleDiseaseTake.getTableName() data is null---questionId--summary_mapping_table_id-----{},{},{}", dynamicTableName, qSingleDiseaseTake.getId(),summaryMappingTableId);
                    continue;
                }

            }
        }
        return true;
    }

    @Override
    public Result updateOptionValue(AdminPrivateUpdateOptionValueParam adminPrivateUpdateOptionValueParam) {
        //查出来所有的单病种表
        LambdaQueryWrapper<Question> lambda = new QueryWrapper<Question>().lambda();
//        lambda.eq(Question::getQuStatus, QuestionConstant.QU_STATUS_RELEASE);
        lambda.eq(Question::getCategoryType, QuestionConstant.CATEGORY_TYPE_CHECK);
        lambda.eq(Question::getDel, QuestionConstant.DEL_NORMAL);
        lambda.eq(Question::getTableName, adminPrivateUpdateOptionValueParam.getTableName());
//        lambda.ge(Question::getId,98);
        List<Question> questionList = questionMapper.selectList(lambda);
        if(questionList.isEmpty()){
            return ResultFactory.fail("未找到相关查检表，请确认该检查表类型设置为检查表");
        }

        for (Question question : questionList) {
            //查出来所有题
            LambdaQueryWrapper<Qsubject> qsubjectLambdaQueryWrapper = new QueryWrapper<Qsubject>().lambda();
            qsubjectLambdaQueryWrapper.eq(Qsubject::getQuId,question.getId())
                    .eq(Qsubject::getSubType,QsubjectConstant.SUB_TYPE_CHOICE_SCORE)
                    .eq(Qsubject::getDel, QsubjectConstant.DEL_NORMAL);
            List<Qsubject> subjectList = qsubjectMapper.selectList(qsubjectLambdaQueryWrapper);
            List<Integer> subjectIdList = subjectList.stream().map(Qsubject::getId).collect(Collectors.toList());
            //查出来所有选项有 是 并且分值大于0
            LambdaQueryWrapper<Qoption> qoptionLambdaQueryWrapper = new QueryWrapper<Qoption>().lambda();
            qoptionLambdaQueryWrapper.in(Qoption::getSubId,subjectIdList);
            qoptionLambdaQueryWrapper.eq(Qoption::getDel, QoptionConstant.DEL_NORMAL);
            qoptionLambdaQueryWrapper.eq(Qoption::getOpName,"是");
            qoptionLambdaQueryWrapper.eq(Qoption::getOpValue,"y");
            qoptionLambdaQueryWrapper.gt(Qoption::getOptionScore,0);
            List<Qoption> qoptions = optionMapper.selectList(qoptionLambdaQueryWrapper);
            ArrayList<Qoption> optionUpdateList = Lists.newArrayList();
            for (Qoption qoption : qoptions) {
                LambdaQueryWrapper<Qoption> qoptionLambda = new QueryWrapper<Qoption>().lambda();
                qoptionLambda.eq(Qoption::getSubId,qoption.getSubId());
                qoptionLambda.eq(Qoption::getDel, QoptionConstant.DEL_NORMAL);
                qoptionLambda.eq(Qoption::getOpName,"否");
                qoptionLambda.eq(Qoption::getOpValue,"n");
//                qoptionLambdaQueryWrapper.ge(Qoption::getOptionScore,1);
                List<Qoption> optionListNo = optionMapper.selectList(qoptionLambda);
                if(optionListNo.isEmpty()){
                    continue;
                }
                Qoption optionNo = optionListNo.get(0);
                BigDecimal optionScoreNo = optionNo.getOptionScore();
                BigDecimal optionScoreYes = qoption.getOptionScore();
                qoption.setOptionScore(optionScoreNo);
                optionNo.setOptionScore(optionScoreYes);
                optionUpdateList.add(qoption);
                optionUpdateList.add(optionNo);
            }
            optionService.updateBatchById(optionUpdateList);
        }
        return ResultFactory.success();
    }
}
