package com.qu.modules.web.controller;

import com.qu.modules.web.entity.QuestionIcon;
import com.qu.modules.web.service.IQuestionIconService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 问卷图标表
 * @Author: jeecg-boot
 * @Date:   2022-09-05
 * @Version: V1.0
 */
@Slf4j
@Api(tags="问卷图标表")
@RestController
@RequestMapping("/business/questionIcon")
public class QuestionIconController {
	@Autowired
	private IQuestionIconService questionIconService;

	/**
	 * 列表查询
	 * @return
	 */
	@AutoLog(value = "问卷图标表-列表查询")
	@ApiOperation(value="问卷图标表-列表查询", notes="问卷图标表-列表查询")
	@GetMapping(value = "/list")
	public Result<List<QuestionIcon>> queryPageList() {
		Result<List<QuestionIcon>> result = new Result<List<QuestionIcon>>();
		List<QuestionIcon> pageList = questionIconService.queryPageList();
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

//	/**
//	  *   添加
//	 * @param questionIcon
//	 * @return
//	 */
//	@AutoLog(value = "问卷图标表-添加")
//	@ApiOperation(value="问卷图标表-添加", notes="问卷图标表-添加")
//	@PostMapping(value = "/add")
//	public Result<QuestionIcon> add(@RequestBody List<QuestionIcon> questionIcon) {
//		Result<QuestionIcon> result = new Result<QuestionIcon>();
//		try {
//			questionIcon.forEach(q->{
//				Date date = new Date();
//				q.setCreateTime(date);
//				q.setUpdateTime(date);
//				q.setType(QuestionIconConstant.TYPE_CHECK);
//				q.setDel(QuestionIconConstant.DEL_NORMAL);
//			});
//			questionIconService.saveBatch(questionIcon);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.error500("操作失败");
//		}
//		return result;
//	}
	
//	/**
//	  * 分页列表查询
//	 * @param questionIcon
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "问卷图标表-分页列表查询")
//	@ApiOperation(value="问卷图标表-分页列表查询", notes="问卷图标表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QuestionIcon>> queryPageList(QuestionIcon questionIcon,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QuestionIcon>> result = new Result<IPage<QuestionIcon>>();
//		QueryWrapper<QuestionIcon> queryWrapper = QueryGenerator.initQueryWrapper(questionIcon, req.getParameterMap());
//		Page<QuestionIcon> page = new Page<QuestionIcon>(pageNo, pageSize);
//		IPage<QuestionIcon> pageList = questionIconService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param questionIcon
//	 * @return
//	 */
//	@AutoLog(value = "问卷图标表-添加")
//	@ApiOperation(value="问卷图标表-添加", notes="问卷图标表-添加")
//	@PostMapping(value = "/add")
//	public Result<QuestionIcon> add(@RequestBody QuestionIcon questionIcon) {
//		Result<QuestionIcon> result = new Result<QuestionIcon>();
//		try {
//			questionIconService.save(questionIcon);
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
//	 * @param questionIcon
//	 * @return
//	 */
//	@AutoLog(value = "问卷图标表-编辑")
//	@ApiOperation(value="问卷图标表-编辑", notes="问卷图标表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QuestionIcon> edit(@RequestBody QuestionIcon questionIcon) {
//		Result<QuestionIcon> result = new Result<QuestionIcon>();
//		QuestionIcon questionIconEntity = questionIconService.getById(questionIcon.getId());
//		if(questionIconEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionIconService.updateById(questionIcon);
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
//	@AutoLog(value = "问卷图标表-通过id删除")
//	@ApiOperation(value="问卷图标表-通过id删除", notes="问卷图标表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QuestionIcon> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionIcon> result = new Result<QuestionIcon>();
//		QuestionIcon questionIcon = questionIconService.getById(id);
//		if(questionIcon==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionIconService.removeById(id);
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
//	@AutoLog(value = "问卷图标表-批量删除")
//	@ApiOperation(value="问卷图标表-批量删除", notes="问卷图标表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QuestionIcon> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QuestionIcon> result = new Result<QuestionIcon>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.questionIconService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "问卷图标表-通过id查询")
//	@ApiOperation(value="问卷图标表-通过id查询", notes="问卷图标表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QuestionIcon> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionIcon> result = new Result<QuestionIcon>();
//		QuestionIcon questionIcon = questionIconService.getById(id);
//		if(questionIcon==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(questionIcon);
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
//      QueryWrapper<QuestionIcon> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QuestionIcon questionIcon = JSON.parseObject(deString, QuestionIcon.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(questionIcon, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QuestionIcon> pageList = questionIconService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "问卷图标表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QuestionIcon.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("问卷图标表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QuestionIcon> listQuestionIcons = ExcelImportUtil.importExcel(file.getInputStream(), QuestionIcon.class, params);
//              for (QuestionIcon questionIconExcel : listQuestionIcons) {
//                  questionIconService.save(questionIconExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQuestionIcons.size());
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
