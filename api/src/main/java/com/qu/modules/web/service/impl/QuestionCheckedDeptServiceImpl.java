package com.qu.modules.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.QuestionCheckedDeptConstant;
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
    public List<QuestionCheckedDept> selectCheckedDeptByQuId(Integer quId, Integer type) {
        LambdaQueryWrapper<QuestionCheckedDept> tbDepLambda = new QueryWrapper<QuestionCheckedDept>().lambda();
        tbDepLambda.eq(QuestionCheckedDept::getQuId, quId);
        tbDepLambda.eq(QuestionCheckedDept::getType, type);
        return this.list(tbDepLambda);
    }

    @Override
    public List<QuestionCheckedDept> selectCheckedDeptByDeptId(String deptId, Integer type) {
        LambdaQueryWrapper<QuestionCheckedDept> tbDepLambda = new QueryWrapper<QuestionCheckedDept>().lambda();
        tbDepLambda.eq(QuestionCheckedDept::getDeptId, deptId);
        tbDepLambda.eq(QuestionCheckedDept::getType, type);
        return this.list(tbDepLambda);
    }


    @Override
    public List<QuestionCheckedDept> selectCheckedDeptByQuIdAndDeptId(Integer quId, String checkedDeptId) {
        LambdaQueryWrapper<QuestionCheckedDept> tbDepLambda = new QueryWrapper<QuestionCheckedDept>().lambda();
        tbDepLambda.eq(QuestionCheckedDept::getQuId, quId);
        tbDepLambda.eq(QuestionCheckedDept::getDeptId, checkedDeptId);
        tbDepLambda.eq(QuestionCheckedDept::getType, QuestionCheckedDeptConstant.TYPE_CHECKED_DEPT);
        return this.list(tbDepLambda);
    }

    @Override
    public void deleteCheckedDeptByQuId(Integer quId,Integer type) {
        LambdaQueryWrapper<QuestionCheckedDept> tbDepLambda = new QueryWrapper<QuestionCheckedDept>().lambda();
        tbDepLambda.eq(QuestionCheckedDept::getQuId, quId);
        tbDepLambda.eq(QuestionCheckedDept::getType, type);
        this.remove(tbDepLambda);
    }
}
