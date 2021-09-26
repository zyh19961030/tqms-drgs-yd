package com.qu.modules.web.controller;

import java.util.List;

import com.qu.modules.web.param.DrugRulesRelationsListParam;
import com.qu.modules.web.vo.PurposeAndActionVo;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import com.qu.modules.web.entity.DrugRulesRelation;
import com.qu.modules.web.service.IDrugRulesRelationService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 药品规则关联表
 * @Author: jeecg-boot
 * @Date:   2021-09-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="药品规则关联表")
@RestController
@RequestMapping("/business/web/drugRulesRelation")
public class DrugRulesRelationController {
	@Autowired
	private IDrugRulesRelationService drugRulesRelationService;

	/**
	 *  设置
	 * @param drugRulesRelations
	 * @return
	 */
	@AutoLog(value = "药品规则关联表-设置")
	@ApiOperation(value="药品规则关联表-设置", notes="药品规则关联表-设置")
	@PutMapping(value = "/edit")
	public Result<DrugRulesRelation> edit(DrugRulesRelationsListParam drugRulesRelations) {
		Result<DrugRulesRelation> result = new Result<DrugRulesRelation>();
		Integer id = drugRulesRelations.getOptionId();
		List<DrugRulesRelation> drugRulesRelationList = drugRulesRelationService.ifExist(id);
		if (drugRulesRelationList != null && drugRulesRelationList.size() > 0){
			drugRulesRelationService.delete(id);
		}
		DrugRulesRelation drugRulesRelation = new DrugRulesRelation();
		List<PurposeAndActionVo> purposeAndActionVos = drugRulesRelations.getPurposeAndActionVos();
		int type = drugRulesRelations.getType();
		purposeAndActionVos.forEach(purposeAndActionVo -> {
			Integer medicationPurposeId = purposeAndActionVo.getMedicationPurposeId();
			Integer drugPhysicalActionId = purposeAndActionVo.getDrugPhysicalActionId();
			drugRulesRelation.setDrugRulesOptionId(id);
			drugRulesRelation.setMedicationPurposeId(medicationPurposeId);
			drugRulesRelation.setDrugPhysicalActionId(drugPhysicalActionId);
			drugRulesRelation.setType(type);
			try {
				drugRulesRelationService.save(drugRulesRelation);
				result.success("设置成功！");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				result.error500("操作失败!");
				return;
			}
		});
		return result;
	}

	/**
	 * 分页列表查询
	 * @param drugRulesRelation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "药品规则关联表-分页列表查询")
//	@ApiOperation(value="药品规则关联表-分页列表查询", notes="药品规则关联表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<DrugRulesRelation>> queryPageList(DrugRulesRelation drugRulesRelation,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<DrugRulesRelation>> result = new Result<IPage<DrugRulesRelation>>();
//		QueryWrapper<DrugRulesRelation> queryWrapper = QueryGenerator.initQueryWrapper(drugRulesRelation, req.getParameterMap());
//		Page<DrugRulesRelation> page = new Page<DrugRulesRelation>(pageNo, pageSize);
//		IPage<DrugRulesRelation> pageList = drugRulesRelationService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}

	/**
	 *   添加
	 * @param drugRulesRelation
	 * @return
	 */
//	@AutoLog(value = "药品规则关联表-添加")
//	@ApiOperation(value="药品规则关联表-添加", notes="药品规则关联表-添加")
//	@PostMapping(value = "/add")
//	public Result<DrugRulesRelation> add(@RequestBody DrugRulesRelation drugRulesRelation) {
//		Result<DrugRulesRelation> result = new Result<DrugRulesRelation>();
//		try {
//			drugRulesRelationService.save(drugRulesRelation);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.error500("操作失败");
//		}
//		return result;
//	}

	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
//	@AutoLog(value = "药品规则关联表-通过id删除")
//	@ApiOperation(value="药品规则关联表-通过id删除", notes="药品规则关联表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<DrugRulesRelation> delete(@RequestParam(name="id",required=true) String id) {
//		Result<DrugRulesRelation> result = new Result<DrugRulesRelation>();
//		DrugRulesRelation drugRulesRelation = drugRulesRelationService.getById(id);
//		if(drugRulesRelation==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = drugRulesRelationService.removeById(id);
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
//	@AutoLog(value = "药品规则关联表-批量删除")
//	@ApiOperation(value="药品规则关联表-批量删除", notes="药品规则关联表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<DrugRulesRelation> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<DrugRulesRelation> result = new Result<DrugRulesRelation>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.drugRulesRelationService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
//	@AutoLog(value = "药品规则关联表-通过id查询")
//	@ApiOperation(value="药品规则关联表-通过id查询", notes="药品规则关联表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<DrugRulesRelation> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<DrugRulesRelation> result = new Result<DrugRulesRelation>();
//		DrugRulesRelation drugRulesRelation = drugRulesRelationService.getById(id);
//		if(drugRulesRelation==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(drugRulesRelation);
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
//      QueryWrapper<DrugRulesRelation> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              DrugRulesRelation drugRulesRelation = JSON.parseObject(deString, DrugRulesRelation.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(drugRulesRelation, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<DrugRulesRelation> pageList = drugRulesRelationService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "药品规则关联表列表");
//      mv.addObject(NormalExcelConstants.CLASS, DrugRulesRelation.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("药品规则关联表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<DrugRulesRelation> listDrugRulesRelations = ExcelImportUtil.importExcel(file.getInputStream(), DrugRulesRelation.class, params);
//              for (DrugRulesRelation drugRulesRelationExcel : listDrugRulesRelations) {
//                  drugRulesRelationService.save(drugRulesRelationExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listDrugRulesRelations.size());
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
