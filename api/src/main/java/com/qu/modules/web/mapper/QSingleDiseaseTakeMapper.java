package com.qu.modules.web.mapper;

import java.util.List;

import com.qu.modules.web.vo.QSingleDiseaseTakeVo;
import org.apache.ibatis.annotations.Param;
import com.qu.modules.web.entity.QSingleDiseaseTake;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date:   2021-04-02
 * @Version: V1.0
 */
public interface QSingleDiseaseTakeMapper extends BaseMapper<QSingleDiseaseTake> {

    List<QSingleDiseaseTakeVo> singleDiseaseList(String name);
}
