package com.qu.modules.web.service;

import java.util.List;

import org.jeecg.common.api.vo.ResultBetter;

import com.qu.modules.web.entity.AnswerCheckUserSet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.param.AnswerCheckUserSetSaveParam;

/**
 * @Description: 检查表的检查人员设置表
 * @Author: jeecg-boot
 * @Date:   2022-11-30
 * @Version: V1.0
 */
public interface IAnswerCheckUserSetService extends IService<AnswerCheckUserSet> {

    ResultBetter saveAnswerCheckUserSet(AnswerCheckUserSetSaveParam param, String deptId);

    List<AnswerCheckUserSet> selectByDeptAndType(String deptId, Integer typeColumn);




}
