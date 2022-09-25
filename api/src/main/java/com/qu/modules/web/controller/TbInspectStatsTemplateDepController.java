package com.qu.modules.web.controller;

import com.qu.modules.web.service.ITbInspectStatsTemplateDepService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 科室检查统计模板
 * @Author: jeecg-boot
 * @Date:   2022-09-18
 * @Version: V1.0
 */
@Slf4j
@Api(tags="科室检查统计模板")
@RestController
@RequestMapping("/business/tbInspectStatsTemplateDep")
public class TbInspectStatsTemplateDepController {
	@Autowired
	private ITbInspectStatsTemplateDepService tbInspectStatsTemplateDepService;
	
//	/**
//	  * 分页列表查询
//	 * @param tbInspectStatsTemplateDep
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "科室检查统计模板-分页列表查询")
//	@ApiOperation(value="科室检查统计模板-分页列表查询", notes="科室检查统计模板-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbInspectStatsTemplateDep>> queryPageList(TbInspectStatsTemplateDep tbInspectStatsTemplateDep,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbInspectStatsTemplateDep>> result = new Result<IPage<TbInspectStatsTemplateDep>>();
//		QueryWrapper<TbInspectStatsTemplateDep> queryWrapper = QueryGenerator.initQueryWrapper(tbInspectStatsTemplateDep, req.getParameterMap());
//		Page<TbInspectStatsTemplateDep> page = new Page<TbInspectStatsTemplateDep>(pageNo, pageSize);
//		IPage<TbInspectStatsTemplateDep> pageList = tbInspectStatsTemplateDepService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbInspectStatsTemplateDep
//	 * @return
//	 */
//	@AutoLog(value = "科室检查统计模板-添加")
//	@ApiOperation(value="科室检查统计模板-添加", notes="科室检查统计模板-添加")
//	@PostMapping(value = "/add")
//	public Result<TbInspectStatsTemplateDep> add(@RequestBody TbInspectStatsTemplateDep tbInspectStatsTemplateDep) {
//		Result<TbInspectStatsTemplateDep> result = new Result<TbInspectStatsTemplateDep>();
//		try {
//			tbInspectStatsTemplateDepService.save(tbInspectStatsTemplateDep);
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
//	 * @param tbInspectStatsTemplateDep
//	 * @return
//	 */
//	@AutoLog(value = "科室检查统计模板-编辑")
//	@ApiOperation(value="科室检查统计模板-编辑", notes="科室检查统计模板-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbInspectStatsTemplateDep> edit(@RequestBody TbInspectStatsTemplateDep tbInspectStatsTemplateDep) {
//		Result<TbInspectStatsTemplateDep> result = new Result<TbInspectStatsTemplateDep>();
//		TbInspectStatsTemplateDep tbInspectStatsTemplateDepEntity = tbInspectStatsTemplateDepService.getById(tbInspectStatsTemplateDep.getId());
//		if(tbInspectStatsTemplateDepEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbInspectStatsTemplateDepService.updateById(tbInspectStatsTemplateDep);
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
//	@AutoLog(value = "科室检查统计模板-通过id删除")
//	@ApiOperation(value="科室检查统计模板-通过id删除", notes="科室检查统计模板-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbInspectStatsTemplateDep> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbInspectStatsTemplateDep> result = new Result<TbInspectStatsTemplateDep>();
//		TbInspectStatsTemplateDep tbInspectStatsTemplateDep = tbInspectStatsTemplateDepService.getById(id);
//		if(tbInspectStatsTemplateDep==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbInspectStatsTemplateDepService.removeById(id);
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
//	@AutoLog(value = "科室检查统计模板-批量删除")
//	@ApiOperation(value="科室检查统计模板-批量删除", notes="科室检查统计模板-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbInspectStatsTemplateDep> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbInspectStatsTemplateDep> result = new Result<TbInspectStatsTemplateDep>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbInspectStatsTemplateDepService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "科室检查统计模板-通过id查询")
//	@ApiOperation(value="科室检查统计模板-通过id查询", notes="科室检查统计模板-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbInspectStatsTemplateDep> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbInspectStatsTemplateDep> result = new Result<TbInspectStatsTemplateDep>();
//		TbInspectStatsTemplateDep tbInspectStatsTemplateDep = tbInspectStatsTemplateDepService.getById(id);
//		if(tbInspectStatsTemplateDep==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbInspectStatsTemplateDep);
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
//      QueryWrapper<TbInspectStatsTemplateDep> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbInspectStatsTemplateDep tbInspectStatsTemplateDep = JSON.parseObject(deString, TbInspectStatsTemplateDep.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbInspectStatsTemplateDep, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbInspectStatsTemplateDep> pageList = tbInspectStatsTemplateDepService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "科室检查统计模板列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbInspectStatsTemplateDep.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("科室检查统计模板列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbInspectStatsTemplateDep> listTbInspectStatsTemplateDeps = ExcelImportUtil.importExcel(file.getInputStream(), TbInspectStatsTemplateDep.class, params);
//              for (TbInspectStatsTemplateDep tbInspectStatsTemplateDepExcel : listTbInspectStatsTemplateDeps) {
//                  tbInspectStatsTemplateDepService.save(tbInspectStatsTemplateDepExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbInspectStatsTemplateDeps.size());
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
