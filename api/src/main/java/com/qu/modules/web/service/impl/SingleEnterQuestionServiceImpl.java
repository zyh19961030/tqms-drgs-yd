package com.qu.modules.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.qu.constant.Constant;
import com.qu.constant.QuestionConstant;
import com.qu.modules.web.entity.*;
import com.qu.modules.web.mapper.SingleEnterQuestionMapper;
import com.qu.modules.web.param.IdIntegerParam;
import com.qu.modules.web.param.SingleEnterQuestionAddParam;
import com.qu.modules.web.param.SingleEnterQuestionListParam;
import com.qu.modules.web.service.*;
import com.qu.modules.web.vo.SingleEnterQuestionListVo;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.ResultBetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 录入表单表
 * @Author: jeecg-boot
 * @Date: 2023-05-24
 * @Version: V1.0
 */
@Service
public class SingleEnterQuestionServiceImpl extends ServiceImpl<SingleEnterQuestionMapper, SingleEnterQuestion> implements ISingleEnterQuestionService {

    @Autowired
    private ISingleEnterQuestionColumnService singleEnterQuestionColumnService;

    @Autowired
    private ISingleEnterQuestionSubjectService singleEnterQuestionSubjectService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private ISubjectService subjectService;


    @Override
    public void add(SingleEnterQuestionAddParam param) {
        SingleEnterQuestion singleEnterQuestion = new SingleEnterQuestion();
        singleEnterQuestion.setQuestionId(param.getQuestionId());
        singleEnterQuestion.setDel(Constant.DEL_NORMAL);
        Date date = new Date();
        singleEnterQuestion.setCreateTime(date);
        singleEnterQuestion.setUpdateTime(date);
        this.save(singleEnterQuestion);

        //保存展示列
        List<Integer> columnIdList = param.getColumnId();
        if (CollectionUtil.isNotEmpty(columnIdList)) {
            List<SingleEnterQuestionColumn> saveColumnList = columnIdList.stream().map(columnId -> {
                SingleEnterQuestionColumn column = new SingleEnterQuestionColumn();
                column.setEnterQuestionId(singleEnterQuestion.getId());
                column.setSubjectId(columnId);
                column.setDel(Constant.DEL_NORMAL);
                column.setCreateTime(date);
                column.setUpdateTime(date);
                return column;
            }).collect(Collectors.toList());
            singleEnterQuestionColumnService.saveBatch(saveColumnList);
        }
        //保存填报题目
        List<Integer> subjectIdList = param.getSubjectId();
        if (CollectionUtil.isNotEmpty(subjectIdList)) {
            List<SingleEnterQuestionSubject> saveSubjectList = subjectIdList.stream().map(columnId -> {
                SingleEnterQuestionSubject subject = new SingleEnterQuestionSubject();
                subject.setEnterQuestionId(singleEnterQuestion.getId());
                subject.setSubjectId(columnId);
                subject.setDel(Constant.DEL_NORMAL);
                subject.setCreateTime(date);
                subject.setUpdateTime(date);
                return subject;
            }).collect(Collectors.toList());
            singleEnterQuestionSubjectService.saveBatch(saveSubjectList);
        }
    }

    @Override
    public IPage<SingleEnterQuestionListVo> queryPageList(Page<SingleEnterQuestion> page, SingleEnterQuestionListParam param) {
        LambdaQueryWrapper<Question> lambda = new QueryWrapper<Question>().lambda();
        if (StringUtils.isNotBlank(param.getQuestionName())) {
            lambda.like(Question::getQuName, param.getQuestionName());
        }
        if (StringUtils.isNotBlank(param.getQuestionNameCategoryId())) {
            lambda.eq(Question::getCategoryId, param.getQuestionNameCategoryId());
        }

        if (StringUtils.isNotBlank(param.getWriteDeptId())) {
            lambda.like(Question::getDeptIds, param.getWriteDeptId());
        }
        if (StringUtils.isNotBlank(param.getSeeDeptId())) {
            lambda.like(Question::getSeeDeptIds, param.getSeeDeptId());
        }
        lambda.eq(Question::getDel, QuestionConstant.DEL_NORMAL);
        List<Question> questionList = questionService.list(lambda);
        if (CollectionUtil.isEmpty(questionList)) {
            return new Page<>();
        }
        List<Integer> questionIdList = questionList.stream().map(Question::getId).distinct().collect(Collectors.toList());
        Map<Integer, Question> questionMap = questionList.stream().collect(Collectors.toMap(Question::getId, Function.identity()));

        LambdaQueryWrapper<SingleEnterQuestion> enterQuestionLambdaQueryWrapper = new QueryWrapper<SingleEnterQuestion>().lambda();
        enterQuestionLambdaQueryWrapper.in(SingleEnterQuestion::getQuestionId, questionIdList);
        IPage<SingleEnterQuestion> singleEnterQuestionIPage = this.page(page, enterQuestionLambdaQueryWrapper);

        List<SingleEnterQuestion> singleEnterQuestionList = singleEnterQuestionIPage.getRecords();
        if (CollectionUtil.isEmpty(singleEnterQuestionList)) {
            return new Page<>();
        }

        List<Integer> singleEnterQuestionIdList = singleEnterQuestionList.stream().map(SingleEnterQuestion::getId).distinct().collect(Collectors.toList());
        List<SingleEnterQuestionSubject> singleEnterQuestionSubjectList = singleEnterQuestionSubjectService.selectBySingleEnterQuestionIdList(singleEnterQuestionIdList);
        Map<Integer, List<SingleEnterQuestionSubject>> singleEnterQuestionSubjectMap = singleEnterQuestionSubjectList.stream().collect(Collectors.groupingBy(SingleEnterQuestionSubject::getEnterQuestionId));

        List<Integer> subjectIdList = singleEnterQuestionSubjectList.stream().map(SingleEnterQuestionSubject::getSubjectId).distinct().collect(Collectors.toList());
        List<Qsubject> subjectList = subjectService.selectByIds(subjectIdList);
        Map<Integer, Qsubject> subjectMap = subjectList.stream().collect(Collectors.toMap(Qsubject::getId, Function.identity()));

        ArrayList<SingleEnterQuestionListVo> resList = Lists.newArrayList();
        for (SingleEnterQuestion singleEnterQuestion : singleEnterQuestionList) {
            SingleEnterQuestionListVo vo = new SingleEnterQuestionListVo();
            vo.setId(singleEnterQuestion.getId());
            Integer questionId = singleEnterQuestion.getQuestionId();
            Question question = questionMap.get(questionId);
            vo.setQuestionId(questionId);
            if (Objects.nonNull(question)) {
                vo.setQuestionName(question.getQuName());
            }
            List<SingleEnterQuestionSubject> singleEnterQuestionSubjects = singleEnterQuestionSubjectMap.get(singleEnterQuestion.getId());
            if (CollectionUtil.isNotEmpty(singleEnterQuestionSubjects)) {
                for (int i = 0; i < singleEnterQuestionSubjects.size(); i++) {
                    SingleEnterQuestionSubject singleEnterQuestionSubject = singleEnterQuestionSubjects.get(i);
                    Qsubject qsubject = subjectMap.get(singleEnterQuestionSubject.getSubjectId());
                    if (Objects.nonNull(qsubject)) {
                        if (i == 0) {
                            vo.setSubjectNameOne(qsubject.getSubName());
                        } else if (i == 1) {
                            vo.setSubjectNameTwo(qsubject.getSubName());
                        } else if (i == 2) {
                            vo.setSubjectNameThree(qsubject.getSubName());
                        } else {
                            break;
                        }
                    }
                }
            }

            resList.add(vo);
        }
        IPage<SingleEnterQuestionListVo> singleEnterQuestionListVoPage = new Page<>(page.getCurrent(), page.getSize());
        singleEnterQuestionListVoPage.setTotal(singleEnterQuestionIPage.getTotal());
        singleEnterQuestionListVoPage.setRecords(resList);
        return singleEnterQuestionListVoPage;
    }

    @Override
    public ResultBetter delete(IdIntegerParam param) {
        Integer id = param.getId();
        SingleEnterQuestion byId = this.getById(id);
        if(Objects.isNull(byId)){
            return ResultBetter.error("数据错误");
        }
        byId.setDel(Constant.DEL_DELETED);
        byId.setUpdateTime(new Date());
        this.updateById(byId);
        return ResultBetter.ok();
    }
}
