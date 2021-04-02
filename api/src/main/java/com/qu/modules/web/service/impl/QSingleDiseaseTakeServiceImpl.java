package com.qu.modules.web.service.impl;

import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.qu.modules.web.mapper.AnswerMapper;
import com.qu.modules.web.mapper.QSingleDiseaseTakeMapper;
import com.qu.modules.web.service.IQSingleDiseaseTakeService;
import com.qu.modules.web.vo.QSingleDiseaseTakeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date:   2021-04-02
 * @Version: V1.0
 */
@Service
public class QSingleDiseaseTakeServiceImpl extends ServiceImpl<QSingleDiseaseTakeMapper, QSingleDiseaseTake> implements IQSingleDiseaseTakeService {

    @Autowired
    private QSingleDiseaseTakeMapper qSingleDiseaseTakeMapper;

    @Override
    public List<QSingleDiseaseTakeVo> singleDiseaseList(String name) {
        return qSingleDiseaseTakeMapper.singleDiseaseList(name);
    }
}
