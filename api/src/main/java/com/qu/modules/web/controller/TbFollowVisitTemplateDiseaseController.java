package com.qu.modules.web.controller;

import com.qu.modules.web.service.ITbFollowVisitTemplateDiseaseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 随访模板疾病表
 * @Author: jeecg-boot
 * @Date:   2023-02-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags="随访模板疾病表")
@RestController
@RequestMapping("/web/tbFollowVisitTemplateDisease")
public class TbFollowVisitTemplateDiseaseController {
	@Autowired
	private ITbFollowVisitTemplateDiseaseService tbFollowVisitTemplateDiseaseService;
	
//	/**
//	  * 分页列表查询
//	 * @param tbFollowVisitTemplateDisease
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "随访模板疾病表-分页列表查询")
//	@ApiOperation(value="随访模板疾病表-分页列表查询", notes="随访模板疾病表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbFollowVisitTemplateDisease>> queryPageList(TbFollowVisitTemplateDisease tbFollowVisitTemplateDisease,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbFollowVisitTemplateDisease>> result = new Result<IPage<TbFollowVisitTemplateDisease>>();
//		QueryWrapper<TbFollowVisitTemplateDisease> queryWrapper = QueryGenerator.initQueryWrapper(tbFollowVisitTemplateDisease, req.getParameterMap());
//		Page<TbFollowVisitTemplateDisease> page = new Page<TbFollowVisitTemplateDisease>(pageNo, pageSize);
//		IPage<TbFollowVisitTemplateDisease> pageList = tbFollowVisitTemplateDiseaseService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbFollowVisitTemplateDisease
//	 * @return
//	 */
//	@AutoLog(value = "随访模板疾病表-添加")
//	@ApiOperation(value="随访模板疾病表-添加", notes="随访模板疾病表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbFollowVisitTemplateDisease> add(@RequestBody TbFollowVisitTemplateDisease tbFollowVisitTemplateDisease) {
//		Result<TbFollowVisitTemplateDisease> result = new Result<TbFollowVisitTemplateDisease>();
//		try {
//			tbFollowVisitTemplateDiseaseService.save(tbFollowVisitTemplateDisease);
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
//	 * @param tbFollowVisitTemplateDisease
//	 * @return
//	 */
//	@AutoLog(value = "随访模板疾病表-编辑")
//	@ApiOperation(value="随访模板疾病表-编辑", notes="随访模板疾病表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbFollowVisitTemplateDisease> edit(@RequestBody TbFollowVisitTemplateDisease tbFollowVisitTemplateDisease) {
//		Result<TbFollowVisitTemplateDisease> result = new Result<TbFollowVisitTemplateDisease>();
//		TbFollowVisitTemplateDisease tbFollowVisitTemplateDiseaseEntity = tbFollowVisitTemplateDiseaseService.getById(tbFollowVisitTemplateDisease.getId());
//		if(tbFollowVisitTemplateDiseaseEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitTemplateDiseaseService.updateById(tbFollowVisitTemplateDisease);
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
//	@AutoLog(value = "随访模板疾病表-通过id删除")
//	@ApiOperation(value="随访模板疾病表-通过id删除", notes="随访模板疾病表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbFollowVisitTemplateDisease> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitTemplateDisease> result = new Result<TbFollowVisitTemplateDisease>();
//		TbFollowVisitTemplateDisease tbFollowVisitTemplateDisease = tbFollowVisitTemplateDiseaseService.getById(id);
//		if(tbFollowVisitTemplateDisease==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitTemplateDiseaseService.removeById(id);
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
//	@AutoLog(value = "随访模板疾病表-批量删除")
//	@ApiOperation(value="随访模板疾病表-批量删除", notes="随访模板疾病表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbFollowVisitTemplateDisease> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbFollowVisitTemplateDisease> result = new Result<TbFollowVisitTemplateDisease>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbFollowVisitTemplateDiseaseService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "随访模板疾病表-通过id查询")
//	@ApiOperation(value="随访模板疾病表-通过id查询", notes="随访模板疾病表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbFollowVisitTemplateDisease> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitTemplateDisease> result = new Result<TbFollowVisitTemplateDisease>();
//		TbFollowVisitTemplateDisease tbFollowVisitTemplateDisease = tbFollowVisitTemplateDiseaseService.getById(id);
//		if(tbFollowVisitTemplateDisease==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbFollowVisitTemplateDisease);
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
//      QueryWrapper<TbFollowVisitTemplateDisease> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbFollowVisitTemplateDisease tbFollowVisitTemplateDisease = JSON.parseObject(deString, TbFollowVisitTemplateDisease.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbFollowVisitTemplateDisease, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbFollowVisitTemplateDisease> pageList = tbFollowVisitTemplateDiseaseService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "随访模板疾病表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbFollowVisitTemplateDisease.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("随访模板疾病表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbFollowVisitTemplateDisease> listTbFollowVisitTemplateDiseases = ExcelImportUtil.importExcel(file.getInputStream(), TbFollowVisitTemplateDisease.class, params);
//              for (TbFollowVisitTemplateDisease tbFollowVisitTemplateDiseaseExcel : listTbFollowVisitTemplateDiseases) {
//                  tbFollowVisitTemplateDiseaseService.save(tbFollowVisitTemplateDiseaseExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbFollowVisitTemplateDiseases.size());
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
