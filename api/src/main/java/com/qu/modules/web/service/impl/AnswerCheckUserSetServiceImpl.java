package com.qu.modules.web.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.jeecg.common.api.vo.ResultBetter;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.AnswerCheckUserSetConstant;
import com.qu.modules.web.entity.AnswerCheckUserSet;
import com.qu.modules.web.mapper.AnswerCheckUserSetMapper;
import com.qu.modules.web.param.AnswerCheckUserSetSaveParam;
import com.qu.modules.web.service.IAnswerCheckUserSetService;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @Description: 检查表的检查人员设置表
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Service
public class AnswerCheckUserSetServiceImpl extends ServiceImpl<AnswerCheckUserSetMapper, AnswerCheckUserSet> implements IAnswerCheckUserSetService {

    @Override
    public ResultBetter saveAnswerCheckUserSet(AnswerCheckUserSetSaveParam param, String deptId) {
        //类型
        Integer type = param.getType();
        LambdaUpdateWrapper<AnswerCheckUserSet> lambda = new UpdateWrapper<AnswerCheckUserSet>().lambda();

        //先删除旧的 再插入新的
        Date date = new Date();
        AnswerCheckUserSet emptyEntity = new AnswerCheckUserSet();
        if (AnswerCheckUserSetConstant.TYPE_LINE.equals(type)) {
            lambda.eq(AnswerCheckUserSet::getType, AnswerCheckUserSetConstant.TYPE_LINE)
                    .eq(AnswerCheckUserSet::getDeptId, deptId)
                    .set(AnswerCheckUserSet::getDel, AnswerCheckUserSetConstant.DEL_DELETED);
            this.update(emptyEntity, lambda);

            List<String> userIdList = param.getUserId();
            if (CollectionUtil.isNotEmpty(userIdList)) {
                List<AnswerCheckUserSet> saveList = userIdList.stream().map(s -> {
                    AnswerCheckUserSet answerCheckUserSet = new AnswerCheckUserSet();

                    answerCheckUserSet.setUserId(s);
                    answerCheckUserSet.setDeptId(deptId);
                    answerCheckUserSet.setUpdateTime(date);
                    answerCheckUserSet.setCreateTime(date);
                    answerCheckUserSet.setDel(AnswerCheckUserSetConstant.DEL_NORMAL);
                    answerCheckUserSet.setType(AnswerCheckUserSetConstant.TYPE_LINE);
                    return answerCheckUserSet;
                }).collect(Collectors.toList());
                this.saveBatch(saveList);
            }
            return ResultBetter.ok();
        } else if (AnswerCheckUserSetConstant.TYPE_COLUMN.equals(type)) {
            lambda.eq(AnswerCheckUserSet::getType, AnswerCheckUserSetConstant.TYPE_COLUMN).eq(AnswerCheckUserSet::getDeptId, deptId).set(AnswerCheckUserSet::getDel, AnswerCheckUserSetConstant.DEL_DELETED);

            this.update(emptyEntity, lambda);

            List<Integer> quIdList = param.getQuId();
            if (CollectionUtil.isNotEmpty(quIdList)) {
                List<AnswerCheckUserSet> saveList = quIdList.stream().map(q -> {
                    AnswerCheckUserSet answerCheckUserSet = new AnswerCheckUserSet();
                    answerCheckUserSet.setQuId(q);
                    answerCheckUserSet.setDeptId(deptId);
                    answerCheckUserSet.setUpdateTime(date);
                    answerCheckUserSet.setCreateTime(date);
                    answerCheckUserSet.setDel(AnswerCheckUserSetConstant.DEL_NORMAL);
                    answerCheckUserSet.setType(AnswerCheckUserSetConstant.TYPE_COLUMN);
                    return answerCheckUserSet;
                }).collect(Collectors.toList());
                this.saveBatch(saveList);
            }
            return ResultBetter.ok();
        }
        return ResultBetter.error("保存失败");
    }
}
