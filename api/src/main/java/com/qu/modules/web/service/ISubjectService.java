package com.qu.modules.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.param.InsertSubjectParam;
import com.qu.modules.web.param.SubjectEditParam;
import com.qu.modules.web.param.SubjectParam;
import com.qu.modules.web.param.UpdateOrderNumParam;
import com.qu.modules.web.vo.SubjectVo;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date: 2021-03-19
 * @Version: V1.0
 */
public interface ISubjectService extends IService<Qsubject> {

    SubjectVo saveSubject(SubjectParam subjectParam);

    SubjectVo updateQsubjectById(SubjectEditParam subjectEditParam);

    Boolean removeSubjectById(Integer id);

    Boolean updateOrderNum(UpdateOrderNumParam updateOrderNumParam);

    SubjectVo insertSubject(InsertSubjectParam insertSubjectParam);

}
