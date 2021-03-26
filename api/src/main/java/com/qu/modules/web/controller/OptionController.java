package com.qu.modules.web.controller;

import com.qu.modules.web.service.IOptionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 选项表
 * @Author: jeecg-boot
 * @Date:   2021-03-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags="选项表")
@RestController
@RequestMapping("/business/option")
public class OptionController {
	@Autowired
	private IOptionService optionService;
//
//	/**
//	  * 分页列表查询
//	 * @param option
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "选项表-分页列表查询")
//	@ApiOperation(value="选项表-分页列表查询", notes="选项表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<Qoption>> queryPageList(Qoption option,
//												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//												HttpServletRequest req) {
//		Result<IPage<Qoption>> result = new Result<IPage<Qoption>>();
//		QueryWrapper<Qoption> queryWrapper = QueryGenerator.initQueryWrapper(option, req.getParameterMap());
//		Page<Qoption> page = new Page<Qoption>(pageNo, pageSize);
//		IPage<Qoption> pageList = optionService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param option
//	 * @return
//	 */
//	@AutoLog(value = "选项表-添加")
//	@ApiOperation(value="选项表-添加", notes="选项表-添加")
//	@PostMapping(value = "/add")
//	public Result<Qoption> add(@RequestBody Qoption option) {
//		Result<Qoption> result = new Result<Qoption>();
//		try {
//			optionService.save(option);
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
//	 * @param option
//	 * @return
//	 */
//	@AutoLog(value = "选项表-编辑")
//	@ApiOperation(value="选项表-编辑", notes="选项表-编辑")
//	@PutMapping(value = "/edit")
//	public Result<Qoption> edit(@RequestBody Qoption option) {
//		Result<Qoption> result = new Result<Qoption>();
//		Qoption optionEntity = optionService.getById(option.getId());
//		if(optionEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = optionService.updateById(option);
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
//	@AutoLog(value = "选项表-通过id删除")
//	@ApiOperation(value="选项表-通过id删除", notes="选项表-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<Qoption> delete(@RequestParam(name="id",required=true) String id) {
//		Result<Qoption> result = new Result<Qoption>();
//		Qoption option = optionService.getById(id);
//		if(option==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = optionService.removeById(id);
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
//	@AutoLog(value = "选项表-批量删除")
//	@ApiOperation(value="选项表-批量删除", notes="选项表-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<Qoption> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<Qoption> result = new Result<Qoption>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.optionService.removeByIds(Arrays.asList(ids.split(",")));
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
//	@AutoLog(value = "选项表-通过id查询")
//	@ApiOperation(value="选项表-通过id查询", notes="选项表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<Qoption> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<Qoption> result = new Result<Qoption>();
//		Qoption option = optionService.getById(id);
//		if(option==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(option);
//			result.setSuccess(true);
//		}
//		return result;
//	}


}
