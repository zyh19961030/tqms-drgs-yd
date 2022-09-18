package com.qu.modules.web.service;

import com.qu.modules.web.entity.QuestionCheckedDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 问卷被检查科室关联表
 * @Author: jeecg-boot
 * @Date:   2022-09-18
 * @Version: V1.0
 */
public interface IQuestionCheckedDeptService extends IService<QuestionCheckedDept> {

    List<QuestionCheckedDept> selectByQuId(Integer quId);

    List<QuestionCheckedDept> selectByQuIdAndDeptId(Integer quId, String checkedDeptId);

}
