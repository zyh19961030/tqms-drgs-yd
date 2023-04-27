package com.qu.modules.web.controller;

import com.qu.constant.Constant;
import com.qu.modules.web.param.QuestionCheckClassificationAddParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.IQuestionCheckClassificationService;
import com.qu.modules.web.vo.QuestionCheckClassificationListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.ResultBetter;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 查检表分类表
 * @Author: jeecg-boot
 * @Date:   2023-04-27
 * @Version: V1.0
 */
@Slf4j
@Api(tags="查检表分类表")
@RestController
@RequestMapping("/business/questionCheckClassification")
public class QuestionCheckClassificationController {
	@Autowired
	private IQuestionCheckClassificationService questionCheckClassificationService;


	 @ApiOperation(value = "分类列表", notes = "分类列表")
	 @GetMapping(value = "/list")
	 public ResultBetter<List<QuestionCheckClassificationListVo>> queryList(HttpServletRequest request) {
		 ResultBetter<List<QuestionCheckClassificationListVo>> result = new ResultBetter<>();
		 Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
		 List<QuestionCheckClassificationListVo> questionCheckPageVoIPage = questionCheckClassificationService.queryList(data);
		 result.setSuccess(true);
		 result.setResult(questionCheckPageVoIPage);
		 return result;
	 }


	 /**
	  *   添加
	  * @param param
	  * @return
	  */
	 @AutoLog(value = "查检表分类表-添加")
	 @ApiOperation(value="查检表分类表-添加", notes="查检表分类表-添加")
	 @PostMapping(value = "/add")
	 public ResultBetter<T> add(@RequestBody QuestionCheckClassificationAddParam param,HttpServletRequest request) {
		 Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
		 questionCheckClassificationService.add(param,data);
		 return ResultBetter.ok();
	 }


//	 /**
//	  * 分页列表查询
//	 * @param questionCheckClassification
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "查检表分类表-分页列表查询")
//	@ApiOperation(value="查检表分类表-分页列表查询", notes="查检表分类表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QuestionCheckClassification>> queryPageList(QuestionCheckClassification questionCheckClassification,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QuestionCheckClassification>> result = new Result<IPage<QuestionCheckClassification>>();
//		QueryWrapper<QuestionCheckClassification> queryWrapper = QueryGenerator.initQueryWrapper(questionCheckClassification, req.getParameterMap());
//		Page<QuestionCheckClassification> page = new Page<QuestionCheckClassification>(pageNo, pageSize);
//		IPage<QuestionCheckClassification> pageList = questionCheckClassificationService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//	/**
//	  *  编辑
//	 * @param questionCheckClassification
//	 * @return
//	 */
//	@AutoLog(value = "查检表分类表-编辑")
//	@ApiOperation(value="查检表分类表-编辑", notes="查检表分类表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QuestionCheckClassification> edit(@RequestBody QuestionCheckClassification questionCheckClassification) {
//		Result<QuestionCheckClassification> result = new Result<QuestionCheckClassification>();
//		QuestionCheckClassification questionCheckClassificationEntity = questionCheckClassificationService.getById(questionCheckClassification.getId());
//		if(questionCheckClassificationEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionCheckClassificationService.updateById(questionCheckClassification);
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
//	@AutoLog(value = "查检表分类表-通过id删除")
//	@ApiOperation(value="查检表分类表-通过id删除", notes="查检表分类表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QuestionCheckClassification> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionCheckClassification> result = new Result<QuestionCheckClassification>();
//		QuestionCheckClassification questionCheckClassification = questionCheckClassificationService.getById(id);
//		if(questionCheckClassification==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = questionCheckClassificationService.removeById(id);
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
//	@AutoLog(value = "查检表分类表-批量删除")
//	@ApiOperation(value="查检表分类表-批量删除", notes="查检表分类表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QuestionCheckClassification> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QuestionCheckClassification> result = new Result<QuestionCheckClassification>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.questionCheckClassificationService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "查检表分类表-通过id查询")
//	@ApiOperation(value="查检表分类表-通过id查询", notes="查检表分类表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QuestionCheckClassification> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QuestionCheckClassification> result = new Result<QuestionCheckClassification>();
//		QuestionCheckClassification questionCheckClassification = questionCheckClassificationService.getById(id);
//		if(questionCheckClassification==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(questionCheckClassification);
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
//      QueryWrapper<QuestionCheckClassification> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QuestionCheckClassification questionCheckClassification = JSON.parseObject(deString, QuestionCheckClassification.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(questionCheckClassification, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QuestionCheckClassification> pageList = questionCheckClassificationService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "查检表分类表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QuestionCheckClassification.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("查检表分类表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QuestionCheckClassification> listQuestionCheckClassifications = ExcelImportUtil.importExcel(file.getInputStream(), QuestionCheckClassification.class, params);
//              for (QuestionCheckClassification questionCheckClassificationExcel : listQuestionCheckClassifications) {
//                  questionCheckClassificationService.save(questionCheckClassificationExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQuestionCheckClassifications.size());
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
