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

import java.util.Date;

/**
 * @Description: 单病种总表
 * @Author: jeecg-boot
 * @Date:   2021-04-24
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
	private Integer reportStatus;
	/**不需要上报原因id*/
	@Excel(name = "不需要上报原因id", width = 15)
    @ApiModelProperty(value = "不需要上报原因id")
	private String reportNoReasonId;
	/**不需要上报原因*/
	@Excel(name = "不需要上报原因", width = 15)
    @ApiModelProperty(value = "不需要上报原因")
	private String reportNoReason;
	/**不需要上报原因中的备注*/
	@Excel(name = "不需要上报原因中的备注", width = 15)
    @ApiModelProperty(value = "不需要上报原因中的备注")
	private String reportNoReasonNote;
	/**医院科室id*/
	@Excel(name = "医院科室id", width = 15)
    @ApiModelProperty(value = "医院科室id")
	private String departmentId;
	/**医院科室名*/
	@Excel(name = "医院科室名", width = 15)
    @ApiModelProperty(value = "医院科室名")
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
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**填写时间*/
    @ApiModelProperty(value = "填写时间")
	private Date writeTime;
	/**主要诊断*/
	@Excel(name = "主要诊断", width = 15)
    @ApiModelProperty(value = "主要诊断")
	private String mainDiagnosis;
	/**提交时间*/
	@Excel(name = "提交时间", width = 15)
    @ApiModelProperty(value = "提交时间")
	private String submitTime;
	/**次要诊断*/
	@Excel(name = "次要诊断", width = 15)
    @ApiModelProperty(value = "次要诊断")
	private String secondaryDiagnosis;
	/**住院天数*/
	@Excel(name = "住院天数", width = 15)
    @ApiModelProperty(value = "住院天数")
	private Integer inHospitalDay;
	/**住院费用 单位分*/
	@Excel(name = "住院费用 单位分", width = 15)
    @ApiModelProperty(value = "住院费用 单位分")
	private Integer inHospitalFee;
	/**药品费用 单位分*/
	@Excel(name = "药品费用 单位分", width = 15)
    @ApiModelProperty(value = "药品费用 单位分")
	private Integer drugFee;
	/**手术治疗费用 单位分*/
	@Excel(name = "手术治疗费用 单位分", width = 15)
    @ApiModelProperty(value = "手术治疗费用 单位分")
	private Integer operationTreatmentFee;
	/**一次性耗材费用 单位分*/
	@Excel(name = "一次性耗材费用 单位分", width = 15)
    @ApiModelProperty(value = "一次性耗材费用 单位分")
	private Integer disposableConsumable;
	/**
	 * 答案json
	 */
	@Excel(name = "答案json", width = 15)
	@ApiModelProperty(value = "答案json")
	private Object answerJson;
	/**
	 * 状态0:草稿1:已提交
	 */
	@ApiModelProperty(value = "状态0:草稿1:已提交")
	private Integer answerStatus;
	@ApiModelProperty(value = "答题人id")
	private String answer;

	@ApiModelProperty(value = "答题人姓名")
	private String answerName;

	/**
	 * 答题时间
	 */
	@Excel(name = "答题时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "答题时间")
	private Date answerTime;

	@ApiModelProperty(value = "答题人部门id")
	private String answerDeptid;
	@ApiModelProperty(value = "答题人部门名称")
	private String answerDeptname;
	@ApiModelProperty(value = "动态创建表的id")
	private String tableId;
}
