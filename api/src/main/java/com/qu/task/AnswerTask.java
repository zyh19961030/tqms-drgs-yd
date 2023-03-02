package com.qu.task;

import com.alibaba.fastjson.JSON;
import com.qu.constant.AnswerConstant;
import com.qu.constant.QuestionConstant;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.mapper.AnswerMapper;
import com.qu.modules.web.mapper.DynamicTableMapper;
import com.qu.modules.web.mapper.QuestionMapper;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.param.Answers;
import com.qu.modules.web.service.ISubjectService;
import com.qu.modules.web.vo.AnswerIdVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
@Slf4j
public class AnswerTask {

    @Resource
    private DynamicTableMapper dynamicTableMapper;

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    // 每天凌晨1点执行
//    @Scheduled(cron = "0 47 16 * * ?")
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = simpleDateFormat.format(d);
        List<Map<String, String>> dataList = dynamicTableMapper.selectDynamicTableColumnList("select concat(id,'') as id,table_name,dept_ids,concat(write_frequency,'') as write_frequency from question where write_frequency in ('1','2','3') and category_type ='3' and del='0' ");
        if(dataList!=null && dataList.size()>0){
            for (Map<String, String> map : dataList) {
                Integer id = Integer.parseInt(map.get("id"));
                String tablename = map.get("table_name");
                String deptids = map.get("dept_ids");
                Integer status = 0;
//                填报频次 0患者登记表 1月度汇总表 2季度汇总表 3年度汇总表
                String writefrequency = map.get("write_frequency");
                String timetype = "";
                String time = "";
                if(writefrequency!=null && writefrequency.length()>0){
                    int writefrequencyint = Integer.parseInt(writefrequency);
                    if(writefrequencyint==1){
                        timetype = "tb_month";
                        time = now.substring(0,7);

                    }
                    if(writefrequencyint==2){
                        timetype = "tb_jidu";
                        String year = now.substring(0,4);
                        String month = now.substring(5,7);
                        String jidu = "";
                        switch (month) {
                            case "01":
                                jidu = "1";
                                break;
                            case "02":
                                jidu = "1";
                                break;
                            case "03":
                                jidu = "1";
                                break;
                            case "04":
                                jidu = "2";
                                break;
                            case "05":
                                jidu = "2";
                                break;
                            case "06":
                                jidu = "2";
                                break;
                            case "07":
                                jidu = "3";
                                break;
                            case "08":
                                jidu = "3";
                                break;
                            case "09":
                                jidu = "3";
                                break;
                            case "10":
                                jidu = "4";
                                break;
                            case "11":
                                jidu = "4";
                                break;
                            case "12":
                                jidu = "4";
                                break;
                        }
                        time = year+jidu;
                    }
                    if(writefrequencyint==3){
                        timetype = "tb_year";
                        time = now.substring(0,4);

                    }
                }
                List<Map<String, String>> questionlist = dynamicTableMapper.selectDynamicTableColumnList("select * from "+tablename+" where "+timetype+" = '"+time+"' and del = '0' ");
                if(questionlist !=null && questionlist.size()>0){
                    continue;
                }
                Answers answer = new Answers();
                answer.setSubColumnName(timetype);
                answer.setSubValue(time);
                Answers[] answers = new Answers[1];
                answers[0] = answer;
                AnswerParam answerParam = new AnswerParam();
                answerParam.setAnswers(answers);
                answerParam.setQuId(id);
                answerParam.setStatus(status);
                log.info("-----------answerParam={}", JSON.toJSONString(answerParam));
                if(deptids!=null && !"".equals(deptids)){
                    String deptid = "";
                    if(deptids.contains(",")){
                        deptid = deptids.split(",")[0];
                    }else{
                        deptid = deptids;
                    }
                    String depAndUserSql = "select userName,id as userId,(select depname from tb_dep where id=tb_user.depId) as depName,depId from tb_user where depId = '"+deptid+"' and isdelete='no' and userName not like '%测试%'";
                    List<Map<String, String>> depAndUserlist = dynamicTableMapper.selectDynamicTableColumnList(depAndUserSql);
                    if(depAndUserlist!=null && depAndUserlist.size()>0){
                        String userName = depAndUserlist.get(0).get("userName");
                        String userId = depAndUserlist.get(0).get("userId");
                        String depName = depAndUserlist.get(0).get("depName");
                        String depId = depAndUserlist.get(0).get("depId");
                        getResult(answerParam,userId,userName,depId,depName);
                    }
                }

            }
        }
    }
    private Result getResult(AnswerParam answerParam, String creater, String creater_name, String creater_deptid, String creater_deptname) {
        Answer answer = new Answer();
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

        List<Qsubject> subjectList = subjectService.selectSubjectByQuId(answerParam.getQuId());
        boolean insertOrUpdate = answer.getId() != null && answer.getId() != 0;

        answer.setCreateTime(date);
        answer.setUpdateTime(date);
        answer.setDel(AnswerConstant.DEL_NORMAL);

        String summaryMappingTableId = UUIDGenerator.generateRandomUUIDAndCurrentTimeMillis();
        answer.setSummaryMappingTableId(summaryMappingTableId);
        answerMapper.insert(answer);

        //插入子表
        StringBuffer sqlAns = new StringBuffer();
        sqlAns.append("insert into `").append(question.getTableName()).append("` (");
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

        AnswerIdVo vo = new AnswerIdVo();
        vo.setAnswerId(answer.getId());
        return ResultFactory.success(vo);
    }
}