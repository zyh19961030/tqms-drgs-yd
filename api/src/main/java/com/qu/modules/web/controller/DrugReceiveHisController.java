package com.qu.modules.web.controller;

import java.util.*;

import com.qu.modules.web.param.NameAndTypeParam;
import org.jeecg.common.aspect.annotation.AutoLog;
import com.qu.modules.web.entity.DrugReceiveHis;
import com.qu.modules.web.service.IDrugReceiveHisService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 药品规则接收his数据表
 * @Author: jeecg-boot
 * @Date:   2021-09-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="药品规则接收his数据表")
@RestController
@RequestMapping("/business/drugReceiveHis")
public class DrugReceiveHisController {
	@Autowired
	private IDrugReceiveHisService drugReceiveHisService;

	 /**
	  * 通过name搜索
	  * @param nameAndTypeVo
	  * @return
	  */
	 @AutoLog(value = "药品规则接收his数据表-通过name搜索")
	 @ApiOperation(value="药品规则接收his数据表-通过name搜索", notes="药品规则接收his数据表-通过name搜索")
	 @GetMapping(value = "/queryHis")
	 public List<DrugReceiveHis> queryHis(NameAndTypeParam nameAndTypeVo) {
	 	List<DrugReceiveHis> list = new ArrayList<>();
		 if (nameAndTypeVo.getType() == 1){
			 list = drugReceiveHisService.queryPurposeByInput(nameAndTypeVo.getName());
		 } else {
			 list = drugReceiveHisService.queryActionByInput(nameAndTypeVo.getName());
		 }
		 return list;
	 }

	 /**
	  //	  * 通过pid查询
	  //	  * @param pid
	  //	  * @return
	  //	  */
//	 @AutoLog(value = "药品规则接收his数据表-通过pid查询")
//	 @ApiOperation(value="药品规则接收his数据表-通过pid查询", notes="药品规则接收his数据表-通过pid查询")
//	 @GetMapping(value = "/queryByPid")
//	 public List<DrugReceiveHis> queryByPid(@RequestParam(name="pid",required=true) Integer pid) {
//		 List<DrugReceiveHis> list = drugReceiveHisService.queryById(pid);
//		 return list;
//	 }

	/**
	  * 分页列表查询
	 * @param drugReceiveHis
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "药品规则接收his数据表-分页列表查询")
//	@ApiOperation(value="药品规则接收his数据表-分页列表查询", notes="药品规则接收his数据表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<DrugReceiveHis>> queryPageList(DrugReceiveHis drugReceiveHis,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<DrugReceiveHis>> result = new Result<IPage<DrugReceiveHis>>();
//		QueryWrapper<DrugReceiveHis> queryWrapper = QueryGenerator.initQueryWrapper(drugReceiveHis, req.getParameterMap());
//		Page<DrugReceiveHis> page = new Page<DrugReceiveHis>(pageNo, pageSize);
//		IPage<DrugReceiveHis> pageList = drugReceiveHisService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
	
	/**
	 * 添加
	 * @param drugReceiveHis
	 * @return
	 */
//	@AutoLog(value = "药品规则接收his数据表-添加")
//	@ApiOperation(value="药品规则接收his数据表-添加", notes="药品规则接收his数据表-添加")
//	@PostMapping(value = "/add")
//	public Result<DrugReceiveHis> add(@RequestBody DrugReceiveHis drugReceiveHis) {
//		Result<DrugReceiveHis> result = new Result<DrugReceiveHis>();
//		try {
//			drugReceiveHisService.save(drugReceiveHis);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.error500("操作失败");
//		}
//		return result;
//	}
	
	/**
	  *  编辑
	 * @param drugReceiveHis
	 * @return
	 */
//	@AutoLog(value = "药品规则接收his数据表-编辑")
//	@ApiOperation(value="药品规则接收his数据表-编辑", notes="药品规则接收his数据表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<DrugReceiveHis> edit(@RequestBody DrugReceiveHis drugReceiveHis) {
//		Result<DrugReceiveHis> result = new Result<DrugReceiveHis>();
//		DrugReceiveHis drugReceiveHisEntity = drugReceiveHisService.getById(drugReceiveHis.getId());
//		if(drugReceiveHisEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = drugReceiveHisService.updateById(drugReceiveHis);
//			//TODO 返回false说明什么？
//			if(ok) {
//				result.success("修改成功!");
//			}
//		}
//
//		return result;
//	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
//	@AutoLog(value = "药品规则接收his数据表-通过id删除")
//	@ApiOperation(value="药品规则接收his数据表-通过id删除", notes="药品规则接收his数据表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<DrugReceiveHis> delete(@RequestParam(name="id",required=true) String id) {
//		Result<DrugReceiveHis> result = new Result<DrugReceiveHis>();
//		DrugReceiveHis drugReceiveHis = drugReceiveHisService.getById(id);
//		if(drugReceiveHis==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = drugReceiveHisService.removeById(id);
//			if(ok) {
//				result.success("删除成功!");
//			}
//		}
//
//		return result;
//	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
//	@AutoLog(value = "药品规则接收his数据表-批量删除")
//	@ApiOperation(value="药品规则接收his数据表-批量删除", notes="药品规则接收his数据表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<DrugReceiveHis> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<DrugReceiveHis> result = new Result<DrugReceiveHis>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.drugReceiveHisService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
//  @RequestMapping(value = "/exportXls")
//  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
//      // Step.1 组装查询条件
//      QueryWrapper<DrugReceiveHis> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              DrugReceiveHis drugReceiveHis = JSON.parseObject(deString, DrugReceiveHis.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(drugReceiveHis, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<DrugReceiveHis> pageList = drugReceiveHisService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "药品规则接收his数据表列表");
//      mv.addObject(NormalExcelConstants.CLASS, DrugReceiveHis.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("药品规则接收his数据表列表数据", "导出人:Jeecg", "导出信息"));
//      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
//      return mv;
//  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
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
//              List<DrugReceiveHis> listDrugReceiveHiss = ExcelImportUtil.importExcel(file.getInputStream(), DrugReceiveHis.class, params);
//              for (DrugReceiveHis drugReceiveHisExcel : listDrugReceiveHiss) {
//                  drugReceiveHisService.save(drugReceiveHisExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listDrugReceiveHiss.size());
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
