package com.qu.modules.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.mapper.AnswerMapper;
import com.qu.modules.web.mapper.DynamicTableMapper;
import com.qu.modules.web.mapper.QsubjectMapper;
import com.qu.modules.web.mapper.QuestionMapper;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.param.Answers;
import com.qu.modules.web.pojo.JsonRootBean;
import com.qu.modules.web.service.IAnswerService;
import com.qu.modules.web.vo.AnswerPageVo;
import com.qu.modules.web.vo.AnswerVo;
import com.qu.util.DateUtil;
import com.qu.util.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            StringBuffer sql = new StringBuffer();
            sql.append("insert into answer (qu_id,answer_json,answer_status,creater,creater_name,create_time,creater_deptid,creater_deptname) values (");
            sql.append("'" + answerParam.getQuId() + "',");
            sql.append("'" + JSON.toJSONString(answerParam.getAnswers()) + "',1,");
            sql.append("'" + creater + "','" + creater_name + "',");
            sql.append("'" + DateUtil.date2String(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS) + "',");
            sql.append("'" + creater_deptid + "','" + creater_deptname + "'");
            sql.append(")");
            log.info("-----insert sql:{}", sql.toString());
            dynamicTableMapper.insertDynamicTable(sql.toString());
            //插入答案表
            Answers[] answers = answerParam.getAnswers();
            Map<Integer, String> mapCache = new HashMap<>();
            for (Answers a : answers) {
                mapCache.put(a.getSubId(), a.getSubValue());
            }
            StringBuffer sqlAns = new StringBuffer();
            Question question = questionMapper.selectById(answerParam.getQuId());
            if (question != null) {
                sqlAns.append("insert into " + question.getTableName() + " (");
                List<Qsubject> subjectList = qsubjectMapper.selectSubjectByQuId(answerParam.getQuId());
                for (int i = 0; i < subjectList.size(); i++) {
                    Qsubject qsubject = subjectList.get(i);
                    sqlAns.append(qsubject.getColumnName());
                    if (i < subjectList.size() - 1) {
                        sqlAns.append(",");
                    }
                }
                sqlAns.append(") values (");
                for (int i = 0; i < subjectList.size(); i++) {
                    Qsubject qsubject = subjectList.get(i);
                    sqlAns.append(mapCache.get(qsubject.getId()));
                    if (i < subjectList.size() - 1) {
                        sqlAns.append(",");
                    }
                }
                sqlAns.append(")");
                log.info("-----insert sqlAns:{}", sqlAns.toString());
                dynamicTableMapper.insertDynamicTable(sqlAns.toString());
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
    public AnswerPageVo questionFillInList(Integer pageNo, Integer pageSize) {
        AnswerPageVo answerPageVo = new AnswerPageVo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("startRow", (pageNo - 1) * pageSize);
            params.put("pageSize", pageSize);
            int total = answerMapper.questionFillInListCount(params);
            List<AnswerVo> answerVoList = answerMapper.questionFillInList(params);
            answerPageVo.setTotal(total);
            answerPageVo.setAnswerVoList(answerVoList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return answerPageVo;
    }

    @Override
    public String withdrawEdit(Integer id) {
        String answer = "";
        try {
            int row = answerMapper.updateWithdrawEdit(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return answer;
    }
}
