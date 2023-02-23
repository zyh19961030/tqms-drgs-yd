package com.qu.modules.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 随访患者信息表
 * @Author: jeecg-boot
 * @Date:   2023-02-23
 * @Version: V1.0
 */
@Slf4j
@Api(tags="随访患者信息表")
@RestController
@RequestMapping("/business/tbFollowVisitPatient")
public class TbFollowVisitPatientController {
//	@Autowired
//	private ITbFollowVisitPatientService tbFollowVisitPatientService;
//
//	/**
//	  * 分页列表查询
//	 * @param tbFollowVisitPatient
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "随访患者信息表-分页列表查询")
//	@ApiOperation(value="随访患者信息表-分页列表查询", notes="随访患者信息表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbFollowVisitPatient>> queryPageList(TbFollowVisitPatient tbFollowVisitPatient,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbFollowVisitPatient>> result = new Result<IPage<TbFollowVisitPatient>>();
//		QueryWrapper<TbFollowVisitPatient> queryWrapper = QueryGenerator.initQueryWrapper(tbFollowVisitPatient, req.getParameterMap());
//		Page<TbFollowVisitPatient> page = new Page<TbFollowVisitPatient>(pageNo, pageSize);
//		IPage<TbFollowVisitPatient> pageList = tbFollowVisitPatientService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbFollowVisitPatient
//	 * @return
//	 */
//	@AutoLog(value = "随访患者信息表-添加")
//	@ApiOperation(value="随访患者信息表-添加", notes="随访患者信息表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbFollowVisitPatient> add(@RequestBody TbFollowVisitPatient tbFollowVisitPatient) {
//		Result<TbFollowVisitPatient> result = new Result<TbFollowVisitPatient>();
//		try {
//			tbFollowVisitPatientService.save(tbFollowVisitPatient);
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
//	 * @param tbFollowVisitPatient
//	 * @return
//	 */
//	@AutoLog(value = "随访患者信息表-编辑")
//	@ApiOperation(value="随访患者信息表-编辑", notes="随访患者信息表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbFollowVisitPatient> edit(@RequestBody TbFollowVisitPatient tbFollowVisitPatient) {
//		Result<TbFollowVisitPatient> result = new Result<TbFollowVisitPatient>();
//		TbFollowVisitPatient tbFollowVisitPatientEntity = tbFollowVisitPatientService.getById(tbFollowVisitPatient.getId());
//		if(tbFollowVisitPatientEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitPatientService.updateById(tbFollowVisitPatient);
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
//	@AutoLog(value = "随访患者信息表-通过id删除")
//	@ApiOperation(value="随访患者信息表-通过id删除", notes="随访患者信息表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbFollowVisitPatient> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitPatient> result = new Result<TbFollowVisitPatient>();
//		TbFollowVisitPatient tbFollowVisitPatient = tbFollowVisitPatientService.getById(id);
//		if(tbFollowVisitPatient==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitPatientService.removeById(id);
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
//	@AutoLog(value = "随访患者信息表-批量删除")
//	@ApiOperation(value="随访患者信息表-批量删除", notes="随访患者信息表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbFollowVisitPatient> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbFollowVisitPatient> result = new Result<TbFollowVisitPatient>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbFollowVisitPatientService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "随访患者信息表-通过id查询")
//	@ApiOperation(value="随访患者信息表-通过id查询", notes="随访患者信息表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbFollowVisitPatient> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitPatient> result = new Result<TbFollowVisitPatient>();
//		TbFollowVisitPatient tbFollowVisitPatient = tbFollowVisitPatientService.getById(id);
//		if(tbFollowVisitPatient==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbFollowVisitPatient);
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
//      QueryWrapper<TbFollowVisitPatient> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbFollowVisitPatient tbFollowVisitPatient = JSON.parseObject(deString, TbFollowVisitPatient.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbFollowVisitPatient, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbFollowVisitPatient> pageList = tbFollowVisitPatientService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "随访患者信息表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbFollowVisitPatient.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("随访患者信息表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbFollowVisitPatient> listTbFollowVisitPatients = ExcelImportUtil.importExcel(file.getInputStream(), TbFollowVisitPatient.class, params);
//              for (TbFollowVisitPatient tbFollowVisitPatientExcel : listTbFollowVisitPatients) {
//                  tbFollowVisitPatientService.save(tbFollowVisitPatientExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbFollowVisitPatients.size());
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
