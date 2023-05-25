package com.qu.modules.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qu.modules.web.entity.SingleEnterQuestion;
import com.qu.modules.web.param.IdIntegerParam;
import com.qu.modules.web.param.SingleEnterQuestionAddParam;
import com.qu.modules.web.param.SingleEnterQuestionListParam;
import com.qu.modules.web.param.SingleEnterQuestionUpdateParam;
import com.qu.modules.web.service.ISingleEnterQuestionService;
import com.qu.modules.web.vo.SingleEnterQuestionInfoVo;
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
    @GetMapping(value = "/list")
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
    @GetMapping(value = "/info")
    public ResultBetter<SingleEnterQuestionInfoVo> info(@RequestParam(name = "id", required = true) String id) {
        SingleEnterQuestionInfoVo singleEnterQuestion = singleEnterQuestionService.info(id);
        return ResultBetter.ok(singleEnterQuestion);
    }

    @AutoLog(value = "录入表单表-编辑")
    @ApiOperation(value = "录入表单表-编辑", notes = "录入表单表-编辑")
    @PostMapping(value = "/edit")
    public ResultBetter edit(@RequestBody @Valid SingleEnterQuestionUpdateParam param) {
        return singleEnterQuestionService.edit(param);
    }




}
