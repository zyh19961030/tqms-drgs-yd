package com.qu.modules.web.controller;

import com.qu.modules.web.service.IQSingleDiseaseStatisticDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 单病种统计表
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
@Slf4j
@Api(tags="单病种统计表")
@RestController
@RequestMapping("/web/qSingleDiseaseStatistic")
public class QSingleDiseaseStatisticDeptController {
	@Autowired
	private IQSingleDiseaseStatisticDeptService qSingleDiseaseStatisticService;

	/**
	 * 整理数据
	 * @param code
	 * @return
	 */
	@AutoLog(value = "单病种统计表-处理数据")
	@ApiOperation(value="单病种统计表-处理数据", notes="单病种统计表-处理数据")
	@GetMapping(value = "/processData")
	public Result<?> processData(@RequestParam(name="code") String code,
								 @RequestParam(name="startDate",required=false) String startDate,
								 @RequestParam(name="endDate") String endDate) {
		if(!"a11".equals(code)){
			return ResultFactory.fail();
		}
		qSingleDiseaseStatisticService.processData(startDate,endDate);
		return ResultFactory.success();
	}

//	/**
//	  * 分页列表查询
//	 * @param qSingleDiseaseStatistic
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "单病种统计表-分页列表查询")
//	@ApiOperation(value="单病种统计表-分页列表查询", notes="单病种统计表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<QSingleDiseaseStatistic>> queryPageList(QSingleDiseaseStatistic qSingleDiseaseStatistic,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<QSingleDiseaseStatistic>> result = new Result<IPage<QSingleDiseaseStatistic>>();
//		QueryWrapper<QSingleDiseaseStatistic> queryWrapper = QueryGenerator.initQueryWrapper(qSingleDiseaseStatistic, req.getParameterMap());
//		Page<QSingleDiseaseStatistic> page = new Page<QSingleDiseaseStatistic>(pageNo, pageSize);
//		IPage<QSingleDiseaseStatistic> pageList = qSingleDiseaseStatisticService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param qSingleDiseaseStatistic
//	 * @return
//	 */
//	@AutoLog(value = "单病种统计表-添加")
//	@ApiOperation(value="单病种统计表-添加", notes="单病种统计表-添加")
//	@PostMapping(value = "/add")
//	public Result<QSingleDiseaseStatistic> add(@RequestBody QSingleDiseaseStatistic qSingleDiseaseStatistic) {
//		Result<QSingleDiseaseStatistic> result = new Result<QSingleDiseaseStatistic>();
//		try {
//			qSingleDiseaseStatisticService.save(qSingleDiseaseStatistic);
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
//	 * @param qSingleDiseaseStatistic
//	 * @return
//	 */
//	@AutoLog(value = "单病种统计表-编辑")
//	@ApiOperation(value="单病种统计表-编辑", notes="单病种统计表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<QSingleDiseaseStatistic> edit(@RequestBody QSingleDiseaseStatistic qSingleDiseaseStatistic) {
//		Result<QSingleDiseaseStatistic> result = new Result<QSingleDiseaseStatistic>();
//		QSingleDiseaseStatistic qSingleDiseaseStatisticEntity = qSingleDiseaseStatisticService.getById(qSingleDiseaseStatistic.getId());
//		if(qSingleDiseaseStatisticEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = qSingleDiseaseStatisticService.updateById(qSingleDiseaseStatistic);
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
//	@AutoLog(value = "单病种统计表-通过id删除")
//	@ApiOperation(value="单病种统计表-通过id删除", notes="单病种统计表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<QSingleDiseaseStatistic> delete(@RequestParam(name="id",required=true) String id) {
//		Result<QSingleDiseaseStatistic> result = new Result<QSingleDiseaseStatistic>();
//		QSingleDiseaseStatistic qSingleDiseaseStatistic = qSingleDiseaseStatisticService.getById(id);
//		if(qSingleDiseaseStatistic==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = qSingleDiseaseStatisticService.removeById(id);
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
//	@AutoLog(value = "单病种统计表-批量删除")
//	@ApiOperation(value="单病种统计表-批量删除", notes="单病种统计表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<QSingleDiseaseStatistic> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<QSingleDiseaseStatistic> result = new Result<QSingleDiseaseStatistic>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.qSingleDiseaseStatisticService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "单病种统计表-通过id查询")
//	@ApiOperation(value="单病种统计表-通过id查询", notes="单病种统计表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<QSingleDiseaseStatistic> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<QSingleDiseaseStatistic> result = new Result<QSingleDiseaseStatistic>();
//		QSingleDiseaseStatistic qSingleDiseaseStatistic = qSingleDiseaseStatisticService.getById(id);
//		if(qSingleDiseaseStatistic==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(qSingleDiseaseStatistic);
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
//      QueryWrapper<QSingleDiseaseStatistic> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              QSingleDiseaseStatistic qSingleDiseaseStatistic = JSON.parseObject(deString, QSingleDiseaseStatistic.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(qSingleDiseaseStatistic, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<QSingleDiseaseStatistic> pageList = qSingleDiseaseStatisticService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "单病种统计表列表");
//      mv.addObject(NormalExcelConstants.CLASS, QSingleDiseaseStatistic.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("单病种统计表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<QSingleDiseaseStatistic> listQSingleDiseaseStatistics = ExcelImportUtil.importExcel(file.getInputStream(), QSingleDiseaseStatistic.class, params);
//              for (QSingleDiseaseStatistic qSingleDiseaseStatisticExcel : listQSingleDiseaseStatistics) {
//                  qSingleDiseaseStatisticService.save(qSingleDiseaseStatisticExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listQSingleDiseaseStatistics.size());
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