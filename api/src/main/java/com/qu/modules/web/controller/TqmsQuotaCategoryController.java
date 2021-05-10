package com.qu.modules.web.controller;

import com.qu.modules.web.service.ITqmsQuotaCategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 分类表
 * @Author: jeecg-boot
 * @Date:   2021-05-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="分类表")
@RestController
@RequestMapping("/web/tqmsQuotaCategory")
public class TqmsQuotaCategoryController {
	@Autowired
	private ITqmsQuotaCategoryService tqmsQuotaCategoryService;
	
	/**
	  * 分页列表查询
	 * @param tqmsQuotaCategory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 *//*
	@AutoLog(value = "分类表-分页列表查询")
	@ApiOperation(value="分类表-分页列表查询", notes="分类表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TqmsQuotaCategory>> queryPageList(TqmsQuotaCategory tqmsQuotaCategory,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<TqmsQuotaCategory>> result = new Result<IPage<TqmsQuotaCategory>>();
		QueryWrapper<TqmsQuotaCategory> queryWrapper = QueryGenerator.initQueryWrapper(tqmsQuotaCategory, req.getParameterMap());
		Page<TqmsQuotaCategory> page = new Page<TqmsQuotaCategory>(pageNo, pageSize);
		IPage<TqmsQuotaCategory> pageList = tqmsQuotaCategoryService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	*//**
	  *   添加
	 * @param tqmsQuotaCategory
	 * @return
	 *//*
	@AutoLog(value = "分类表-添加")
	@ApiOperation(value="分类表-添加", notes="分类表-添加")
	@PostMapping(value = "/add")
	public Result<TqmsQuotaCategory> add(@RequestBody TqmsQuotaCategory tqmsQuotaCategory) {
		Result<TqmsQuotaCategory> result = new Result<TqmsQuotaCategory>();
		try {
			tqmsQuotaCategoryService.save(tqmsQuotaCategory);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	*//**
	  *  编辑
	 * @param tqmsQuotaCategory
	 * @return
	 *//*
	@AutoLog(value = "分类表-编辑")
	@ApiOperation(value="分类表-编辑", notes="分类表-编辑")
	@PutMapping(value = "/edit")
	public Result<TqmsQuotaCategory> edit(@RequestBody TqmsQuotaCategory tqmsQuotaCategory) {
		Result<TqmsQuotaCategory> result = new Result<TqmsQuotaCategory>();
		TqmsQuotaCategory tqmsQuotaCategoryEntity = tqmsQuotaCategoryService.getById(tqmsQuotaCategory.getId());
		if(tqmsQuotaCategoryEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = tqmsQuotaCategoryService.updateById(tqmsQuotaCategory);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	*//**
	  *   通过id删除
	 * @param id
	 * @return
	 *//*
	@AutoLog(value = "分类表-通过id删除")
	@ApiOperation(value="分类表-通过id删除", notes="分类表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<TqmsQuotaCategory> delete(@RequestParam(name="id",required=true) String id) {
		Result<TqmsQuotaCategory> result = new Result<TqmsQuotaCategory>();
		TqmsQuotaCategory tqmsQuotaCategory = tqmsQuotaCategoryService.getById(id);
		if(tqmsQuotaCategory==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = tqmsQuotaCategoryService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}
		
		return result;
	}
	
	*//**
	  *  批量删除
	 * @param ids
	 * @return
	 *//*
	@AutoLog(value = "分类表-批量删除")
	@ApiOperation(value="分类表-批量删除", notes="分类表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<TqmsQuotaCategory> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<TqmsQuotaCategory> result = new Result<TqmsQuotaCategory>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.tqmsQuotaCategoryService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	*//**
	  * 通过id查询
	 * @param id
	 * @return
	 *//*
	@AutoLog(value = "分类表-通过id查询")
	@ApiOperation(value="分类表-通过id查询", notes="分类表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TqmsQuotaCategory> queryById(@RequestParam(name="id",required=true) String id) {
		Result<TqmsQuotaCategory> result = new Result<TqmsQuotaCategory>();
		TqmsQuotaCategory tqmsQuotaCategory = tqmsQuotaCategoryService.getById(id);
		if(tqmsQuotaCategory==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(tqmsQuotaCategory);
			result.setSuccess(true);
		}
		return result;
	}

  *//**
      * 导出excel
   *
   * @param request
   * @param response
   *//*
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<TqmsQuotaCategory> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              TqmsQuotaCategory tqmsQuotaCategory = JSON.parseObject(deString, TqmsQuotaCategory.class);
              queryWrapper = QueryGenerator.initQueryWrapper(tqmsQuotaCategory, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<TqmsQuotaCategory> pageList = tqmsQuotaCategoryService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "分类表列表");
      mv.addObject(NormalExcelConstants.CLASS, TqmsQuotaCategory.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("分类表列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  *//**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   *//*
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<TqmsQuotaCategory> listTqmsQuotaCategorys = ExcelImportUtil.importExcel(file.getInputStream(), TqmsQuotaCategory.class, params);
              for (TqmsQuotaCategory tqmsQuotaCategoryExcel : listTqmsQuotaCategorys) {
                  tqmsQuotaCategoryService.save(tqmsQuotaCategoryExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listTqmsQuotaCategorys.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }*/

}
