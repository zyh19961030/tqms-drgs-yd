package com.qu.modules.web.controller;

import java.util.*;

import com.qu.modules.web.entity.DrugRulesSubject;
import com.qu.modules.web.entity.Question;
import com.qu.modules.web.service.IDrugRulesSubjectService;
import com.qu.modules.web.service.IQuestionService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import com.qu.modules.web.entity.DrugRulesQuestion;
import com.qu.modules.web.service.IDrugRulesQuestionService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 药品规则问卷表
 * @Author: jeecg-boot
 * @Date:   2021-09-12
 * @Version: V1.0
 */
@Slf4j
@Api(tags="药品规则问卷表")
@RestController
@RequestMapping("/business/web/drugRulesQuestion")
public class DrugRulesQuestionController {
	@Autowired
	private IDrugRulesQuestionService drugRulesQuestionService;
	@Autowired
	private IQuestionService questionService;
	@Autowired
	private IDrugRulesSubjectService drugRulesSubjectService;

	 /**
	  *   通过id删除
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "药品规则问卷表-通过id删除")
	 @ApiOperation(value="药品规则问卷表-通过id删除", notes="药品规则问卷表-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<DrugRulesQuestion> delete(@RequestParam(name="id",required=true) Integer id) {
		 Result<DrugRulesQuestion> result = new Result<DrugRulesQuestion>();
		 Date date = new Date();
		 List<DrugRulesQuestion> drugRulesQuestions = drugRulesQuestionService.queryById(id);
		 drugRulesQuestions.forEach(drugRulesQuestion -> {
			 Integer questionId = drugRulesQuestion.getQuestionId();
			 List<DrugRulesSubject> drugRulesSubjects = drugRulesSubjectService.querySubject(questionId);
			 drugRulesSubjects.forEach(drugRulesSubject -> {
				 Integer drugRulesSubjectId = drugRulesSubject.getId();
				 drugRulesSubjectService.deleteSubject(drugRulesSubjectId, date);
			 });
		 });
		 int i = drugRulesQuestionService.updateQuestion(id, 1, date);
		 if(i != 1) {
			 result.error500("删除失败");
		 }else {
		 	 result.success("删除成功!");
			 }
		 return result;
	 }

	 /**
	  *   添加界面根据输入内容搜索药品规则问卷
	  * @param name
	  * @return
	  */
	 @AutoLog(value = "药品规则问卷表-添加界面根据输入内容搜索问卷")
	 @ApiOperation(value="药品规则问卷表-添加界面根据输入内容搜索问卷", notes="药品规则问卷表-添加界面根据输入内容搜索问卷")
	 @GetMapping(value = "/queryQuestionByInput")
	 public List<Question> queryQuestionByInput(@RequestParam(name = "name", required = true) String name) {
		 List<Question> list = questionService.queryQuestionByInput(name);
		 return list;
	 }

	/**
	  * 分页列表查询
	 * @param drugRulesQuestion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "药品规则问卷表-分页列表查询")
//	@ApiOperation(value="药品规则问卷表-分页列表查询", notes="药品规则问卷表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<DrugRulesQuestion>> queryPageList(DrugRulesQuestion drugRulesQuestion,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<DrugRulesQuestion>> result = new Result<IPage<DrugRulesQuestion>>();
//		QueryWrapper<DrugRulesQuestion> queryWrapper = QueryGenerator.initQueryWrapper(drugRulesQuestion, req.getParameterMap());
//		Page<DrugRulesQuestion> page = new Page<DrugRulesQuestion>(pageNo, pageSize);
//		IPage<DrugRulesQuestion> pageList = drugRulesQuestionService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}

//	/**
//	  *  编辑
//	 * @param drugRulesQuestion
//	 * @return
//	 */
//	@AutoLog(value = "药品规则问卷表-编辑")
//	@ApiOperation(value="药品规则问卷表-编辑", notes="药品规则问卷表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<DrugRulesQuestion> edit(@RequestBody DrugRulesQuestion drugRulesQuestion) {
//		Result<DrugRulesQuestion> result = new Result<DrugRulesQuestion>();
//		DrugRulesQuestion drugRulesQuestionEntity = drugRulesQuestionService.getById(drugRulesQuestion.getId());
//		if(drugRulesQuestionEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = drugRulesQuestionService.updateById(drugRulesQuestion);
//			//TODO 返回false说明什么？
//			if(ok) {
//				result.success("修改成功!");
//			}
//		}
//
//		return result;
//	}

	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
//	@AutoLog(value = "药品规则问卷表-批量删除")
//	@ApiOperation(value="药品规则问卷表-批量删除", notes="药品规则问卷表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<DrugRulesQuestion> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<DrugRulesQuestion> result = new Result<DrugRulesQuestion>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.drugRulesQuestionService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
//	@AutoLog(value = "药品规则问卷表-通过id查询")
//	@ApiOperation(value="药品规则问卷表-通过id查询", notes="药品规则问卷表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<DrugRulesQuestion> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<DrugRulesQuestion> result = new Result<DrugRulesQuestion>();
//		DrugRulesQuestion drugRulesQuestion = drugRulesQuestionService.getById(id);
//		if(drugRulesQuestion==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(drugRulesQuestion);
//			result.setSuccess(true);
//		}
//		return result;
//	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
//  @RequestMapping(value = "/exportXls")
//  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
//      // Step.1 组装查询条件
//      QueryWrapper<DrugRulesQuestion> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              DrugRulesQuestion drugRulesQuestion = JSON.parseObject(deString, DrugRulesQuestion.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(drugRulesQuestion, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<DrugRulesQuestion> pageList = drugRulesQuestionService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "药品规则问卷表列表");
//      mv.addObject(NormalExcelConstants.CLASS, DrugRulesQuestion.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("药品规则问卷表列表数据", "导出人:Jeecg", "导出信息"));
//      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
//      return mv;
//  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
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
//              List<DrugRulesQuestion> listDrugRulesQuestions = ExcelImportUtil.importExcel(file.getInputStream(), DrugRulesQuestion.class, params);
//              for (DrugRulesQuestion drugRulesQuestionExcel : listDrugRulesQuestions) {
//                  drugRulesQuestionService.save(drugRulesQuestionExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listDrugRulesQuestions.size());
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
