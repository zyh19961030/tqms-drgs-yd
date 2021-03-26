package com.qu.modules.web.controller;

import com.qu.modules.web.param.QsubjectlibAddParam;
import com.qu.modules.web.param.QsubjectlibEditParam;
import com.qu.modules.web.param.QsubjectlibParam;
import com.qu.modules.web.service.IQsubjectlibService;
import com.qu.modules.web.vo.QsubjectlibPageVo;
import com.qu.modules.web.vo.QsubjectlibVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 题库题目表
 * @Author: jeecg-boot
 * @Date: 2021-03-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "题库题目表")
@RestController
@RequestMapping("/business/qsubjectlib")
public class QsubjectlibController {

    @Autowired
    private IQsubjectlibService qsubjectlibService;

    /**
     * 分页列表查询
     *
     * @param qsubjectlibParam
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "题库题目表-分页列表查询")
    @ApiOperation(value = "题库题目表-分页列表查询", notes = "题库题目表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<QsubjectlibPageVo> queryPageList(QsubjectlibParam qsubjectlibParam,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        Result<QsubjectlibPageVo> result = new Result<QsubjectlibPageVo>();
        QsubjectlibPageVo qsubjectlibPageVo = qsubjectlibService.queryPageList(qsubjectlibParam, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(qsubjectlibPageVo);
        return result;
    }

    /**
     * 添加
     *
     * @param qsubjectlibAddParam
     * @return
     */
    @AutoLog(value = "题库题目表-添加")
    @ApiOperation(value = "题库题目表-添加", notes = "题库题目表-添加")
    @PostMapping(value = "/add")
    public Result<QsubjectlibVo> add(@RequestBody QsubjectlibAddParam qsubjectlibAddParam) {
        Result<QsubjectlibVo> result = new Result<QsubjectlibVo>();
        try {
            QsubjectlibVo qsubjectlibVo = qsubjectlibService.saveQsubjectlib(qsubjectlibAddParam);
            result.setResult(qsubjectlibVo);
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
     * @param qsubjectlibEditParam
     * @return
     */
    @AutoLog(value = "题库题目表-编辑")
    @ApiOperation(value = "题库题目表-编辑", notes = "题库题目表-编辑")
    @PutMapping(value = "/edit")
    public Result<QsubjectlibVo> edit(@RequestBody QsubjectlibEditParam qsubjectlibEditParam) {
        Result<QsubjectlibVo> result = new Result<QsubjectlibVo>();
        QsubjectlibVo qsubjectlibVo = qsubjectlibService.updateQsubjectlibById(qsubjectlibEditParam);
        result.setResult(qsubjectlibVo);
        result.success("修改成功!");
        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "题库题目表-通过id删除")
    @ApiOperation(value = "题库题目表-通过id删除", notes = "题库题目表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<Boolean> delete(@RequestParam(name = "id", required = true) Integer id) {
        Result<Boolean> result = new Result<Boolean>();
        Boolean ok = qsubjectlibService.removeQsubjectlibById(id);
        result.setResult(ok);
        result.success("删除成功!");
        return result;
    }

//	/**
//	  *  批量删除
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "题库题目表-批量删除")
//	@ApiOperation(value="题库题目表-批量删除", notes="题库题目表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<Qsubjectlib> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<Qsubjectlib> result = new Result<Qsubjectlib>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.qsubjectlibService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "题库题目表-通过id查询")
    @ApiOperation(value = "题库题目表-通过id查询", notes = "题库题目表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<QsubjectlibVo> queryById(@RequestParam(name = "id", required = true) Integer id) {
        Result<QsubjectlibVo> result = new Result<QsubjectlibVo>();
        QsubjectlibVo qsubjectlibVo = qsubjectlibService.getQsubjectlibById(id);
        result.setResult(qsubjectlibVo);
        result.setSuccess(true);
        return result;
    }


}
