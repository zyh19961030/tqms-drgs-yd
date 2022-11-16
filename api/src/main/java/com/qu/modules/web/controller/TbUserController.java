package com.qu.modules.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2022-11-16
 * @Version: V1.0
 */
@Slf4j
@Api(tags="用户表")
@RestController
@RequestMapping("/business/tbUser")
public class TbUserController {
//	@Autowired
//	private ITbUserService tbUserService;
//
//	/**
//	  * 分页列表查询
//	 * @param tbUser
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "用户表-分页列表查询")
//	@ApiOperation(value="用户表-分页列表查询", notes="用户表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbUser>> queryPageList(TbUser tbUser,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbUser>> result = new Result<IPage<TbUser>>();
//		QueryWrapper<TbUser> queryWrapper = QueryGenerator.initQueryWrapper(tbUser, req.getParameterMap());
//		Page<TbUser> page = new Page<TbUser>(pageNo, pageSize);
//		IPage<TbUser> pageList = tbUserService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbUser
//	 * @return
//	 */
//	@AutoLog(value = "用户表-添加")
//	@ApiOperation(value="用户表-添加", notes="用户表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbUser> add(@RequestBody TbUser tbUser) {
//		Result<TbUser> result = new Result<TbUser>();
//		try {
//			tbUserService.save(tbUser);
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
//	 * @param tbUser
//	 * @return
//	 */
//	@AutoLog(value = "用户表-编辑")
//	@ApiOperation(value="用户表-编辑", notes="用户表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbUser> edit(@RequestBody TbUser tbUser) {
//		Result<TbUser> result = new Result<TbUser>();
//		TbUser tbUserEntity = tbUserService.getById(tbUser.getId());
//		if(tbUserEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbUserService.updateById(tbUser);
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
//	@AutoLog(value = "用户表-通过id删除")
//	@ApiOperation(value="用户表-通过id删除", notes="用户表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbUser> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbUser> result = new Result<TbUser>();
//		TbUser tbUser = tbUserService.getById(id);
//		if(tbUser==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbUserService.removeById(id);
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
//	@AutoLog(value = "用户表-批量删除")
//	@ApiOperation(value="用户表-批量删除", notes="用户表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbUser> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbUser> result = new Result<TbUser>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbUserService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "用户表-通过id查询")
//	@ApiOperation(value="用户表-通过id查询", notes="用户表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbUser> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbUser> result = new Result<TbUser>();
//		TbUser tbUser = tbUserService.getById(id);
//		if(tbUser==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbUser);
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
//      QueryWrapper<TbUser> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbUser tbUser = JSON.parseObject(deString, TbUser.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbUser, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbUser> pageList = tbUserService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "用户表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbUser.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbUser> listTbUsers = ExcelImportUtil.importExcel(file.getInputStream(), TbUser.class, params);
//              for (TbUser tbUserExcel : listTbUsers) {
//                  tbUserService.save(tbUserExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbUsers.size());
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
