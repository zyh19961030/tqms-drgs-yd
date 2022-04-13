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
 * @Description: 单病种统计表
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
@Data
@TableName("q_single_disease_statistic")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="q_single_disease_statistic对象", description="单病种统计表")
public class QSingleDiseaseStatistic {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**年*/
	@Excel(name = "年", width = 15)
    @ApiModelProperty(value = "年")
	private Integer year;
	/**月份*/
	@Excel(name = "月份", width = 15)
    @ApiModelProperty(value = "月份")
	private Integer month;
	/**年月*/
	@Excel(name = "年月", width = 15)
    @ApiModelProperty(value = "年月")
	private String yearMonth;
	/**分类id*/
	@Excel(name = "分类id", width = 15)
    @ApiModelProperty(value = "分类id")
	private Integer categoryId;
	/**单病种名称*/
	@Excel(name = "单病种名称", width = 15)
    @ApiModelProperty(value = "单病种名称")
	private String singleDiseaseName;
	/**动态创建表的表名*/
	@Excel(name = "动态创建表的表名", width = 15)
    @ApiModelProperty(value = "动态创建表的表名")
	private String dynamicTableName;
	/**科室id*/
	@Excel(name = "科室id", width = 15)
    @ApiModelProperty(value = "科室id")
	private String deptId;
	/**科室名称*/
	@Excel(name = "科室名称", width = 15)
    @ApiModelProperty(value = "科室名称")
	private String deptName;
	/**需要上报数*/
	@Excel(name = "需要上报数", width = 15)
    @ApiModelProperty(value = "需要上报数")
	private Integer needReportCount;
	/**已填报数*/
	@Excel(name = "已填报数", width = 15)
    @ApiModelProperty(value = "已填报数")
	private Integer completeWriteCount;
	/**未填报数*/
	@Excel(name = "未填报数", width = 15)
    @ApiModelProperty(value = "未填报数")
	private Integer notWriteCount;
	/**上报国家率*/
	@Excel(name = "上报国家率", width = 15)
    @ApiModelProperty(value = "上报国家率")
	private String completeReportCountryRate;
	/**平均住院天数 字符串 可以带小数*/
	@Excel(name = "平均住院天数 字符串 可以带小数", width = 15)
    @ApiModelProperty(value = "平均住院天数 字符串 可以带小数")
	private String averageInHospitalDay;
	/**平均住院费用 字符串 可以带小数*/
	@Excel(name = "平均住院费用 字符串 可以带小数", width = 15)
    @ApiModelProperty(value = "平均住院费用 字符串 可以带小数")
	private String averageInHospitalFee;
	/**手术并发症发生率*/
	@Excel(name = "手术并发症发生率", width = 15)
    @ApiModelProperty(value = "手术并发症发生率")
	private String operationComplicationRate;
	/**死亡率*/
	@Excel(name = "死亡率", width = 15)
    @ApiModelProperty(value = "死亡率")
	private String mortality;
	/**平均药品费用 字符串 可以带小数*/
	@Excel(name = "平均药品费用 字符串 可以带小数", width = 15)
    @ApiModelProperty(value = "平均药品费用 字符串 可以带小数")
	private String averageDrugFee;
	/**平均手术治疗费用 字符串 可以带小数*/
	@Excel(name = "平均手术治疗费用 字符串 可以带小数", width = 15)
    @ApiModelProperty(value = "平均手术治疗费用 字符串 可以带小数")
	private String averageOperationTreatmentFee;
	/**平均一次性耗材费用 字符串 可以带小数*/
	@Excel(name = "平均一次性耗材费用 字符串 可以带小数", width = 15)
    @ApiModelProperty(value = "平均一次性耗材费用 字符串 可以带小数")
	private String averageDisposableConsumable;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
