package com.qu.modules.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.AnswerConstant;
import com.qu.constant.QuestionConstant;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.mapper.AnswerMapper;
import com.qu.modules.web.mapper.DynamicTableMapper;
import com.qu.modules.web.mapper.QsubjectMapper;
import com.qu.modules.web.mapper.QuestionMapper;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.param.AnswerPatientSubmitParam;
import com.qu.modules.web.param.Answers;
import com.qu.modules.web.pojo.JsonRootBean;
import com.qu.modules.web.service.IAnswerService;
import com.qu.modules.web.vo.AnswerPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInAndSubmitPageVo;
import com.qu.modules.web.vo.AnswerPatientFillingInAndSubmitVo;
import com.qu.modules.web.vo.AnswerVo;
import com.qu.util.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    @Autowired
    private DynamicTableMapper dynamicTableMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private QsubjectMapper qsubjectMapper;

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
    public Boolean answer(String cookie, AnswerParam answerParam) {
        Boolean falg = true;
        try {
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
                    return false;
                }
            }
            //插入总表
            answer.setQuId(answerParam.getQuId());
            answer.setAnswerJson( JSON.toJSONString(answerParam.getAnswers()));
            Integer status = answerParam.getStatus();
            answer.setAnswerStatus(status);
            Date date = new Date();
            if(status.equals(1)){
                answer.setSubmitTime(date);
            }
            answer.setCreater(creater);
            answer.setCreaterName(creater_name);
            answer.setCreateTime(date);
            answer.setCreaterDeptid(creater_deptid);
            answer.setCreaterDeptname(creater_deptname);


            boolean insertOrUpdate = answer.getId() != null && answer.getId() != 0;
            if (insertOrUpdate) {
                answerMapper.updateById(answer);
            }else{
                String summaryMappingTableId = UUIDGenerator.generateRandomUUIDAndCurrentTimeMillis();
                answer.setSummaryMappingTableId(summaryMappingTableId);
                answerMapper.insert(answer);
            }
            //插入子表
            Answers[] answers = answerParam.getAnswers();
            Map<String, String> mapCache = new HashMap<>();
            for (Answers a : answers) {
                mapCache.put(a.getSubColumnName(), a.getSubValue());
            }
            StringBuffer sqlAns = new StringBuffer();
            Question question = questionMapper.selectById(answerParam.getQuId());
            if (question != null) {
                if (insertOrUpdate) {
                    sqlAns.append("update `" + question.getTableName() + "` set ");
                    List<Qsubject> subjectList = qsubjectMapper.selectSubjectByQuId(answerParam.getQuId());
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

                    List<Qsubject> subjectList = qsubjectMapper.selectSubjectByQuId(answerParam.getQuId());
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
        } catch (Exception e) {
            falg = false;
            log.error(e.getMessage(), e);
        }
        return falg;
    }

    @Override
    public String queryByQuId(Integer quId) {
        String answer = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select answer_json from answer where  qu_id = " + quId + "  order by create_time  desc limit  1");
            log.info("-----insert sql:{}", sql.toString());
            answer = dynamicTableMapper.selectDynamicTable(sql.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return answer;
    }

    @Override
    public AnswerPageVo questionFillInList(String quName, Integer pageNo, Integer pageSize) {
        Page<Answer> page = new Page<>(pageNo, pageSize);

        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
        questionLambdaQueryWrapper.like(Question::getQuName, quName);
        List<Question> questionList = questionMapper.selectList(questionLambdaQueryWrapper);
        List<Integer> questionSearchIdList = questionList.stream().map(Question::getId).distinct().collect(Collectors.toList());

        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
        if(!questionSearchIdList.isEmpty()){
            lambda.in(Answer::getQuId,questionSearchIdList);
        }

        AnswerPageVo res = new AnswerPageVo();
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

        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
        questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_PATIENT_WRITE);
        List<Question> questions = questionMapper.selectList(questionLambdaQueryWrapper);
        List<Integer> questionIdList = questions.stream().map(Question::getId).distinct().collect(Collectors.toList());
        lambda.in(Answer::getQuId,questionIdList);
        return getAnswerPatientFillingInAndSubmitPageVo(page, lambda);
    }

    private LambdaQueryWrapper<Question> getQuestionLambda() {
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new QueryWrapper<Question>().lambda();
        questionLambdaQueryWrapper.eq(Question::getCategoryType, QuestionConstant.CATEGORY_TYPE_NORMAL);
        questionLambdaQueryWrapper.eq(Question::getQuStop,QuestionConstant.QU_STATUS_RELEASE);
        questionLambdaQueryWrapper.eq(Question::getDel,QuestionConstant.DEL_NORMAL);
        return questionLambdaQueryWrapper;
    }

    @Override
    public AnswerPatientFillingInAndSubmitPageVo patientSubmitList(String deptId, AnswerPatientSubmitParam answerPatientSubmitParam, Integer pageNo, Integer pageSize) {
        Page<Answer> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
        lambda.like(Answer::getCreaterDeptid,deptId);
        lambda.eq(Answer::getAnswerStatus,AnswerConstant.ANSWER_STATUS_RELEASE);
        if(StringUtils.isNotBlank(answerPatientSubmitParam.getPatientName())){
            lambda.like(Answer::getPatientName,answerPatientSubmitParam.getPatientName());
        }
        if(StringUtils.isNotBlank(answerPatientSubmitParam.getQuName())){
            LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
            questionLambdaQueryWrapper.like(Question::getQuName,answerPatientSubmitParam.getQuName());
            List<Question> questions = questionMapper.selectList(questionLambdaQueryWrapper);
            List<Integer> questionIdList = questions.stream().map(Question::getId).distinct().collect(Collectors.toList());

            lambda.in(Answer::getQuId,questionIdList);
        }
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
    public AnswerPatientFillingInAndSubmitPageVo monthQuarterYearList(String deptId, String type, Integer pageNo, Integer pageSize) {
        Page<Answer> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Answer> lambda = new QueryWrapper<Answer>().lambda();
        lambda.like(Answer::getCreaterDeptid,deptId);
        lambda.eq(Answer::getAnswerStatus,AnswerConstant.ANSWER_STATUS_DRAFT);

        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = getQuestionLambda();
        if(type.equals("0")){
            questionLambdaQueryWrapper.eq(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_MONTH);
        }else{
            questionLambdaQueryWrapper.in(Question::getWriteFrequency,QuestionConstant.WRITE_FREQUENCY_MONTH_QUARTER_YEAR);
        }
        List<Question> questions = questionMapper.selectList(questionLambdaQueryWrapper);
        List<Integer> questionIdList = questions.stream().map(Question::getId).distinct().collect(Collectors.toList());
        lambda.in(Answer::getQuId,questionIdList);

        return getAnswerPatientFillingInAndSubmitPageVo(page, lambda);
    }
}
