package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AdminPrivateParam;
import com.qu.modules.web.param.AdminPrivateUpdateTableDrugFeeParam;

public interface IAdminPrivateService extends IService<Answer> {


    boolean updateTableDate(AdminPrivateParam adminPrivateParam);

    boolean updateTableDrugFee(AdminPrivateUpdateTableDrugFeeParam adminPrivateUpdateTableDrugFeeParam);

}
