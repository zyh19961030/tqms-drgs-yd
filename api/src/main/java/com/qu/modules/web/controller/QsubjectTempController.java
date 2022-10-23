package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 题目临时表
 * @Author: jeecg-boot
 * @Date:   2022-10-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags="题目临时表")
@RestController
@RequestMapping("/business/qsubjectTemp")
public class QsubjectTempController {
//	@Autowired
//	private IQsubjectTempService qsubjectTempService;
//
//	/**
//	  * 分页列表查询
//	 * @param qsubjectTemp
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "题目临时表-分页列表查询")
//	@ApiOperation(value="题目临时表-分页列表查询", notes="题目临时表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QsubjectTemp>> queryPageList(QsubjectTemp qsubjectTemp,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QsubjectTemp>> result = new Result<IPage<QsubjectTemp>>();
//		QueryWrapper<QsubjectTemp> queryWrapper = QueryGenerator.initQueryWrapper(qsubjectTemp, req.getParameterMap());
//		Page<QsubjectTemp> page = new Page<QsubjectTemp>(pageNo, pageSize);
//		IPage<QsubjectTemp> pageList = qsubjectTempService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param qsubjectTemp
//	 * @return
//	 */
//	@AutoLog(value = "题目临时表-添加")
//	@ApiOperation(value="题目临时表-添加", notes="题目临时表-添加")
//	@PostMapping(value = "/add")
//	public Result<QsubjectTemp> add(@RequestBody QsubjectTemp qsubjectTemp) {
//		Result<QsubjectTemp> result = new Result<QsubjectTemp>();
//		try {
//			qsubjectTempService.save(qsubjectTemp);
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
//	 * @param qsubjectTemp
//	 * @return
//	 */
//	@AutoLog(value = "题目临时表-编辑")
//	@ApiOperation(value="题目临时表-编辑", notes="题目临时表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QsubjectTemp> edit(@RequestBody QsubjectTemp qsubjectTemp) {
//		Result<QsubjectTemp> result = new Result<QsubjectTemp>();
//		QsubjectTemp qsubjectTempEntity = qsubjectTempService.getById(qsubjectTemp.getId());
//		if(qsubjectTempEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = qsubjectTempService.updateById(qsubjectTemp);
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
//	@AutoLog(value = "题目临时表-通过id删除")
//	@ApiOperation(value="题目临时表-通过id删除", notes="题目临时表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QsubjectTemp> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QsubjectTemp> result = new Result<QsubjectTemp>();
//		QsubjectTemp qsubjectTemp = qsubjectTempService.getById(id);
//		if(qsubjectTemp==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = qsubjectTempService.removeById(id);
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
//	@AutoLog(value = "题目临时表-批量删除")
//	@ApiOperation(value="题目临时表-批量删除", notes="题目临时表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QsubjectTemp> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QsubjectTemp> result = new Result<QsubjectTemp>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.qsubjectTempService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "题目临时表-通过id查询")
//	@ApiOperation(value="题目临时表-通过id查询", notes="题目临时表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QsubjectTemp> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QsubjectTemp> result = new Result<QsubjectTemp>();
//		QsubjectTemp qsubjectTemp = qsubjectTempService.getById(id);
//		if(qsubjectTemp==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(qsubjectTemp);
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
//      QueryWrapper<QsubjectTemp> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QsubjectTemp qsubjectTemp = JSON.parseObject(deString, QsubjectTemp.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(qsubjectTemp, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QsubjectTemp> pageList = qsubjectTempService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "题目临时表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QsubjectTemp.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("题目临时表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QsubjectTemp> listQsubjectTemps = ExcelImportUtil.importExcel(file.getInputStream(), QsubjectTemp.class, params);
//              for (QsubjectTemp qsubjectTempExcel : listQsubjectTemps) {
//                  qsubjectTempService.save(qsubjectTempExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQsubjectTemps.size());
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
