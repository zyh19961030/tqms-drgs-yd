package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 分组管理与科室关联表
 * @Author: jeecg-boot
 * @Date:   2022-09-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags="分组管理与科室关联表")
@RestController
@RequestMapping("/web/tbDeptGroupRelation")
public class TbDeptGroupRelationController {
//	@Autowired
//	private ITbDeptGroupRelationService tbDeptGroupRelationService;
//
//	/**
//	  * 分页列表查询
//	 * @param tbDeptGroupRelation
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "分组管理与科室关联表-分页列表查询")
//	@ApiOperation(value="分组管理与科室关联表-分页列表查询", notes="分组管理与科室关联表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbDeptGroupRelation>> queryPageList(TbDeptGroupRelation tbDeptGroupRelation,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbDeptGroupRelation>> result = new Result<IPage<TbDeptGroupRelation>>();
//		QueryWrapper<TbDeptGroupRelation> queryWrapper = QueryGenerator.initQueryWrapper(tbDeptGroupRelation, req.getParameterMap());
//		Page<TbDeptGroupRelation> page = new Page<TbDeptGroupRelation>(pageNo, pageSize);
//		IPage<TbDeptGroupRelation> pageList = tbDeptGroupRelationService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbDeptGroupRelation
//	 * @return
//	 */
//	@AutoLog(value = "分组管理与科室关联表-添加")
//	@ApiOperation(value="分组管理与科室关联表-添加", notes="分组管理与科室关联表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbDeptGroupRelation> add(@RequestBody TbDeptGroupRelation tbDeptGroupRelation) {
//		Result<TbDeptGroupRelation> result = new Result<TbDeptGroupRelation>();
//		try {
//			tbDeptGroupRelationService.save(tbDeptGroupRelation);
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
//	 * @param tbDeptGroupRelation
//	 * @return
//	 */
//	@AutoLog(value = "分组管理与科室关联表-编辑")
//	@ApiOperation(value="分组管理与科室关联表-编辑", notes="分组管理与科室关联表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbDeptGroupRelation> edit(@RequestBody TbDeptGroupRelation tbDeptGroupRelation) {
//		Result<TbDeptGroupRelation> result = new Result<TbDeptGroupRelation>();
//		TbDeptGroupRelation tbDeptGroupRelationEntity = tbDeptGroupRelationService.getById(tbDeptGroupRelation.getId());
//		if(tbDeptGroupRelationEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbDeptGroupRelationService.updateById(tbDeptGroupRelation);
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
//	@AutoLog(value = "分组管理与科室关联表-通过id删除")
//	@ApiOperation(value="分组管理与科室关联表-通过id删除", notes="分组管理与科室关联表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbDeptGroupRelation> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbDeptGroupRelation> result = new Result<TbDeptGroupRelation>();
//		TbDeptGroupRelation tbDeptGroupRelation = tbDeptGroupRelationService.getById(id);
//		if(tbDeptGroupRelation==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbDeptGroupRelationService.removeById(id);
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
//	@AutoLog(value = "分组管理与科室关联表-批量删除")
//	@ApiOperation(value="分组管理与科室关联表-批量删除", notes="分组管理与科室关联表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbDeptGroupRelation> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbDeptGroupRelation> result = new Result<TbDeptGroupRelation>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbDeptGroupRelationService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "分组管理与科室关联表-通过id查询")
//	@ApiOperation(value="分组管理与科室关联表-通过id查询", notes="分组管理与科室关联表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbDeptGroupRelation> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbDeptGroupRelation> result = new Result<TbDeptGroupRelation>();
//		TbDeptGroupRelation tbDeptGroupRelation = tbDeptGroupRelationService.getById(id);
//		if(tbDeptGroupRelation==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbDeptGroupRelation);
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
//      QueryWrapper<TbDeptGroupRelation> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbDeptGroupRelation tbDeptGroupRelation = JSON.parseObject(deString, TbDeptGroupRelation.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbDeptGroupRelation, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbDeptGroupRelation> pageList = tbDeptGroupRelationService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "分组管理与科室关联表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbDeptGroupRelation.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("分组管理与科室关联表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbDeptGroupRelation> listTbDeptGroupRelations = ExcelImportUtil.importExcel(file.getInputStream(), TbDeptGroupRelation.class, params);
//              for (TbDeptGroupRelation tbDeptGroupRelationExcel : listTbDeptGroupRelations) {
//                  tbDeptGroupRelationService.save(tbDeptGroupRelationExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbDeptGroupRelations.size());
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
