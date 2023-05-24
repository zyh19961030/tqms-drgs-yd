package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 录入表单填报题目表
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags="录入表单填报题目表")
@RestController
@RequestMapping("/web/singleEnterQuestionSubject")
public class SingleEnterQuestionSubjectController {
//	@Autowired
//	private ISingleEnterQuestionSubjectService singleEnterQuestionSubjectService;
//
//	/**
//	  * 分页列表查询
//	 * @param singleEnterQuestionSubject
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "录入表单填报题目表-分页列表查询")
//	@ApiOperation(value="录入表单填报题目表-分页列表查询", notes="录入表单填报题目表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<SingleEnterQuestionSubject>> queryPageList(SingleEnterQuestionSubject singleEnterQuestionSubject,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<SingleEnterQuestionSubject>> result = new Result<IPage<SingleEnterQuestionSubject>>();
//		QueryWrapper<SingleEnterQuestionSubject> queryWrapper = QueryGenerator.initQueryWrapper(singleEnterQuestionSubject, req.getParameterMap());
//		Page<SingleEnterQuestionSubject> page = new Page<SingleEnterQuestionSubject>(pageNo, pageSize);
//		IPage<SingleEnterQuestionSubject> pageList = singleEnterQuestionSubjectService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param singleEnterQuestionSubject
//	 * @return
//	 */
//	@AutoLog(value = "录入表单填报题目表-添加")
//	@ApiOperation(value="录入表单填报题目表-添加", notes="录入表单填报题目表-添加")
//	@PostMapping(value = "/add")
//	public Result<SingleEnterQuestionSubject> add(@RequestBody SingleEnterQuestionSubject singleEnterQuestionSubject) {
//		Result<SingleEnterQuestionSubject> result = new Result<SingleEnterQuestionSubject>();
//		try {
//			singleEnterQuestionSubjectService.save(singleEnterQuestionSubject);
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
//	 * @param singleEnterQuestionSubject
//	 * @return
//	 */
//	@AutoLog(value = "录入表单填报题目表-编辑")
//	@ApiOperation(value="录入表单填报题目表-编辑", notes="录入表单填报题目表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<SingleEnterQuestionSubject> edit(@RequestBody SingleEnterQuestionSubject singleEnterQuestionSubject) {
//		Result<SingleEnterQuestionSubject> result = new Result<SingleEnterQuestionSubject>();
//		SingleEnterQuestionSubject singleEnterQuestionSubjectEntity = singleEnterQuestionSubjectService.getById(singleEnterQuestionSubject.getId());
//		if(singleEnterQuestionSubjectEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = singleEnterQuestionSubjectService.updateById(singleEnterQuestionSubject);
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
//	@AutoLog(value = "录入表单填报题目表-通过id删除")
//	@ApiOperation(value="录入表单填报题目表-通过id删除", notes="录入表单填报题目表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<SingleEnterQuestionSubject> delete(@RequestParam(name="id",required=true) String id) {
//		Result<SingleEnterQuestionSubject> result = new Result<SingleEnterQuestionSubject>();
//		SingleEnterQuestionSubject singleEnterQuestionSubject = singleEnterQuestionSubjectService.getById(id);
//		if(singleEnterQuestionSubject==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = singleEnterQuestionSubjectService.removeById(id);
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
//	@AutoLog(value = "录入表单填报题目表-批量删除")
//	@ApiOperation(value="录入表单填报题目表-批量删除", notes="录入表单填报题目表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<SingleEnterQuestionSubject> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<SingleEnterQuestionSubject> result = new Result<SingleEnterQuestionSubject>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.singleEnterQuestionSubjectService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "录入表单填报题目表-通过id查询")
//	@ApiOperation(value="录入表单填报题目表-通过id查询", notes="录入表单填报题目表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<SingleEnterQuestionSubject> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<SingleEnterQuestionSubject> result = new Result<SingleEnterQuestionSubject>();
//		SingleEnterQuestionSubject singleEnterQuestionSubject = singleEnterQuestionSubjectService.getById(id);
//		if(singleEnterQuestionSubject==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(singleEnterQuestionSubject);
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
//      QueryWrapper<SingleEnterQuestionSubject> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              SingleEnterQuestionSubject singleEnterQuestionSubject = JSON.parseObject(deString, SingleEnterQuestionSubject.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(singleEnterQuestionSubject, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<SingleEnterQuestionSubject> pageList = singleEnterQuestionSubjectService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "录入表单填报题目表列表");
//      mv.addObject(NormalExcelConstants.CLASS, SingleEnterQuestionSubject.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("录入表单填报题目表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<SingleEnterQuestionSubject> listSingleEnterQuestionSubjects = ExcelImportUtil.importExcel(file.getInputStream(), SingleEnterQuestionSubject.class, params);
//              for (SingleEnterQuestionSubject singleEnterQuestionSubjectExcel : listSingleEnterQuestionSubjects) {
//                  singleEnterQuestionSubjectService.save(singleEnterQuestionSubjectExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listSingleEnterQuestionSubjects.size());
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
