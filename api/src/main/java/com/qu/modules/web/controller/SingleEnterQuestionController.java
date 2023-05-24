package com.qu.modules.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qu.modules.web.entity.SingleEnterQuestion;
import com.qu.modules.web.param.IdIntegerParam;
import com.qu.modules.web.param.SingleEnterQuestionAddParam;
import com.qu.modules.web.param.SingleEnterQuestionListParam;
import com.qu.modules.web.service.ISingleEnterQuestionService;
import com.qu.modules.web.vo.SingleEnterQuestionListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultBetter;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description: 录入表单表
 * @Author: jeecg-boot
 * @Date: 2023-05-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "录入表单表")
@RestController
@RequestMapping("/business/singleEnterQuestion")
public class SingleEnterQuestionController {

    @Autowired
    private ISingleEnterQuestionService singleEnterQuestionService;


    /**
     * 添加
     *
     * @param param
     * @return
     */
    @AutoLog(value = "录入表单表-添加")
    @ApiOperation(value = "录入表单表-添加", notes = "录入表单表-添加")
    @PostMapping(value = "/add")
    public ResultBetter add(@RequestBody @Valid SingleEnterQuestionAddParam param) {
        singleEnterQuestionService.add(param);
        return ResultBetter.ok();
    }

    /**
     * 分页列表查询
     *
     * @param param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "录入表单表-分页列表查询")
    @ApiOperation(value = "录入表单表-分页列表查询", notes = "录入表单表-分页列表查询")
    @PostMapping(value = "/list")
    public Result<IPage<SingleEnterQuestionListVo>> queryPageList(SingleEnterQuestionListParam param,
                                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                  HttpServletRequest req) {
        Result<IPage<SingleEnterQuestionListVo>> result = new Result<IPage<SingleEnterQuestionListVo>>();
        Page<SingleEnterQuestion> page = new Page<SingleEnterQuestion>(pageNo, pageSize);
        IPage<SingleEnterQuestionListVo> pageList = singleEnterQuestionService.queryPageList(page,param);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @AutoLog(value = "录入表单表-删除")
    @ApiOperation(value = "录入表单表-删除", notes = "录入表单表-删除")
    @PostMapping(value = "/delete")
    public ResultBetter delete(@RequestBody @Valid IdIntegerParam param) {
        return singleEnterQuestionService.delete(param);
    }


    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "录入表单表-通过id查询")
    @ApiOperation(value = "录入表单表-通过id查询", notes = "录入表单表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<SingleEnterQuestion> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<SingleEnterQuestion> result = new Result<SingleEnterQuestion>();
        SingleEnterQuestion singleEnterQuestion = singleEnterQuestionService.getById(id);
        if (singleEnterQuestion == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(singleEnterQuestion);
            result.setSuccess(true);
        }
        return result;
    }


    /**
     * 编辑
     *
     * @param singleEnterQuestion
     * @return
     */
    @AutoLog(value = "录入表单表-编辑")
    @ApiOperation(value = "录入表单表-编辑", notes = "录入表单表-编辑")
    @PutMapping(value = "/edit")
    public Result<SingleEnterQuestion> edit(@RequestBody SingleEnterQuestion singleEnterQuestion) {
        Result<SingleEnterQuestion> result = new Result<SingleEnterQuestion>();
        SingleEnterQuestion singleEnterQuestionEntity = singleEnterQuestionService.getById(singleEnterQuestion.getId());
        if (singleEnterQuestionEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = singleEnterQuestionService.updateById(singleEnterQuestion);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

}
