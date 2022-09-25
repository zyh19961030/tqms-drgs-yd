package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.QoptionConstant;
import com.qu.modules.web.entity.QuestionVersion;
import com.qu.modules.web.mapper.QuestionVersionMapper;
import com.qu.modules.web.service.IQuestionVersionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 问卷版本表
 * @Author: jeecg-boot
 * @Date:   2022-09-25
 * @Version: V1.0
 */
@Service
public class QuestionVersionServiceImpl extends ServiceImpl<QuestionVersionMapper, QuestionVersion> implements IQuestionVersionService {

    @Override
    public QuestionVersion selectByQuestionAndVersion(Integer quId, Integer questionVersionNumber) {
        LambdaQueryWrapper<QuestionVersion> lambda = new QueryWrapper<QuestionVersion>().lambda();
        lambda.in(QuestionVersion::getQuId,quId);
        lambda.in(QuestionVersion::getQuestionVersion, questionVersionNumber);
        lambda.in(QuestionVersion::getDel, QoptionConstant.DEL_NORMAL);
        List<QuestionVersion> questionVersions = this.list(lambda);
        return questionVersions.isEmpty()?null:questionVersions.get(0);
    }
}
