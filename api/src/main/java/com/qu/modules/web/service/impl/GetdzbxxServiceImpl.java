package com.qu.modules.web.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qu.modules.web.entity.Getdzbxx;
import com.qu.modules.web.mapper.GenTableColumnMapper;
import com.qu.modules.web.mapper.GetdzbxxMapper;
import com.qu.modules.web.mapper.QsubjectMapper;
import com.qu.modules.web.service.GetdzbxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class GetdzbxxServiceImpl extends ServiceImpl<GetdzbxxMapper, Getdzbxx> implements GetdzbxxService {

    @Autowired
    private GetdzbxxMapper getdzbxxMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private QsubjectMapper qsubjectMapper;

    @Override
    public void queryGetdzbxxInsertDrgs() {
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -5);
        Date date_6 = calendar.getTime();
        String time_6 = formatter.format(date_6);
        calendar.add(Calendar.DATE, -1);
        Date date_7 = calendar.getTime();
        String time_7 = formatter.format(date_7);
        //取视图中6天前上报的数据
        HashMap map = new HashMap<>();
        List<String> columnNameList = new ArrayList<>();
        List<String> DRGIDList = getdzbxxMapper.queryDRGID(time_6, time_7);
        //拆分为单个病种
        for (String DRGID : DRGIDList) {
            List<Getdzbxx> getdzbxxList = getdzbxxMapper.queryGetdzbxx(DRGID, time_6, time_7);
            if ("CAPC".equals(DRGID)) {
                DRGID = "CAP";
            }
            //获取TQMS中单病种子表的字段
            columnNameList = qsubjectMapper.querySubColumnNameByTableName("DRGS_"+DRGID);
            for (Getdzbxx getdzbxx : getdzbxxList) {
                String colid = getdzbxx.getCOLID();
                String colcode = getdzbxx.getCOLCODE();
                //只解析存在于TQMS子表字段中的数据
                if (columnNameList.contains(colid)){
                    map.put("`"+colid+"`", colcode);
                }
            }
            genTableColumnMapper.insertData(map, "drgs_"+DRGID.toLowerCase());
            map.clear();
        }
    }

}
