package com.qu.modules.web.controller.miniapp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qu.constant.AnswerCheckConstant;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.entity.AnswerCheck;
import com.qu.modules.web.param.AnswerCheckListMiniAppParam;
import com.qu.modules.web.param.AnswerListParam;
import com.qu.modules.web.param.AnswerMiniAppParam;
import com.qu.modules.web.request.AnswerCheckListRequest;
import com.qu.modules.web.service.IAnswerCheckService;
import com.qu.modules.web.service.IAnswerService;
import com.qu.modules.web.vo.AnswerCheckPageVo;
import com.qu.modules.web.vo.AnswerCheckVo;
import com.qu.modules.web.vo.AnswerMonthQuarterYearFillingInAndSubmitPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "小程序后台调用_答案")
@RestController
@RequestMapping("/web/miniapp/answer")
public class AnswerMiniAppController {

    @Autowired
    private IAnswerCheckService answerCheckService;

    @Autowired
    private IAnswerService answerService;


    @ApiOperation(value = "检查表问卷_填报记录分页列表", notes = "检查表问卷_填报记录分页列表")
    @GetMapping(value = "/checkQuestionRecordList")
    public Result<AnswerCheckPageVo> checkQuestionRecordList(AnswerCheckListMiniAppParam answerCheckListMiniAppParam, @RequestHeader(name = "userId") String userId,
                                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             HttpServletRequest req) {
        log.info("检查表问卷_填报记录分页列表-checkQuestionRecordList-answerCheckListMiniAppParam-------->{}",JSON.toJSONString(answerCheckListMiniAppParam));
        Result<AnswerCheckPageVo> result = new Result<AnswerCheckPageVo>();
        AnswerCheckListRequest listRequest = new AnswerCheckListRequest();
        BeanUtils.copyProperties(answerCheckListMiniAppParam,listRequest);
        listRequest.setCreaterDeptId(answerCheckListMiniAppParam.getUserDeptId());
        Integer checkUserType = answerCheckListMiniAppParam.getCheckUserType();
        if(AnswerCheckConstant.ANSWER_CHECK_LIST_MINIAPP_PARAM_CHECK_USER_TYPE_DEPT.equals(checkUserType)){
            listRequest.setUserId(null);
        }else{
            listRequest.setUserId(userId);
        }
        IPage<AnswerCheckVo> answerPageVo = answerCheckService.checkQuestionFillInList(listRequest,pageNo, pageSize, null);
        result.setSuccess(true);
        result.setResult(answerPageVo);
        return result;
    }

    @ApiOperation(value = "登记表_填报记录分页列表", notes = "登记表_填报记录分页列表")
    @GetMapping(value = "/answerQuestionRecordList")
    public Result<AnswerMonthQuarterYearFillingInAndSubmitPageVo> answerQuestionRecordList(AnswerListParam answerListParam, @RequestHeader(name = "userId") String userId,
                                                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                              HttpServletRequest req) {
        log.info("登记表_填报记录分页列表-answerQuestionRecordList-answerListParam-------->{}",JSON.toJSONString(answerListParam));
        Result<AnswerMonthQuarterYearFillingInAndSubmitPageVo> result = new Result<AnswerMonthQuarterYearFillingInAndSubmitPageVo>();
        AnswerMonthQuarterYearFillingInAndSubmitPageVo answerPageVo = answerService.answerQuestionFillInAndSubmitList(answerListParam,pageNo, pageSize,userId);
        result.setSuccess(true);
        result.setResult(answerPageVo);
        return result;
    }



    @ApiOperation(value = "检查表_问卷填报记录_通过id查询_查询的答案", notes = "检查表_问卷填报记录_通过id查询_查询的答案")
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


    @ApiOperation(value = "登记表_通过id查询_查询的答案", notes = "登记表_通过id查询_查询的答案")
    @GetMapping(value = "/answerQueryById")
    public Result<Answer> answerQueryById(@RequestParam(name = "id", required = true) String id) {
        Result<Answer> result = new Result<Answer>();
        Answer answer = answerService.queryById(id);
        if (answer == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(answer);
            result.setSuccess(true);
        }
        return result;
    }

    @ApiOperation(value = "登记表(普通问卷)_答题", notes = "登记表(普通问卷)_答题")
    @PostMapping(value = "/answer")
    public Result answer(@RequestBody AnswerMiniAppParam answerMiniAppParam, @RequestHeader(name = "userId") String userId) {
        log.info("-----------answerMiniAppParam={}", JSON.toJSONString(answerMiniAppParam));
        try {
            return answerService.answerByMiniApp(answerMiniAppParam,userId);
        }catch (Exception e){
            if( e.getMessage().contains("Table") &&  e.getMessage().contains("doesn't exist")) {
                return ResultFactory.fail("问卷未发布");
            }
        }
        return ResultFactory.fail();
    }


}
