package com.qu.modules.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultBetter;
import org.jeecg.common.api.vo.ResultBetterFactory;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.util.UUIDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qu.constant.AnswerConstant;
import com.qu.constant.QuestionConstant;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.mapper.AnswerMapper;
import com.qu.modules.web.mapper.DynamicTableMapper;
import com.qu.modules.web.mapper.QuestionMapper;
import com.qu.modules.web.param.AnswerAllDataParam;
import com.qu.modules.web.param.AnswerMonthQuarterYearSubmitParam;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.param.AnswerPatientSubmitParam;
import com.qu.modules.web.param.Answers;
import com.qu.modules.web.param.SingleDiseaseAnswer;
import com.qu.modules.web.pojo.JsonRootBean;
import com.qu.modules.web.service.IAnswerService;
import com.qu.modules.web.service.ISubjectService;
import com.qu.modules.web.vo.AnswerAllDataVo;
import com.qu.modules.web.vo.AnswerMonthQuarterYearFillingInAndSubmitPageVo;
import com.qu.modules.web.vo.AnswerMonthQuarterYearFillingInAndSubmitVo;
import com.qu.modules.web.vo.AnswerPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInAndSubmitPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInAndSubmitVo;
import com.qu.modules.web.vo.AnswerVo;
import com.qu.modules.web.vo.SubjectVo;
import com.qu.util.HttpClient;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    @Autowired
    private DynamicTableMapper dynamicTableMapper;

    @Autowired
    private AnswerMapper answerMapper;

//    @Autowired
//    private QsubjectMapper qsubjectMapper;

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private QuestionMapper questionMapper;


    @Value("${system.tokenUrl}")
    private String tokenUrl;

    @Override
    public int createDynamicTable(String sql) {
        return dynamicTableMapper.createDynamicTable(sql);
    }

    @Override
    public int insertDynamicTable(String sql) {
        return dynamicTableMapper.insertDynamicTable(sql);
    }

    @Override
    public Result answer(String cookie, AnswerParam answerParam) {
        //解析token
        String res = HttpClient.doPost(tokenUrl, cookie, null);
        JsonRootBean jsonRootBean = JSON.parseObject(res, JsonRootBean.class);
        String creater = "";
        String creater_name = "";
        String creater_deptid = "";
        String creater_deptname = "";
        if (jsonRootBean != null) {
            if (jsonRootBean.getData() != null) {
                creater = jsonRootBean.getData().getTbUser().getId();
                creater_name = jsonRootBean.getData().getTbUser().getUserName();
                creater_deptid = jsonRootBean.getData().getDeps().get(0).getId();
                creater_deptname = jsonRootBean.getData().getDeps().get(0).getDepName();
            }
        }
        Answer answer = this.getById(answerParam.getId());
        if(answer==null){
            answer = new Answer();
        }else{
            if(answer.getAnswerStatus().equals(1)){
                return ResultFactory.error("该记录已提交,无法更改。");
            }
        }
        Integer quId = answerParam.getQuId();
        Question question = questionMapper.selectById(quId);
        if (question == null || QuestionConstant.DEL_DELETED.equals(question.getDel())) {
            return ResultFactory.error("问卷不存在,无法保存。");
        }
        //插入总表
        answer.setQuId(answerParam.getQuId());
        answer.setQuestionVersion(question.getQuestionVersion());
        answer.setAnswerJson( JSON.toJSONString(answerParam.getAnswers()));
        Integer status = answerParam.getStatus();
        answer.setAnswerStatus(status);
        Date date = new Date();
        if(status.equals(1)){
            answer.setSubmitTime(date);
        }
        answer.setCreater(creater);
        answer.setCreaterName(creater_name);
        answer.setCreaterDeptid(creater_deptid);
        answer.setCreaterDeptname(creater_deptname);
        answer.setAnswerTime(date);
        answer.setQuestionVersion(question.getQuestionVersion());

        Answers[] answers = answerParam.getAnswers();
        Map<String, String> mapCache = new HashMap<>();
        for (Answers a : answers) {
            mapCache.put(a.getSubColumnName(), a.getSubValue());
        }

        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_TH_MONTH)
                && mapCache.get(AnswerConstant.COLUMN_NAME_TH_MONTH)!=null){
            answer.setQuestionAnswerTime(mapCache.get(AnswerConstant.COLUMN_NAME_TH_MONTH));
        }
        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_TH_QUARTER)
                && mapCache.get(AnswerConstant.COLUMN_NAME_TH_QUARTER)!=null){
            answer.setQuestionAnswerTime(mapCache.get(AnswerConstant.COLUMN_NAME_TH_QUARTER));
        }
        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_TH_YEAR)
                && mapCache.get(AnswerConstant.COLUMN_NAME_TH_YEAR)!=null){
            answer.setQuestionAnswerTime(mapCache.get(AnswerConstant.COLUMN_NAME_TH_YEAR));
        }

        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_CASE_ID)
                && mapCache.get(AnswerConstant.COLUMN_NAME_CASE_ID)!=null){
            answer.setHospitalInNo(mapCache.get(AnswerConstant.COLUMN_NAME_CASE_ID));
        }

        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_PATIENT_NAME)
                && mapCache.get(AnswerConstant.COLUMN_NAME_PATIENT_NAME)!=null){
            answer.setPatientName(mapCache.get(AnswerConstant.COLUMN_NAME_PATIENT_NAME));
        }

        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_PATIENT_NAME_LOWER_CASE)
                && mapCache.get(AnswerConstant.COLUMN_NAME_PATIENT_NAME_LOWER_CASE)!=null){
            answer.setPatientName(mapCache.get(AnswerConstant.COLUMN_NAME_PATIENT_NAME_LOWER_CASE));
        }

        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_AGE)
                && mapCache.get(AnswerConstant.COLUMN_NAME_AGE)!=null){
            answer.setAge(Integer.parseInt(mapCache.get(AnswerConstant.COLUMN_NAME_AGE)));
        }

        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_AGE_LOWER_CASE)
                && mapCache.get(AnswerConstant.COLUMN_NAME_AGE_LOWER_CASE)!=null){
            answer.setAge(Integer.parseInt(mapCache.get(AnswerConstant.COLUMN_NAME_AGE_LOWER_CASE)));
        }

        if(mapCache.containsKey(AnswerConstant.COLUMN_NAME_IN_TIME)
                && mapCache.get(AnswerConstant.COLUMN_NAME_IN_TIME)!=null){
            String dateInTimeString = mapCache.get(AnswerConstant.COLUMN_NAME_IN_TIME);
            Date dateInTime = DateUtil.parse(dateInTimeString).toJdkDate();
            answer.setInTime(dateInTime);
        }

        boolean insertOrUpdate = answer.getId() != null && answer.getId() != 0;
        if (insertOrUpdate) {
            answer.setUpdateTime(date);
            answerMapper.updateById(answer);
        }else{
            answer.setCreateTime(date);
            answer.setUpdateTime(date);
            answer.setDel(AnswerConstant.DEL_NORMAL);

            String summaryMappingTableId = UUIDGenerator.generateRandomUUIDAndCurrentTimeMillis();
            answer.setSummaryMappingTableId(summaryMappingTableId);
            answerMapper.insert(answer);
        }
        //插入子表
        StringBuffer sqlAns = new StringBuffer();
//        Question question = questionMapper.selectById(answerParam.getQuId());
        if (question != null) {
            if (insertOrUpdate) {
                sqlAns.append("update `" + question.getTableName() + "` set ");
//                List<Qsubject> subjectList = qsubjectMapper.selectSubjectByQuId(answerParam.getQuId());
                List<Qsubject> subjectList = subjectService.selectSubjectByQuId(answerParam.getQuId());
                for (int i = 0; i < subjectList.size(); i++) {
                    Qsubject qsubjectDynamicTable = subjectList.get(i);
                    String subType = qsubjectDynamicTable.getSubType();
                    Integer del = qsubjectDynamicTable.getDel();
                    if (QuestionConstant.SUB_TYPE_GROUP.equals(subType) || QuestionConstant.SUB_TYPE_TITLE.equals(subType)
                            || QuestionConstant.DEL_DELETED.equals(del) || mapCache.get(qsubjectDynamicTable.getColumnName())==null
                            || StringUtils.isBlank(mapCache.get(qsubjectDynamicTable.getColumnName()))) {
                        continue;
                    }
                    sqlAns.append("`");
                    sqlAns.append(qsubjectDynamicTable.getColumnName());
                    sqlAns.append("`");
                    sqlAns.append("=");
                    sqlAns.append("'");
                    sqlAns.append(mapCache.get(qsubjectDynamicTable.getColumnName()));
                    sqlAns.append("'");
                    sqlAns.append(",");
                }
                sqlAns.append("`tbksmc`='");
                sqlAns.append(creater_deptname);
                sqlAns.append("',");
                sqlAns.append("`tbksdm`='");
                sqlAns.append(creater_deptid);
                sqlAns.append("'");
//                    sqlAns.delete(sqlAns.length()-1,sqlAns.length());
                sqlAns.append(" where summary_mapping_table_id = '");
                sqlAns.append(answer.getSummaryMappingTableId());
                sqlAns.append("'");
                log.info("-----update sqlAns:{}", sqlAns.toString());
                dynamicTableMapper.updateDynamicTable(sqlAns.toString());
            }else{
                sqlAns.append("insert into `" + question.getTableName() + "` (");

//                List<Qsubject> subjectList = qsubjectMapper.selectSubjectByQuId(answerParam.getQuId());
                List<Qsubject> subjectList = subjectService.selectSubjectByQuId(answerParam.getQuId());
                for (int i = 0; i < subjectList.size(); i++) {
                    Qsubject qsubjectDynamicTable = subjectList.get(i);
                    String subType = qsubjectDynamicTable.getSubType();
                    Integer del = qsubjectDynamicTable.getDel();
                    if (QuestionConstant.SUB_TYPE_GROUP.equals(subType) || QuestionConstant.SUB_TYPE_TITLE.equals(subType)
                            || QuestionConstant.DEL_DELETED.equals(del) || mapCache.get(qsubjectDynamicTable.getColumnName())==null
                            || StringUtils.isBlank(mapCache.get(qsubjectDynamicTable.getColumnName()))) {
                        continue;
                    }

                    Qsubject qsubject = subjectList.get(i);
                    sqlAns.append("`");
                    sqlAns.append(qsubject.getColumnName());
                    sqlAns.append("`");
                    sqlAns.append(",");
                }
                sqlAns.append("`tbksmc`,");
                sqlAns.append("`tbksdm`,");
                sqlAns.append("`summary_mapping_table_id`");
//                sqlAns.delete(sqlAns.length()-1,sqlAns.length());
                sqlAns.append(") values (");
                for (int i = 0; i < subjectList.size(); i++) {
                    Qsubject qsubjectDynamicTable = subjectList.get(i);
                    String subType = qsubjectDynamicTable.getSubType();
                    Integer del = qsubjectDynamicTable.getDel();
                    if (QuestionConstant.SUB_TYPE_GROUP.equals(subType) || QuestionConstant.SUB_TYPE_TITLE.equals(subType)
                            || QuestionConstant.DEL_DELETED.equals(del) || mapCache.get(qsubjectDynamicTable.getColumnName())==null
                            || StringUtils.isBlank(mapCache.get(qsubjectDynamicTable.getColumnName()))) {
                        continue;
                    }
                    sqlAns.append("'");
                    sqlAns.append(mapCache.get(qsubjectDynamicTable.getColumnName()));
                    sqlAns.append("',");
                }
                sqlAns.append("'");
                sqlAns.append(creater_deptname);
                sqlAns.append("',");

                sqlAns.append("'");
                sqlAns.append(creater_deptid);
                sqlAns.append("',");

                sqlAns.append("'");
                sqlAns.append(answer.getSummaryMappingTableId());
                sqlAns.append("'");
//                sqlAns.delete(sqlAns.length()-1,sqlAns.length());

                sqlAns.append(")");
                log.info("-----insert sqlAns:{}", sqlAns.toString());
                dynamicTableMapper.insertDynamicTable(sqlAns.toString());
            }
        }
        return ResultFactory.success();
    }


//    @Override
//    public String queryByQuId(Integer quId) {
//        String answer = null;
//        try {
//            StringBuffer sql = new StringBuffer();
//            sql.append("select answer_json from answer where  qu_id = " + quId + "  order by create_time  desc limit  1");
//            log.info("-----insert sql:{}", sql.toString());
//            answer = dynamicTableMapper.selectDynamicTable(sql.toString());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return answer;
//    }

    @Override
    public AnswerPageVo questionFillInList(String quName, Integer pageNo, Integer pageSize) {
        Page<Answer> page = new Page<>(pageNo, pageSize);

        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
        questionLambdaQueryWrapper.like(Question::getQuName, quName);
        questionLambdaQueryWrapper.eq(Question::getQuStatus,QuestionConstant.QU_STATUS_RELEASE);
        questionLambdaQueryWrapper.eq(Question::getCategoryType,QuestionConstant.CATEGORY_TYPE_NORMAL);
        questionLambdaQueryWrapper.eq(Question::getDel,QuestionConstant.DEL_NORMAL);

        List<Question> questionList = questionMapper.selectList(questionLambdaQueryWrapper);
        List<Integer> questionSearchIdList = questionList.stream().map(Question::getId).distinct().collect(Collectors.toList());

        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
        if(!questionSearchIdList.isEmpty()){
            lambda.in(Answer::getQuId,questionSearchIdList);
        }
        AnswerPageVo res = new AnswerPageVo();
        if(StringUtils.isNotBlank(quName) && questionSearchIdList.isEmpty()){
            res.setTotal(0);
            return res;
        }
        lambda.orderByAsc(Answer::getAnswerTime);
        IPage<Answer> answerIPage = this.page(page, lambda);
        List<Answer> answerList = answerIPage.getRecords();
        if(answerList.isEmpty()){
            res.setTotal(answerIPage.getTotal());
            return res;
        }

        List<Integer> questionIdList = answerList.stream().map(Answer::getQuId).distinct().collect(Collectors.toList());
        List<Question> answerQuestionList = questionMapper.selectBatchIds(questionIdList);
        Map<Integer, Question> questionMap = answerQuestionList.stream().collect(Collectors.toMap(Question::getId, q -> q));
        List<AnswerVo> answerVoList = answerList.stream().map(answer -> {
            AnswerVo answerVo = new AnswerVo();
            BeanUtils.copyProperties(answer,answerVo);
            answerVo.setQuName(questionMap.get(answer.getQuId()).getQuName());
            return answerVo;
        }).collect(Collectors.toList());

        res.setTotal(answerIPage.getTotal());
        res.setAnswerVoList(answerVoList);
        return res;
    }


    @Override
    public boolean withdrawEdit(Integer id) {
        Answer answer = answerMapper.selectById(id);
        if (answer == null || AnswerConstant.DEL_DELETED.equals(answer.getDel())) {
            return false;
        }
        answer.setAnswerStatus(AnswerConstant.ANSWER_STATUS_DRAFT);
        answerMapper.updateById(answer);
        return true;
    }

    @Override
    public AnswerPatientFillingInAndSubmitPageVo patientFillingInList(String deptId, Integer pageNo, Integer pageSize) {
        Page<Answer> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
        lambda.like(Answer::getCreaterDeptid,deptId);
        lambda.eq(Answer::getAnswerStatus,AnswerConstant.ANSWER_STATUS_DRAFT);
        lambda.eq(Answer::getDel,AnswerConstant.DEL_NORMAL);

        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
        questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_PATIENT_WRITE);
        List<Question> questions = questionMapper.selectList(questionLambdaQueryWrapper);
        if(questions.isEmpty()){
            AnswerPatientFillingInAndSubmitPageVo res = new AnswerPatientFillingInAndSubmitPageVo();
            res.setTotal(0);
            return res;
        }
        List<Integer> questionIdList = questions.stream().map(Question::getId).distinct().collect(Collectors.toList());
        lambda.in(Answer::getQuId,questionIdList);
        return getAnswerPatientFillingInAndSubmitPageVo(page, lambda);
    }

    private LambdaQueryWrapper<Question> getQuestionLambda() {
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new QueryWrapper<Question>().lambda();
        questionLambdaQueryWrapper.eq(Question::getQuStatus,QuestionConstant.QU_STATUS_RELEASE);
        questionLambdaQueryWrapper.eq(Question::getDel,QuestionConstant.DEL_NORMAL);
        questionLambdaQueryWrapper.and(wrapper->wrapper.eq(Question::getCategoryType, QuestionConstant.CATEGORY_TYPE_NORMAL).or().isNull(Question::getCategoryType));
        return questionLambdaQueryWrapper;
    }

    @Override
    public AnswerPatientFillingInAndSubmitPageVo patientSubmitList(String deptId, AnswerPatientSubmitParam answerPatientSubmitParam, Integer pageNo, Integer pageSize) {
        Page<Answer> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
        //院领导和质控办放开查询的权限
        log.info("-----查询的科室ID{}", deptId);
        if(!"341f22af7cd148c9813e53496969032a".equals(deptId)&&!"c9f3d69323e84f019bb77207b72f5c85".equals(deptId)){
            lambda.like(Answer::getCreaterDeptid,deptId);
        }
        lambda.eq(Answer::getAnswerStatus,AnswerConstant.ANSWER_STATUS_RELEASE);
        lambda.eq(Answer::getDel,AnswerConstant.DEL_NORMAL);

        if(StringUtils.isNotBlank(answerPatientSubmitParam.getPatientName())){
            lambda.like(Answer::getPatientName,answerPatientSubmitParam.getPatientName());
        }
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
        questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_PATIENT_WRITE);
        if(StringUtils.isNotBlank(answerPatientSubmitParam.getQuName())){
            questionLambdaQueryWrapper.like(Question::getQuName,answerPatientSubmitParam.getQuName());
        }
        List<Question> questions = questionMapper.selectList(questionLambdaQueryWrapper);
        if(questions.isEmpty()){
            AnswerPatientFillingInAndSubmitPageVo res = new AnswerPatientFillingInAndSubmitPageVo();
            res.setTotal(0);
            return res;
        }
        List<Integer> questionIdList = questions.stream().map(Question::getId).distinct().collect(Collectors.toList());
        lambda.in(Answer::getQuId,questionIdList);

        if (answerPatientSubmitParam.getInHospitalStartDate() != null) {
            lambda.ge(Answer::getInTime, answerPatientSubmitParam.getInHospitalStartDate());
        }
        if (answerPatientSubmitParam.getInHospitalEndDate() != null) {
            lambda.le(Answer::getInTime, answerPatientSubmitParam.getInHospitalEndDate());
        }
        if (answerPatientSubmitParam.getSubmitStartDate() != null) {
            lambda.ge(Answer::getSubmitTime, answerPatientSubmitParam.getSubmitStartDate());
        }
        if (answerPatientSubmitParam.getSubmitEndDate() != null) {
            lambda.le(Answer::getSubmitTime, answerPatientSubmitParam.getSubmitEndDate());
        }
        if (StringUtils.isNotBlank(answerPatientSubmitParam.getCreaterName())) {
            lambda.like(Answer::getCreaterName, answerPatientSubmitParam.getCreaterName());
        }
        if (StringUtils.isNotBlank(answerPatientSubmitParam.getHospitalInNo())) {
            lambda.like(Answer::getHospitalInNo, answerPatientSubmitParam.getHospitalInNo());
        }
        return getAnswerPatientFillingInAndSubmitPageVo(page, lambda);
    }

    private AnswerPatientFillingInAndSubmitPageVo getAnswerPatientFillingInAndSubmitPageVo(Page<Answer> page, LambdaQueryWrapper<Answer> lambda) {
        AnswerPatientFillingInAndSubmitPageVo res = new AnswerPatientFillingInAndSubmitPageVo();
        IPage<Answer> answerIPage = this.page(page, lambda);
        List<Answer> questions = answerIPage.getRecords();
        if(questions.isEmpty()){
            res.setTotal(answerIPage.getTotal());
            return res;
        }
        List<Integer> questionIdList = questions.stream().map(Answer::getQuId).distinct().collect(Collectors.toList());
        List<Question> questionList = questionMapper.selectBatchIds(questionIdList);
        Map<Integer, Question> questionMap = questionList.stream().collect(Collectors.toMap(Question::getId, q -> q));
        List<AnswerPatientFillingInAndSubmitVo> answerPatientFillingInVos = questions.stream().map(answer -> {
            AnswerPatientFillingInAndSubmitVo answerPatientFillingInVo = new AnswerPatientFillingInAndSubmitVo();
            BeanUtils.copyProperties(answer,answerPatientFillingInVo);
            answerPatientFillingInVo.setQuName(questionMap.get(answer.getQuId()).getQuName());
            return answerPatientFillingInVo;
        }).collect(Collectors.toList());
        res.setTotal(answerIPage.getTotal());
        res.setAnswerPatientFillingInVos(answerPatientFillingInVos);
        return res;
    }

    @Override
    public AnswerMonthQuarterYearFillingInAndSubmitPageVo monthQuarterYearFillingInList(String deptId, String type, String month, Integer pageNo, Integer pageSize) {
        Page<Answer> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
        lambda.like(Answer::getCreaterDeptid,deptId);
        lambda.eq(Answer::getAnswerStatus,AnswerConstant.ANSWER_STATUS_DRAFT);
        lambda.eq(Answer::getDel,AnswerConstant.DEL_NORMAL);
        if(StringUtils.isNotBlank(month)){
            lambda.eq(Answer::getQuestionAnswerTime,month);
        }

        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
        if("0".equals(type)){
            questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_MONTH);
        }else if("1".equals(type)){
            questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_QUARTER);
        }else if("2".equals(type)){
            questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_YEAR);
        }else{
            questionLambdaQueryWrapper.in(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_MONTH_QUARTER_YEAR);
        }

        List<Question> questions = questionMapper.selectList(questionLambdaQueryWrapper);
        if(questions.isEmpty()){
            AnswerMonthQuarterYearFillingInAndSubmitPageVo res = new AnswerMonthQuarterYearFillingInAndSubmitPageVo();
            res.setTotal(0);
            return res;
        }
        List<Integer> questionIdList = questions.stream().map(Question::getId).distinct().collect(Collectors.toList());
        lambda.in(Answer::getQuId,questionIdList);

        return getAnswerMonthQuarterYearFillingInAndSubmitPageVo(page, lambda);
    }



    @Override
    public AnswerMonthQuarterYearFillingInAndSubmitPageVo monthQuarterYearSubmitList(String deptId, AnswerMonthQuarterYearSubmitParam answerMonthQuarterYearSubmitParam,
                                                                                     Integer pageNo, Integer pageSize) {
        Page<Answer> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
        //院领导和质控办放开查询的权限
        log.info("-----查询的科室ID{}", deptId);
        if(!"341f22af7cd148c9813e53496969032a".equals(deptId)&&!"c9f3d69323e84f019bb77207b72f5c85".equals(deptId)){
            lambda.like(Answer::getCreaterDeptid,deptId);
        }
        lambda.eq(Answer::getAnswerStatus,AnswerConstant.ANSWER_STATUS_RELEASE);
        lambda.eq(Answer::getDel,AnswerConstant.DEL_NORMAL);

        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
        if(answerMonthQuarterYearSubmitParam.getWriteFrequency().equals(QuestionConstant.WRITE_FREQUENCY_ALL)){
            questionLambdaQueryWrapper.in(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_MONTH_QUARTER_YEAR);
        }else if(answerMonthQuarterYearSubmitParam.getWriteFrequency().equals(QuestionConstant.WRITE_FREQUENCY_MONTH)){
            questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_MONTH);
        }else if(answerMonthQuarterYearSubmitParam.getWriteFrequency().equals(QuestionConstant.WRITE_FREQUENCY_QUARTER)){
            questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_QUARTER);
        }else if(answerMonthQuarterYearSubmitParam.getWriteFrequency().equals(QuestionConstant.WRITE_FREQUENCY_YEAR)){
            questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_YEAR);
        }

        if(StringUtils.isNotBlank(answerMonthQuarterYearSubmitParam.getQuName())){
            questionLambdaQueryWrapper.like(Question::getQuName,answerMonthQuarterYearSubmitParam.getQuName());
        }
        List<Question> questions = questionMapper.selectList(questionLambdaQueryWrapper);
        if(questions.isEmpty()){
            AnswerMonthQuarterYearFillingInAndSubmitPageVo res = new AnswerMonthQuarterYearFillingInAndSubmitPageVo();
            res.setTotal(0);
            return res;
        }
        List<Integer> questionIdList = questions.stream().map(Question::getId).distinct().collect(Collectors.toList());
        lambda.in(Answer::getQuId,questionIdList);
        if (answerMonthQuarterYearSubmitParam.getSubmitStartDate() != null) {
            lambda.ge(Answer::getSubmitTime, answerMonthQuarterYearSubmitParam.getSubmitStartDate());
        }
        if (answerMonthQuarterYearSubmitParam.getSubmitEndDate() != null) {
            lambda.le(Answer::getSubmitTime, answerMonthQuarterYearSubmitParam.getSubmitEndDate());
        }
        if (StringUtils.isNotBlank(answerMonthQuarterYearSubmitParam.getQuestionAnswerTime())) {
            lambda.eq(Answer::getQuestionAnswerTime, answerMonthQuarterYearSubmitParam.getQuestionAnswerTime());
        }
        if (StringUtils.isNotBlank(answerMonthQuarterYearSubmitParam.getCreaterName())) {
            lambda.like(Answer::getCreaterName, answerMonthQuarterYearSubmitParam.getCreaterName());
        }
        return getAnswerMonthQuarterYearFillingInAndSubmitPageVo(page, lambda);
    }

    private AnswerMonthQuarterYearFillingInAndSubmitPageVo getAnswerMonthQuarterYearFillingInAndSubmitPageVo(Page<Answer> page, LambdaQueryWrapper<Answer> lambda) {
        //定期汇总登记表中要排除系统中转表
        lambda.notIn(Answer::getQuId,248);
        AnswerMonthQuarterYearFillingInAndSubmitPageVo res = new AnswerMonthQuarterYearFillingInAndSubmitPageVo();
        IPage<Answer> answerIPage = this.page(page, lambda);
        List<Answer> answerList = answerIPage.getRecords();
        if(answerList.isEmpty()){
            res.setTotal(answerIPage.getTotal());
            return res;
        }
        List<Integer> questionIdList = answerList.stream().map(Answer::getQuId).distinct().collect(Collectors.toList());
        List<Question> questionList = questionMapper.selectBatchIds(questionIdList);
        Map<Integer, Question> questionMap = questionList.stream().collect(Collectors.toMap(Question::getId, q -> q));

        List<AnswerMonthQuarterYearFillingInAndSubmitVo> answerMonthQuarterYearFillingInVos = answerList.stream().map(answer -> {
            AnswerMonthQuarterYearFillingInAndSubmitVo answerMonthQuarterYearFillingInVo = new AnswerMonthQuarterYearFillingInAndSubmitVo();
            BeanUtils.copyProperties(answer,answerMonthQuarterYearFillingInVo);
            answerMonthQuarterYearFillingInVo.setQuName(questionMap.get(answer.getQuId()).getQuName());
            answerMonthQuarterYearFillingInVo.setIcon(questionMap.get(answer.getQuId()).getIcon());
            return answerMonthQuarterYearFillingInVo;
        }).collect(Collectors.toList());
        res.setTotal(answerIPage.getTotal());
        res.setAnswerPatientFillingInVos(answerMonthQuarterYearFillingInVos);
        return res;
    }

    @Override
    public boolean patientMonthQuarterYearFillingInDelete(Integer id) {
        Answer answer = this.answerMapper.selectById(id);
        if (answer == null || AnswerConstant.DEL_DELETED.equals(answer.getDel())) {
            return false;
        }
        answer.setDel(AnswerConstant.DEL_DELETED);
        answerMapper.updateById(answer);
        return true;
    }

    @Override
    public Answer queryById(String id) {
        Answer answer = this.getById(id);
        if (answer == null) {
            return null;
        }

        String answerJson = (String) answer.getAnswerJson();
        List<SingleDiseaseAnswer> singleDiseaseAnswerList = JSON.parseArray(answerJson, SingleDiseaseAnswer.class);
        Map<String, SingleDiseaseAnswer> mapCache = new HashMap<>();
        if(singleDiseaseAnswerList!=null && !singleDiseaseAnswerList.isEmpty()){
            for (SingleDiseaseAnswer a : singleDiseaseAnswerList) {
                mapCache.put(a.getSubColumnName(), a);
            }
        }
        Question question = questionMapper.selectById(answer.getQuId());

        StringBuffer sqlAns = new StringBuffer();
        if (question != null) {
            sqlAns.append("select * from `");
            sqlAns.append(question.getTableName());
            sqlAns.append("` where summary_mapping_table_id ='");
            sqlAns.append(answer.getSummaryMappingTableId());
            sqlAns.append("'");
            Map<String,String> map = dynamicTableMapper.selectDynamicTableColumn(sqlAns.toString());
//            String s = "select * from q_single_disease_take where id =20 ";
//            Map<String, String> map = dynamicTableMapper.selectDynamicTableColumn(s);
            if(map==null){
                return answer;
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                QueryWrapper<Qsubject> wrapper = new QueryWrapper<Qsubject>();
                if("id".equals(entry.getKey())){
                    continue;
                }
                wrapper.eq("column_name", entry.getKey());
                wrapper.eq("qu_id", question.getId());
                wrapper.eq("del", "0");
                Qsubject qsubject = subjectService.getOne(wrapper);
                if(qsubject==null){
                    continue;
                }
                SingleDiseaseAnswer singleDiseaseAnswer = new SingleDiseaseAnswer();
                singleDiseaseAnswer.setSubColumnName(qsubject.getColumnName());
                singleDiseaseAnswer.setSubValue(String.valueOf(entry.getValue()));
                mapCache.put(qsubject.getColumnName(), singleDiseaseAnswer);
            }
            List<SingleDiseaseAnswer> resList = new ArrayList<>(mapCache.values());
            answer.setAnswerJson( JSON.toJSONString(resList));
        }

        return answer;
    }

    @Override
    public ResultBetter<AnswerAllDataVo> answerAllData(String deptId, AnswerAllDataParam param) {
        //查询数据
        Integer quId = param.getQuId();
        Integer type = param.getType();
        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
//        lambda.like(Answer::getCreaterDeptid, deptId);
        lambda.eq(Answer::getAnswerStatus, AnswerConstant.ANSWER_STATUS_RELEASE);
        lambda.eq(Answer::getDel, AnswerConstant.DEL_NORMAL);
        lambda.eq(Answer::getQuId, quId);
        lambda.gt(Answer::getQuestionAnswerTime, param.getStartDate());
        lambda.le(Answer::getQuestionAnswerTime, param.getEndDate());
        List<Answer> answerList = this.list(lambda);

        if(answerList.isEmpty()){
            return ResultBetterFactory.fail("未查到数据");
        }

        AnswerAllDataVo vo = new AnswerAllDataVo();
        //表头
        List<LinkedHashMap<String, String>> fieldItems = Lists.newArrayList();
        LinkedHashMap<String, String> fieldItemCheckDept = Maps.newLinkedHashMap();
        fieldItems.add(fieldItemCheckDept);
        fieldItemCheckDept.put("fieldTxt", "检查项目");
        fieldItemCheckDept.put("fieldId", "tb_check_project");
        setItems(param, fieldItems);
        vo.setFieldItems(fieldItems);

        //数据
        List<LinkedHashMap<String, String>> detailDataList = Lists.newArrayList();
        vo.setDetailDataList(detailDataList);
        //处理题目 查询题目
        List<SubjectVo> subjectList = subjectService.selectSubjectAndOptionByQuId(quId);
        for (SubjectVo subjectVo : subjectList) {
            LinkedHashMap<String, String> valueItem = Maps.newLinkedHashMap();
            detailDataList.add(valueItem);
            valueItem.put("tb_check_project", subjectVo.getSubName());
            valueItem.put("dataKey", subjectVo.getColumnName());
        }

        //处理数据
        List<String> summaryMappingTableIdList = answerList.stream().map(Answer::getSummaryMappingTableId).collect(Collectors.toList());
        //查子表
        Question question = questionMapper.selectById(quId);
        StringBuffer sqlSelect = new StringBuffer();
        sqlSelect.append("select * from `");
        sqlSelect.append(question.getTableName());
        sqlSelect.append("`");
        sqlSelect.append(" where summary_mapping_table_id in (");
        for (String summaryMappingTableId : summaryMappingTableIdList) {
            sqlSelect.append("'");
            sqlSelect.append(summaryMappingTableId);
            sqlSelect.append("'");
            sqlSelect.append(",");
        }
        sqlSelect.delete(sqlSelect.length() - 1, sqlSelect.length());
        sqlSelect.append(")");
        List<Map<String, String>> dataList = dynamicTableMapper.selectDynamicTableColumnList(sqlSelect.toString());
        for (Map<String, String> dataItemMap : dataList) {
//            public static final String COLUMN_NAME_TH_MONTH = "tb_month";
//            public static final String COLUMN_NAME_TH_QUARTER = "tb_jidu";
//            public static final String COLUMN_NAME_TH_YEAR = "tb_year";
            String key=null;
            if(type.equals(1)){
                key = dataItemMap.get("tb_month");
            }else if(type.equals(2)){
                key = dataItemMap.get("tb_jidu");
            }else if(type.equals(3)){
                key = dataItemMap.get("tb_year");
            }
            for (LinkedHashMap<String, String> stringObjectLinkedHashMap : detailDataList) {
                String dataKey = stringObjectLinkedHashMap.get("dataKey");
                String s = dataItemMap.get(dataKey);
                stringObjectLinkedHashMap.put(key,s);
//                stringObjectLinkedHashMap.remove("dataKey");
            }
        }

        return ResultBetter.ok(vo);
    }

    private void setItems(AnswerAllDataParam param, List<LinkedHashMap<String, String>> fieldItems) {
        //放入表头
        Integer type = param.getType();
        if(type.equals(1)){
            List<LinkedHashMap<String, String>> monthBetweenDate = getMonthBetweenDate(param.getStartDate(), param.getEndDate());
            fieldItems.addAll(monthBetweenDate);
        }else if(type.equals(2)){
            List<LinkedHashMap<String, String>> quarterBetweenDate = getQuarterBetweenDate(param.getStartDate(), param.getEndDate());
            fieldItems.addAll(quarterBetweenDate);
        }else if(type.equals(3)){
            List<LinkedHashMap<String, String>> yearBetweenDate = getYearBetweenDate(param.getStartDate(), param.getEndDate());
            fieldItems.addAll(yearBetweenDate);
        }
    }


    /**
     * 获取两个日期之间的所有月份 (年月)
     *
     * @param startDate
     * @param endDate
     * @return LinkedHashMap
     */
    public static List<LinkedHashMap<String, String>> getMonthBetweenDate(String startDate, String endDate){
        // 声明保存日期集合
        List<LinkedHashMap<String, String>> list = Lists.newArrayList();

        // 转化成日期类型
        DateTime startDateTime = DateUtil.parse(startDate, DatePattern.NORM_MONTH_PATTERN);
        DateTime endDateTime = DateUtil.parse(endDate, DatePattern.NORM_MONTH_PATTERN);

        //用Calendar 进行日期比较判断
        Calendar calendar = Calendar.getInstance();
        while (startDateTime.getTime()<=endDateTime.getTime()){
            LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
            list.add(map);
            // 把日期添加到集合
            map.put("fieldTxt", startDateTime.toString(DatePattern.NORM_MONTH_PATTERN));
            map.put("fieldId", startDateTime.toString(DatePattern.NORM_MONTH_PATTERN));
            // 设置日期
            calendar.setTime(startDateTime);
            //把日期增加
            calendar.add(Calendar.MONTH, 1);
            // 获取增加后的日期
            startDateTime.setTime(calendar.getTimeInMillis());
        }
        return list;
    }

    /**
     * 获取两个日期之间的所有季度
     *
     * @param startDate
     * @param endDate
     * @return LinkedHashMap
     */
    private List<LinkedHashMap<String, String>> getQuarterBetweenDate(String startDate, String endDate) {
        // 声明保存日期集合
        List<LinkedHashMap<String, String>> list = Lists.newArrayList();

        // 转化成日期类型
        DateTime dateTime = new DateTime();
        dateTime.setField(DateField.YEAR, Integer.parseInt(startDate.substring(0,4)));
        DateTime startDateTime = dateTime.offsetNew(DateField.MONTH, Integer.parseInt(startDate.substring(4,5)));
        dateTime.setField(DateField.YEAR, Integer.parseInt(endDate.substring(0,4)));
        DateTime endDateTime = dateTime.offsetNew(DateField.MONTH, Integer.parseInt(endDate.substring(4,5)));

        //用Calendar 进行日期比较判断
        final Calendar calendar = Calendar.getInstance();
        while (startDateTime.getTime()<=endDateTime.getTime()) {
            LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
            list.add(map);
            // 把日期添加到集合
            map.put("fieldTxt", String.format("%s第%s季度",startDateTime.year(),startDateTime.quarter()));
            map.put("fieldId", CalendarUtil.yearAndQuarter(calendar));
            // 设置日期
            calendar.setTime(startDateTime);
            //把日期增加
            calendar.add(Calendar.MONTH, 3);
            // 获取增加后的日期
            startDateTime.setTime(calendar.getTimeInMillis());
        }
        return list;
    }

    /**
     * 获取两个日期之间的所有年
     *
     * @param startDate
     * @param endDate
     * @return LinkedHashMap
     */
    private List<LinkedHashMap<String, String>> getYearBetweenDate(String startDate, String endDate) {
        // 声明保存日期集合
        List<LinkedHashMap<String, String>> list = Lists.newArrayList();

        int start = Integer.parseInt(startDate);
        int end = Integer.parseInt(endDate);

        while (start<=end) {
            LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
            list.add(map);
            // 把日期添加到集合
            map.put("fieldTxt", String.format("%s年",start));
            map.put("fieldId", String.valueOf(start));
            start++;
        }
        return list;
    }








}
