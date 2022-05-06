package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AdminPrivateParam;

public interface IAdminPrivateService extends IService<Answer> {


    boolean updateTableDate(AdminPrivateParam adminPrivateParam);


}
