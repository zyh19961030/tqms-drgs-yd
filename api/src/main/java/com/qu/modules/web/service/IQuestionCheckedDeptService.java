package com.qu.modules.web.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.QuestionCheckedDept;

/**
 * @Description: 问卷被检查科室关联表
 * @Author: jeecg-boot
 * @Date:   2022-09-18
 * @Version: V1.0
 */
public interface IQuestionCheckedDeptService extends IService<QuestionCheckedDept> {

    List<QuestionCheckedDept> selectCheckedDeptByQuId(Integer quId);

    List<QuestionCheckedDept> selectCheckedDeptByQuIdAndDeptId(Integer quId, String checkedDeptId);

    void deleteCheckedDeptByQuId(Integer quId);

}
