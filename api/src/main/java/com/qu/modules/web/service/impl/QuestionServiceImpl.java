package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.Qoption;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.mapper.DynamicTableMapper;
import com.qu.modules.web.mapper.OptionMapper;
import com.qu.modules.web.mapper.QsubjectMapper;
import com.qu.modules.web.mapper.QuestionMapper;
import com.qu.modules.web.param.QuestionEditParam;
import com.qu.modules.web.param.QuestionParam;
import com.qu.modules.web.param.UpdateDeptIdsParam;
import com.qu.modules.web.service.IQuestionService;
import com.qu.modules.web.vo.QuestionPageVo;
import com.qu.modules.web.vo.QuestionVo;
import com.qu.modules.web.vo.SubjectVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: 问卷表
 * @Author: jeecg-boot
 * @Date: 2021-03-19
 * @Version: V1.0
 */
@Slf4j
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QsubjectMapper subjectMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private DynamicTableMapper dynamicTableMapper;

    @Override
    public Question saveQuestion(QuestionParam questionParam) {
        Question question = new Question();
        try {
            BeanUtils.copyProperties(questionParam, question);
            question.setQuStatus(0);
            question.setQuStop(0);
            question.setDel(0);
            question.setCreater(1);
            question.setCreateTime(new Date());
            question.setUpdater(1);
            question.setUpdateTime(new Date());
            questionMapper.insert(question);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return question;
    }

    @Override
    public QuestionVo queryById(Integer id) {
        QuestionVo questionVo = new QuestionVo();
        try {
            Question question = questionMapper.selectById(id);
            BeanUtils.copyProperties(question, questionVo);
            List<Qsubject> subjectList = subjectMapper.selectSubjectByQuId(id);
            List<SubjectVo> subjectVoList = new ArrayList<>();
            for (Qsubject subject : subjectList) {
                SubjectVo subjectVo = new SubjectVo();
                BeanUtils.copyProperties(subject, subjectVo);
                List<Qoption> qoptionList = optionMapper.selectQoptionBySubId(subject.getId());
                subjectVo.setOptionList(qoptionList);
                subjectVoList.add(subjectVo);
            }
            //开始组装分组题逻辑
            //先缓存
            Map<Integer, SubjectVo> mapCache = new HashMap<>();
            for (SubjectVo subjectVo : subjectVoList) {
                mapCache.put(subjectVo.getId(), subjectVo);
            }
            //开始算
            StringBuffer groupIdsAll = new StringBuffer();
            for (SubjectVo subjectVo : subjectVoList) {
                //如果是分组题
                if (subjectVo.getSubType().equals("8")) {
                    String groupIds = subjectVo.getGroupIds();//包含题号
                    if (null != groupIds) {
                        String[] gids = groupIds.split(",");
                        List<SubjectVo> subjectVoGroupList = new ArrayList<>();
                        for (String subId : gids) {
                            groupIdsAll.append(subId);
                            groupIdsAll.append(",");
                            SubjectVo svo = mapCache.get(Integer.parseInt(subId));
                            subjectVoGroupList.add(svo);
                        }
                        //设置到分组题对象列表
                        subjectVo.setSubjectVoList(subjectVoGroupList);
                    }
                }
            }
            //删除在subjectVoList集合中删除groupIdsAll包含的题
            if (groupIdsAll.length() != 0) {
                String removeIds = groupIdsAll.toString();
                String[] remIds = removeIds.split(",");
                if (null != remIds && remIds.length > 0) {
                    for (int i = 0; i < subjectVoList.size(); i++) {
                        //for (SubjectVo subjectVo : subjectVoList) {
                        SubjectVo subjectVo = subjectVoList.get(i);
                        Integer nowId = subjectVo.getId();
                        for (String remId : remIds) {
                            if (nowId == Integer.parseInt(remId)) {
                                subjectVoList.remove(i);//移除
                                i--;
                            }
                        }

                    }
                }
            }
            questionVo.setSubjectVoList(subjectVoList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return questionVo;
    }

    @Override
    public Question updateQuestionById(QuestionEditParam questionEditParam) {
        Question question = new Question();
        try {
            BeanUtils.copyProperties(questionEditParam, question);
            question.setUpdater(1);
            question.setUpdateTime(new Date());
            questionMapper.updateById(question);
            question = questionMapper.selectById(questionEditParam.getId());
            //如果是已发布，建表
            if (question.getQuStatus() == 1) {
                StringBuffer sql = new StringBuffer();
                sql.append("CREATE TABLE `" + question.getTableName() + "` (");
                sql.append("`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',");
                List<Qsubject> subjectList = subjectMapper.selectSubjectByQuId(questionEditParam.getId());
                for (Qsubject qsubject : subjectList) {
                    sql.append("`" + qsubject.getColumnName() + "` varchar(2000) COMMENT '" + qsubject.getSubName() + "',");
                }
                sql.append(" PRIMARY KEY (`id`)");
                sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
                dynamicTableMapper.createDynamicTable(sql.toString());
            }
        } catch (Exception e) {
            question = null;
            log.error(e.getMessage(), e);
        }
        return question;
    }

    @Override
    public boolean removeQuestionById(Integer id) {
        boolean delFlag = true;
        try {
            Question question = new Question();
            question.setId(id);
            question.setDel(1);
            question.setUpdater(1);
            question.setUpdateTime(new Date());
            questionMapper.updateById(question);
        } catch (Exception e) {
            delFlag = false;
            log.error(e.getMessage(), e);
        }
        return delFlag;
    }

    @Override
    public QuestionPageVo queryPageList(QuestionParam questionParam, Integer pageNo, Integer pageSize) {
        QuestionPageVo questionPageVo = new QuestionPageVo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("quName", questionParam.getQuName());
            params.put("quDesc", questionParam.getQuDesc());
            params.put("startRow", (pageNo - 1) * pageSize);
            params.put("pageSize", pageSize);
            int total = questionMapper.queryPageListCount(params);
            List<Question> questionList = questionMapper.queryPageList(params);
            questionPageVo.setTotal(total);
            questionPageVo.setQuestionList(questionList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return questionPageVo;
    }

    @Override
    public QuestionPageVo questionFillInList(QuestionParam questionParam, Integer pageNo, Integer pageSize) {
        QuestionPageVo questionPageVo = new QuestionPageVo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("quName", questionParam.getQuName());
            params.put("quDesc", questionParam.getQuDesc());
            params.put("startRow", (pageNo - 1) * pageSize);
            params.put("pageSize", pageSize);
            int total = questionMapper.questionFillInListCount(params);
            List<Question> questionList = questionMapper.questionFillInList(params);
            questionPageVo.setTotal(total);
            questionPageVo.setQuestionList(questionList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return questionPageVo;
    }

    @Override
    public Boolean updateDeptIdsParam(UpdateDeptIdsParam updateDeptIdsParam) {
        Boolean flag = true;
        try {
            String[] quIds = updateDeptIdsParam.getQuIds();
            String[] deptIds = updateDeptIdsParam.getDeptIds();
            if (quIds != null && deptIds != null) {
                StringBuffer deptid = new StringBuffer();
                for (String did : deptIds) {
                    deptid.append(did);
                    deptid.append(",");
                }
                ///更新
                for (String qid : quIds) {
                    Question question = new Question();
                    question.setId(Integer.parseInt(qid));
                    question.setDeptIds(deptid.toString());
                    question.setUpdateTime(new Date());
                    questionMapper.updateById(question);
                }
            }
        } catch (Exception e) {
            flag = false;
            log.error(e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public QuestionVo queryPersonById(Integer id) {
        QuestionVo questionVo = new QuestionVo();
        try {
            Question question = questionMapper.selectById(id);
            BeanUtils.copyProperties(question, questionVo);
            List<Qsubject> subjectList = subjectMapper.selectPersonSubjectByQuId(id);
            List<SubjectVo> subjectVoList = new ArrayList<>();
            for (Qsubject subject : subjectList) {
                SubjectVo subjectVo = new SubjectVo();
                BeanUtils.copyProperties(subject, subjectVo);
                List<Qoption> qoptionList = optionMapper.selectQoptionBySubId(subject.getId());
                subjectVo.setOptionList(qoptionList);
                subjectVoList.add(subjectVo);
            }
            //开始组装分组题逻辑
            //先缓存
            Map<Integer, SubjectVo> mapCache = new HashMap<>();
            for (SubjectVo subjectVo : subjectVoList) {
                mapCache.put(subjectVo.getId(), subjectVo);
            }
            //开始算
            StringBuffer groupIdsAll = new StringBuffer();
            for (SubjectVo subjectVo : subjectVoList) {
                //如果是分组题
                if (subjectVo.getSubType().equals("8")) {
                    String groupIds = subjectVo.getGroupIds();//包含题号
                    if (null != groupIds) {
                        String[] gids = groupIds.split(",");
                        List<SubjectVo> subjectVoGroupList = new ArrayList<>();
                        for (String subId : gids) {
                            groupIdsAll.append(subId);
                            groupIdsAll.append(",");
                            SubjectVo svo = mapCache.get(Integer.parseInt(subId));
                            subjectVoGroupList.add(svo);
                        }
                        //设置到分组题对象列表
                        subjectVo.setSubjectVoList(subjectVoGroupList);
                    }
                }
            }
            //删除在subjectVoList集合中删除groupIdsAll包含的题
            if (groupIdsAll.length() != 0) {
                String removeIds = groupIdsAll.toString();
                String[] remIds = removeIds.split(",");
                if (null != remIds && remIds.length > 0) {
                    for (int i = 0; i < subjectVoList.size(); i++) {
                        //for (SubjectVo subjectVo : subjectVoList) {
                        SubjectVo subjectVo = subjectVoList.get(i);
                        Integer nowId = subjectVo.getId();
                        for (String remId : remIds) {
                            if (nowId == Integer.parseInt(remId)) {
                                subjectVoList.remove(i);//移除
                                i--;
                            }
                        }

                    }
                }
            }
            questionVo.setSubjectVoList(subjectVoList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return questionVo;
    }
}
