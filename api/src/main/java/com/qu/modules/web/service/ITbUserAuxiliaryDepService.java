package com.qu.modules.web.service;

import com.qu.modules.web.entity.TbUserAuxiliaryDep;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 用户辅助科室表
 * @Author: jeecg-boot
 * @Date:   2022-11-16
 * @Version: V1.0
 */
public interface ITbUserAuxiliaryDepService extends IService<TbUserAuxiliaryDep> {

    TbUserAuxiliaryDep selectByUserIdAndDepId(String userId, String deptId);

}
