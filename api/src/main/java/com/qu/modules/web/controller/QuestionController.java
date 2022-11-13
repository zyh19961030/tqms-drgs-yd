package com.qu.modules.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
import org.jeecg.common.api.vo.ResultBetter;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    @ApiOperation(value = "问卷表-分页列表查询", notes = "问卷表-分页列表查询",response = QuestionAndCategoryPageVo.class)
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
    public Result<Question> add(@RequestBody QuestionParam questionParam, HttpServletRequest request) {
        Result<Question> result = new Result<Question>();
        try {
            Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
            Question q = questionService.saveQuestion(questionParam,data.getTbUser());
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
    public Result<Question> edit(@RequestBody QuestionEditParam questionEditParam, HttpServletRequest request) {
        Result<Question> result = new Result<Question>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        Question question = questionService.updateQuestionById(questionEditParam,data.getTbUser());
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
     * 生成溯源表
     *
     * @param idParam  traceability
     * @return
     */
    @AutoLog(value = "问卷表-生成溯源表")
    @ApiOperation(value = "问卷表-生成溯源表", notes = "问卷表-生成溯源表")
    @PostMapping(value = "/generateTraceability")
    public Result<?> generateTraceability(@Valid @RequestBody IdParam idParam) {
        return questionService.generateTraceability(idParam.getId());
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
    public Result<Boolean> delete(@RequestParam(name = "id", required = true) Integer id, HttpServletRequest request) {
        Result<Boolean> result = new Result<Boolean>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        Boolean ok = questionService.removeQuestionById(id,data.getTbUser());
        result.setResult(ok);
        result.success("删除成功!");
        return result;
    }

    /**
     * 普通问卷_通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "普通问卷_通过id查询")
    @ApiOperation(value = "普通问卷_通过id查询", notes = "普通问卷_通过id查询")
    @GetMapping(value = "/queryById")
    public Result<QuestionVo> queryById(@RequestParam(name = "id", required = true) Integer id) {
        Result<QuestionVo> result = new Result<QuestionVo>();
        QuestionVo questionVo = questionService.queryById(id);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }

    /**
     * 普通问卷_通过id查询_new
     *
     * @param param
     * @return
     */
    @AutoLog(value = "普通问卷_通过id查询_new")
    @ApiOperation(value = "普通问卷_通过id查询_new", notes = "普通问卷_通过id查询_new")
    @GetMapping(value = "/answerQueryById")
    public Result<QuestionVo> answerQueryById(@Valid QuestionQueryByIdParam param) {
        Result<QuestionVo> result = new Result<QuestionVo>();
        QuestionVo questionVo = questionService.queryByIdNew(param);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }

    /**
     * 查检表_通过id查询题目数据
     *
     * @param param
     * @return
     */
    @AutoLog(value = "查检表_通过id查询题目数据")
    @ApiOperation(value = "查检表_通过id查询题目数据", notes = "查检表_通过id查询题目数据")
    @GetMapping(value = "/answerCheckQueryById")
    public Result<QuestionVo> answerCheckQueryById(@Valid QuestionQueryByIdParam param) {
        Result<QuestionVo> result = new Result<QuestionVo>();
        QuestionVo questionVo = questionService.answerCheckQueryById(param);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }

    /**
     * 单病种_通过id查询题目数据
     *
     * @param param
     * @return
     */
    @AutoLog(value = "单病种_通过id查询题目数据")
    @ApiOperation(value = "单病种_通过id查询题目数据", notes = "单病种_通过id查询题目数据")
    @GetMapping(value = "/singleDiseaseQueryById")
    public Result<QuestionVo> singleDiseaseQueryById(@Valid QuestionQueryByIdParam param) {
        Result<QuestionVo> result = new Result<QuestionVo>();
        QuestionVo questionVo = questionService.singleDiseaseQueryById(param);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }

    /**
     * 通过视图查询数据
     *
     * @param depParam
     * @return
     */
    @AutoLog(value = "问卷表-通过视图查询数据")
    @ApiOperation(value = "问卷表-通过视图查询数据", notes = "问卷表-通过视图查询数据")
    @GetMapping(value = "/queryByViewName")
    public Result<ViewNameVo> queryByViewName(QuestionCheckedDepParam depParam) {
        Result<ViewNameVo> result = new Result<ViewNameVo>();
        List<ViewNameVo> queryByViewNameList = questionService.queryByViewName(depParam);
        result.setResult(queryByViewNameList);
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

    @ApiOperation(value = "普通问卷新增填报分页列表", notes = "普通问卷新增填报分页列表")
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


    @ApiOperation(value = "检查表_新增分页列表", notes = "检查表_新增分页列表")
    @GetMapping(value = "/checkQuestionList")
    public Result<QuestionCheckVo> checkQuestionList(QuestionCheckParam questionCheckParam,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest request) {
        Result<QuestionCheckVo> result = new Result<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        IPage<QuestionCheckVo> questionCheckPageVoIPage = questionService.checkQuestionList(questionCheckParam, pageNo, pageSize,data);
        result.setSuccess(true);
        result.setResult(questionCheckPageVoIPage);
        return result;
    }


    @ApiOperation(value = "检查表统计中使用_数据源SQL下拉的接口", notes = "检查表统计中使用_数据源SQL下拉的接口")
    @GetMapping(value = "/statisticsCheckList")
    public Result<QuestionStatisticsCheckVo> statisticsCheckList(QuestionCheckParam questionCheckParam) {
        Result<QuestionStatisticsCheckVo> result = new Result<>();
        List<QuestionStatisticsCheckVo> checkVoList = questionService.statisticsCheckList(questionCheckParam);
        result.setSuccess(true);
        result.setResult(checkVoList);
        return result;
    }

    @ApiOperation(value = "检查管理_历史统计列表", notes = "检查管理_历史统计列表")
    @GetMapping(value = "/checkQuestionHistoryStatisticList")
    public ResultBetter<List<CheckQuestionHistoryStatisticVo>> checkQuestionHistoryStatisticList(HttpServletRequest request) {
        ResultBetter<List<CheckQuestionHistoryStatisticVo>> result = new ResultBetter<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        List<CheckQuestionHistoryStatisticVo> questionCheckPageVoIPage = questionService.checkQuestionHistoryStatisticList(data);
        result.setSuccess(true);
        result.setResult(questionCheckPageVoIPage);
        return result;
    }


    @ApiOperation(value = "检查管理_参数设置列表", notes = "检查管理_参数设置列表")
    @GetMapping(value = "/checkQuestionParameterSetList")
    public ResultBetter<List<CheckQuestionParameterSetListVo>> checkQuestionParameterSetList(HttpServletRequest request) {
        ResultBetter<List<CheckQuestionParameterSetListVo>> result = new ResultBetter<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getDeps().get(0).getId();
        List<CheckQuestionParameterSetListVo> questionCheckPageVoIPage = questionService.checkQuestionParameterSetList(deptId);
        result.setSuccess(true);
        result.setResult(questionCheckPageVoIPage);
        return result;
    }

    @ApiOperation(value = "检查管理_历史统计列表_上级督查_填报记录和检查明细_被检查科室筛选条件(职能科室和临床科室同一个接口)", notes = "检查管理_历史统计列表_上级督查_填报记录和检查明细_被检查科室筛选条件(职能科室和临床科室同一个接口)")
    @GetMapping(value = "/checkQuestionHistoryStatisticInspectedDeptList")
    public ResultBetter<List<CheckQuestionHistoryStatisticDeptListDeptVo>> checkQuestionHistoryStatisticInspectedDeptList(@Valid CheckQuestionHistoryStatisticDeptListParam deptListParam,
                                                                                         HttpServletRequest request) {
        ResultBetter<List<CheckQuestionHistoryStatisticDeptListDeptVo>> result = new ResultBetter<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        List<CheckQuestionHistoryStatisticDeptListDeptVo> deptList = questionService.checkQuestionHistoryStatisticInspectedDeptList(deptListParam,data);
        result.setSuccess(true);
        result.setResult(deptList);
        return result;
    }

    @ApiOperation(value = "检查管理_历史统计列表_上级督查_填报记录和检查明细_检查科室筛选条件(职能科室和临床科室同一个接口)", notes = "检查管理_历史统计列表_上级督查_填报记录和检查明细_检查科室筛选条件(职能科室和临床科室同一个接口)")
    @GetMapping(value = "/checkQuestionHistoryStatisticDeptList")
    public ResultBetter<List<CheckQuestionHistoryStatisticDeptListDeptVo>> checkQuestionHistoryStatisticDeptList(@Valid CheckQuestionHistoryStatisticDeptListParam deptListParam,
                                                                                                     HttpServletRequest request) {
        ResultBetter<List<CheckQuestionHistoryStatisticDeptListDeptVo>> result = new ResultBetter<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        List<CheckQuestionHistoryStatisticDeptListDeptVo> deptList = questionService.checkQuestionHistoryStatisticDeptList(deptListParam,data);
        result.setSuccess(true);
        result.setResult(deptList);
        return result;
    }

    @ApiOperation(value = "检查管理_历史统计列表_科室自查_填报记录和检查明细_自查科室筛选条件(职能科室和临床科室同一个接口)", notes = "检查管理_历史统计列表_科室自查_填报记录和检查明细_自查科室筛选条件(职能科室和临床科室同一个接口)")
    @GetMapping(value = "/checkQuestionHistoryStatisticSelfDeptList")
    public ResultBetter<List<CheckQuestionHistoryStatisticDeptListDeptVo>> checkQuestionHistoryStatisticSelfDeptList(@Valid CheckQuestionHistoryStatisticDeptListParam deptListParam,
                                                                                                     HttpServletRequest request) {
        ResultBetter<List<CheckQuestionHistoryStatisticDeptListDeptVo>> result = new ResultBetter<>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        List<CheckQuestionHistoryStatisticDeptListDeptVo> deptList = questionService.checkQuestionHistoryStatisticSelfDeptList(deptListParam,data);
        result.setSuccess(true);
        result.setResult(deptList);
        return result;
    }

    @ApiOperation(value = "批量更新问卷权限_改为配置填报科室接口", notes = "批量更新问卷权限_改为配置填报科室接口")
    @PostMapping(value = "/updateDeptIdsParam")
    public Result<Boolean> updateDeptIdsParam(@RequestBody UpdateDeptIdsParam updateDeptIdsParam) {
        Result<Boolean> result = new Result<Boolean>();
        questionService.updateDeptIdsParam(updateDeptIdsParam);
        result.setResult(true);
        result.success("更新成功！");
        return result;
    }

    @ApiOperation(value = "批量更新问卷权限_配置查看科室接口", notes = "批量更新问卷权限_配置查看科室接口")
    @PostMapping(value = "/updateSeeDeptIdsParam")
    public Result<Boolean> updateSeeDeptIdsParam(@RequestBody UpdateDeptIdsParam updateDeptIdsParam) {
        Result<Boolean> result = new Result<Boolean>();
        questionService.updateSeeDeptIdsParam(updateDeptIdsParam);
        result.setResult(true);
        result.success("更新成功！");
        return result;
    }

    @ApiOperation(value = "参数设置_设被检科室接口", notes = "参数设置_设被检科室接口")
    @PostMapping(value = "/updateCheckedDeptIdsParam")
    public Result<Boolean> updateCheckedDeptIdsParam(@RequestBody @Valid UpdateCheckedDeptIdsParam updateCheckedDeptIdsParam) {
        Result<Boolean> result = new Result<Boolean>();
        questionService.updateCheckedDeptIdsParam(updateCheckedDeptIdsParam);
        result.setResult(true);
        result.success("更新成功！");
        return result;
    }


    @ApiOperation(value = "参数设置_设被检科室接口_回显", notes = "参数设置_设被检科室接口_回显")
    @PostMapping(value = "/selectCheckedDeptIdsParam")
    public Result<String> selectCheckedDeptIdsParam(@RequestBody @Valid SelectCheckedDeptIdsParam selectCheckedDeptIdsParam) {
        Result<String> result = new Result<String>();
        List<String> deptIdList = questionService.selectCheckedDeptIdsParam(selectCheckedDeptIdsParam);
        result.setResult(deptIdList);
        result.success("更新成功！");
        return result;
    }

    @ApiOperation(value = "参数设置_设置责任人", notes = "参数设置_设置责任人")
    @PostMapping(value = "/updateResponsibilityUserIdsParam")
    public Result<Boolean> updateResponsibilityUserIdsParam(@RequestBody @Valid UpdateResponsibilityUserIdsParam updateResponsibilityUserIdsParam) {
        Result<Boolean> result = new Result<Boolean>();
        questionService.updateResponsibilityUserIdsParam(updateResponsibilityUserIdsParam);
        result.setResult(true);
        result.success("设置成功！");
        return result;
    }


    @ApiOperation(value = "参数设置_设置责任人_回显", notes = "参数设置_设置责任人_回显")
    @PostMapping(value = "/selectResponsibilityUserIdsParam")
    public Result<String> selectResponsibilityUserIdsParam(@RequestBody @Valid SelectResponsibilityUserIdsParam selectResponsibilityUserIdsParam) {
        Result<String> result = new Result<String>();
        List<String> deptIdList = questionService.selectResponsibilityUserIdsParam(selectResponsibilityUserIdsParam);
        result.setResult(deptIdList);
        result.success("成功！");
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


    @ApiOperation(value = "设置匹配模板", notes = "设置匹配模板")
    @PostMapping(value = "/updateTemplateIdIdParam")
    public Result<Boolean> updateTemplateIdIdParam(@RequestBody @Valid UpdateTemplateIdParam param) {
        Result<Boolean> result = new Result<Boolean>();
        questionService.updateTemplateIdIdParam(param);
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


    @ApiOperation(value = "设置问卷图标", notes = "设置问卷图标")
    @PostMapping(value = "/updateQuestionIcon")
    public Result<Boolean> updateQuestionIcon(@RequestBody @Validated UpdateQuestionIconParam updateQuestionIconParam) {
        Result<Boolean> result = new Result<Boolean>();
        questionService.updateQuestionIcon(updateQuestionIconParam);
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
    public Result<List<QuestionMonthQuarterYearCreateListVo>> monthQuarterYearCreateList(@RequestParam(name = "type",required = false)@ApiParam("类型,月度传0,季度传1,年传2") String type,
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

    /**
     * 检查项目添加中获取表单
     *
     * @return
     */
    @AutoLog(value = "检查项目添加中获取表单")
    @ApiOperation(value = "检查项目添加中获取表单", notes = "检查项目添加中获取表单")
    @GetMapping(value = "/checkProjectGetTable")
    public Result<QuestionCheckProject> checkProjectGetTable() {
        Result<QuestionCheckProject> result = new Result<>();
        Question byId = questionService.getById(252);
        QuestionCheckProject res = new QuestionCheckProject();
        BeanUtils.copyProperties(byId,res);
        result.setResult(res);
        result.setSuccess(true);
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
