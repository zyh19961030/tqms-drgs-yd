package com.qu.modules.web.controller;

import com.qu.constant.Constant;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.param.*;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.IQuestionService;
import com.qu.modules.web.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    public Result<QuestionAndCategoryPageVo> queryPageList(QuestionParam questionParam,
                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                           HttpServletRequest req) {
        Result<QuestionAndCategoryPageVo> result = new Result<>();
        QuestionAndCategoryPageVo questionAndCategoryPageVo = questionService.queryPageList(questionParam, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(questionAndCategoryPageVo);
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
     * 再次发布
     *
     * @param questionAgainreleaseParam
     * @return
     */
    @AutoLog(value = "问卷表-再次发布")
    @ApiOperation(value = "问卷表-再次发布", notes = "问卷表-再次发布")
    @PutMapping(value = "/againRelease")
    public Result<Boolean> againRelease(@RequestBody QuestionAgainReleaseParam questionAgainreleaseParam) {
        Result<Boolean> result = new Result<Boolean>();
        Boolean flag = questionService.againRelease(questionAgainreleaseParam);
        result.setResult(flag);
        result.success("再次发布成功!");
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
        questionService.updateDeptIdsParam(updateDeptIdsParam);
        result.setResult(true);
        result.success("更新成功！");
        return result;
    }

    @ApiOperation(value = "设置分类", notes = "设置分类")
    @PostMapping(value = "/updateCategoryIdParam")
    public Result<Boolean> updateCategoryIdParam(@RequestBody UpdateCategoryIdParam updateCategoryIdParam) {
        Result<Boolean> result = new Result<Boolean>();
        questionService.updateCategoryIdParam(updateCategoryIdParam);
        result.setResult(true);
        result.success("更新成功！");
        return result;
    }

    /**
     * 根据输入内容搜索问卷
     * @param name
     * @return
     */
    @AutoLog(value = "问卷表-根据输入内容搜索问卷")
    @ApiOperation(value = "问卷表-根据输入内容搜索问卷", notes = "问卷表-根据输入内容搜索问卷")
    @GetMapping(value = "/queryQuestionByInput")
    public List<Question> queryQuestionByInput(@RequestParam(name = "name", required = true) String name) {
        List<Question> list = new ArrayList<>();
        List<Question> questions = questionService.queryQuestionByInput(name);
        questions.forEach(question -> {
            list.add(question);
        });
        return list;
    }

    @ApiOperation(value = "设置填报频次", notes = "设置填报频次")
    @PostMapping(value = "/updateWriteFrequencyIdsParam")
    public Result<Boolean> updateWriteFrequencyIdsParam(@RequestBody @Validated UpdateWriteFrequencyIdsParam updateWriteFrequencyIdsParam) {
        Result<Boolean> result = new Result<Boolean>();
        questionService.updateWriteFrequencyIdsParam(updateWriteFrequencyIdsParam);
        result.setResult(true);
        result.success("更新成功！");
        return result;
    }

    /**
     * 患者登记表-新建查询
     */
    @AutoLog(value = "患者登记表-新建查询")
    @ApiOperation(value = "患者登记表-新建查询", notes = "患者登记表-新建查询")
    @GetMapping(value = "/patientCreateList")
    public Result<List<QuestionPatientCreateListVo>> patientCreateList(@RequestParam(name = "name", required = false) String name, HttpServletRequest request) {
        Result<List<QuestionPatientCreateListVo>> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getDeps().get(0).getId();
        List<QuestionPatientCreateListVo> list = questionService.patientCreateList(name,deptId);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 月度,季度,年汇总-新建查询
     */
    @AutoLog(value = "月度,季度,年汇总-新建查询")
    @ApiOperation(value = "月度,季度,年汇总-新建查询", notes = "月度,季度,年汇总-新建查询")
    @GetMapping(value = "/monthQuarterYearCreateList")
    public Result<List<QuestionMonthQuarterYearCreateListVo>> monthQuarterYearCreateList(@RequestParam(name = "type")@ApiParam("类型必填,月度传0,季度传1,年传2") String type,
                                                                                HttpServletRequest request) {
        Result<List<QuestionMonthQuarterYearCreateListVo>> result = new Result<>();
        //加科室过滤---
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getDeps().get(0).getId();
        List<QuestionMonthQuarterYearCreateListVo> list = questionService.monthQuarterYearCreateList(type,deptId);
        result.setSuccess(true);
        result.setResult(list);
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

}
