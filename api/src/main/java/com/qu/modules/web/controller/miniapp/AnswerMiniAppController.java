package com.qu.modules.web.controller.miniapp;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qu.modules.web.entity.AnswerCheck;
import com.qu.modules.web.param.AnswerCheckListParam;
import com.qu.modules.web.request.AnswerCheckListRequest;
import com.qu.modules.web.service.IAnswerCheckService;
import com.qu.modules.web.vo.AnswerCheckPageVo;
import com.qu.modules.web.vo.AnswerCheckVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "小程序后台调用_答案")
@RestController
@RequestMapping("/web/miniapp/answer")
public class AnswerMiniAppController {

    @Autowired
    private IAnswerCheckService answerCheckService;


    @ApiOperation(value = "检查表问卷_填报记录分页列表", notes = "检查表问卷_填报中分页列表")
    @GetMapping(value = "/checkQuestionRecordList")
    public Result<AnswerCheckPageVo> checkQuestionRecordList(AnswerCheckListParam answerCheckListParam,
                                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             HttpServletRequest req) {
        Result<AnswerCheckPageVo> result = new Result<AnswerCheckPageVo>();
        AnswerCheckListRequest listRequest = new AnswerCheckListRequest();
        BeanUtils.copyProperties(answerCheckListParam,listRequest);
        IPage<AnswerCheckVo> answerPageVo = answerCheckService.checkQuestionFillInList(listRequest,pageNo, pageSize, null);
        result.setSuccess(true);
        result.setResult(answerPageVo);
        return result;
    }



    @ApiOperation(value = "问卷填报记录-通过id查询", notes = "问卷填报记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<AnswerCheck> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<AnswerCheck> result = new Result<>();
        AnswerCheck answerCheck = answerCheckService.queryById(id);
        if (answerCheck == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(answerCheck);
            result.setSuccess(true);
        }
        return result;
    }


}
