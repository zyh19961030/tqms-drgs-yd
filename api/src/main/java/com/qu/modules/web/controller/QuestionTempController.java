package com.qu.modules.web.controller;

import com.qu.modules.web.param.IdParam;
import com.qu.modules.web.service.IQuestionTempService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description: 问卷临时表
 * @Author: jeecg-boot
 * @Date:   2022-10-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags="问卷临时表")
@RestController
@RequestMapping("/business/questionTemp")
public class QuestionTempController {
	@Autowired
	private IQuestionTempService questionTempService;

	/**
     * 从temp表向问卷表添加问卷
	 * @param idParam
	 * @return
	 */
	@AutoLog(value = "从temp表向问卷表添加问卷")
	@ApiOperation(value="从temp表向问卷表(question)添加问卷", notes="从temp表向问卷表添加问卷")
	@PostMapping(value = "/copyTemp")
	public Result<?> copyTemp(@Valid @RequestBody IdParam idParam) {
		return questionTempService.copyTemp(idParam);
	}


//	/**
//	  * 分页列表查询
//	 * @param questionTemp
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "问卷临时表-分页列表查询")
//	@ApiOperation(value="问卷临时表-分页列表查询", notes="问卷临时表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QuestionTemp>> queryPageList(QuestionTemp questionTemp,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QuestionTemp>> result = new Result<IPage<QuestionTemp>>();
//		QueryWrapper<QuestionTemp> queryWrapper = QueryGenerator.initQueryWrapper(questionTemp, req.getParameterMap());
//		Page<QuestionTemp> page = new Page<QuestionTemp>(pageNo, pageSize);
//		IPage<QuestionTemp> pageList = questionTempService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param questionTemp
//	 * @return
//	 */
//	@AutoLog(value = "问卷临时表-添加")
//	@ApiOperation(value="问卷临时表-添加", notes="问卷临时表-添加")
//	@PostMapping(value = "/add")
//	public Result<QuestionTemp> add(@RequestBody QuestionTemp questionTemp) {
//		Result<QuestionTemp> result = new Result<QuestionTemp>();
//		try {
//			questionTempService.save(questionTemp);
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
//	 * @param questionTemp
//	 * @return
//	 */
//	@AutoLog(value = "问卷临时表-编辑")
//	@ApiOperation(value="问卷临时表-编辑", notes="问卷临时表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QuestionTemp> edit(@RequestBody QuestionTemp questionTemp) {
//		Result<QuestionTemp> result = new Result<QuestionTemp>();
//		QuestionTemp questionTempEntity = questionTempService.getById(questionTemp.getId());
//		if(questionTempEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionTempService.updateById(questionTemp);
//			//TO DO 返回false说明什么？
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
//	@AutoLog(value = "问卷临时表-通过id删除")
//	@ApiOperation(value="问卷临时表-通过id删除", notes="问卷临时表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QuestionTemp> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionTemp> result = new Result<QuestionTemp>();
//		QuestionTemp questionTemp = questionTempService.getById(id);
//		if(questionTemp==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionTempService.removeById(id);
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
//	@AutoLog(value = "问卷临时表-批量删除")
//	@ApiOperation(value="问卷临时表-批量删除", notes="问卷临时表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QuestionTemp> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QuestionTemp> result = new Result<QuestionTemp>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.questionTempService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "问卷临时表-通过id查询")
//	@ApiOperation(value="问卷临时表-通过id查询", notes="问卷临时表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QuestionTemp> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionTemp> result = new Result<QuestionTemp>();
//		QuestionTemp questionTemp = questionTempService.getById(id);
//		if(questionTemp==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(questionTemp);
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
//      QueryWrapper<QuestionTemp> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QuestionTemp questionTemp = JSON.parseObject(deString, QuestionTemp.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(questionTemp, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QuestionTemp> pageList = questionTempService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "问卷临时表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QuestionTemp.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("问卷临时表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QuestionTemp> listQuestionTemps = ExcelImportUtil.importExcel(file.getInputStream(), QuestionTemp.class, params);
//              for (QuestionTemp questionTempExcel : listQuestionTemps) {
//                  questionTempService.save(questionTempExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQuestionTemps.size());
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
