package com.qu.modules.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultBetter;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qu.constant.Constant;
import com.qu.modules.web.entity.TbFollowVisitTemplate;
import com.qu.modules.web.param.TbFollowVisitTemplateAddOrUpdateParam;
import com.qu.modules.web.param.TbFollowVisitTemplateListParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.ITbFollowVisitTemplateService;
import com.qu.modules.web.vo.TbFollowVisitTemplateInfoVo;
import com.qu.modules.web.vo.TbFollowVisitTemplateListVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 随访模板表
 * @Author: jeecg-boot
 * @Date:   2023-02-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags="随访模板表")
@RestController
@RequestMapping("/business/tbFollowVisitTemplate")
public class TbFollowVisitTemplateController {

	@Autowired
	private ITbFollowVisitTemplateService tbFollowVisitTemplateService;
	
	/**
	  * 分页列表查询
	 * @param listParam
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "随访模板表-分页列表查询")
	@ApiOperation(value="随访模板表-分页列表查询", notes="随访模板表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TbFollowVisitTemplateListVo>> queryPageList(TbFollowVisitTemplateListParam listParam,
																	@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																	@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																	HttpServletRequest req) {
		Result<IPage<TbFollowVisitTemplateListVo>> result = new Result<IPage<TbFollowVisitTemplateListVo>>();
		Page<TbFollowVisitTemplate> page = new Page<TbFollowVisitTemplate>(pageNo, pageSize);
		IPage<TbFollowVisitTemplateListVo> pageList = tbFollowVisitTemplateService.queryPageList(page, listParam);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	@AutoLog(value = "随访模板表-添加或编辑")
	@ApiOperation(value="随访模板表-添加或编辑", notes="随访模板表-添加或编辑")
	@PostMapping(value = "/addOrUpdate")
	public ResultBetter<Boolean> addOrUpdate(@RequestBody TbFollowVisitTemplateAddOrUpdateParam addParam, HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        return tbFollowVisitTemplateService.addOrUpdate(addParam,data);
	}

	/**
  	 * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "随访模板表-通过id查询详情")
	@ApiOperation(value="随访模板表-通过id查询详情", notes="随访模板表-通过id查询详情")
	@GetMapping(value = "/info")
	public Result<TbFollowVisitTemplateInfoVo> info(@RequestParam(name="id",required=true) String id) {
		Result<TbFollowVisitTemplateInfoVo> result = new Result<TbFollowVisitTemplateInfoVo>();
		TbFollowVisitTemplateInfoVo tbFollowVisitTemplate = tbFollowVisitTemplateService.info(id);
		if(tbFollowVisitTemplate==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(tbFollowVisitTemplate);
			result.setSuccess(true);
		}
		return result;
	}


    @AutoLog(value = "随访模板表-停用")
    @ApiOperation(value = "随访模板表-停用", notes = "随访模板表-停用")
    @GetMapping(value = "/deactivate")
    public Result deactivate(@RequestParam(name="id",required=true)@ApiParam("从列表中获取的主键id") Integer id) {
        boolean flag = tbFollowVisitTemplateService.deactivate(id);
        if(flag){
            return ResultFactory.success();
        }else{
            return ResultFactory.fail("停用失败");
        }
    }

//	/**
//	  *  编辑
//	 * @param tbFollowVisitTemplate
//	 * @return
//	 */
//	@AutoLog(value = "随访模板表-编辑")
//	@ApiOperation(value="随访模板表-编辑", notes="随访模板表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<TbFollowVisitTemplate> edit(@RequestBody TbFollowVisitTemplate tbFollowVisitTemplate) {
//		Result<TbFollowVisitTemplate> result = new Result<TbFollowVisitTemplate>();
//		TbFollowVisitTemplate tbFollowVisitTemplateEntity = tbFollowVisitTemplateService.getById(tbFollowVisitTemplate.getId());
//		if(tbFollowVisitTemplateEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitTemplateService.updateById(tbFollowVisitTemplate);
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
//	@AutoLog(value = "随访模板表-通过id删除")
//	@ApiOperation(value="随访模板表-通过id删除", notes="随访模板表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<TbFollowVisitTemplate> delete(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitTemplate> result = new Result<TbFollowVisitTemplate>();
//		TbFollowVisitTemplate tbFollowVisitTemplate = tbFollowVisitTemplateService.getById(id);
//		if(tbFollowVisitTemplate==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = tbFollowVisitTemplateService.removeById(id);
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
//	@AutoLog(value = "随访模板表-批量删除")
//	@ApiOperation(value="随访模板表-批量删除", notes="随访模板表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<TbFollowVisitTemplate> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<TbFollowVisitTemplate> result = new Result<TbFollowVisitTemplate>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.tbFollowVisitTemplateService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "随访模板表-通过id查询")
//	@ApiOperation(value="随访模板表-通过id查询", notes="随访模板表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<TbFollowVisitTemplate> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<TbFollowVisitTemplate> result = new Result<TbFollowVisitTemplate>();
//		TbFollowVisitTemplate tbFollowVisitTemplate = tbFollowVisitTemplateService.getById(id);
//		if(tbFollowVisitTemplate==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(tbFollowVisitTemplate);
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
//      QueryWrapper<TbFollowVisitTemplate> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              TbFollowVisitTemplate tbFollowVisitTemplate = JSON.parseObject(deString, TbFollowVisitTemplate.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(tbFollowVisitTemplate, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<TbFollowVisitTemplate> pageList = tbFollowVisitTemplateService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "随访模板表列表");
//      mv.addObject(NormalExcelConstants.CLASS, TbFollowVisitTemplate.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("随访模板表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<TbFollowVisitTemplate> listTbFollowVisitTemplates = ExcelImportUtil.importExcel(file.getInputStream(), TbFollowVisitTemplate.class, params);
//              for (TbFollowVisitTemplate tbFollowVisitTemplateExcel : listTbFollowVisitTemplates) {
//                  tbFollowVisitTemplateService.save(tbFollowVisitTemplateExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listTbFollowVisitTemplates.size());
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
