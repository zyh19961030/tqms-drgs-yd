package com.qu.modules.web.controller;

import com.qu.modules.web.service.ITbDepService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 科室表
 * @Author: jeecg-boot
 * @Date:   2022-04-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="科室表")
@RestController
@RequestMapping("/web/tbDep")
public class TbDepController {
	@Autowired
	private ITbDepService tbDepService;
	
//	/**
//	  * 分页列表查询
//	 * @param tbDep
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "科室表-分页列表查询")
//	@ApiOperation(value="科室表-分页列表查询", notes="科室表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbDep>> queryPageList(TbDep tbDep,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbDep>> result = new Result<IPage<TbDep>>();
//		QueryWrapper<TbDep> queryWrapper = QueryGenerator.initQueryWrapper(tbDep, req.getParameterMap());
//		Page<TbDep> page = new Page<TbDep>(pageNo, pageSize);
//		IPage<TbDep> pageList = tbDepService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbDep
//	 * @return
//	 */
//	@AutoLog(value = "科室表-添加")
//	@ApiOperation(value="科室表-添加", notes="科室表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbDep> add(@RequestBody TbDep tbDep) {
//		Result<TbDep> result = new Result<TbDep>();
//		try {
//			tbDepService.save(tbDep);
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
//	 * @param tbDep
//	 * @return
//	 */
//	@AutoLog(value = "科室表-编辑")
//	@ApiOperation(value="科室表-编辑", notes="科室表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbDep> edit(@RequestBody TbDep tbDep) {
//		Result<TbDep> result = new Result<TbDep>();
//		TbDep tbDepEntity = tbDepService.getById(tbDep.getId());
//		if(tbDepEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbDepService.updateById(tbDep);
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
//	@AutoLog(value = "科室表-通过id删除")
//	@ApiOperation(value="科室表-通过id删除", notes="科室表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbDep> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbDep> result = new Result<TbDep>();
//		TbDep tbDep = tbDepService.getById(id);
//		if(tbDep==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbDepService.removeById(id);
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
//	@AutoLog(value = "科室表-批量删除")
//	@ApiOperation(value="科室表-批量删除", notes="科室表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbDep> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbDep> result = new Result<TbDep>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbDepService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "科室表-通过id查询")
//	@ApiOperation(value="科室表-通过id查询", notes="科室表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbDep> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbDep> result = new Result<TbDep>();
//		TbDep tbDep = tbDepService.getById(id);
//		if(tbDep==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbDep);
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
//      QueryWrapper<TbDep> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbDep tbDep = JSON.parseObject(deString, TbDep.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbDep, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbDep> pageList = tbDepService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "科室表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbDep.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("科室表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbDep> listTbDeps = ExcelImportUtil.importExcel(file.getInputStream(), TbDep.class, params);
//              for (TbDep tbDepExcel : listTbDeps) {
//                  tbDepService.save(tbDepExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbDeps.size());
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
