package com.qu.modules.web.service.impl;

import com.qu.modules.web.mapper.DynamicTableMapper;
import com.qu.modules.web.service.IAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnswerServiceImpl implements IAnswerService {

    @Autowired
    private DynamicTableMapper dynamicTableMapper;

    @Override
    public int createDynamicTable(String sql) {
        return dynamicTableMapper.createDynamicTable(sql);
    }

    @Override
    public int insertDynamicTable(String sql) {
        return dynamicTableMapper.insertDynamicTable(sql);
    }
}
