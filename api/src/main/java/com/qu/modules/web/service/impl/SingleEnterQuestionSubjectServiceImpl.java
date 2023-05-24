package com.qu.modules.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.qu.modules.web.entity.SingleEnterQuestionSubject;
import com.qu.modules.web.mapper.SingleEnterQuestionSubjectMapper;
import com.qu.modules.web.service.ISingleEnterQuestionSubjectService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 录入表单填报题目表
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Service
public class SingleEnterQuestionSubjectServiceImpl extends ServiceImpl<SingleEnterQuestionSubjectMapper, SingleEnterQuestionSubject> implements ISingleEnterQuestionSubjectService {

    @Override
    public List<SingleEnterQuestionSubject> selectBySingleEnterQuestionIdList(List<Integer> singleEnterQuestionIdList) {
        if(CollectionUtil.isEmpty(singleEnterQuestionIdList)){
            return Lists.newArrayList();
        }
        return this.baseMapper.selectBatchIds(singleEnterQuestionIdList);
    }
}
