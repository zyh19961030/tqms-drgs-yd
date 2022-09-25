package com.qu.modules.web.controller;

import com.qu.modules.web.service.IQuestionVersionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 问卷版本表
 * @Author: jeecg-boot
 * @Date:   2022-09-25
 * @Version: V1.0
 */
@Slf4j
@Api(tags="问卷版本表")
@RestController
@RequestMapping("/business/questionVersion")
public class QuestionVersionController {
	@Autowired
	private IQuestionVersionService questionVersionService;
	
//	/**
//	  * 分页列表查询
//	 * @param questionVersion
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "问卷版本表-分页列表查询")
//	@ApiOperation(value="问卷版本表-分页列表查询", notes="问卷版本表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QuestionVersion>> queryPageList(QuestionVersion questionVersion,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QuestionVersion>> result = new Result<IPage<QuestionVersion>>();
//		QueryWrapper<QuestionVersion> queryWrapper = QueryGenerator.initQueryWrapper(questionVersion, req.getParameterMap());
//		Page<QuestionVersion> page = new Page<QuestionVersion>(pageNo, pageSize);
//		IPage<QuestionVersion> pageList = questionVersionService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param questionVersion
//	 * @return
//	 */
//	@AutoLog(value = "问卷版本表-添加")
//	@ApiOperation(value="问卷版本表-添加", notes="问卷版本表-添加")
//	@PostMapping(value = "/add")
//	public Result<QuestionVersion> add(@RequestBody QuestionVersion questionVersion) {
//		Result<QuestionVersion> result = new Result<QuestionVersion>();
//		try {
//			questionVersionService.save(questionVersion);
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
//	 * @param questionVersion
//	 * @return
//	 */
//	@AutoLog(value = "问卷版本表-编辑")
//	@ApiOperation(value="问卷版本表-编辑", notes="问卷版本表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QuestionVersion> edit(@RequestBody QuestionVersion questionVersion) {
//		Result<QuestionVersion> result = new Result<QuestionVersion>();
//		QuestionVersion questionVersionEntity = questionVersionService.getById(questionVersion.getId());
//		if(questionVersionEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionVersionService.updateById(questionVersion);
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
//	@AutoLog(value = "问卷版本表-通过id删除")
//	@ApiOperation(value="问卷版本表-通过id删除", notes="问卷版本表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QuestionVersion> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionVersion> result = new Result<QuestionVersion>();
//		QuestionVersion questionVersion = questionVersionService.getById(id);
//		if(questionVersion==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionVersionService.removeById(id);
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
//	@AutoLog(value = "问卷版本表-批量删除")
//	@ApiOperation(value="问卷版本表-批量删除", notes="问卷版本表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QuestionVersion> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QuestionVersion> result = new Result<QuestionVersion>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.questionVersionService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "问卷版本表-通过id查询")
//	@ApiOperation(value="问卷版本表-通过id查询", notes="问卷版本表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QuestionVersion> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionVersion> result = new Result<QuestionVersion>();
//		QuestionVersion questionVersion = questionVersionService.getById(id);
//		if(questionVersion==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(questionVersion);
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
//      QueryWrapper<QuestionVersion> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QuestionVersion questionVersion = JSON.parseObject(deString, QuestionVersion.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(questionVersion, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QuestionVersion> pageList = questionVersionService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "问卷版本表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QuestionVersion.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("问卷版本表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QuestionVersion> listQuestionVersions = ExcelImportUtil.importExcel(file.getInputStream(), QuestionVersion.class, params);
//              for (QuestionVersion questionVersionExcel : listQuestionVersions) {
//                  questionVersionService.save(questionVersionExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQuestionVersions.size());
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
