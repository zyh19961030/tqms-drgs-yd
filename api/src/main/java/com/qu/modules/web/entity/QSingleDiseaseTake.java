package com.qu.modules.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date:   2021-04-03
 * @Version: V1.0
 */
@Data
@TableName("q_single_disease_take")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="q_single_disease_take对象", description="单病种总表")
public class QSingleDiseaseTake {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**患者id*/
	@Excel(name = "患者id", width = 15)
    @ApiModelProperty(value = "患者id")
	private String patientId;
	/**患者姓名*/
	@Excel(name = "患者姓名", width = 15)
    @ApiModelProperty(value = "患者姓名")
	private String patientName;
	/**患者性别*/
	@Excel(name = "患者性别", width = 15)
    @ApiModelProperty(value = "患者性别")
	private String patientGender;
	/**年龄*/
	@Excel(name = "年龄", width = 15)
    @ApiModelProperty(value = "年龄")
	private String age;
	/**住院编号-病案号*/
	@Excel(name = "住院编号-病案号", width = 15)
    @ApiModelProperty(value = "住院编号-病案号")
	private String hospitalInNo;
	/**idc10编码*/
	@Excel(name = "idc10编码", width = 15)
    @ApiModelProperty(value = "idc10编码")
	private String icdTen;
	/**疾病名称*/
	@Excel(name = "疾病名称", width = 15)
    @ApiModelProperty(value = "疾病名称")
	private String icdName;
	/**状态：0待填报 1填报中 2已填报待审核 3审核失败驳回 4通过上报上中 5已上报待国家审核 6已完成 7已被国家驳回 8无需填报*/
	@Excel(name = "状态：0待填报 1填报中 2已填报待审核 3审核失败驳回 4通过上报上中 5已上报待国家审核 6已完成 7已被国家驳回 8无需填报", width = 15)
    @ApiModelProperty(value = "状态：0待填报 1填报中 2已填报待审核 3审核失败驳回 4通过上报上中 5已上报待国家审核 6已完成 7已被国家驳回 8无需填报")
	private Integer status;
	/**住院时间*/
    @ApiModelProperty(value = "住院时间")
	private Date inTime;
	/**出院时间*/
    @ApiModelProperty(value = "出院时间")
	private Date outTime;
	/**手术名称*/
	@Excel(name = "手术名称", width = 15)
    @ApiModelProperty(value = "手术名称")
	private String operationName;
	/**主治医生名称*/
	@Excel(name = "主治医生名称", width = 15)
    @ApiModelProperty(value = "主治医生名称")
	private String doctorName;
	/**主治医生id*/
	@Excel(name = "主治医生id", width = 15)
    @ApiModelProperty(value = "主治医生id")
	private String doctorId;
	/**填报表单id*/
	@Excel(name = "填报表单id", width = 15)
    @ApiModelProperty(value = "填报表单id")
	private Integer questionId;
	/**填报表单名称*/
	@Excel(name = "填报表单名称", width = 15)
    @ApiModelProperty(value = "填报表单名称")
	private String questionName;
	/**是否需要上报 0不需要上报  1需要上报*/
	@Excel(name = "是否需要上报 0不需要上报  1需要上报", width = 15)
    @ApiModelProperty(value = "是否需要上报 0不需要上报  1需要上报")
	private String reportStatus;
	/**科室名*/
	@Excel(name = "科室名", width = 15)
    @ApiModelProperty(value = "科室名")
	private String department;
	/**审核状态  0未审核  1已审核通过 2审核不通过*/
	@Excel(name = "审核状态  0未审核  1已审核通过 2审核不通过", width = 15)
    @ApiModelProperty(value = "审核状态  0未审核  1已审核通过 2审核不通过")
	private String examineStatus;
	/**审核不通过原因*/
	@Excel(name = "审核不通过原因", width = 15)
    @ApiModelProperty(value = "审核不通过原因")
	private String examineReason;
	/**国家平台驳回原因*/
	@Excel(name = "国家平台驳回原因", width = 15)
    @ApiModelProperty(value = "国家平台驳回原因")
	private String countryExamineReason;
	/**图标*/
	@Excel(name = "图标", width = 15)
    @ApiModelProperty(value = "图标")
	private String icon;
	/**身份证号*/
	@Excel(name = "身份证号", width = 15)
    @ApiModelProperty(value = "身份证号")
	private String idCard;
}
