package com.qu.modules.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qu.modules.web.entity.TbFollowVisitPatientTemplate;
import com.qu.modules.web.param.TbFollowVisitPatientTemplateListParam;
import com.qu.modules.web.service.ITbFollowVisitPatientTemplateService;
import com.qu.modules.web.vo.TbFollowVisitPatientTemplateListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 随访患者模板总记录表
 * @Author: jeecg-boot
 * @Date:   2023-02-23
 * @Version: V1.0
 */
@Slf4j
@Api(tags="患者随访管理(随访患者模板总记录表)")
@RestController
@RequestMapping("/business/tbFollowVisitPatientTemplate")
public class TbFollowVisitPatientTemplateController {

	@Autowired
	private ITbFollowVisitPatientTemplateService tbFollowVisitPatientTemplateService;

	/**
	  * 分页列表查询
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "患者随访管理-分页列表查询")
	@ApiOperation(value="患者随访管理-分页列表查询", notes="患者随访管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TbFollowVisitPatientTemplateListVo>> queryPageList(TbFollowVisitPatientTemplateListParam param,
                                                                           @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                                           @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                                           HttpServletRequest req) {
		Result<IPage<TbFollowVisitPatientTemplateListVo>> result = new Result<IPage<TbFollowVisitPatientTemplateListVo>>();
		Page<TbFollowVisitPatientTemplate> page = new Page<TbFollowVisitPatientTemplate>(pageNo, pageSize);
		IPage<TbFollowVisitPatientTemplateListVo> pageList = tbFollowVisitPatientTemplateService.queryPageList(page, param);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
//
//	/**
//	  *   添加
//	 * @param tbFollowVisitPatientTemplate
//	 * @return
//	 */
//	@AutoLog(value = "随访患者模板总记录表-添加")
//	@ApiOperation(value="随访患者模板总记录表-添加", notes="随访患者模板总记录表-添加")
//	@PostMapping(value = "/add")
//	public Result<TbFollowVisitPatientTemplate> add(@RequestBody TbFollowVisitPatientTemplate tbFollowVisitPatientTemplate) {
//		Result<TbFollowVisitPatientTemplate> result = new Result<TbFollowVisitPatientTemplate>();
//		try {
//			tbFollowVisitPatientTemplateService.save(tbFollowVisitPatientTemplate);
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
//	 * @param tbFollowVisitPatientTemplate
//	 * @return
//	 */
//	@AutoLog(value = "随访患者模板总记录表-编辑")
//	@ApiOperation(value="随访患者模板总记录表-编辑", notes="随访患者模板总记录表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbFollowVisitPatientTemplate> edit(@RequestBody TbFollowVisitPatientTemplate tbFollowVisitPatientTemplate) {
//		Result<TbFollowVisitPatientTemplate> result = new Result<TbFollowVisitPatientTemplate>();
//		TbFollowVisitPatientTemplate tbFollowVisitPatientTemplateEntity = tbFollowVisitPatientTemplateService.getById(tbFollowVisitPatientTemplate.getId());
//		if(tbFollowVisitPatientTemplateEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitPatientTemplateService.updateById(tbFollowVisitPatientTemplate);
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
//	@AutoLog(value = "随访患者模板总记录表-通过id删除")
//	@ApiOperation(value="随访患者模板总记录表-通过id删除", notes="随访患者模板总记录表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbFollowVisitPatientTemplate> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitPatientTemplate> result = new Result<TbFollowVisitPatientTemplate>();
//		TbFollowVisitPatientTemplate tbFollowVisitPatientTemplate = tbFollowVisitPatientTemplateService.getById(id);
//		if(tbFollowVisitPatientTemplate==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitPatientTemplateService.removeById(id);
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
//	@AutoLog(value = "随访患者模板总记录表-批量删除")
//	@ApiOperation(value="随访患者模板总记录表-批量删除", notes="随访患者模板总记录表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbFollowVisitPatientTemplate> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbFollowVisitPatientTemplate> result = new Result<TbFollowVisitPatientTemplate>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbFollowVisitPatientTemplateService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "随访患者模板总记录表-通过id查询")
//	@ApiOperation(value="随访患者模板总记录表-通过id查询", notes="随访患者模板总记录表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbFollowVisitPatientTemplate> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitPatientTemplate> result = new Result<TbFollowVisitPatientTemplate>();
//		TbFollowVisitPatientTemplate tbFollowVisitPatientTemplate = tbFollowVisitPatientTemplateService.getById(id);
//		if(tbFollowVisitPatientTemplate==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbFollowVisitPatientTemplate);
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
//      QueryWrapper<TbFollowVisitPatientTemplate> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbFollowVisitPatientTemplate tbFollowVisitPatientTemplate = JSON.parseObject(deString, TbFollowVisitPatientTemplate.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbFollowVisitPatientTemplate, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbFollowVisitPatientTemplate> pageList = tbFollowVisitPatientTemplateService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "随访患者模板总记录表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbFollowVisitPatientTemplate.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("随访患者模板总记录表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbFollowVisitPatientTemplate> listTbFollowVisitPatientTemplates = ExcelImportUtil.importExcel(file.getInputStream(), TbFollowVisitPatientTemplate.class, params);
//              for (TbFollowVisitPatientTemplate tbFollowVisitPatientTemplateExcel : listTbFollowVisitPatientTemplates) {
//                  tbFollowVisitPatientTemplateService.save(tbFollowVisitPatientTemplateExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbFollowVisitPatientTemplates.size());
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
