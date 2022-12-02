package com.qu.modules.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import com.qu.modules.web.entity.TbUserPosition;
import com.qu.modules.web.service.ITbUserPositionService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 用户职位中间表
 * @Author: jeecg-boot
 * @Date:   2022-12-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="用户职位中间表")
@RestController
@RequestMapping("/web/tbUserPosition")
public class TbUserPositionController {
	@Autowired
	private ITbUserPositionService tbUserPositionService;
	
	/**
	  * 分页列表查询
	 * @param tbUserPosition
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "用户职位中间表-分页列表查询")
	@ApiOperation(value="用户职位中间表-分页列表查询", notes="用户职位中间表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TbUserPosition>> queryPageList(TbUserPosition tbUserPosition,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<TbUserPosition>> result = new Result<IPage<TbUserPosition>>();
		QueryWrapper<TbUserPosition> queryWrapper = QueryGenerator.initQueryWrapper(tbUserPosition, req.getParameterMap());
		Page<TbUserPosition> page = new Page<TbUserPosition>(pageNo, pageSize);
		IPage<TbUserPosition> pageList = tbUserPositionService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param tbUserPosition
	 * @return
	 */
	@AutoLog(value = "用户职位中间表-添加")
	@ApiOperation(value="用户职位中间表-添加", notes="用户职位中间表-添加")
	@PostMapping(value = "/add")
	public Result<TbUserPosition> add(@RequestBody TbUserPosition tbUserPosition) {
		Result<TbUserPosition> result = new Result<TbUserPosition>();
		try {
			tbUserPositionService.save(tbUserPosition);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param tbUserPosition
	 * @return
	 */
	@AutoLog(value = "用户职位中间表-编辑")
	@ApiOperation(value="用户职位中间表-编辑", notes="用户职位中间表-编辑")
	@PutMapping(value = "/edit")
	public Result<TbUserPosition> edit(@RequestBody TbUserPosition tbUserPosition) {
		Result<TbUserPosition> result = new Result<TbUserPosition>();
		TbUserPosition tbUserPositionEntity = tbUserPositionService.getById(tbUserPosition.getId());
		if(tbUserPositionEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = tbUserPositionService.updateById(tbUserPosition);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户职位中间表-通过id删除")
	@ApiOperation(value="用户职位中间表-通过id删除", notes="用户职位中间表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<TbUserPosition> delete(@RequestParam(name="id",required=true) String id) {
		Result<TbUserPosition> result = new Result<TbUserPosition>();
		TbUserPosition tbUserPosition = tbUserPositionService.getById(id);
		if(tbUserPosition==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = tbUserPositionService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户职位中间表-批量删除")
	@ApiOperation(value="用户职位中间表-批量删除", notes="用户职位中间表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<TbUserPosition> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<TbUserPosition> result = new Result<TbUserPosition>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.tbUserPositionService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户职位中间表-通过id查询")
	@ApiOperation(value="用户职位中间表-通过id查询", notes="用户职位中间表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TbUserPosition> queryById(@RequestParam(name="id",required=true) String id) {
		Result<TbUserPosition> result = new Result<TbUserPosition>();
		TbUserPosition tbUserPosition = tbUserPositionService.getById(id);
		if(tbUserPosition==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(tbUserPosition);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<TbUserPosition> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              TbUserPosition tbUserPosition = JSON.parseObject(deString, TbUserPosition.class);
              queryWrapper = QueryGenerator.initQueryWrapper(tbUserPosition, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<TbUserPosition> pageList = tbUserPositionService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "用户职位中间表列表");
      mv.addObject(NormalExcelConstants.CLASS, TbUserPosition.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户职位中间表列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
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
              List<TbUserPosition> listTbUserPositions = ExcelImportUtil.importExcel(file.getInputStream(), TbUserPosition.class, params);
              for (TbUserPosition tbUserPositionExcel : listTbUserPositions) {
                  tbUserPositionService.save(tbUserPositionExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listTbUserPositions.size());
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
  }

}
