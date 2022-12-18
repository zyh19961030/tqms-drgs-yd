package com.qu.modules.web.controller.miniapp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qu.constant.QuestionConstant;
import com.qu.modules.web.param.AnswerMiniAppParam;
import com.qu.modules.web.param.QuestionCheckedDepParam;
import com.qu.modules.web.param.QuestionQueryByIdParam;
import com.qu.modules.web.service.IAnswerCheckService;
import com.qu.modules.web.service.IQuestionService;
import com.qu.modules.web.vo.QuestionMiniAppPageVo;
import com.qu.modules.web.vo.QuestionVo;
import com.qu.modules.web.vo.ViewNameVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description: 小程序后台调用_问卷表
 */
@Slf4j
@Api(tags = "小程序后台调用_问卷表")
@RestController
@RequestMapping("/web/miniapp/question")
public class QuestionMiniAppController {

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IAnswerCheckService answerCheckService;


    /**
     * 分页列表查询
     *
     * @param deptId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AutoLog(value = "检查填报-分页列表查询")
    @ApiOperation(value = "检查填报-分页列表查询", notes = "检查填报-分页列表查询",response = QuestionMiniAppPageVo.class)
    @GetMapping(value = "/list")
    public Result<QuestionMiniAppPageVo> queryPageList(String deptId, @RequestHeader(name = "userId") String userId,
                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "100") Integer pageSize) {
        IPage<QuestionMiniAppPageVo> iPage = questionService.queryPageListByMiniApp(deptId, userId, pageNo, pageSize);
        return ResultFactory.success(iPage);
    }

    /**
     * 登记表分页列表查询
     *
     * @param deptId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AutoLog(value = "登记表-分页列表查询")
    @ApiOperation(value = "登记表-分页列表查询", notes = "登记表-分页列表查询",response = QuestionMiniAppPageVo.class)
    @GetMapping(value = "/answerList")
    public Result<QuestionMiniAppPageVo> answerList(String deptId, @RequestHeader(name = "userId") String userId,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "100") Integer pageSize) {
        IPage<QuestionMiniAppPageVo> iPage = questionService.answerQueryPageListByMiniApp(deptId, userId, pageNo, pageSize);
        return ResultFactory.success(iPage);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "检查填报问卷-通过id查询")
    @ApiOperation(value = "检查填报问卷-通过id查询", notes = "检查填报问卷-通过id查询")
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
        QuestionVo questionVo = questionService.queryByIdNew(param, QuestionConstant.CATEGORY_TYPE_NORMAL);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }

    /**
     * 单病种_通过id查询
     *
     * @param param
     * @return
     */
    @AutoLog(value = "单病种_通过id查询")
    @ApiOperation(value = "单病种_通过id查询", notes = "单病种_通过id查询")
    @GetMapping(value = "/singleDiseaseQueryById")
    public Result<QuestionVo> singleDiseaseQueryById(@Valid QuestionQueryByIdParam param) {
        Result<QuestionVo> result = new Result<QuestionVo>();
        QuestionVo questionVo = questionService.queryByIdNew(param, QuestionConstant.CATEGORY_TYPE_SINGLE_DISEASE);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }

    @ApiOperation(value = "答题", notes = "答题")
    @PostMapping(value = "/answer")
    public Result answer(@RequestBody AnswerMiniAppParam answerMiniAppParam) {
        try {
            return answerCheckService.answerByMiniApp(answerMiniAppParam);
        }catch (Exception e){
            if( e.getMessage().contains("Table") &&  e.getMessage().contains("doesn't exist")) {
                return ResultFactory.fail("问卷未发布");
            }
        }
        return ResultFactory.fail();
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


}
