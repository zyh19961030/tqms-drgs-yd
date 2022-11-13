package com.qu.event;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qu.constant.QuestionConstant;
import com.qu.modules.web.entity.*;
import com.qu.modules.web.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class QuestionVersionEventListener implements ApplicationListener<QuestionVersionEvent> {

    @Lazy
    @Resource
    private IQuestionService questionService;

    @Resource
    private ISubjectService subjectService;

    @Resource
    private IOptionService optionService;

    @Resource
    private IQuestionVersionService questionVersionService;

    @Resource
    private IQsubjectVersionService subjectVersionService;

    @Resource
    private IQoptionVersionService optionVersionService;

    @Async
    @Override
    public void onApplicationEvent(QuestionVersionEvent event) {
        Integer quId = event.getQuId();
        Question question = questionService.getById(quId);
        if (question == null || QuestionConstant.DEL_DELETED.equals(question.getDel())) {
            return;
        }

        Date date = new Date();
        QuestionVersion questionVersion = new QuestionVersion();
        BeanUtils.copyProperties(question,questionVersion);
        questionVersion.setQuId(quId);
        questionVersion.setCurrentCreateTime(date);
        questionVersionService.save(questionVersion);
        String questionVersionId = questionVersion.getId();

        List<Qsubject> subjectList = subjectService.selectSubjectByQuId(quId);
        List<QsubjectVersion> qsubjectVersionList = Lists.newArrayList();
        HashMap<Integer, String> qsubjectMap = Maps.newHashMap();
        for (Qsubject qsubject : subjectList) {
            QsubjectVersion qsubjectVersion = new QsubjectVersion();
            BeanUtils.copyProperties(qsubject,qsubjectVersion);
            qsubjectVersion.setQuestionVersionId(questionVersionId);
            qsubjectVersion.setQuId(quId);
            qsubjectVersion.setSubjectId(qsubject.getId());
            qsubjectVersion.setCurrentCreateTime(date);
            String qsubjectId = UUID.randomUUID().toString().replaceAll("-", "");
            qsubjectVersion.setId(qsubjectId);
            qsubjectMap.put(qsubject.getId(),qsubjectId);
            qsubjectVersionList.add(qsubjectVersion);
        }
        subjectVersionService.saveBatch(qsubjectVersionList);

        List<QoptionVersion> qoptionVersionList = Lists.newArrayList();
        List<Integer> subjectIdList = subjectList.stream().map(Qsubject::getId).distinct().collect(Collectors.toList());
        List<Qoption> qoptions = optionService.selectBySubjectList(subjectIdList);
        for (Qoption qoption : qoptions) {
            QoptionVersion qoptionVersion = new QoptionVersion();
            BeanUtils.copyProperties(qoption,qoptionVersion);
            qoptionVersion.setQuestionVersionId(questionVersionId);
            qoptionVersion.setQuestionId(quId);
            qoptionVersion.setSubjectId(qoption.getSubId());
            qoptionVersion.setOptionId(qoption.getId());
            qoptionVersion.setCurrentCreateTime(date);

            String qsubjectId = qsubjectMap.get(qoption.getSubId());
            qoptionVersion.setSubjectVersionId(qsubjectId);

            qoptionVersionList.add(qoptionVersion);
        }
        optionVersionService.saveBatch(qoptionVersionList);
    }
}
