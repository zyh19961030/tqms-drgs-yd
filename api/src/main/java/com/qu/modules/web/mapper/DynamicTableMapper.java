package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface DynamicTableMapper extends BaseMapper {

    int createDynamicTable(@Param("sql") String sql);

    int insertDynamicTable(@Param("sql") String sql);
}
