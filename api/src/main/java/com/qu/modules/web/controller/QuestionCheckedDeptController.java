package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 问卷被检查科室关联表
 * @Author: jeecg-boot
 * @Date:   2022-09-18
 * @Version: V1.0
 */
@Slf4j
@Api(tags="问卷被检查科室关联表")
@RestController
@RequestMapping("/business/questionCheckedDept")
public class QuestionCheckedDeptController {
//	@Autowired
//	private IQuestionCheckedDeptService questionCheckedDeptService;
//
//	/**
//	  * 分页列表查询
//	 * @param questionCheckedDept
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "问卷被检查科室关联表-分页列表查询")
//	@ApiOperation(value="问卷被检查科室关联表-分页列表查询", notes="问卷被检查科室关联表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QuestionCheckedDept>> queryPageList(QuestionCheckedDept questionCheckedDept,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QuestionCheckedDept>> result = new Result<IPage<QuestionCheckedDept>>();
//		QueryWrapper<QuestionCheckedDept> queryWrapper = QueryGenerator.initQueryWrapper(questionCheckedDept, req.getParameterMap());
//		Page<QuestionCheckedDept> page = new Page<QuestionCheckedDept>(pageNo, pageSize);
//		IPage<QuestionCheckedDept> pageList = questionCheckedDeptService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param questionCheckedDept
//	 * @return
//	 */
//	@AutoLog(value = "问卷被检查科室关联表-添加")
//	@ApiOperation(value="问卷被检查科室关联表-添加", notes="问卷被检查科室关联表-添加")
//	@PostMapping(value = "/add")
//	public Result<QuestionCheckedDept> add(@RequestBody QuestionCheckedDept questionCheckedDept) {
//		Result<QuestionCheckedDept> result = new Result<QuestionCheckedDept>();
//		try {
//			questionCheckedDeptService.save(questionCheckedDept);
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
//	 * @param questionCheckedDept
//	 * @return
//	 */
//	@AutoLog(value = "问卷被检查科室关联表-编辑")
//	@ApiOperation(value="问卷被检查科室关联表-编辑", notes="问卷被检查科室关联表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QuestionCheckedDept> edit(@RequestBody QuestionCheckedDept questionCheckedDept) {
//		Result<QuestionCheckedDept> result = new Result<QuestionCheckedDept>();
//		QuestionCheckedDept questionCheckedDeptEntity = questionCheckedDeptService.getById(questionCheckedDept.getId());
//		if(questionCheckedDeptEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionCheckedDeptService.updateById(questionCheckedDept);
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
//	@AutoLog(value = "问卷被检查科室关联表-通过id删除")
//	@ApiOperation(value="问卷被检查科室关联表-通过id删除", notes="问卷被检查科室关联表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QuestionCheckedDept> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionCheckedDept> result = new Result<QuestionCheckedDept>();
//		QuestionCheckedDept questionCheckedDept = questionCheckedDeptService.getById(id);
//		if(questionCheckedDept==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionCheckedDeptService.removeById(id);
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
//	@AutoLog(value = "问卷被检查科室关联表-批量删除")
//	@ApiOperation(value="问卷被检查科室关联表-批量删除", notes="问卷被检查科室关联表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QuestionCheckedDept> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QuestionCheckedDept> result = new Result<QuestionCheckedDept>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.questionCheckedDeptService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "问卷被检查科室关联表-通过id查询")
//	@ApiOperation(value="问卷被检查科室关联表-通过id查询", notes="问卷被检查科室关联表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QuestionCheckedDept> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionCheckedDept> result = new Result<QuestionCheckedDept>();
//		QuestionCheckedDept questionCheckedDept = questionCheckedDeptService.getById(id);
//		if(questionCheckedDept==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(questionCheckedDept);
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
//      QueryWrapper<QuestionCheckedDept> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QuestionCheckedDept questionCheckedDept = JSON.parseObject(deString, QuestionCheckedDept.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(questionCheckedDept, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QuestionCheckedDept> pageList = questionCheckedDeptService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "问卷被检查科室关联表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QuestionCheckedDept.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("问卷被检查科室关联表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QuestionCheckedDept> listQuestionCheckedDepts = ExcelImportUtil.importExcel(file.getInputStream(), QuestionCheckedDept.class, params);
//              for (QuestionCheckedDept questionCheckedDeptExcel : listQuestionCheckedDepts) {
//                  questionCheckedDeptService.save(questionCheckedDeptExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQuestionCheckedDepts.size());
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
