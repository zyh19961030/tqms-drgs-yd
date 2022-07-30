package com.qu.modules.web.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qu.constant.AnswerCheckConstant;
import com.qu.modules.web.entity.AnswerCheck;
import com.qu.modules.web.param.AnswerCheckAddParam;
import com.qu.modules.web.param.AnswerCheckListParam;
import com.qu.modules.web.service.IAnswerCheckService;
import com.qu.modules.web.vo.AnswerCheckPageVo;
import com.qu.modules.web.vo.AnswerCheckVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

 /**
 * @Description: 检查表问卷总表
 * @Author: jeecg-boot
 * @Date:   2022-07-30
 * @Version: V1.0
 */
@Slf4j
@Api(tags="检查表问卷总表")
@RestController
@RequestMapping("/business/answerCheck")
public class AnswerCheckController {

	@Autowired
	private IAnswerCheckService answerCheckService;

	 @ApiOperation(value = "检查表问卷_填报中分页列表", notes = "检查表问卷_填报中分页列表")
	 @GetMapping(value = "/checkQuestionFillInList")
	 public Result<AnswerCheckPageVo> checkQuestionFillInList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			 HttpServletRequest req) {
		 Result<AnswerCheckPageVo> result = new Result<AnswerCheckPageVo>();
		 IPage<AnswerCheckVo> answerPageVo = answerCheckService.checkQuestionFillInList(null, pageNo, pageSize, AnswerCheckConstant.ANSWER_STATUS_DRAFT);
		 result.setSuccess(true);
		 result.setResult(answerPageVo);
		 return result;
	 }

	 @ApiOperation(value = "检查表问卷_填报记录(已完成的)分页列表", notes = "检查表问卷_填报记录(已完成的)分页列表")
	 @GetMapping(value = "/checkQuestionRecordList")
	 public Result<AnswerCheckPageVo> checkQuestionRecordList(AnswerCheckListParam answerCheckListParam,
															  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
															  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
															  HttpServletRequest req) {
		 Result<AnswerCheckPageVo> result = new Result<AnswerCheckPageVo>();
		 IPage<AnswerCheckVo> answerPageVo = answerCheckService.checkQuestionFillInList(answerCheckListParam,pageNo, pageSize, AnswerCheckConstant.ANSWER_STATUS_RELEASE);
		 result.setSuccess(true);
		 result.setResult(answerPageVo);
		 return result;
	 }

	 @ApiOperation(value = "答题", notes = "答题")
	 @PostMapping(value = "/answer")
	 public Result answer(@RequestBody AnswerCheckAddParam answerCheckAddParam, HttpServletRequest request) {
		 String token = request.getHeader("token");
		 String cookie = "JSESSIONID=" + token;
		 log.info("-----------answerCheckAddParam={}", JSON.toJSONString(answerCheckAddParam));
		 return answerCheckService.answer(cookie, answerCheckAddParam);
	 }


	 @ApiOperation(value = "回显_通过id查询", notes = "回显_通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<AnswerCheck> queryById(@RequestParam(name = "id", required = true) String id) {
		 Result<AnswerCheck> result = new Result<AnswerCheck>();
		 AnswerCheck answerCheck = answerCheckService.queryById(id);
		 if (answerCheck == null) {
			 result.error500("未找到对应实体");
		 } else {
			 result.setResult(answerCheck);
			 result.setSuccess(true);
		 }
		 return result;
	 }





//	/**
//	  * 分页列表查询
//	 * @param answerCheck
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "检查表问卷总表-分页列表查询")
//	@ApiOperation(value="检查表问卷总表-分页列表查询", notes="检查表问卷总表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<AnswerCheck>> queryPageList(AnswerCheck answerCheck,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<AnswerCheck>> result = new Result<IPage<AnswerCheck>>();
//		QueryWrapper<AnswerCheck> queryWrapper = QueryGenerator.initQueryWrapper(answerCheck, req.getParameterMap());
//		Page<AnswerCheck> page = new Page<AnswerCheck>(pageNo, pageSize);
//		IPage<AnswerCheck> pageList = answerCheckService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param answerCheck
//	 * @return
//	 */
//	@AutoLog(value = "检查表问卷总表-添加")
//	@ApiOperation(value="检查表问卷总表-添加", notes="检查表问卷总表-添加")
//	@PostMapping(value = "/add")
//	public Result<AnswerCheck> add(@RequestBody AnswerCheck answerCheck) {
//		Result<AnswerCheck> result = new Result<AnswerCheck>();
//		try {
//			answerCheckService.save(answerCheck);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.error500("操作失败");
//		}
//		return result;
//	}
//
//	/**
//	  *  编辑
//	 * @param answerCheck
//	 * @return
//	 */
//	@AutoLog(value = "检查表问卷总表-编辑")
//	@ApiOperation(value="检查表问卷总表-编辑", notes="检查表问卷总表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<AnswerCheck> edit(@RequestBody AnswerCheck answerCheck) {
//		Result<AnswerCheck> result = new Result<AnswerCheck>();
//		AnswerCheck answerCheckEntity = answerCheckService.getById(answerCheck.getId());
//		if(answerCheckEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = answerCheckService.updateById(answerCheck);
//			//TODO 返回false说明什么？
//			if(ok) {
//				result.success("修改成功!");
//			}
//		}
//
//		return result;
//	}
//
//	/**
//	  *   通过id删除
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "检查表问卷总表-通过id删除")
//	@ApiOperation(value="检查表问卷总表-通过id删除", notes="检查表问卷总表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<AnswerCheck> delete(@RequestParam(name="id",required=true) String id) {
//		Result<AnswerCheck> result = new Result<AnswerCheck>();
//		AnswerCheck answerCheck = answerCheckService.getById(id);
//		if(answerCheck==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = answerCheckService.removeById(id);
//			if(ok) {
//				result.success("删除成功!");
//			}
//		}
//
//		return result;
//	}
//
//	/**
//	  *  批量删除
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "检查表问卷总表-批量删除")
//	@ApiOperation(value="检查表问卷总表-批量删除", notes="检查表问卷总表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<AnswerCheck> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<AnswerCheck> result = new Result<AnswerCheck>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.answerCheckService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}
//
//	/**
//	  * 通过id查询
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "检查表问卷总表-通过id查询")
//	@ApiOperation(value="检查表问卷总表-通过id查询", notes="检查表问卷总表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<AnswerCheck> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<AnswerCheck> result = new Result<AnswerCheck>();
//		AnswerCheck answerCheck = answerCheckService.getById(id);
//		if(answerCheck==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(answerCheck);
//			result.setSuccess(true);
//		}
//		return result;
//	}
//
//  /**
//      * 导出excel
//   *
//   * @param request
//   * @param response
//   */
//  @RequestMapping(value = "/exportXls")
//  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
//      // Step.1 组装查询条件
//      QueryWrapper<AnswerCheck> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              AnswerCheck answerCheck = JSON.parseObject(deString, AnswerCheck.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(answerCheck, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<AnswerCheck> pageList = answerCheckService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "检查表问卷总表列表");
//      mv.addObject(NormalExcelConstants.CLASS, AnswerCheck.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("检查表问卷总表列表数据", "导出人:Jeecg", "导出信息"));
//      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
//      return mv;
//  }
//
//  /**
//      * 通过excel导入数据
//   *
//   * @param request
//   * @param response
//   * @return
//   */
//  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
//  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//          MultipartFile file = entity.getValue();// 获取上传文件对象
//          ImportParams params = new ImportParams();
//          params.setTitleRows(2);
//          params.setHeadRows(1);
//          params.setNeedSave(true);
//          try {
//              List<AnswerCheck> listAnswerChecks = ExcelImportUtil.importExcel(file.getInputStream(), AnswerCheck.class, params);
//              for (AnswerCheck answerCheckExcel : listAnswerChecks) {
//                  answerCheckService.save(answerCheckExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listAnswerChecks.size());
//          } catch (Exception e) {
//              log.error(e.getMessage(),e);
//              return Result.error("文件导入失败:"+e.getMessage());
//          } finally {
//              try {
//                  file.getInputStream().close();
//              } catch (IOException e) {
//                  e.printStackTrace();
//              }
//          }
//      }
//      return Result.ok("文件导入失败！");
//  }

}
