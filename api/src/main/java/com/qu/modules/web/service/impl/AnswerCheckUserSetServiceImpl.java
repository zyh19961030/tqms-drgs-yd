package com.qu.modules.web.service.impl;

import java.util.Date;
import java.util.List;

import org.jeecg.common.api.vo.ResultBetter;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
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
                List<AnswerCheckUserSet> saveList = Lists.newArrayList();
                for (int i = 0; i < userIdList.size(); i++) {
                    AnswerCheckUserSet answerCheckUserSet = new AnswerCheckUserSet();
                    answerCheckUserSet.setUserId(userIdList.get(i));
                    answerCheckUserSet.setDeptId(deptId);
                    answerCheckUserSet.setUpdateTime(date);
                    answerCheckUserSet.setCreateTime(date);
                    answerCheckUserSet.setDel(AnswerCheckUserSetConstant.DEL_NORMAL);
                    answerCheckUserSet.setType(AnswerCheckUserSetConstant.TYPE_LINE);
                    answerCheckUserSet.setSortNumber(i+1);
                    saveList.add(answerCheckUserSet);
                }
                this.saveBatch(saveList);
            }
            return ResultBetter.ok();
        } else if (AnswerCheckUserSetConstant.TYPE_COLUMN.equals(type)) {
            lambda.eq(AnswerCheckUserSet::getType, AnswerCheckUserSetConstant.TYPE_COLUMN).eq(AnswerCheckUserSet::getDeptId, deptId).set(AnswerCheckUserSet::getDel, AnswerCheckUserSetConstant.DEL_DELETED);

            this.update(emptyEntity, lambda);

            List<Integer> quIdList = param.getQuId();
            if (CollectionUtil.isNotEmpty(quIdList)) {
                List<AnswerCheckUserSet> saveList = Lists.newArrayList();
                for (int i = 0; i < quIdList.size(); i++) {
                    AnswerCheckUserSet answerCheckUserSet = new AnswerCheckUserSet();
                    answerCheckUserSet.setQuId(quIdList.get(i));
                    answerCheckUserSet.setDeptId(deptId);
                    answerCheckUserSet.setUpdateTime(date);
                    answerCheckUserSet.setCreateTime(date);
                    answerCheckUserSet.setDel(AnswerCheckUserSetConstant.DEL_NORMAL);
                    answerCheckUserSet.setType(AnswerCheckUserSetConstant.TYPE_COLUMN);
                    answerCheckUserSet.setSortNumber(i+1);
                    saveList.add(answerCheckUserSet);
                }
                this.saveBatch(saveList);
            }
            return ResultBetter.ok();
        }
        return ResultBetter.error("保存失败");
    }

    @Override
    public List<AnswerCheckUserSet> selectByDeptAndType(String deptId, Integer typeColumn) {
        LambdaUpdateWrapper<AnswerCheckUserSet> lambda = new UpdateWrapper<AnswerCheckUserSet>().lambda();
        lambda.eq(AnswerCheckUserSet::getDeptId,deptId);
        lambda.eq(AnswerCheckUserSet::getType,typeColumn);
        lambda.eq(AnswerCheckUserSet::getDel, AnswerCheckUserSetConstant.DEL_NORMAL);
        lambda.orderByAsc(AnswerCheckUserSet::getSortNumber);
        return this.list(lambda);
    }


}
