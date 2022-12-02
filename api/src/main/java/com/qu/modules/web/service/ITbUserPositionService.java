package com.qu.modules.web.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.TbUserPosition;


public interface ITbUserPositionService extends IService<TbUserPosition> {


    List<TbUserPosition> getByPositionId(String positionId);

}
