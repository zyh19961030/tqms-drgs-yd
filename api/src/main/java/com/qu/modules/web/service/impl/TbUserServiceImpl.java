package com.qu.modules.web.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jeecg.common.api.vo.ResultBetter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.constant.AnswerCheckUserSetConstant;
import com.qu.constant.Constant;
import com.qu.modules.web.entity.AnswerCheckUserSet;
import com.qu.modules.web.entity.TbUser;
import com.qu.modules.web.entity.TbUserPosition;
import com.qu.modules.web.mapper.TbUserMapper;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.IAnswerCheckUserSetService;
import com.qu.modules.web.service.ITbUserPositionService;
import com.qu.modules.web.service.ITbUserService;
import com.qu.modules.web.vo.QuestionSetLineAllVo;
import com.qu.modules.web.vo.QuestionSetLineChooseVo;
import com.qu.modules.web.vo.QuestionSetLineVo;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2022-11-16
 * @Version: V1.0
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {

    @Autowired
    private IAnswerCheckUserSetService answerCheckUserSetService;

    @Autowired
    private ITbUserPositionService tbUserPositionService;

    @Override
    public ResultBetter<QuestionSetLineVo> setLine(Data data) {
        String deptId = data.getDeps().get(0).getId();
        String userId = data.getTbUser().getId();

        //职位
        LambdaQueryWrapper<TbUser> lambda = new QueryWrapper<TbUser>().lambda();
        String positionCode = data.getPositions().get(0).getTbPosition().getPositionCode();
        if (positionCode.contains(Constant.POSITION_CODE_ZNKS)) {
            //		 1、如果登录账号是职能科室（无论是科主任还是科室干事），都返回本科室的全部人员（除科主任以外）和本科室全部填报的查检表
            lambda.eq(TbUser::getDepid,deptId);
            lambda.ne(TbUser::getId,userId);
        } else if (positionCode.contains(Constant.POSITION_CODE_LCKSZR)) {
            //		 2、如果登录账号是临床科室主任，返回本科的全部医生（职位是LCKSZKY），和本科室全部医疗类型的能填报的查检表
            List<TbUserPosition> byPositionIds = tbUserPositionService.getByPositionId(Constant.POSITION_CODE_LCKSZKY);
            List<String> userIdListByPosition = byPositionIds.stream().map(TbUserPosition::getUserid).distinct().collect(Collectors.toList());
            lambda.in(TbUser::getId,userIdListByPosition);
            lambda.eq(TbUser::getDepid,deptId);
            LambdaQueryWrapper<TbUser> or = lambda.or();
            or.eq(TbUser::getDepid,deptId);
            or.eq(TbUser::getPositionid,Constant.POSITION_CODE_LCKSZKY);
        } else if (positionCode.contains(Constant.POSITION_CODE_LCKHSZ)) {
            //		 3、如果登录账号是临床科室护士长，返回本科的全部护士（职位是LCKSZKYHL），和本科室全部护理类型的能填报的查检表
            List<TbUserPosition> byPositionIds = tbUserPositionService.getByPositionId(Constant.POSITION_CODE_LCKSZKYHL);
            List<String> userIdListByPosition = byPositionIds.stream().map(TbUserPosition::getUserid).distinct().collect(Collectors.toList());
            lambda.in(TbUser::getId,userIdListByPosition);
            lambda.eq(TbUser::getDepid,deptId);
            LambdaQueryWrapper<TbUser> or = lambda.or();
            or.eq(TbUser::getDepid,deptId);
            or.eq(TbUser::getPositionid,Constant.POSITION_CODE_LCKSZKY);
        }else{
            //		 4、其他账号登录，本页面无返回
            return ResultBetter.ok();
        }
        lambda.eq(TbUser::getIsdelete, Constant.IS_DELETE_NO);
        List<TbUser> tbUserList = this.list(lambda);
        List<QuestionSetLineAllVo> questionSetLineAllVoList = tbUserList.stream().map(u -> {
            QuestionSetLineAllVo vo = new QuestionSetLineAllVo();
            BeanUtils.copyProperties(u, vo);
            vo.setUserName(u.getUsername());
            return vo;
        }).collect(Collectors.toList());
        Map<String, TbUser> tbUserMap = tbUserList.stream().collect(Collectors.toMap(TbUser::getId, Function.identity()));

        //选择的数据
        List<AnswerCheckUserSet> answerCheckUserSetList = answerCheckUserSetService.selectByDeptAndType(deptId, AnswerCheckUserSetConstant.TYPE_LINE);
        List<QuestionSetLineChooseVo> answerCheckUserChooseList = answerCheckUserSetList.stream().map(answerCheckUserSet -> {
            QuestionSetLineChooseVo vo = new QuestionSetLineChooseVo();
            String answerCheckUserSetUserId = answerCheckUserSet.getUserId();
            vo.setId(answerCheckUserSetUserId);
            TbUser tbUser = tbUserMap.get(answerCheckUserSetUserId);
            if(tbUser!=null){
                vo.setUserName(tbUser.getUsername());
            }
            return vo;
        }).collect(Collectors.toList());

        QuestionSetLineVo build = QuestionSetLineVo.builder().allData(questionSetLineAllVoList).chooseData(answerCheckUserChooseList).build();
        return ResultBetter.ok(build);
    }
}
