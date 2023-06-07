package com.qu.modules.web.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultBetter;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qu.constant.Constant;
import com.qu.modules.web.entity.SingleEnterQuestion;
import com.qu.modules.web.param.IdIntegerParam;
import com.qu.modules.web.param.QuestionCheckParam;
import com.qu.modules.web.param.SingleEnterQuestionAddParam;
import com.qu.modules.web.param.SingleEnterQuestionAmendmentSaveDataParam;
import com.qu.modules.web.param.SingleEnterQuestionEnterQuestionHeadListParam;
import com.qu.modules.web.param.SingleEnterQuestionEnterQuestionListParam;
import com.qu.modules.web.param.SingleEnterQuestionListParam;
import com.qu.modules.web.param.SingleEnterQuestionSaveDataParam;
import com.qu.modules.web.param.SingleEnterQuestionUpdateParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.ISingleEnterQuestionService;
import com.qu.modules.web.vo.SingleEnterQuestionEnterQuestionHeadListVo;
import com.qu.modules.web.vo.SingleEnterQuestionInfoVo;
import com.qu.modules.web.vo.SingleEnterQuestionListVo;
import com.qu.modules.web.vo.SingleEnterQuestionQuestionCheckVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
        return singleEnterQuestionService.add(param);
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


    @ApiOperation(value = "录入表单_开始检查", notes = "录入表单_开始检查")
    @GetMapping(value = "/startCheckList")
    public Result<SingleEnterQuestionQuestionCheckVo> startCheckList(QuestionCheckParam questionCheckParam,
                                                                     HttpServletRequest request) {
        Result<SingleEnterQuestionQuestionCheckVo> result = new Result<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        List<SingleEnterQuestionQuestionCheckVo> questionCheckPageVoIPage = singleEnterQuestionService.startCheckList(questionCheckParam, data);
        result.setSuccess(true);
        result.setResult(questionCheckPageVoIPage);
        return result;
    }


    @AutoLog(value = "录入表单表_填报列表(待处理和已完成)_表头")
    @ApiOperation(value = "录入表单表_填报列表(待处理和已完成)_表头", notes = "录入表单表_填报列表(待处理和已完成)_表头")
    @GetMapping(value = "/enterQuestionHeadList")
    public ResultBetter<SingleEnterQuestionEnterQuestionHeadListVo> enterQuestionHeadList(@Valid SingleEnterQuestionEnterQuestionHeadListParam param) {
        return singleEnterQuestionService.enterQuestionHeadList(param);
    }

    @AutoLog(value = "录入表单表_填报列表(待处理和已完成)_数据")
    @ApiOperation(value = "录入表单表_填报列表(待处理和已完成)_数据", notes = "录入表单表_填报列表(待处理和已完成)_数据")
    @GetMapping(value = "/enterQuestionDataList")
    public ResultBetter<IPage<LinkedHashMap<String, Object>>> enterQuestionDataList(@Valid SingleEnterQuestionEnterQuestionListParam param,
                                                                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                              HttpServletRequest req) {
        IPage<LinkedHashMap<String, Object>> pageList = singleEnterQuestionService.enterQuestionDataList(param, pageNo, pageSize);
        return ResultBetter.ok(pageList);
    }

    @ApiOperation(value = "保存", notes = "保存")
    @PostMapping(value = "/saveData")
    public ResultBetter saveData(@RequestBody @Valid SingleEnterQuestionSaveDataParam saveParam, HttpServletRequest request) {
        String token = request.getHeader("token");
        String cookie = "JSESSIONID=" + token;
        log.info("-----------saveParam={}", JSON.toJSONString(saveParam));
        return singleEnterQuestionService.saveData(cookie, saveParam);
    }


    @ApiOperation(value = "修订", notes = "修订")
    @PostMapping(value = "/amendmentSaveData")
    public ResultBetter amendmentSaveData(@RequestBody @Valid SingleEnterQuestionAmendmentSaveDataParam amendmentSaveDataParam, HttpServletRequest request) {
        String token = request.getHeader("token");
        String cookie = "JSESSIONID=" + token;
        log.info("-----------amendmentSaveDataParam={}", JSON.toJSONString(amendmentSaveDataParam));
        return singleEnterQuestionService.amendmentSaveData(cookie, amendmentSaveDataParam);
    }







}
