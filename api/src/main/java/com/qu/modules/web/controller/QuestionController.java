package com.qu.modules.web.controller;

import com.qu.modules.web.entity.Question;
import com.qu.modules.web.param.QuestionEditParam;
import com.qu.modules.web.param.QuestionParam;
import com.qu.modules.web.param.UpdateDeptIdsParam;
import com.qu.modules.web.service.IQuestionService;
import com.qu.modules.web.vo.QuestionPageVo;
import com.qu.modules.web.vo.QuestionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 问卷表
 * @Author: jeecg-boot
 * @Date: 2021-03-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "问卷表")
@RestController
@RequestMapping("/business/question")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    /**
     * 分页列表查询
     *
     * @param questionParam
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "问卷表-分页列表查询")
    @ApiOperation(value = "问卷表-分页列表查询", notes = "问卷表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<QuestionPageVo> queryPageList(QuestionParam questionParam,
                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                HttpServletRequest req) {
        Result<QuestionPageVo> result = new Result<QuestionPageVo>();
        QuestionPageVo questionPageVo = questionService.queryPageList(questionParam, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(questionPageVo);
        return result;
    }

    /**
     * 添加
     *
     * @param questionParam
     * @return
     */
    @AutoLog(value = "问卷表-添加")
    @ApiOperation(value = "问卷表-添加", notes = "问卷表-添加")
    @PostMapping(value = "/add")
    public Result<Question> add(@RequestBody QuestionParam questionParam) {
        Result<Question> result = new Result<Question>();
        try {
            Question q = questionService.saveQuestion(questionParam);
            result.setResult(q);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param questionEditParam
     * @return
     */
    @AutoLog(value = "问卷表-编辑")
    @ApiOperation(value = "问卷表-编辑", notes = "问卷表-编辑")
    @PutMapping(value = "/edit")
    public Result<Question> edit(@RequestBody QuestionEditParam questionEditParam) {
        Result<Question> result = new Result<Question>();
        Question question = questionService.updateQuestionById(questionEditParam);
        if (question == null) {
            result.error500("建表失败，表名重复!");
            return result;
        }
        result.setResult(question);
        result.success("修改成功!");
        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "问卷表-通过id删除")
    @ApiOperation(value = "问卷表-通过id删除", notes = "问卷表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<Boolean> delete(@RequestParam(name = "id", required = true) Integer id) {
        Result<Boolean> result = new Result<Boolean>();
        Boolean ok = questionService.removeQuestionById(id);
        result.setResult(ok);
        result.success("删除成功!");
        return result;
    }

//    /**
//     * 批量删除
//     *
//     * @param ids
//     * @return
//     */
//    @AutoLog(value = "问卷表-批量删除")
//    @ApiOperation(value = "问卷表-批量删除", notes = "问卷表-批量删除")
//    @DeleteMapping(value = "/deleteBatch")
//    public Result<Question> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
//        Result<Question> result = new Result<Question>();
//        if (ids == null || "".equals(ids.trim())) {
//            result.error500("参数不识别！");
//        } else {
//            this.questionService.removeByIds(Arrays.asList(ids.split(",")));
//            result.success("删除成功!");
//        }
//        return result;
//    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "问卷表-通过id查询")
    @ApiOperation(value = "问卷表-通过id查询", notes = "问卷表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<QuestionVo> queryById(@RequestParam(name = "id", required = true) Integer id) {
        Result<QuestionVo> result = new Result<QuestionVo>();
        QuestionVo questionVo = questionService.queryById(id);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }

    @ApiOperation(value = "问卷表-通过id查询人工类型", notes = "问卷表-通过id查询人工类型")
    @GetMapping(value = "/queryPersonById")
    public Result<QuestionVo> queryPersonById(@RequestParam(name = "id", required = true) Integer id) {
        Result<QuestionVo> result = new Result<QuestionVo>();
        QuestionVo questionVo = questionService.queryPersonById(id);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }

    @ApiOperation(value = "问卷填报分页列表", notes = "问卷填报分页列表")
    @GetMapping(value = "/questionFillInList")
    public Result<QuestionPageVo> questionFillInList(QuestionParam questionParam,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        Result<QuestionPageVo> result = new Result<QuestionPageVo>();
        QuestionPageVo questionPageVo = questionService.questionFillInList(questionParam, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(questionPageVo);
        return result;
    }

    @ApiOperation(value = "批量更新问卷权限", notes = "批量更新问卷权限")
    @PostMapping(value = "/updateDeptIdsParam")
    public Result<Boolean> updateDeptIdsParam(@RequestBody UpdateDeptIdsParam updateDeptIdsParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            Boolean ok = questionService.updateDeptIdsParam(updateDeptIdsParam);
            result.setResult(ok);
            result.success("更新成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("更新失败");
        }
        return result;
    }

}
