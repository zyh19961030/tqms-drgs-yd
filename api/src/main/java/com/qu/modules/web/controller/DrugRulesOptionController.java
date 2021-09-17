package com.qu.modules.web.controller;

import java.util.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qu.modules.web.entity.DrugReceiveHis;
import com.qu.modules.web.entity.Qoption;
import com.qu.modules.web.service.IOptionService;
import com.qu.modules.web.vo.optionVo;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import com.qu.modules.web.entity.DrugRulesOption;
import com.qu.modules.web.service.IDrugRulesOptionService;
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
 * @Description: 药品规则答案表
 * @Author: jeecg-boot
 * @Date:   2021-09-12
 * @Version: V1.0
 */
@Slf4j
@Api(tags="药品规则答案表")
@RestController
@RequestMapping("/web/drugRulesOption")
public class DrugRulesOptionController {
	@Autowired
	private IDrugRulesOptionService drugRulesOptionService;
	@Autowired
	private IOptionService optionService;
	@Autowired
	private DrugReceiveHisController drugReceiveHisController;
	@Autowired
	private optionVo optionVo;
	
	/**
	  * 分页列表查询
	 * @param drugRulesOption
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "药品规则答案表-分页列表查询")
//	@ApiOperation(value="药品规则答案表-分页列表查询", notes="药品规则答案表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<DrugRulesOption>> queryPageList(DrugRulesOption drugRulesOption,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<DrugRulesOption>> result = new Result<IPage<DrugRulesOption>>();
//		QueryWrapper<DrugRulesOption> queryWrapper = QueryGenerator.initQueryWrapper(drugRulesOption, req.getParameterMap());
//		Page<DrugRulesOption> page = new Page<DrugRulesOption>(pageNo, pageSize);
//		IPage<DrugRulesOption> pageList = drugRulesOptionService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
	
	/**
	  *   添加
	 * @param drugRulesOption
	 * @return
	 */
//	@AutoLog(value = "药品规则答案表-添加")
//	@ApiOperation(value="药品规则答案表-添加", notes="药品规则答案表-添加")
//	@PostMapping(value = "/add")
//	public Result<DrugRulesOption> add(@RequestBody DrugRulesOption drugRulesOption) {
//		Result<DrugRulesOption> result = new Result<DrugRulesOption>();
//		try {
//			drugRulesOptionService.save(drugRulesOption);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.error500("操作失败");
//		}
//		return result;
//	}
	
	/**
	  *  编辑
	 * @param drugRulesOption
	 * @return
	 */
//	@AutoLog(value = "药品规则答案表-编辑")
//	@ApiOperation(value="药品规则答案表-编辑", notes="药品规则答案表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<DrugRulesOption> edit(@RequestBody DrugRulesOption drugRulesOption) {
//		Result<DrugRulesOption> result = new Result<DrugRulesOption>();
//		DrugRulesOption drugRulesOptionEntity = drugRulesOptionService.getById(drugRulesOption.getId());
//		if(drugRulesOptionEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = drugRulesOptionService.updateById(drugRulesOption);
//			//TODO 返回false说明什么？
//			if(ok) {
//				result.success("修改成功!");
//			}
//		}
//
//		return result;
//	}
	
//	/**
//	  *   通过id删除
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "药品规则答案表-通过id删除")
//	@ApiOperation(value="药品规则答案表-通过id删除", notes="药品规则答案表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<DrugRulesOption> delete(@RequestParam(name="id",required=true) String id) {
//		Result<DrugRulesOption> result = new Result<DrugRulesOption>();
//		DrugRulesOption drugRulesOption = drugRulesOptionService.getById(id);
//		if(drugRulesOption==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = drugRulesOptionService.removeById(id);
//			if(ok) {
//				result.success("删除成功!");
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
//	@AutoLog(value = "药品规则答案表-批量删除")
//	@ApiOperation(value="药品规则答案表-批量删除", notes="药品规则答案表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<DrugRulesOption> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<DrugRulesOption> result = new Result<DrugRulesOption>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.drugRulesOptionService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
//	@AutoLog(value = "药品规则答案表-通过id查询")
//	@ApiOperation(value="药品规则答案表-通过id查询", notes="药品规则答案表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<DrugRulesOption> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<DrugRulesOption> result = new Result<DrugRulesOption>();
//		DrugRulesOption drugRulesOption = drugRulesOptionService.getById(id);
//		if(drugRulesOption==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(drugRulesOption);
//			result.setSuccess(true);
//		}
//		return result;
//	}

	 /**
	  * 根据选择问题的id获取答案
	  * @param subjectId
	  * @param subjectId
	  * @return
	  */
	 @AutoLog(value = "药品规则答案表-根据选择问题的id获取答案")
	 @ApiOperation(value="药品规则答案表-根据选择问题的id获取答案", notes="药品规则答案表-根据选择问题的id获取答案")
	 @GetMapping(value = "/queryOption")
	 public List<optionVo> queryOption(@RequestParam(name="subjectId",required=true) Integer subjectId,
									   @RequestParam(name = "matches", required = true) Integer matches) {
		 List<optionVo> optionVoList = new ArrayList<>();
		 List<DrugRulesOption> drugRulesOptions = drugRulesOptionService.queryOption(subjectId);
		 drugRulesOptions.forEach(drugRulesOption -> {
		 	 optionVo optionVo = new optionVo();
			 List<String> his = new ArrayList<>();
			 Integer id = drugRulesOption.getId();
			 Integer optionId = drugRulesOption.getOptionId();
			 Qoption qoption = optionService.getById(optionId);
			 String opName = qoption.getOpName();
			 if (matches == 0){
				 List<DrugReceiveHis> list = drugReceiveHisController.queryByPid(id);
				 list.forEach(drugReceiveHis -> {
					 Integer drugPhysicalActionId = drugReceiveHis.getDrugPhysicalActionId();
					 if (drugPhysicalActionId != null && drugPhysicalActionId >= 0){
						 String drugPhysicalActionName = drugReceiveHis.getDrugPhysicalActionName();
						 his.add(drugPhysicalActionName);
					 } else {
						 String medicationPurposeName = drugReceiveHis.getMedicationPurposeName();
						 his.add(medicationPurposeName);
					 }
				 });
			 } else {
			 	his.add(null);
			 }
			 optionVo.setId(id);
			 optionVo.setName(opName);
			 optionVo.setHis(his);
			 optionVoList.add(optionVo);
		 });
		 return optionVoList;
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
//      QueryWrapper<DrugRulesOption> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              DrugRulesOption drugRulesOption = JSON.parseObject(deString, DrugRulesOption.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(drugRulesOption, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<DrugRulesOption> pageList = drugRulesOptionService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "药品规则答案表列表");
//      mv.addObject(NormalExcelConstants.CLASS, DrugRulesOption.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("药品规则答案表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<DrugRulesOption> listDrugRulesOptions = ExcelImportUtil.importExcel(file.getInputStream(), DrugRulesOption.class, params);
//              for (DrugRulesOption drugRulesOptionExcel : listDrugRulesOptions) {
//                  drugRulesOptionService.save(drugRulesOptionExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listDrugRulesOptions.size());
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
