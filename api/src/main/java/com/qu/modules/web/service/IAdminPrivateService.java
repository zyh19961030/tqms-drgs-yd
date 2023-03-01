package com.qu.modules.web.service;

import org.jeecg.common.api.vo.Result;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AdminPrivateParam;
import com.qu.modules.web.param.AdminPrivateUpdateOptionValueParam;
import com.qu.modules.web.param.AdminPrivateUpdateTableAddDelFeeParam;
import com.qu.modules.web.param.AdminPrivateUpdateTableDrugFeeParam;

public interface IAdminPrivateService extends IService<Answer> {


    boolean updateTableDate(AdminPrivateParam adminPrivateParam);

    boolean updateTableDrugFee(AdminPrivateUpdateTableDrugFeeParam adminPrivateUpdateTableDrugFeeParam);

    Result updateOptionValue(AdminPrivateUpdateOptionValueParam adminPrivateUpdateOptionValueParam);

    Result updateTableAddDel(AdminPrivateUpdateTableAddDelFeeParam param);

    Result updateAnswerCheckCaseId(AdminPrivateUpdateTableDrugFeeParam param);

    Result updateAnswerOutTime(AdminPrivateUpdateTableDrugFeeParam param);

    Result updateTableAddMark(AdminPrivateUpdateTableDrugFeeParam param);



}
