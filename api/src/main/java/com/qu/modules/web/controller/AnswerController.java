package com.qu.modules.web.controller;

import com.alibaba.fastjson.JSON;
import com.qu.modules.web.entity.Answer;
import com.qu.modules.web.param.AnswerParam;
import com.qu.modules.web.service.IAnswerService;
import com.qu.modules.web.vo.AnswerPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "答案")
@RestController
@RequestMapping("/business/answer")
public class AnswerController {

    @Autowired
    private IAnswerService answerService;

//    @ApiOperation(value = "创建动态表", notes = "创建动态表")
//    @GetMapping(value = "/createDynamicTable")
//    public Result<Integer> createDynamicTable() {
//        Result<Integer> result = new Result<Integer>();
//        StringBuffer sql = new StringBuffer();
//        sql.append("CREATE TABLE `abc` (");
//        sql.append("`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',");
//        sql.append("`aaa` varchar(200),");
//        sql.append(" PRIMARY KEY (`id`)");
//        sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='aaa';");
//        answerService.createDynamicTable(sql.toString());
//        result.setSuccess(true);
//        result.setResult(1);
//        return result;
//    }
//
//    @ApiOperation(value = "insert table", notes = "insert table")
//    @GetMapping(value = "/insertDynamicTable")
//    public Result<Integer> insertDynamicTable() {
//        Result<Integer> result = new Result<Integer>();
//        StringBuffer sql = new StringBuffer();
//        sql.append("insert into abc (aaa) values  ('123654');");
//        answerService.insertDynamicTable(sql.toString());
//        result.setSuccess(true);
//        result.setResult(1);
//        return result;
//    }

    @ApiOperation(value = "答题", notes = "答题")
    @PostMapping(value = "/answer")
    public Result<Boolean> answer(@RequestBody AnswerParam answerParam) {
        Result<Boolean> result = new Result<Boolean>();
        log.info("-----------answerParam={}", JSON.toJSONString(answerParam));
        Boolean flag = answerService.answer(answerParam);
        result.setSuccess(true);
        result.setResult(flag);
        return result;
    }

    @ApiOperation(value = "答题回显", notes = "答题回显")
    @GetMapping(value = "/queryByQuId")
    public Result<String> queryByQuId(@RequestParam Integer quId) {
        Result<String> result = new Result<String>();
        String answer = answerService.queryByQuId(quId);
        result.setSuccess(true);
        result.setResult(answer);
        return result;
    }

    @ApiOperation(value = "问卷填报记录分页列表", notes = "问卷填报记录分页列表")
    @GetMapping(value = "/questionFillInList")
    public Result<AnswerPageVo> questionFillInList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        Result<AnswerPageVo> result = new Result<AnswerPageVo>();
        AnswerPageVo answerPageVo = answerService.questionFillInList(pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(answerPageVo);
        return result;
    }

    @ApiOperation(value = "问卷填报记录-通过id查询", notes = "问卷填报记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<Answer> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<Answer> result = new Result<Answer>();
        Answer answer = answerService.getById(id);
        if (answer == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(answer);
            result.setSuccess(true);
        }
        return result;
    }

}
