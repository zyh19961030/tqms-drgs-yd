package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.QuestionCheckedDept;
import com.qu.modules.web.mapper.QuestionCheckedDeptMapper;
import com.qu.modules.web.service.IQuestionCheckedDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 问卷被检查科室关联表
 * @Author: jeecg-boot
 * @Date:   2022-09-18
 * @Version: V1.0
 */
@Service
public class QuestionCheckedDeptServiceImpl extends ServiceImpl<QuestionCheckedDeptMapper, QuestionCheckedDept> implements IQuestionCheckedDeptService {

    @Override
    public List<QuestionCheckedDept> selectByQuId(Integer quId) {
        LambdaQueryWrapper<QuestionCheckedDept> tbDepLambda = new QueryWrapper<QuestionCheckedDept>().lambda();
        tbDepLambda.eq(QuestionCheckedDept::getQuId, quId);
        return this.list(tbDepLambda);
    }


    @Override
    public List<QuestionCheckedDept> selectByQuIdAndDeptId(Integer quId, String checkedDeptId) {
        LambdaQueryWrapper<QuestionCheckedDept> tbDepLambda = new QueryWrapper<QuestionCheckedDept>().lambda();
        tbDepLambda.eq(QuestionCheckedDept::getQuId, quId);
        tbDepLambda.eq(QuestionCheckedDept::getDeptId, checkedDeptId);
        return this.list(tbDepLambda);
    }


}
