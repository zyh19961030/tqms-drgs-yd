package com.qu.modules.web.controller;

import java.util.*;

import com.qu.modules.web.entity.DrugRulesQuestion;
import com.qu.modules.web.param.QuestionAndSubjectParam;
import com.qu.modules.web.service.IDrugRulesQuestionService;
import com.qu.modules.web.vo.SearchResultVo;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import com.qu.modules.web.entity.DrugRulesSubject;
import com.qu.modules.web.service.IDrugRulesSubjectService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 药品规则问题表
 * @Author: jeecg-boot
 * @Date:   2021-09-12
 * @Version: V1.0
 */
@Slf4j
@Api(tags="药品规则问题表")
@RestController
@RequestMapping("/web/drugRulesSubject")
public class DrugRulesSubjectController {
	@Autowired
	private IDrugRulesSubjectService drugRulesSubjectService;
	@Autowired
	private IDrugRulesQuestionService drugRulesQuestionService;

	 /**
	  * 根据问卷id查询答案
	  * @param drugRulesQuestionId
	  * @return
	  */
	 @AutoLog(value = "药品规则问题表-根据问卷id查询答案")
	 @ApiOperation(value="药品规则问题表-根据问卷id查询答案", notes="药品规则问题表-根据问卷id查询答案")
	 @GetMapping(value = "/querySubject")
	 public List<DrugRulesSubject> querySubject(@RequestParam(name="drugRulesQuestionId",required=true) Integer drugRulesQuestionId) {
		 List<DrugRulesSubject> list = drugRulesSubjectService.querySubject(drugRulesQuestionId);
		 return list;
	 }

	 /**
	  *   添加
	  * @param questionAndSubjectVo
	  * @return
	  */
	 @AutoLog(value = "药品规则问题表-添加")
	 @ApiOperation(value="药品规则问题表-添加", notes="药品规则问题表-添加")
	 @PostMapping(value = "/add")
	 public Result<DrugRulesSubject> add(QuestionAndSubjectParam questionAndSubjectVo) {
		 Result<DrugRulesSubject> result = new Result<DrugRulesSubject>();
		 DrugRulesQuestion drugRulesQuestion = questionAndSubjectVo.getDrugRulesQuestion();
		 DrugRulesSubject drugRulesSubject = questionAndSubjectVo.getDrugRulesSubject();
		 Integer drugRulesQuestionId = drugRulesSubject.getDrugRulesQuestionId();
		 List<DrugRulesQuestion> drugRulesQuestions1 = drugRulesQuestionService.queryQuestionIfExistById(drugRulesQuestionId);
		 List<DrugRulesQuestion> drugRulesQuestions2 = drugRulesQuestionService.queryQuestionIfDelById(drugRulesQuestionId);
		 if (drugRulesQuestions1.isEmpty() && drugRulesQuestions2.isEmpty()){
			 drugRulesQuestionService.save(drugRulesQuestion);
		 } else {
			 Date date = new Date();
			 drugRulesQuestionService.updateQuestion(drugRulesQuestion.getQuestionId(), 0, date);
		 }
		 try {
			 drugRulesSubjectService.save(drugRulesSubject);
			 result.success("添加成功！");
		 } catch (Exception e) {
			 log.error(e.getMessage(),e);
			 result.error500("操作失败");
		 }
		 return result;
	 }

	 /**
	  *   通过id删除
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "药品规则问题表-通过id删除")
	 @ApiOperation(value="药品规则问题表-通过id删除", notes="药品规则问题表-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<DrugRulesSubject> delete(@RequestParam(name="id",required=true) Integer id) {
		 Result<DrugRulesSubject> result = new Result<DrugRulesSubject>();
		 Date date = new Date();
		 int i = drugRulesSubjectService.deleteSubject(id, date);
		 if (i == 1){
			 result.success("删除成功！");
		 } else {
			 result.error500("删除失败");
		 }
		 return result;
	 }

	 /**
	  * 根据输入内容搜索药品规则问题
	  * @param name
	  * @return
	  */
	 @AutoLog(value = "药品规则问题表-根据输入内容搜索药品规则问题")
	 @ApiOperation(value="药品规则问题表-根据输入内容搜索药品规则问题", notes="药品规则问题表-根据输入内容搜索药品规则问题")
	 @PutMapping(value = "/queryQuestionByInput")
	 public List<SearchResultVo> queryQuestionByInput(@RequestParam(name="name",required=false) String name) {
		 List<SearchResultVo> list = new ArrayList<>();
		 if (name != null && name.length() > 0){
			 List<Integer> list0 = new ArrayList<>();
			 List<DrugRulesSubject> drugRulesSubjects = drugRulesSubjectService.querySubjectByInput(name);
			 if (drugRulesSubjects != null && drugRulesSubjects.size() > 0){
				 drugRulesSubjects.forEach(drugRulesSubject -> {
					 Integer drugRulesQuestionId = drugRulesSubject.getDrugRulesQuestionId();
					 list0.add(drugRulesQuestionId);
				 });
				 HashSet hashSet = new HashSet(list0);
				 list0.clear();
				 list0.addAll(hashSet);
				 list0.forEach(drugRulesQuestionId -> {
					 SearchResultVo searchResultVo = new SearchResultVo();
					 DrugRulesQuestion drugRulesQuestion = drugRulesQuestionService.queryQuestionsById(drugRulesQuestionId);
					 List<DrugRulesSubject> rulesSubjects = drugRulesSubjectService.querySubjectByInputAndId(name, drugRulesQuestionId);
					 searchResultVo.setDrugRulesQuestion(drugRulesQuestion);
					 searchResultVo.setDrugRulesSubjectList(rulesSubjects);
					 list.add(searchResultVo);
				 });
			 }
		 } else {
			 List<DrugRulesQuestion> drugRulesQuestions = drugRulesQuestionService.queryQuestion();
			 drugRulesQuestions.forEach(drugRulesQuestion -> {
				 SearchResultVo searchResultVo = new SearchResultVo();
				 searchResultVo.setDrugRulesQuestion(drugRulesQuestion);
				 list.add(searchResultVo);
			 });
		 }
		 return list;
	 }

	/**
	  * 分页列表查询
	 * @param drugRulesSubject
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "药品规则问题表-分页列表查询")
//	@ApiOperation(value="药品规则问题表-分页列表查询", notes="药品规则问题表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<DrugRulesSubject>> queryPageList(DrugRulesSubject drugRulesSubject,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<DrugRulesSubject>> result = new Result<IPage<DrugRulesSubject>>();
//		QueryWrapper<DrugRulesSubject> queryWrapper = QueryGenerator.initQueryWrapper(drugRulesSubject, req.getParameterMap());
//		Page<DrugRulesSubject> page = new Page<DrugRulesSubject>(pageNo, pageSize);
//		IPage<DrugRulesSubject> pageList = drugRulesSubjectService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
	
	/**
	  *  编辑
	 * @param drugRulesSubject
	 * @return
	 */
//	@AutoLog(value = "药品规则问题表-编辑")
//	@ApiOperation(value="药品规则问题表-编辑", notes="药品规则问题表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<DrugRulesSubject> edit(@RequestBody DrugRulesSubject drugRulesSubject) {
//		Result<DrugRulesSubject> result = new Result<DrugRulesSubject>();
//		DrugRulesSubject drugRulesSubjectEntity = drugRulesSubjectService.getById(drugRulesSubject.getId());
//		if(drugRulesSubjectEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = drugRulesSubjectService.updateById(drugRulesSubject);
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
//	@AutoLog(value = "药品规则问题表-批量删除")
//	@ApiOperation(value="药品规则问题表-批量删除", notes="药品规则问题表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<DrugRulesSubject> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<DrugRulesSubject> result = new Result<DrugRulesSubject>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.drugRulesSubjectService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
//	@AutoLog(value = "药品规则问题表-通过id查询")
//	@ApiOperation(value="药品规则问题表-通过id查询", notes="药品规则问题表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<DrugRulesSubject> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<DrugRulesSubject> result = new Result<DrugRulesSubject>();
//		DrugRulesSubject drugRulesSubject = drugRulesSubjectService.getById(id);
//		if(drugRulesSubject==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(drugRulesSubject);
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
//      QueryWrapper<DrugRulesSubject> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              DrugRulesSubject drugRulesSubject = JSON.parseObject(deString, DrugRulesSubject.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(drugRulesSubject, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<DrugRulesSubject> pageList = drugRulesSubjectService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "药品规则问题表列表");
//      mv.addObject(NormalExcelConstants.CLASS, DrugRulesSubject.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("药品规则问题表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<DrugRulesSubject> listDrugRulesSubjects = ExcelImportUtil.importExcel(file.getInputStream(), DrugRulesSubject.class, params);
//              for (DrugRulesSubject drugRulesSubjectExcel : listDrugRulesSubjects) {
//                  drugRulesSubjectService.save(drugRulesSubjectExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listDrugRulesSubjects.size());
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
