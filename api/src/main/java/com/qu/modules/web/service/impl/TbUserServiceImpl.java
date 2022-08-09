package com.qu.modules.web.service.impl;

import com.qu.modules.web.entity.TbUser;
import com.qu.modules.web.mapper.TbUserMapper;
import com.qu.modules.web.service.ITbUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2022-08-09
 * @Version: V1.0
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {

}
