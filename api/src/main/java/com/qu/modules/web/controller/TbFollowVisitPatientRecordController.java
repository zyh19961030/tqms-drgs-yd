package com.qu.modules.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qu.modules.web.entity.TbFollowVisitPatientRecord;
import com.qu.modules.web.param.TbFollowVisitPatientRecordListParam;
import com.qu.modules.web.service.ITbFollowVisitPatientRecordService;
import com.qu.modules.web.vo.TbFollowVisitPatientRecordListVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 随访患者记录表
 * @Author: jeecg-boot
 * @Date:   2023-02-23
 * @Version: V1.0
 */
@Slf4j
@Api(tags="随访患者记录表")
@RestController
@RequestMapping("/business/tbFollowVisitPatientRecord")
public class TbFollowVisitPatientRecordController {

	@Autowired
	private ITbFollowVisitPatientRecordService tbFollowVisitPatientRecordService;
	
	/**
	  * 分页列表查询
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "随访患者记录表-分页列表查询")
	@ApiOperation(value="随访患者记录表-分页列表查询", notes="随访患者记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TbFollowVisitPatientRecordListVo>> queryPageList(TbFollowVisitPatientRecordListParam param,
                                                                         @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                                         @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                                         HttpServletRequest req) {
		Result<IPage<TbFollowVisitPatientRecordListVo>> result = new Result<IPage<TbFollowVisitPatientRecordListVo>>();
		Page<TbFollowVisitPatientRecord> page = new Page<TbFollowVisitPatientRecord>(pageNo, pageSize);
		IPage<TbFollowVisitPatientRecordListVo> pageList = tbFollowVisitPatientRecordService.queryPageList(param,page);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

     @AutoLog(value = "随访患者记录表-终止随访")
     @ApiOperation(value = "随访患者记录表-终止随访", notes = "随访患者记录表-终止随访")
     @GetMapping(value = "/stopFollowVisit")
     public Result stopFollowVisit(@RequestParam(name="id",required=true)@ApiParam("从列表中获取的主键id") Integer id) {
         boolean flag = tbFollowVisitPatientRecordService.stopFollowVisit(id);
         if(flag){
             return ResultFactory.success();
         }else{
             return ResultFactory.fail("终止随访失败");
         }
     }
	
//	/**
//	  *   添加
//	 * @param tbFollowVisitPatientRecord
//	 * @return
//	 */
//	@AutoLog(value = "随访患者记录表-添加")
//	@ApiOperation(value="随访患者记录表-添加", notes="随访患者记录表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbFollowVisitPatientRecord> add(@RequestBody TbFollowVisitPatientRecord tbFollowVisitPatientRecord) {
//		Result<TbFollowVisitPatientRecord> result = new Result<TbFollowVisitPatientRecord>();
//		try {
//			tbFollowVisitPatientRecordService.save(tbFollowVisitPatientRecord);
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
//	 * @param tbFollowVisitPatientRecord
//	 * @return
//	 */
//	@AutoLog(value = "随访患者记录表-编辑")
//	@ApiOperation(value="随访患者记录表-编辑", notes="随访患者记录表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbFollowVisitPatientRecord> edit(@RequestBody TbFollowVisitPatientRecord tbFollowVisitPatientRecord) {
//		Result<TbFollowVisitPatientRecord> result = new Result<TbFollowVisitPatientRecord>();
//		TbFollowVisitPatientRecord tbFollowVisitPatientRecordEntity = tbFollowVisitPatientRecordService.getById(tbFollowVisitPatientRecord.getId());
//		if(tbFollowVisitPatientRecordEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitPatientRecordService.updateById(tbFollowVisitPatientRecord);
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
//	@AutoLog(value = "随访患者记录表-通过id删除")
//	@ApiOperation(value="随访患者记录表-通过id删除", notes="随访患者记录表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbFollowVisitPatientRecord> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitPatientRecord> result = new Result<TbFollowVisitPatientRecord>();
//		TbFollowVisitPatientRecord tbFollowVisitPatientRecord = tbFollowVisitPatientRecordService.getById(id);
//		if(tbFollowVisitPatientRecord==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitPatientRecordService.removeById(id);
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
//	@AutoLog(value = "随访患者记录表-批量删除")
//	@ApiOperation(value="随访患者记录表-批量删除", notes="随访患者记录表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbFollowVisitPatientRecord> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbFollowVisitPatientRecord> result = new Result<TbFollowVisitPatientRecord>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbFollowVisitPatientRecordService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "随访患者记录表-通过id查询")
//	@ApiOperation(value="随访患者记录表-通过id查询", notes="随访患者记录表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbFollowVisitPatientRecord> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitPatientRecord> result = new Result<TbFollowVisitPatientRecord>();
//		TbFollowVisitPatientRecord tbFollowVisitPatientRecord = tbFollowVisitPatientRecordService.getById(id);
//		if(tbFollowVisitPatientRecord==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbFollowVisitPatientRecord);
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
//      QueryWrapper<TbFollowVisitPatientRecord> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbFollowVisitPatientRecord tbFollowVisitPatientRecord = JSON.parseObject(deString, TbFollowVisitPatientRecord.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbFollowVisitPatientRecord, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbFollowVisitPatientRecord> pageList = tbFollowVisitPatientRecordService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "随访患者记录表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbFollowVisitPatientRecord.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("随访患者记录表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbFollowVisitPatientRecord> listTbFollowVisitPatientRecords = ExcelImportUtil.importExcel(file.getInputStream(), TbFollowVisitPatientRecord.class, params);
//              for (TbFollowVisitPatientRecord tbFollowVisitPatientRecordExcel : listTbFollowVisitPatientRecords) {
//                  tbFollowVisitPatientRecordService.save(tbFollowVisitPatientRecordExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbFollowVisitPatientRecords.size());
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
