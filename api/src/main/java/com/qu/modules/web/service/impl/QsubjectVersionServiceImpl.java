package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.QoptionConstant;
import com.qu.modules.web.entity.QsubjectVersion;
import com.qu.modules.web.mapper.QsubjectVersionMapper;
import com.qu.modules.web.service.IQsubjectVersionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 题目版本表
 * @Author: jeecg-boot
 * @Date:   2022-09-25
 * @Version: V1.0
 */
@Service
public class QsubjectVersionServiceImpl extends ServiceImpl<QsubjectVersionMapper, QsubjectVersion> implements IQsubjectVersionService {

    @Override
    public List<QsubjectVersion> selectSubjectVersionByQuIdAndVersion(Integer quId, String questionVersionId) {
        LambdaQueryWrapper<QsubjectVersion> lambda = new QueryWrapper<QsubjectVersion>().lambda();
        lambda.in(QsubjectVersion::getQuId,quId);
        lambda.in(QsubjectVersion::getQuestionVersionId, questionVersionId);
        lambda.in(QsubjectVersion::getDel, QoptionConstant.DEL_NORMAL);
        return this.list(lambda);
    }
}
