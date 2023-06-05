package com.qu.modules.web.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.Getdzbxx;
import com.qu.modules.web.mapper.GetdzbxxMapper;
import com.qu.modules.web.service.GetdzbxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@DS("sqlServer")
public class GetdzbxxServiceImpl extends ServiceImpl<GetdzbxxMapper, Getdzbxx> implements GetdzbxxService {

    @Autowired
    private GetdzbxxMapper getdzbxxMapper;


    @Override
    public List<HashMap<Object, Object>> queryDRGIDList(String time_6, String time_7) {
        List<HashMap<Object, Object>> list = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        //获取6天前上报单病种名称集合
        List<String> DRGIDList = getdzbxxMapper.queryDRGID(time_6, time_7);
        if (DRGIDList != null && DRGIDList.size() > 0) {
            for (String DRGID : DRGIDList) {
                //根据单病种名称获取试图数据
                List<Getdzbxx> getdzbxxList = getdzbxxMapper.queryGetdzbxx(DRGID, time_6, time_7);
                //对应试图与tqmsn中单病种子表名称
                if ("CAPC".equals(DRGID)) {
                    DRGID = "CAP";
                }
                map.put("DRGID", DRGID);
                map.put("getdzbxxList", getdzbxxList);
                list.add(map);
            }
        }
        return list;
    }

}
