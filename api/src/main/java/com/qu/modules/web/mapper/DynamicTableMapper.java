package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DynamicTableMapper extends BaseMapper {

    int createDynamicTable(@Param("sql") String sql);

    int insertDynamicTable(@Param("sql") String sql);

    String selectDynamicTable(@Param("sql") String sql);

    int updateDynamicTable(@Param("sql") String sql);

    Map<String,String> selectDynamicTableColumn(@Param("sql") String sql);

    List<Map<String,String>> selectDynamicTableColumnList(@Param("sql") String sql);
}
