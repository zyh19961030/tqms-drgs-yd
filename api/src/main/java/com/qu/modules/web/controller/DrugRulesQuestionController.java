package com.qu.modules.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qu.modules.web.entity.DrugRulesSubject;
import com.qu.modules.web.mapper.DrugRulesSubjectMapper;
import com.qu.modules.web.service.IDrugRulesSubjectService;
import com.qu.modules.web.vo.QuestionVo;
import com.qu.modules.web.vo.inputVo;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import com.qu.modules.web.entity.DrugRulesQuestion;
import com.qu.modules.web.service.IDrugRulesQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
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
@RequestMapping("/web/drugRulesQuestion")
public class DrugRulesQuestionController {
	@Autowired
	private IDrugRulesQuestionService drugRulesQuestionService;
	@Autowired
	private IDrugRulesSubjectService drugRulesSubjectService;
	@Autowired
	private DrugRulesSubjectController drugRulesSubjectController;
    @Autowired
	private inputVo inputVo;

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

	/**
	  *   添加
	 * @param questionoId
	 * @param questionNmae
	 * @param subjectId
	 * @param subjectNmae
	 * @param matches
	 * @param timeLimit
	 * @param startEvent
	 * @param startAround
	 * @param startTime
	 * @param endEvent
	 * @param endAround
	 * @return
	 */
	@AutoLog(value = "药品规则问卷表-添加")
	@ApiOperation(value="药品规则问卷表-添加", notes="药品规则问卷表-添加")
	@PostMapping(value = "/add")
	public Result<DrugRulesQuestion> add(@RequestParam(name = "questionId", required = false) Integer questionoId,
										 @RequestParam(name = "questionNmae", required = false) String questionNmae,
										 @RequestParam(name = "subjectId", required = true) Integer subjectId,
										 @RequestParam(name = "subjectName", required = true) String subjectNmae,
										 @RequestParam(name = "matches", required = true) Integer matches,
										 @RequestParam(name = "timeLimit", required = true) Integer timeLimit,
										 @RequestParam(name = "startEvevt", required = false) String startEvent,
										 @RequestParam(name = "startAround", required = false) String startAround,
										 @RequestParam(name = "startTime", required = false) Integer startTime,
										 @RequestParam(name = "endEvevt", required = false) String endEvent,
										 @RequestParam(name = "endAround", required = false) String endAround,
										 @RequestParam(name = "endTime", required = false) Integer endTime) {
		Result<DrugRulesQuestion> result = new Result<DrugRulesQuestion>();
		Date date = new Date();
		DrugRulesQuestion drugRulesQuestion = new DrugRulesQuestion();
		drugRulesQuestion.setId(questionoId);
		drugRulesQuestion.setQuestionId(questionoId);
		drugRulesQuestion.setQuestionName(questionNmae);
		drugRulesQuestion.setDel(0);
		drugRulesQuestion.setCreateTime(date);
		drugRulesQuestion.setUpdateTime(date);
		Result<DrugRulesSubject> add = drugRulesSubjectController.add(subjectId, subjectNmae, questionoId, matches, timeLimit, startEvent, startAround, startTime, endEvent, endAround, endTime);
		try {
			boolean success = add.isSuccess();
			if (success == true){
				drugRulesQuestionService.save(drugRulesQuestion);
				String message = add.getMessage();
				result.success(message);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

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
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "药品规则问卷表-通过id删除")
	@ApiOperation(value="药品规则问卷表-通过id删除", notes="药品规则问卷表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<DrugRulesQuestion> delete(@RequestParam(name="id",required=true) String id) {
		Result<DrugRulesQuestion> result = new Result<DrugRulesQuestion>();
		DrugRulesQuestion drugRulesQuestion = drugRulesQuestionService.getById(id);
		if(drugRulesQuestion==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = drugRulesQuestionService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}

		return result;
	}

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
	  * 展示药品规则问卷
	  * @return
	  */
	 @AutoLog(value = "药品规则问卷表-展示药品规则问卷")
	 @ApiOperation(value="药品规则问卷表-展示药品规则问卷", notes="药品规则问卷表-展示药品规则问卷")
	 @PutMapping(value = "/queryquestion")
	 public List<DrugRulesQuestion> queryquestion() {
		 List<DrugRulesQuestion> list = new ArrayList<>();
		 List<DrugRulesQuestion> list1 = drugRulesQuestionService.queryQuestion();
		 list1.forEach(drugRulesQuestion -> {
		 	list.add(drugRulesQuestion);
		 });
		 return list;
	 }

	 /**
	  * 根据输入内容搜索药品规则问卷和问题
	  * @param name
	  * @return
	  */
	 @AutoLog(value = "药品规则问卷表-根据输入内容搜索药品规则问卷和问题")
	 @ApiOperation(value="药品规则问卷表-根据输入内容搜索药品规则问卷和问题", notes="药品规则问卷表-根据输入内容搜索药品规则问卷和问题")
	 @PutMapping(value = "/queryQuestionByInput")
	 public List<inputVo> queryQuestionByInput(@RequestParam(name="name",required=true) String name) {
		 List<inputVo> list = new ArrayList<>();
		 List<DrugRulesQuestion> drugRulesQuestions = drugRulesQuestionService.queryQuestionByInput(name);
		 if (drugRulesQuestions.size() > 0){
			 drugRulesQuestions.forEach(drugRulesQuestion -> {
				 inputVo inputVo = new inputVo();
				 inputVo.setDrugRulesQuestion(drugRulesQuestion);
				 list.add(inputVo);
			 });
		 }
		 List<DrugRulesSubject> drugRulesSubjects = drugRulesSubjectService.querySubjectByInput(name);
		 if ((drugRulesSubjects.size() > 0)){
			 drugRulesSubjects.forEach(drugRulesSubject -> {
				 inputVo inputVo = new inputVo();
				 inputVo.setDrugRulesSubject(drugRulesSubject);
				 list.add(inputVo);
			 });
		 }
		 return list;
	 }

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
