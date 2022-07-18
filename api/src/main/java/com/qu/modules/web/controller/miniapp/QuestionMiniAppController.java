package com.qu.modules.web.controller.miniapp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qu.modules.web.service.IQuestionService;
import com.qu.modules.web.vo.QuestionAndCategoryPageVo;
import com.qu.modules.web.vo.QuestionMiniAppPageVo;
import com.qu.modules.web.vo.QuestionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 问卷表_小程序后台调用
 */
@Slf4j
@Api(tags = "问卷表_小程序后台调用")
@RestController
@RequestMapping("/web/miniapp/question")
public class QuestionMiniAppController {

    @Autowired
    private IQuestionService questionService;

    /**
     * 分页列表查询
     *
     * @param deptId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AutoLog(value = "检查填报-分页列表查询")
    @ApiOperation(value = "检查填报-分页列表查询", notes = "检查填报-分页列表查询",response = QuestionAndCategoryPageVo.class)
    @GetMapping(value = "/list")
    public Result<QuestionAndCategoryPageVo> queryPageList(String deptId,
                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "100") Integer pageSize) {
        IPage<QuestionMiniAppPageVo> iPage = questionService.queryPageListByMiniApp(deptId, pageNo, pageSize);
        return ResultFactory.success(iPage);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "检查填报-通过id查询")
    @ApiOperation(value = "检查填报-通过id查询", notes = "检查填报-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<QuestionVo> queryById(@RequestParam(name = "id", required = true) Integer id) {
        Result<QuestionVo> result = new Result<QuestionVo>();
        QuestionVo questionVo = questionService.queryById(id);
        result.setResult(questionVo);
        result.setSuccess(true);
        return result;
    }




}
