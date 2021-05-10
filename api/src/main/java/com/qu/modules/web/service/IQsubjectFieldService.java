package com.qu.modules.web.service;

import com.qu.modules.web.entity.QsubjectField;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 题目字段表
 * @Author: jeecg-boot
 * @Date:   2021-05-10
 * @Version: V1.0
 */
public interface IQsubjectFieldService extends IService<QsubjectField> {

    QsubjectField getBySubjectName(String subjectName);

}
