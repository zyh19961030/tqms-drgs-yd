package com.qu.modules.web.controller;

import com.qu.constant.Constant;
import com.qu.modules.web.entity.Qsubject;
import com.qu.modules.web.param.*;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.ISubjectService;
import com.qu.modules.web.vo.QsubjectIdAndNameVo;
import com.qu.modules.web.vo.StatisticsCheckTableSubjectVo;
import com.qu.modules.web.vo.SubjectNameVo;
import com.qu.modules.web.vo.SubjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date: 2021-03-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "题目表")
@RestController
@RequestMapping("/business/subject")
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;

    /**
     * 添加
     *
     * @param subjectParam
     * @return
     */
    @AutoLog(value = "题目表-添加")
    @ApiOperation(value = "题目表-添加", notes = "题目表-添加")
    @PostMapping(value = "/add")
    public Result<SubjectVo> add(@RequestBody @Validated SubjectParam subjectParam, HttpServletRequest request) {
        Result<SubjectVo> result = new Result<SubjectVo>();
        try {
            Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
            SubjectVo so = subjectService.saveSubject(subjectParam,data.getTbUser());
            if (so == null) {
                result.error500("添加失败,字段名重复!");
                return result;
            }
            result.setResult(so);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @ApiOperation(value = "在某题下面插入题目", notes = "在某题下面插入题目")
    @PostMapping(value = "/insertSubject")
    public Result<SubjectVo> insertSubject(@Valid @RequestBody InsertSubjectParam insertSubjectParam, HttpServletRequest request) {
        Result<SubjectVo> result = new Result<SubjectVo>();
        try {
            Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
            SubjectVo so = subjectService.insertSubject(insertSubjectParam,data.getTbUser());
            if (so == null) {
                result.error500("添加失败,字段名重复!");
                return result;
            }
            result.setResult(so);
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
     * @param subjectEditParam
     * @return
     */
    @AutoLog(value = "题目表-编辑")
    @ApiOperation(value = "题目表-编辑", notes = "题目表-编辑")
    @PutMapping(value = "/edit")
    public Result<SubjectVo> edit(@RequestBody @Valid SubjectEditParam subjectEditParam, HttpServletRequest request) {
        Result<SubjectVo> result = new Result<SubjectVo>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        SubjectVo subjectVo = subjectService.updateQsubjectById(subjectEditParam,data.getTbUser());
        if(subjectVo==null){
            result.error500("修改失败,字段名重复!");
            return result;
        }
        result.setResult(subjectVo);
        result.success("修改成功!");
        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "题目表-通过id删除")
    @ApiOperation(value = "题目表-通过id删除", notes = "题目表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<Boolean> delete(@RequestParam(name = "id", required = true) Integer id, HttpServletRequest request) {
        Result<Boolean> result = new Result<Boolean>();
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        Boolean ok = subjectService.removeSubjectById(id,data.getTbUser());
        if(ok){
            result.setResult(true);
            result.success("删除成功!");
        }else{
            result.error500("操作失败");
        }
        return result;
    }

    @ApiOperation(value = "调换题目顺序", notes = "调换题目顺序")
    @PostMapping(value = "/updateOrderNum")
    public Result<Boolean> updateOrderNum(@RequestBody UpdateOrderNumParam updateOrderNumParam) {
        Result<Boolean> result = new Result<Boolean>();
        try {
            Boolean ok = subjectService.updateOrderNum(updateOrderNumParam);
            result.setResult(ok);
            result.success("成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑逻辑
     *
     * @param subjectLogicParam
     * @return
     */
    @AutoLog(value = "题目表-编辑逻辑")
    @ApiOperation(value = "题目表-编辑逻辑", notes = "题目表-编辑逻辑")
    @PostMapping(value = "/editLogic")
    public Result<Boolean> editLogic(@RequestBody SubjectLogicParam subjectLogicParam) {
        Result<Boolean> result = new Result<>();
        subjectService.editLogic(subjectLogicParam);
        result.setResult(true);
        result.success("修改成功!");
        return result;
    }


    /**
     * 编辑特殊逻辑
     *
     * @param subjectSpecialLogicParam
     * @return
     */
    @AutoLog(value = "题目表-编辑特殊逻辑")
    @ApiOperation(value = "题目表-编辑特殊逻辑", notes = "题目表-编辑特殊逻辑")
    @PostMapping(value = "/editSpecialLogic")
    public Result<Boolean> editSpecialLogic(@RequestBody SubjectSpecialLogicParam subjectSpecialLogicParam) {
        Result<Boolean> result = new Result<>();
        subjectService.editSpecialLogic(subjectSpecialLogicParam);
        result.setResult(true);
        result.success("修改成功!");
        return result;
    }

    /**
     * 根据输入内容搜索问题
     * @param name
     * @return
     */
    @AutoLog(value = "问题表-根据输入内容搜索问题")
    @ApiOperation(value = "问题表-根据输入内容搜索问题", notes = "问题表-根据输入内容搜索问题")
    @GetMapping(value = "/querySubjectByInput")
    public List<Qsubject> querySubjectByInput(@RequestParam(name = "name", required = true) String name) {
        return subjectService.querySubjectByInput(name);
    }

    /**
     * 针对19.数量统计题型的选择统计题目列表
     * @param
     * @return
     */
    @AutoLog(value = "针对19.数量统计题型的选择统计题目列表")
    @ApiOperation(value = "针对19.数量统计题型的选择统计题目列表", notes = "针对19.数量统计题型的选择统计题目列表",response = Qsubject.class)
    @PostMapping(value = "/querySubjectByQuantityStatistics")
    public Result querySubjectByQuantityStatistics(@RequestBody SubjectQuantityStatisticsParam subjectQuantityStatisticsParam) {
        List<Qsubject> qsubjects = subjectService.querySubjectByQuantityStatistics(subjectQuantityStatisticsParam);
        return Result.ok(qsubjects);
    }

    /**
     * 针对20.选择题分数求和题型的选择统计题目列表
     * @param
     * @return
     */
    @AutoLog(value = "针对20.选择题分数求和题型的选择统计题目列表")
    @ApiOperation(value = "针对20.选择题分数求和题型的选择统计题目列表", notes = "针对20.选择题分数求和题型的选择统计题目列表",response = Qsubject.class)
    @PostMapping(value = "/querySubjectByScoreCount")
    public Result querySubjectByScoreCount(@RequestBody SubjectQuantityStatisticsParam subjectQuantityStatisticsParam) {
        List<Qsubject> qsubjects = subjectService.querySubjectByScoreCount(subjectQuantityStatisticsParam);
        return Result.ok(qsubjects);
    }

    /**
     * 针对21.结果评价题型和22.数值题求和题型 的选择统计题目列表
     * @param
     * @return
     */
    @AutoLog(value = "针对21.结果评价题型和22.数值题求和题型 的选择统计题目列表")
    @ApiOperation(value = "针对21.结果评价题型和22.数值题求和题型 的选择统计题目列表", notes = "针对21.结果评价题型和22.数值题求和题型 的选择统计题目列表",response = Qsubject.class)
    @PostMapping(value = "/querySubjectByResultEvaluate")
    public Result querySubjectByResultEvaluate(@RequestBody SubjectQuantityStatisticsParam subjectQuantityStatisticsParam) {
        List<Qsubject> qsubjects = subjectService.querySubjectByResultEvaluate(subjectQuantityStatisticsParam);
        return Result.ok(qsubjects);
    }

    /**
     * 针对针对  17.数据源下拉单选 的关联题目id 的选择题目列表
     * @param
     * @return
     */
    @AutoLog(value = "针对针对  17.数据源下拉单选 的关联题目id 的选择题目列表")
    @ApiOperation(value = "针对针对  17.数据源下拉单选 的关联题目id 的选择题目列表", notes = "针对针对  17.数据源下拉单选 的关联题目id 的选择题目列表",response = QsubjectIdAndNameVo.class)
    @PostMapping(value = "/querySubjectByDataSource")
    public Result<List<QsubjectIdAndNameVo>> querySubjectByDataSource(@RequestBody SubjectQuantityStatisticsParam subjectQuantityStatisticsParam) {
        List<QsubjectIdAndNameVo> qsubjects = subjectService.querySubjectByDataSource(subjectQuantityStatisticsParam);
        return ResultFactory.success(qsubjects);
    }

    /**
     * 检查表统计中使用_表格数据接口
     * @param
     * @return
     */
    @AutoLog(value = "检查表统计中使用_表格数据接口")
    @ApiOperation(value = "检查表统计中使用_表格数据接口", notes = "检查表统计中使用_表格数据接口",response = StatisticsCheckTableSubjectVo.class)
    @GetMapping(value = "/statisticsCheckTable")
    public Result<List<StatisticsCheckTableSubjectVo>> statisticsCheckTable( @Valid  StatisticsCheckTableParam statisticsCheckTableParam) {
        List<StatisticsCheckTableSubjectVo> statisticsCheckTableList = subjectService.statisticsCheckTable(statisticsCheckTableParam);
        return ResultFactory.success(statisticsCheckTableList);
    }


    @AutoLog(value = "录入表单时选择列和选择题目")
    @ApiOperation(value = "录入表单时选择列和选择题目", notes = "录入表单时选择列和选择题目")
    @PostMapping(value = "/enterQuestionSelectSubject")
    public Result<List<SubjectNameVo>> enterQuestionSelectSubject(@RequestBody @Valid EnterQuestionSelectSubjectParam param,HttpServletRequest request) {
        Result<List<SubjectNameVo>> result = new Result<List<SubjectNameVo>>();
//        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
//        String deptId = data.getTbUser().getDepId();
        List<SubjectNameVo> selectSubject = subjectService.enterQuestionSelectSubject(param);
        result.setResult(selectSubject);
        result.setSuccess(true);
        return result;
    }



//    /**
//     * 分页列表查询
//     *
//     * @param subject
//     * @param pageNo
//     * @param pageSize
//     * @param req
//     * @return
//     */
//    @AutoLog(value = "题目表-分页列表查询")
//    @ApiOperation(value = "题目表-分页列表查询", notes = "题目表-分页列表查询")
//    @GetMapping(value = "/list")
//    public Result<IPage<Subject>> queryPageList(Subject subject,
//                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
//                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
//                                                HttpServletRequest req) {
//        Result<IPage<Subject>> result = new Result<IPage<Subject>>();
//        QueryWrapper<Subject> queryWrapper = QueryGenerator.initQueryWrapper(subject, req.getParameterMap());
//        Page<Subject> page = new Page<Subject>(pageNo, pageSize);
//        IPage<Subject> pageList = subjectService.page(page, queryWrapper);
//        result.setSuccess(true);
//        result.setResult(pageList);
//        return result;
//    }


//    /**
//     * 批量删除
//     *
//     * @param ids
//     * @return
//     */
//    @AutoLog(value = "题目表-批量删除")
//    @ApiOperation(value = "题目表-批量删除", notes = "题目表-批量删除")
//    @DeleteMapping(value = "/deleteBatch")
//    public Result<Subject> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
//        Result<Subject> result = new Result<Subject>();
//        if (ids == null || "".equals(ids.trim())) {
//            result.error500("参数不识别！");
//        } else {
//            this.subjectService.removeByIds(Arrays.asList(ids.split(",")));
//            result.success("删除成功!");
//        }
//        return result;
//    }
//
//    /**
//     * 通过id查询
//     *
//     * @param id
//     * @return
//     */
//    @AutoLog(value = "题目表-通过id查询")
//    @ApiOperation(value = "题目表-通过id查询", notes = "题目表-通过id查询")
//    @GetMapping(value = "/queryById")
//    public Result<Subject> queryById(@RequestParam(name = "id", required = true) String id) {
//        Result<Subject> result = new Result<Subject>();
//        Subject subject = subjectService.getById(id);
//        if (subject == null) {
//            result.error500("未找到对应实体");
//        } else {
//            result.setResult(subject);
//            result.setSuccess(true);
//        }
//        return result;
//    }
}
