package com.qu.modules.web.controller;

import com.qu.constant.Constant;
import com.qu.modules.web.param.AnswerCheckUserSetSaveParam;
import com.qu.modules.web.param.AnswerCheckUserSetSaveServiceParam;
import com.qu.modules.web.pojo.Data;
import com.qu.modules.web.service.IAnswerCheckUserSetService;
import com.qu.modules.web.service.IQuestionService;
import com.qu.modules.web.service.ITbUserService;
import com.qu.modules.web.vo.AnswerCheckSetAllDataVo;
import com.qu.modules.web.vo.QuestionSetColumnVo;
import com.qu.modules.web.vo.QuestionSetLineVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.ResultBetter;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 检查表的检查人员设置表
 * @Author: jeecg-boot
 * @Date:   2022-11-30
 * @Version: V1.0
 */
@Slf4j
@Api(tags="检查表的检查人员设置表")
@RestController
@RequestMapping("/business/answerCheckUserSet")
public class AnswerCheckUserSetController {
	@Autowired
	private IAnswerCheckUserSetService answerCheckUserSetService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
	private ITbUserService tbUserService;

    /**
     * 检查人员管理设置行
     */
    @ApiOperation(value = "检查人员管理设置行", notes = "检查人员管理设置行")
    @GetMapping(value = "/setLine")
    public ResultBetter<QuestionSetLineVo> setLine(HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        return  tbUserService.setLine(data);
    }

    /**
     * 检查人员管理设置列
     */
    @AutoLog(value = "检查人员管理设置列")
    @ApiOperation(value = "检查人员管理设置列", notes = "检查人员管理设置列")
    @GetMapping(value = "/setColumn")
    public ResultBetter<QuestionSetColumnVo> setColumn(HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        return questionService.setColumn(data);

    }


    /**
     * 检查人员管理保存行或保存列
     */
    @AutoLog(value = "检查人员管理保存行或保存列")
    @ApiOperation(value = "检查人员管理保存行或保存列", notes = "检查人员管理保存行或保存列")
    @PostMapping(value = "/saveAnswerCheckUserSet")
    public ResultBetter saveAnswerCheckUserSet(@RequestBody @Validated AnswerCheckUserSetSaveParam param, HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getDeps().get(0).getId();
//        String userId = data.getTbUser().getId();
        return  answerCheckUserSetService.saveAnswerCheckUserSet(param,deptId);
    }

     /**
	  * 查看某科室的检查人员设置接口
	  */
	 @AutoLog(value = "查看某科室的检查人员设置接口")
	 @ApiOperation(value = "查看某科室的检查人员设置接口", notes = "查看某科室的检查人员设置接口")
	 @GetMapping(value = "/selectAnswerCheckUserSet")
	 public ResultBetter<AnswerCheckSetAllDataVo> selectAnswerCheckUserSet(HttpServletRequest request) {
		 //加科室过滤---
		 Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
		 String deptId = data.getDeps().get(0).getId();
		 return answerCheckUserSetService.selectAnswerCheckUserSet(deptId);
	 }


    /**
     * 保存服务设置
     */
    @AutoLog(value = "保存服务设置")
    @ApiOperation(value = "保存服务设置", notes = "保存服务设置")
    @PostMapping(value = "/saveService")
    public ResultBetter saveService(@RequestBody @Validated List<AnswerCheckUserSetSaveServiceParam> param, HttpServletRequest request) {
        Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
        String deptId = data.getDeps().get(0).getId();
//        String userId = data.getTbUser().getId();
        return  answerCheckUserSetService.saveService(param,deptId);
    }




//	 /**
//	  * 查看某一个登记表的筛选时间(月度、季度、年)的数据接口
//	  */
//	 @AutoLog(value = "查看某一个登记表的筛选时间(月度、季度、年)的数据接口")
//	 @ApiOperation(value = "查看某一个登记表的筛选时间(月度、季度、年)的数据接口", notes = "查看某一个登记表的筛选时间(月度、季度、年)的数据接口")
//	 @GetMapping(value = "/answerAllData")
//	 public ResultBetter<AnswerAllDataVo> answerAllData(HttpServletRequest request, @Validated AnswerAllDataParam param) {
//		 //加科室过滤---
//		 Data data = (Data) request.getSession().getAttribute(Constant.SESSION_USER);
//		 String deptId = data.getDeps().get(0).getId();
//		 return answerService.answerAllData(deptId,param);
//	 }

//	/**
//	  * 分页列表查询
//	 * @param answerCheckUserSet
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "检查表的检查人员设置表-分页列表查询")
//	@ApiOperation(value="检查表的检查人员设置表-分页列表查询", notes="检查表的检查人员设置表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<AnswerCheckUserSet>> queryPageList(AnswerCheckUserSet answerCheckUserSet,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<AnswerCheckUserSet>> result = new Result<IPage<AnswerCheckUserSet>>();
//		QueryWrapper<AnswerCheckUserSet> queryWrapper = QueryGenerator.initQueryWrapper(answerCheckUserSet, req.getParameterMap());
//		Page<AnswerCheckUserSet> page = new Page<AnswerCheckUserSet>(pageNo, pageSize);
//		IPage<AnswerCheckUserSet> pageList = answerCheckUserSetService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param answerCheckUserSet
//	 * @return
//	 */
//	@AutoLog(value = "检查表的检查人员设置表-添加")
//	@ApiOperation(value="检查表的检查人员设置表-添加", notes="检查表的检查人员设置表-添加")
//	@PostMapping(value = "/add")
//	public Result<AnswerCheckUserSet> add(@RequestBody AnswerCheckUserSet answerCheckUserSet) {
//		Result<AnswerCheckUserSet> result = new Result<AnswerCheckUserSet>();
//		try {
//			answerCheckUserSetService.save(answerCheckUserSet);
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
//	 * @param answerCheckUserSet
//	 * @return
//	 */
//	@AutoLog(value = "检查表的检查人员设置表-编辑")
//	@ApiOperation(value="检查表的检查人员设置表-编辑", notes="检查表的检查人员设置表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<AnswerCheckUserSet> edit(@RequestBody AnswerCheckUserSet answerCheckUserSet) {
//		Result<AnswerCheckUserSet> result = new Result<AnswerCheckUserSet>();
//		AnswerCheckUserSet answerCheckUserSetEntity = answerCheckUserSetService.getById(answerCheckUserSet.getId());
//		if(answerCheckUserSetEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = answerCheckUserSetService.updateById(answerCheckUserSet);
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
//	@AutoLog(value = "检查表的检查人员设置表-通过id删除")
//	@ApiOperation(value="检查表的检查人员设置表-通过id删除", notes="检查表的检查人员设置表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<AnswerCheckUserSet> delete(@RequestParam(name="id",required=true) String id) {
//		Result<AnswerCheckUserSet> result = new Result<AnswerCheckUserSet>();
//		AnswerCheckUserSet answerCheckUserSet = answerCheckUserSetService.getById(id);
//		if(answerCheckUserSet==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = answerCheckUserSetService.removeById(id);
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
//	@AutoLog(value = "检查表的检查人员设置表-批量删除")
//	@ApiOperation(value="检查表的检查人员设置表-批量删除", notes="检查表的检查人员设置表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<AnswerCheckUserSet> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<AnswerCheckUserSet> result = new Result<AnswerCheckUserSet>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.answerCheckUserSetService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "检查表的检查人员设置表-通过id查询")
//	@ApiOperation(value="检查表的检查人员设置表-通过id查询", notes="检查表的检查人员设置表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<AnswerCheckUserSet> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<AnswerCheckUserSet> result = new Result<AnswerCheckUserSet>();
//		AnswerCheckUserSet answerCheckUserSet = answerCheckUserSetService.getById(id);
//		if(answerCheckUserSet==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(answerCheckUserSet);
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
//      QueryWrapper<AnswerCheckUserSet> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              AnswerCheckUserSet answerCheckUserSet = JSON.parseObject(deString, AnswerCheckUserSet.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(answerCheckUserSet, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<AnswerCheckUserSet> pageList = answerCheckUserSetService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "检查表的检查人员设置表列表");
//      mv.addObject(NormalExcelConstants.CLASS, AnswerCheckUserSet.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("检查表的检查人员设置表列表数据", "导出人:Jeecg", "导出信息"));
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
//              List<AnswerCheckUserSet> listAnswerCheckUserSets = ExcelImportUtil.importExcel(file.getInputStream(), AnswerCheckUserSet.class, params);
//              for (AnswerCheckUserSet answerCheckUserSetExcel : listAnswerCheckUserSets) {
//                  answerCheckUserSetService.save(answerCheckUserSetExcel);
//              }
//              return Result.ok("文件导入成功！数据行数:" + listAnswerCheckUserSets.size());
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
