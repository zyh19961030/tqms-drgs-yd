package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 用户辅助科室表
 * @Author: jeecg-boot
 * @Date:   2022-11-16
 * @Version: V1.0
 */
@Slf4j
@Api(tags="用户辅助科室表")
@RestController
@RequestMapping("/business/tbUserAuxiliaryDep")
public class TbUserAuxiliaryDepController {
//	@Autowired
//	private ITbUserAuxiliaryDepService tbUserAuxiliaryDepService;
//
//	/**
//	  * 分页列表查询
//	 * @param tbUserAuxiliaryDep
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "用户辅助科室表-分页列表查询")
//	@ApiOperation(value="用户辅助科室表-分页列表查询", notes="用户辅助科室表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbUserAuxiliaryDep>> queryPageList(TbUserAuxiliaryDep tbUserAuxiliaryDep,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbUserAuxiliaryDep>> result = new Result<IPage<TbUserAuxiliaryDep>>();
//		QueryWrapper<TbUserAuxiliaryDep> queryWrapper = QueryGenerator.initQueryWrapper(tbUserAuxiliaryDep, req.getParameterMap());
//		Page<TbUserAuxiliaryDep> page = new Page<TbUserAuxiliaryDep>(pageNo, pageSize);
//		IPage<TbUserAuxiliaryDep> pageList = tbUserAuxiliaryDepService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbUserAuxiliaryDep
//	 * @return
//	 */
//	@AutoLog(value = "用户辅助科室表-添加")
//	@ApiOperation(value="用户辅助科室表-添加", notes="用户辅助科室表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbUserAuxiliaryDep> add(@RequestBody TbUserAuxiliaryDep tbUserAuxiliaryDep) {
//		Result<TbUserAuxiliaryDep> result = new Result<TbUserAuxiliaryDep>();
//		try {
//			tbUserAuxiliaryDepService.save(tbUserAuxiliaryDep);
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
//	 * @param tbUserAuxiliaryDep
//	 * @return
//	 */
//	@AutoLog(value = "用户辅助科室表-编辑")
//	@ApiOperation(value="用户辅助科室表-编辑", notes="用户辅助科室表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbUserAuxiliaryDep> edit(@RequestBody TbUserAuxiliaryDep tbUserAuxiliaryDep) {
//		Result<TbUserAuxiliaryDep> result = new Result<TbUserAuxiliaryDep>();
//		TbUserAuxiliaryDep tbUserAuxiliaryDepEntity = tbUserAuxiliaryDepService.getById(tbUserAuxiliaryDep.getId());
//		if(tbUserAuxiliaryDepEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbUserAuxiliaryDepService.updateById(tbUserAuxiliaryDep);
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
//	@AutoLog(value = "用户辅助科室表-通过id删除")
//	@ApiOperation(value="用户辅助科室表-通过id删除", notes="用户辅助科室表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbUserAuxiliaryDep> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbUserAuxiliaryDep> result = new Result<TbUserAuxiliaryDep>();
//		TbUserAuxiliaryDep tbUserAuxiliaryDep = tbUserAuxiliaryDepService.getById(id);
//		if(tbUserAuxiliaryDep==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbUserAuxiliaryDepService.removeById(id);
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
//	@AutoLog(value = "用户辅助科室表-批量删除")
//	@ApiOperation(value="用户辅助科室表-批量删除", notes="用户辅助科室表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbUserAuxiliaryDep> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbUserAuxiliaryDep> result = new Result<TbUserAuxiliaryDep>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbUserAuxiliaryDepService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "用户辅助科室表-通过id查询")
//	@ApiOperation(value="用户辅助科室表-通过id查询", notes="用户辅助科室表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbUserAuxiliaryDep> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbUserAuxiliaryDep> result = new Result<TbUserAuxiliaryDep>();
//		TbUserAuxiliaryDep tbUserAuxiliaryDep = tbUserAuxiliaryDepService.getById(id);
//		if(tbUserAuxiliaryDep==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbUserAuxiliaryDep);
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
//      QueryWrapper<TbUserAuxiliaryDep> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbUserAuxiliaryDep tbUserAuxiliaryDep = JSON.parseObject(deString, TbUserAuxiliaryDep.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbUserAuxiliaryDep, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbUserAuxiliaryDep> pageList = tbUserAuxiliaryDepService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "用户辅助科室表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbUserAuxiliaryDep.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户辅助科室表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbUserAuxiliaryDep> listTbUserAuxiliaryDeps = ExcelImportUtil.importExcel(file.getInputStream(), TbUserAuxiliaryDep.class, params);
//              for (TbUserAuxiliaryDep tbUserAuxiliaryDepExcel : listTbUserAuxiliaryDeps) {
//                  tbUserAuxiliaryDepService.save(tbUserAuxiliaryDepExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbUserAuxiliaryDeps.size());
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
