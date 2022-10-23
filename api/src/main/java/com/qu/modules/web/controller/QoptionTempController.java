package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 选项临时表
 * @Author: jeecg-boot
 * @Date:   2022-10-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags="选项临时表")
@RestController
@RequestMapping("/business/qoptionTemp")
public class QoptionTempController {
//	@Autowired
//	private IQoptionTempService qoptionTempService;
//
//	/**
//	  * 分页列表查询
//	 * @param qoptionTemp
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "选项临时表-分页列表查询")
//	@ApiOperation(value="选项临时表-分页列表查询", notes="选项临时表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QoptionTemp>> queryPageList(QoptionTemp qoptionTemp,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QoptionTemp>> result = new Result<IPage<QoptionTemp>>();
//		QueryWrapper<QoptionTemp> queryWrapper = QueryGenerator.initQueryWrapper(qoptionTemp, req.getParameterMap());
//		Page<QoptionTemp> page = new Page<QoptionTemp>(pageNo, pageSize);
//		IPage<QoptionTemp> pageList = qoptionTempService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param qoptionTemp
//	 * @return
//	 */
//	@AutoLog(value = "选项临时表-添加")
//	@ApiOperation(value="选项临时表-添加", notes="选项临时表-添加")
//	@PostMapping(value = "/add")
//	public Result<QoptionTemp> add(@RequestBody QoptionTemp qoptionTemp) {
//		Result<QoptionTemp> result = new Result<QoptionTemp>();
//		try {
//			qoptionTempService.save(qoptionTemp);
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
//	 * @param qoptionTemp
//	 * @return
//	 */
//	@AutoLog(value = "选项临时表-编辑")
//	@ApiOperation(value="选项临时表-编辑", notes="选项临时表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QoptionTemp> edit(@RequestBody QoptionTemp qoptionTemp) {
//		Result<QoptionTemp> result = new Result<QoptionTemp>();
//		QoptionTemp qoptionTempEntity = qoptionTempService.getById(qoptionTemp.getId());
//		if(qoptionTempEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = qoptionTempService.updateById(qoptionTemp);
//			//TO DO 返回false说明什么？
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
//	@AutoLog(value = "选项临时表-通过id删除")
//	@ApiOperation(value="选项临时表-通过id删除", notes="选项临时表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QoptionTemp> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QoptionTemp> result = new Result<QoptionTemp>();
//		QoptionTemp qoptionTemp = qoptionTempService.getById(id);
//		if(qoptionTemp==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = qoptionTempService.removeById(id);
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
//	@AutoLog(value = "选项临时表-批量删除")
//	@ApiOperation(value="选项临时表-批量删除", notes="选项临时表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QoptionTemp> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QoptionTemp> result = new Result<QoptionTemp>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.qoptionTempService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "选项临时表-通过id查询")
//	@ApiOperation(value="选项临时表-通过id查询", notes="选项临时表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QoptionTemp> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QoptionTemp> result = new Result<QoptionTemp>();
//		QoptionTemp qoptionTemp = qoptionTempService.getById(id);
//		if(qoptionTemp==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(qoptionTemp);
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
//      QueryWrapper<QoptionTemp> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QoptionTemp qoptionTemp = JSON.parseObject(deString, QoptionTemp.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(qoptionTemp, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QoptionTemp> pageList = qoptionTempService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "选项临时表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QoptionTemp.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("选项临时表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QoptionTemp> listQoptionTemps = ExcelImportUtil.importExcel(file.getInputStream(), QoptionTemp.class, params);
//              for (QoptionTemp qoptionTempExcel : listQoptionTemps) {
//                  qoptionTempService.save(qoptionTempExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQoptionTemps.size());
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
