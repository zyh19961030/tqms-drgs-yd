package com.qu.modules.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 题库选项表
 * @Author: jeecg-boot
 * @Date:   2021-03-22
 * @Version: V1.0
 */
@Data
@TableName("qoptionlib")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="qoptionlib对象", description="题库选项表")
public class Qoptionlib {
    
	/**主键*/
	@TableId(type = IdType.AUTO)
	@Excel(name = "主键", width = 15)
    @ApiModelProperty(value = "主键")
	private java.lang.Integer id;
	/**题目id*/
	@Excel(name = "题目id", width = 15)
    @ApiModelProperty(value = "题目id")
	private java.lang.Integer subId;
	/**选项名称*/
	@Excel(name = "选项名称", width = 15)
    @ApiModelProperty(value = "选项名称")
	private java.lang.String opName;
	/**选项顺序*/
	@Excel(name = "选项顺序", width = 15)
    @ApiModelProperty(value = "选项顺序")
	private java.lang.Integer opOrder;

	@ApiModelProperty(value = "是否其他  0:否 1:是")
	private Integer others;

	@ApiModelProperty(value = "绑定值")
	private java.lang.String bindValue;

	/**0:正常1:已删除*/
	@Excel(name = "0:正常1:已删除", width = 15)
    @ApiModelProperty(value = "0:正常1:已删除")
	private java.lang.Integer del;
	/**创建者*/
	@Excel(name = "创建者", width = 15)
    @ApiModelProperty(value = "创建者")
	private java.lang.Integer creater;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**更新者*/
	@Excel(name = "更新者", width = 15)
    @ApiModelProperty(value = "更新者")
	private java.lang.Integer updater;
	/**更新时间*/
	@Excel(name = "更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
	private java.util.Date updateTime;
}
