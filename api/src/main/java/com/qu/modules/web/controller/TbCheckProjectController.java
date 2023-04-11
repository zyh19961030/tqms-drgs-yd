package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 检查项目表
 * @Author: jeecg-boot
 * @Date:   2023-04-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="检查项目表")
@RestController
@RequestMapping("/web/tbCheckProject")
public class TbCheckProjectController {
//	@Autowired
//	private ITbCheckProjectService tbCheckProjectService;
//
//	/**
//	  * 分页列表查询
//	 * @param tbCheckProject
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "检查项目表-分页列表查询")
//	@ApiOperation(value="检查项目表-分页列表查询", notes="检查项目表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbCheckProject>> queryPageList(TbCheckProject tbCheckProject,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbCheckProject>> result = new Result<IPage<TbCheckProject>>();
//		QueryWrapper<TbCheckProject> queryWrapper = QueryGenerator.initQueryWrapper(tbCheckProject, req.getParameterMap());
//		Page<TbCheckProject> page = new Page<TbCheckProject>(pageNo, pageSize);
//		IPage<TbCheckProject> pageList = tbCheckProjectService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbCheckProject
//	 * @return
//	 */
//	@AutoLog(value = "检查项目表-添加")
//	@ApiOperation(value="检查项目表-添加", notes="检查项目表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbCheckProject> add(@RequestBody TbCheckProject tbCheckProject) {
//		Result<TbCheckProject> result = new Result<TbCheckProject>();
//		try {
//			tbCheckProjectService.save(tbCheckProject);
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
//	 * @param tbCheckProject
//	 * @return
//	 */
//	@AutoLog(value = "检查项目表-编辑")
//	@ApiOperation(value="检查项目表-编辑", notes="检查项目表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbCheckProject> edit(@RequestBody TbCheckProject tbCheckProject) {
//		Result<TbCheckProject> result = new Result<TbCheckProject>();
//		TbCheckProject tbCheckProjectEntity = tbCheckProjectService.getById(tbCheckProject.getId());
//		if(tbCheckProjectEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbCheckProjectService.updateById(tbCheckProject);
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
//	@AutoLog(value = "检查项目表-通过id删除")
//	@ApiOperation(value="检查项目表-通过id删除", notes="检查项目表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbCheckProject> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbCheckProject> result = new Result<TbCheckProject>();
//		TbCheckProject tbCheckProject = tbCheckProjectService.getById(id);
//		if(tbCheckProject==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbCheckProjectService.removeById(id);
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
//	@AutoLog(value = "检查项目表-批量删除")
//	@ApiOperation(value="检查项目表-批量删除", notes="检查项目表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbCheckProject> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbCheckProject> result = new Result<TbCheckProject>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbCheckProjectService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "检查项目表-通过id查询")
//	@ApiOperation(value="检查项目表-通过id查询", notes="检查项目表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbCheckProject> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbCheckProject> result = new Result<TbCheckProject>();
//		TbCheckProject tbCheckProject = tbCheckProjectService.getById(id);
//		if(tbCheckProject==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbCheckProject);
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
//      QueryWrapper<TbCheckProject> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbCheckProject tbCheckProject = JSON.parseObject(deString, TbCheckProject.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbCheckProject, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbCheckProject> pageList = tbCheckProjectService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "检查项目表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbCheckProject.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("检查项目表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbCheckProject> listTbCheckProjects = ExcelImportUtil.importExcel(file.getInputStream(), TbCheckProject.class, params);
//              for (TbCheckProject tbCheckProjectExcel : listTbCheckProjects) {
//                  tbCheckProjectService.save(tbCheckProjectExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbCheckProjects.size());
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
