package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 数据字典表
 * @Author: jeecg-boot
 * @Date:   2022-09-30
 * @Version: V1.0
 */
@Slf4j
@Api(tags="数据字典表")
@RestController
@RequestMapping("/business/tbData")
public class TbDataController {
//	@Autowired
//	private ITbDataService tbDataService;
//
//	/**
//	  * 分页列表查询
//	 * @param tbData
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "数据字典表-分页列表查询")
//	@ApiOperation(value="数据字典表-分页列表查询", notes="数据字典表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbData>> queryPageList(TbData tbData,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbData>> result = new Result<IPage<TbData>>();
//		QueryWrapper<TbData> queryWrapper = QueryGenerator.initQueryWrapper(tbData, req.getParameterMap());
//		Page<TbData> page = new Page<TbData>(pageNo, pageSize);
//		IPage<TbData> pageList = tbDataService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbData
//	 * @return
//	 */
//	@AutoLog(value = "数据字典表-添加")
//	@ApiOperation(value="数据字典表-添加", notes="数据字典表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbData> add(@RequestBody TbData tbData) {
//		Result<TbData> result = new Result<TbData>();
//		try {
//			tbDataService.save(tbData);
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
//	 * @param tbData
//	 * @return
//	 */
//	@AutoLog(value = "数据字典表-编辑")
//	@ApiOperation(value="数据字典表-编辑", notes="数据字典表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbData> edit(@RequestBody TbData tbData) {
//		Result<TbData> result = new Result<TbData>();
//		TbData tbDataEntity = tbDataService.getById(tbData.getId());
//		if(tbDataEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbDataService.updateById(tbData);
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
//	@AutoLog(value = "数据字典表-通过id删除")
//	@ApiOperation(value="数据字典表-通过id删除", notes="数据字典表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbData> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbData> result = new Result<TbData>();
//		TbData tbData = tbDataService.getById(id);
//		if(tbData==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbDataService.removeById(id);
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
//	@AutoLog(value = "数据字典表-批量删除")
//	@ApiOperation(value="数据字典表-批量删除", notes="数据字典表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbData> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbData> result = new Result<TbData>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbDataService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "数据字典表-通过id查询")
//	@ApiOperation(value="数据字典表-通过id查询", notes="数据字典表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbData> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbData> result = new Result<TbData>();
//		TbData tbData = tbDataService.getById(id);
//		if(tbData==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbData);
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
//      QueryWrapper<TbData> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbData tbData = JSON.parseObject(deString, TbData.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbData, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbData> pageList = tbDataService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "数据字典表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbData.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("数据字典表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbData> listTbDatas = ExcelImportUtil.importExcel(file.getInputStream(), TbData.class, params);
//              for (TbData tbDataExcel : listTbDatas) {
//                  tbDataService.save(tbDataExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbDatas.size());
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
