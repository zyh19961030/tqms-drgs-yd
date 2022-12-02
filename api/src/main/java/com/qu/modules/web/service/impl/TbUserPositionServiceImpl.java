package com.qu.modules.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.TbUserPosition;
import com.qu.modules.web.mapper.TbUserPositionMapper;
import com.qu.modules.web.service.ITbUserPositionService;

@Service
public class TbUserPositionServiceImpl extends ServiceImpl<TbUserPositionMapper, TbUserPosition> implements ITbUserPositionService {

    @Override
    public List<TbUserPosition> getByPositionId(String positionId) {
        LambdaQueryWrapper<TbUserPosition> lambda = new QueryWrapper<TbUserPosition>().lambda();
        lambda.eq(TbUserPosition::getPositionId,positionId);
        return this.list(lambda);
    }
}
