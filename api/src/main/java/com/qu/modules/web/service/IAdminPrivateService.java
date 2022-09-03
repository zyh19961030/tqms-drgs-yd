package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AdminPrivateParam;
import com.qu.modules.web.param.AdminPrivateUpdateOptionValueParam;
import com.qu.modules.web.param.AdminPrivateUpdateTableDrugFeeParam;
import org.jeecg.common.api.vo.Result;

public interface IAdminPrivateService extends IService<Answer> {


    boolean updateTableDate(AdminPrivateParam adminPrivateParam);

    boolean updateTableDrugFee(AdminPrivateUpdateTableDrugFeeParam adminPrivateUpdateTableDrugFeeParam);

    Result updateOptionValue(AdminPrivateUpdateOptionValueParam adminPrivateUpdateOptionValueParam);

}
