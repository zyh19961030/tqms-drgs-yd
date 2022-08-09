package com.qu.modules.web.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2022-08-09
 * @Version: V1.0
 */
@Data
@TableName("tb_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="tb_user对象", description="用户表")
public class TbUser {
    
	/**主键ID*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "主键ID")
	private String id;
	/**账户名称*/
	@Excel(name = "账户名称", width = 15)
    @ApiModelProperty(value = "账户名称")
	private String username;
	/**密码*/
	@Excel(name = "密码", width = 15)
    @ApiModelProperty(value = "密码")
	private String password;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
	private String status;
	/**联系人名称*/
	@Excel(name = "联系人名称", width = 15)
    @ApiModelProperty(value = "联系人名称")
	private String contact;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
	private String phone;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private Date createtime;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private Date updatetime;
	/**是否删除*/
	@Excel(name = "是否删除", width = 15)
    @ApiModelProperty(value = "是否删除")
	private String isdelete;
	/**账户类型*/
	@Excel(name = "账户类型", width = 15)
    @ApiModelProperty(value = "账户类型")
	private String type;
	/**所属医院ID（如果为平台的则为空）*/
	@Excel(name = "所属医院ID（如果为平台的则为空）", width = 15)
    @ApiModelProperty(value = "所属医院ID（如果为平台的则为空）")
	private String system;
	/**所属科室*/
	@Excel(name = "所属科室", width = 15)
    @ApiModelProperty(value = "所属科室")
	private String depid;
	/**用户登录名*/
	@Excel(name = "用户登录名", width = 15)
    @ApiModelProperty(value = "用户登录名")
	private String loginname;
	/**微信公众号唯一ID*/
	@Excel(name = "微信公众号唯一ID", width = 15)
    @ApiModelProperty(value = "微信公众号唯一ID")
	private String openid;
	/**用户编码*/
	@Excel(name = "用户编码", width = 15)
    @ApiModelProperty(value = "用户编码")
	private String usercode;
}
