package com.qu.modules.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qu.modules.web.entity.Getdzbxx;

import java.util.List;

public interface GetdzbxxMapper extends BaseMapper<Getdzbxx> {

    List<Getdzbxx> queryGetdzbxx(String DRGID, String time_6, String time_7);

    List<String> queryDRGID(String time_6, String time_7);

}
