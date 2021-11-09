package com.qu.modules.web.controller;

import com.qu.modules.web.service.IZbdjService;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/business/zbdj")
public class ZbdjController {

    @Autowired
    private IZbdjService iZbdjService;

    /**
     * 分页列表查询
     *
     * @param tableName
     * @return
     */
    @AutoLog(value = "生成登记表详情")
    @GetMapping(value = "/generate")
    public Result<Boolean> queryPageList(@RequestParam(name = "tableName") String tableName) {
        Result<Boolean> result = new Result<Boolean>();
        result = iZbdjService.batchGeneerate(tableName);
        return result;
    }
}
