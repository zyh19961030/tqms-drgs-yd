package com.qu.modules.web.controller;

import com.qu.modules.web.service.IQsubjectVersionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 题目版本表
 * @Author: jeecg-boot
 * @Date:   2022-09-25
 * @Version: V1.0
 */
@Slf4j
@Api(tags="题目版本表")
@RestController
@RequestMapping("/business/qsubjectVersion")
public class QsubjectVersionController {
	@Autowired
	private IQsubjectVersionService qsubjectVersionService;
	
//	/**
//	  * 分页列表查询
//	 * @param qsubjectVersion
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "题目版本表-分页列表查询")
//	@ApiOperation(value="题目版本表-分页列表查询", notes="题目版本表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QsubjectVersion>> queryPageList(QsubjectVersion qsubjectVersion,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QsubjectVersion>> result = new Result<IPage<QsubjectVersion>>();
//		QueryWrapper<QsubjectVersion> queryWrapper = QueryGenerator.initQueryWrapper(qsubjectVersion, req.getParameterMap());
//		Page<QsubjectVersion> page = new Page<QsubjectVersion>(pageNo, pageSize);
//		IPage<QsubjectVersion> pageList = qsubjectVersionService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param qsubjectVersion
//	 * @return
//	 */
//	@AutoLog(value = "题目版本表-添加")
//	@ApiOperation(value="题目版本表-添加", notes="题目版本表-添加")
//	@PostMapping(value = "/add")
//	public Result<QsubjectVersion> add(@RequestBody QsubjectVersion qsubjectVersion) {
//		Result<QsubjectVersion> result = new Result<QsubjectVersion>();
//		try {
//			qsubjectVersionService.save(qsubjectVersion);
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
//	 * @param qsubjectVersion
//	 * @return
//	 */
//	@AutoLog(value = "题目版本表-编辑")
//	@ApiOperation(value="题目版本表-编辑", notes="题目版本表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QsubjectVersion> edit(@RequestBody QsubjectVersion qsubjectVersion) {
//		Result<QsubjectVersion> result = new Result<QsubjectVersion>();
//		QsubjectVersion qsubjectVersionEntity = qsubjectVersionService.getById(qsubjectVersion.getId());
//		if(qsubjectVersionEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = qsubjectVersionService.updateById(qsubjectVersion);
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
//	@AutoLog(value = "题目版本表-通过id删除")
//	@ApiOperation(value="题目版本表-通过id删除", notes="题目版本表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QsubjectVersion> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QsubjectVersion> result = new Result<QsubjectVersion>();
//		QsubjectVersion qsubjectVersion = qsubjectVersionService.getById(id);
//		if(qsubjectVersion==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = qsubjectVersionService.removeById(id);
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
//	@AutoLog(value = "题目版本表-批量删除")
//	@ApiOperation(value="题目版本表-批量删除", notes="题目版本表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QsubjectVersion> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QsubjectVersion> result = new Result<QsubjectVersion>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.qsubjectVersionService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "题目版本表-通过id查询")
//	@ApiOperation(value="题目版本表-通过id查询", notes="题目版本表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QsubjectVersion> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QsubjectVersion> result = new Result<QsubjectVersion>();
//		QsubjectVersion qsubjectVersion = qsubjectVersionService.getById(id);
//		if(qsubjectVersion==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(qsubjectVersion);
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
//      QueryWrapper<QsubjectVersion> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QsubjectVersion qsubjectVersion = JSON.parseObject(deString, QsubjectVersion.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(qsubjectVersion, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QsubjectVersion> pageList = qsubjectVersionService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "题目版本表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QsubjectVersion.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("题目版本表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QsubjectVersion> listQsubjectVersions = ExcelImportUtil.importExcel(file.getInputStream(), QsubjectVersion.class, params);
//              for (QsubjectVersion qsubjectVersionExcel : listQsubjectVersions) {
//                  qsubjectVersionService.save(qsubjectVersionExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQsubjectVersions.size());
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
