package com.qu.modules.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jeecg.common.api.vo.ResultBetter;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qu.modules.web.param.TbDeptGroupAddParam;
import com.qu.modules.web.service.ITbDeptGroupService;
import com.qu.modules.web.vo.TbDeptGroupAddVo;
import com.qu.modules.web.vo.TbDeptGroupListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 分组管理表
 * @Author: jeecg-boot
 * @Date:   2022-09-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags="分组管理表")
@RestController
@RequestMapping("/business/tbDeptGroup")
public class TbDeptGroupController {
	@Autowired
	private ITbDeptGroupService tbDeptGroupService;

	 /**
	  *   添加
	  * @param addParam
	  * @return
	  */
	 @AutoLog(value = "分组管理表-添加")
	 @ApiOperation(value = "分组管理表-添加", notes = "分组管理表-添加")
	 @PostMapping(value = "/addOrUpdate")
	 public ResultBetter<Object> addOrUpdate(@RequestBody @Valid TbDeptGroupAddParam addParam) {
		 tbDeptGroupService.addOrUpdate(addParam);
		 return ResultBetter.ok();
	 }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "分组管理表-通过id删除")
	@ApiOperation(value="分组管理表-通过id删除", notes="分组管理表-通过id删除")
	@DeleteMapping(value = "/delete")
	public ResultBetter<Object> delete(@RequestParam(name="id",required=true) String id) {
		tbDeptGroupService.delete(id);
		return ResultBetter.ok();
	}


	/**
	  * 列表查询
	 * @param req
	 * @return
	 */
	@AutoLog(value = "分组管理表-列表查询")
	@ApiOperation(value="分组管理表-列表查询", notes="分组管理表-列表查询")
	@GetMapping(value = "/list")
	public ResultBetter<List<TbDeptGroupListVo>> queryPageList(HttpServletRequest req) {
		ResultBetter<List<TbDeptGroupListVo>> result = new ResultBetter<List<TbDeptGroupListVo>>();
		List<TbDeptGroupListVo> pageList = tbDeptGroupService.queryPageList();
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * 快速添加
	 * @param req
	 * @return
	 */
	@AutoLog(value = "分组管理表-快速添加")
	@ApiOperation(value="分组管理表-快速添加", notes="分组管理表-快速添加")
	@GetMapping(value = "/fastAddList")
	public ResultBetter<List<TbDeptGroupAddVo>> fastAddList(HttpServletRequest req) {
		ResultBetter<List<TbDeptGroupAddVo>> result = new ResultBetter<List<TbDeptGroupAddVo>>();
		List<TbDeptGroupAddVo> listVos = tbDeptGroupService.fastAddList();
		result.setSuccess(true);
		result.setResult(listVos);
		return result;
	}


//	/**
//	  * 分页列表查询
//	 * @param tbDeptGroup
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "分组管理表-分页列表查询")
//	@ApiOperation(value="分组管理表-分页列表查询", notes="分组管理表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<TbDeptGroup>> queryPageList(TbDeptGroup tbDeptGroup,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<TbDeptGroup>> result = new Result<IPage<TbDeptGroup>>();
//		QueryWrapper<TbDeptGroup> queryWrapper = QueryGenerator.initQueryWrapper(tbDeptGroup, req.getParameterMap());
//		Page<TbDeptGroup> page = new Page<TbDeptGroup>(pageNo, pageSize);
//		IPage<TbDeptGroup> pageList = tbDeptGroupService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param tbDeptGroup
//	 * @return
//	 */
//	@AutoLog(value = "分组管理表-添加")
//	@ApiOperation(value="分组管理表-添加", notes="分组管理表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbDeptGroup> add(@RequestBody TbDeptGroup tbDeptGroup) {
//		Result<TbDeptGroup> result = new Result<TbDeptGroup>();
//		try {
//			tbDeptGroupService.save(tbDeptGroup);
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
//	 * @param tbDeptGroup
//	 * @return
//	 */
//	@AutoLog(value = "分组管理表-编辑")
//	@ApiOperation(value="分组管理表-编辑", notes="分组管理表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbDeptGroup> edit(@RequestBody TbDeptGroup tbDeptGroup) {
//		Result<TbDeptGroup> result = new Result<TbDeptGroup>();
//		TbDeptGroup tbDeptGroupEntity = tbDeptGroupService.getById(tbDeptGroup.getId());
//		if(tbDeptGroupEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbDeptGroupService.updateById(tbDeptGroup);
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
//	@AutoLog(value = "分组管理表-通过id删除")
//	@ApiOperation(value="分组管理表-通过id删除", notes="分组管理表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbDeptGroup> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbDeptGroup> result = new Result<TbDeptGroup>();
//		TbDeptGroup tbDeptGroup = tbDeptGroupService.getById(id);
//		if(tbDeptGroup==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbDeptGroupService.removeById(id);
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
//	@AutoLog(value = "分组管理表-批量删除")
//	@ApiOperation(value="分组管理表-批量删除", notes="分组管理表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbDeptGroup> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbDeptGroup> result = new Result<TbDeptGroup>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbDeptGroupService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "分组管理表-通过id查询")
//	@ApiOperation(value="分组管理表-通过id查询", notes="分组管理表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbDeptGroup> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbDeptGroup> result = new Result<TbDeptGroup>();
//		TbDeptGroup tbDeptGroup = tbDeptGroupService.getById(id);
//		if(tbDeptGroup==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbDeptGroup);
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
//      QueryWrapper<TbDeptGroup> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbDeptGroup tbDeptGroup = JSON.parseObject(deString, TbDeptGroup.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbDeptGroup, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbDeptGroup> pageList = tbDeptGroupService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "分组管理表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbDeptGroup.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("分组管理表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbDeptGroup> listTbDeptGroups = ExcelImportUtil.importExcel(file.getInputStream(), TbDeptGroup.class, params);
//              for (TbDeptGroup tbDeptGroupExcel : listTbDeptGroups) {
//                  tbDeptGroupService.save(tbDeptGroupExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbDeptGroups.size());
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
