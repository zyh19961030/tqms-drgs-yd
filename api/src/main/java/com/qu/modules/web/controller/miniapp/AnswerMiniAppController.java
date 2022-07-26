package com.qu.modules.web.controller.miniapp;

import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.service.IAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "小程序后台调用_答案")
@RestController
@RequestMapping("/web/miniapp/answer")
public class AnswerMiniAppController {

    @Autowired
    private IAnswerService answerService;


//    @ApiOperation(value = "问卷填报记录分页列表", notes = "问卷填报记录分页列表")
//    @GetMapping(value = "/questionFillInList")
//    public Result<AnswerPageVo> questionFillInList(@RequestParam(name = "quName", required = false) String quName,
//                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
//                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
//                                                   HttpServletRequest req) {
//        Result<AnswerPageVo> result = new Result<AnswerPageVo>();
//        AnswerPageVo answerPageVo = answerService.questionFillInList(quName,pageNo, pageSize);
//        result.setSuccess(true);
//        result.setResult(answerPageVo);
//        return result;
//    }


    @ApiOperation(value = "问卷填报记录-通过id查询", notes = "问卷填报记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<Answer> queryById(@RequestParam(name = "id", required = true) String id) {
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


}
