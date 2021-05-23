package com.qu.modules.web.controller;

import com.qu.modules.web.entity.QsubjectField;
import com.qu.modules.web.service.IQsubjectFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 题目字段表
 * @Author: jeecg-boot
 * @Date: 2021-05-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "题目字段表")
@RestController
@RequestMapping("/business/qsubjectField")
public class QsubjectFieldController {
    @Autowired
    private IQsubjectFieldService qsubjectFieldService;

    /**
     * 通过题目名查询
     *
     * @param subjectName
     * @return
     */
    @AutoLog(value = "题目字段表-通过题目名查询")
    @ApiOperation(value = "题目字段表-通过题目名查询", notes = "题目字段表-通过题目名查询")
    @GetMapping(value = "/queryBySubjectName")
    public Result<List<QsubjectField>> queryBySubjectName(@RequestParam(name = "subjectName", required = true) @ApiParam("题目名") String subjectName) {
        Result<List<QsubjectField>> result = new Result<>();
        List<QsubjectField> qsubjectFieldList = qsubjectFieldService.getBySubjectName(subjectName);
        if (qsubjectFieldList == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(qsubjectFieldList);
            result.setSuccess(true);
        }
        return result;
    }


    /**
     * 分页列表查询
     * @param qsubjectField
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     *//*
	@AutoLog(value = "题目字段表-分页列表查询")
	@ApiOperation(value="题目字段表-分页列表查询", notes="题目字段表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<QsubjectField>> queryPageList(QsubjectField qsubjectField,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<QsubjectField>> result = new Result<IPage<QsubjectField>>();
		QueryWrapper<QsubjectField> queryWrapper = QueryGenerator.initQueryWrapper(qsubjectField, req.getParameterMap());
		Page<QsubjectField> page = new Page<QsubjectField>(pageNo, pageSize);
		IPage<QsubjectField> pageList = qsubjectFieldService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	*//**
     *   添加
     * @param qsubjectField
     * @return
     *//*
	@AutoLog(value = "题目字段表-添加")
	@ApiOperation(value="题目字段表-添加", notes="题目字段表-添加")
	@PostMapping(value = "/add")
	public Result<QsubjectField> add(@RequestBody QsubjectField qsubjectField) {
		Result<QsubjectField> result = new Result<QsubjectField>();
		try {
			qsubjectFieldService.save(qsubjectField);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	*//**
     *  编辑
     * @param qsubjectField
     * @return
     *//*
	@AutoLog(value = "题目字段表-编辑")
	@ApiOperation(value="题目字段表-编辑", notes="题目字段表-编辑")
	@PutMapping(value = "/edit")
	public Result<QsubjectField> edit(@RequestBody QsubjectField qsubjectField) {
		Result<QsubjectField> result = new Result<QsubjectField>();
		QsubjectField qsubjectFieldEntity = qsubjectFieldService.getById(qsubjectField.getId());
		if(qsubjectFieldEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = qsubjectFieldService.updateById(qsubjectField);
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
	@AutoLog(value = "题目字段表-通过id删除")
	@ApiOperation(value="题目字段表-通过id删除", notes="题目字段表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<QsubjectField> delete(@RequestParam(name="id",required=true) String id) {
		Result<QsubjectField> result = new Result<QsubjectField>();
		QsubjectField qsubjectField = qsubjectFieldService.getById(id);
		if(qsubjectField==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = qsubjectFieldService.removeById(id);
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
	@AutoLog(value = "题目字段表-批量删除")
	@ApiOperation(value="题目字段表-批量删除", notes="题目字段表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<QsubjectField> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<QsubjectField> result = new Result<QsubjectField>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.qsubjectFieldService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	*//**
     * 通过id查询
     * @param id
     * @return
     *//*
	@AutoLog(value = "题目字段表-通过id查询")
	@ApiOperation(value="题目字段表-通过id查询", notes="题目字段表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<QsubjectField> queryById(@RequestParam(name="id",required=true) String id) {
		Result<QsubjectField> result = new Result<QsubjectField>();
		QsubjectField qsubjectField = qsubjectFieldService.getById(id);
		if(qsubjectField==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(qsubjectField);
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
      QueryWrapper<QsubjectField> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              QsubjectField qsubjectField = JSON.parseObject(deString, QsubjectField.class);
              queryWrapper = QueryGenerator.initQueryWrapper(qsubjectField, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<QsubjectField> pageList = qsubjectFieldService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "题目字段表列表");
      mv.addObject(NormalExcelConstants.CLASS, QsubjectField.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("题目字段表列表数据", "导出人:Jeecg", "导出信息"));
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
              List<QsubjectField> listQsubjectFields = ExcelImportUtil.importExcel(file.getInputStream(), QsubjectField.class, params);
              for (QsubjectField qsubjectFieldExcel : listQsubjectFields) {
                  qsubjectFieldService.save(qsubjectFieldExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listQsubjectFields.size());
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
