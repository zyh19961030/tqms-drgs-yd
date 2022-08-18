package com.qu.modules.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.api.vo.ResultFactory;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qu.constant.Constant;
import com.qu.modules.web.entity.CheckDetailSet;
import com.qu.modules.web.param.CheckDetailSetParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.ICheckDetailSetService;
import com.qu.modules.web.vo.CheckDetailSetVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 检查明细列设置表
 * @Author: jeecg-boot
 * @Date:   2022-08-09
 * @Version: V1.0
 */
@Slf4j
@Api(tags="检查明细列设置表")
@RestController
@RequestMapping("/business/checkDetailSet")
public class CheckDetailSetController {
	@Autowired
	private ICheckDetailSetService checkDetailSetService;


	/**
	 * 检查明细列设置表-通过问卷id查询数据
	 * @param questionId
	 * @return
	 */
	@AutoLog(value = "检查明细列设置表-通过问卷id查询数据")
	@ApiOperation(value="检查明细列设置表-通过问卷id查询数据", notes="检查明细列设置表-通过问卷id查询数据",response = CheckDetailSetVo.class)
	@GetMapping(value = "/queryByQuestionId")
	public Result<CheckDetailSetVo> queryByQuestionId(@RequestParam(name="questionId",required=true)@ApiParam("问卷id") Integer questionId, HttpServletRequest request) {
		Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
		String userId = data.getTbUser().getId();
		Result<CheckDetailSetVo> result = new Result<CheckDetailSetVo>();
		List<CheckDetailSetVo> checkDetailSet = checkDetailSetService.queryByQuestionId(questionId,userId);
		return ResultFactory.success(checkDetailSet);
	}

	/**
	 *   更新
	 * @param paramList
	 * @return
	 */
	@AutoLog(value = "检查明细列设置表-更新")
	@ApiOperation(value="检查明细列设置表-更新", notes="检查明细列设置表-更新")
	@PostMapping(value = "/update")
	public Result<CheckDetailSet> update(@RequestBody List<CheckDetailSetParam> paramList, HttpServletRequest request) {
		Result<CheckDetailSet> result = new Result<CheckDetailSet>();
		try {
			Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
			String userId = data.getTbUser().getId();
			checkDetailSetService.addList(paramList,userId);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}



//	/**
//	  * 分页列表查询
//	 * @param checkDetailSet
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "检查明细列设置表-分页列表查询")
//	@ApiOperation(value="检查明细列设置表-分页列表查询", notes="检查明细列设置表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<CheckDetailSet>> queryPageList(CheckDetailSet checkDetailSet,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<CheckDetailSet>> result = new Result<IPage<CheckDetailSet>>();
//		QueryWrapper<CheckDetailSet> queryWrapper = QueryGenerator.initQueryWrapper(checkDetailSet, req.getParameterMap());
//		Page<CheckDetailSet> page = new Page<CheckDetailSet>(pageNo, pageSize);
//		IPage<CheckDetailSet> pageList = checkDetailSetService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param checkDetailSet
//	 * @return
//	 */
//	@AutoLog(value = "检查明细列设置表-添加")
//	@ApiOperation(value="检查明细列设置表-添加", notes="检查明细列设置表-添加")
//	@PostMapping(value = "/add")
//	public Result<CheckDetailSet> add(@RequestBody CheckDetailSet checkDetailSet) {
//		Result<CheckDetailSet> result = new Result<CheckDetailSet>();
//		try {
//			checkDetailSetService.save(checkDetailSet);
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
//	 * @param checkDetailSet
//	 * @return
//	 */
//	@AutoLog(value = "检查明细列设置表-编辑")
//	@ApiOperation(value="检查明细列设置表-编辑", notes="检查明细列设置表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<CheckDetailSet> edit(@RequestBody CheckDetailSet checkDetailSet) {
//		Result<CheckDetailSet> result = new Result<CheckDetailSet>();
//		CheckDetailSet checkDetailSetEntity = checkDetailSetService.getById(checkDetailSet.getId());
//		if(checkDetailSetEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = checkDetailSetService.updateById(checkDetailSet);
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
//	@AutoLog(value = "检查明细列设置表-通过id删除")
//	@ApiOperation(value="检查明细列设置表-通过id删除", notes="检查明细列设置表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<CheckDetailSet> delete(@RequestParam(name="id",required=true) String id) {
//		Result<CheckDetailSet> result = new Result<CheckDetailSet>();
//		CheckDetailSet checkDetailSet = checkDetailSetService.getById(id);
//		if(checkDetailSet==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = checkDetailSetService.removeById(id);
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
//	@AutoLog(value = "检查明细列设置表-批量删除")
//	@ApiOperation(value="检查明细列设置表-批量删除", notes="检查明细列设置表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<CheckDetailSet> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<CheckDetailSet> result = new Result<CheckDetailSet>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.checkDetailSetService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "检查明细列设置表-通过id查询")
//	@ApiOperation(value="检查明细列设置表-通过id查询", notes="检查明细列设置表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<CheckDetailSet> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<CheckDetailSet> result = new Result<CheckDetailSet>();
//		CheckDetailSet checkDetailSet = checkDetailSetService.getById(id);
//		if(checkDetailSet==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(checkDetailSet);
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
//      QueryWrapper<CheckDetailSet> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              CheckDetailSet checkDetailSet = JSON.parseObject(deString, CheckDetailSet.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(checkDetailSet, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<CheckDetailSet> pageList = checkDetailSetService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "检查明细列设置表列表");
//      mv.addObject(NormalExcelConstants.CLASS, CheckDetailSet.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("检查明细列设置表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<CheckDetailSet> listCheckDetailSets = ExcelImportUtil.importExcel(file.getInputStream(), CheckDetailSet.class, params);
//              for (CheckDetailSet checkDetailSetExcel : listCheckDetailSets) {
//                  checkDetailSetService.save(checkDetailSetExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listCheckDetailSets.size());
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
