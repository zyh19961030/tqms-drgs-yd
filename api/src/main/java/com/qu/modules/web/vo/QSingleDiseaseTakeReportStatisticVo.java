package com.qu.modules.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "单病种上报统计Vo", description = "单病种上报统计Vo")
public class QSingleDiseaseTakeReportStatisticVo {
    @ApiModelProperty(value = "病种名称")
    private String disease;
    @ApiModelProperty(value = "科室名称,勾选显示科室返回有值,其它为空")
    private String dept;
    @ApiModelProperty(value = "住院数")
    private Integer  inHospitalCount;
    @ApiModelProperty(value = "无需填报数")
    private Integer noNeedWriteCount;
    @ApiModelProperty(value = "需填报数")
    private Integer needWriteCount;
    @ApiModelProperty(value = "未填报")
    private Integer notWriteCount;
    @ApiModelProperty(value = "已填报")
    private Integer completeWriteCount;
    @ApiModelProperty(value = "医院已填报率")
    private String hospitalWriteRate;
    @ApiModelProperty(value = "已上报国家数")
    private Integer completeReportCountryCount;
    @ApiModelProperty(value = "国家上报率")
    private String completeReportCountryRate;
    @ApiModelProperty(value = "同期数量")
    private Integer samePeriodReportCount;
    @ApiModelProperty(value = "同期数量增长率")
    private String samePeriodReportRate;
    @ApiModelProperty(value = "环期数量")
    private Integer lastCycleReportCount;
    @ApiModelProperty(value = "环期数量增长率")
    private String lastCycleReportRate;
    @ApiModelProperty(value = "平均住院日")
    private BigDecimal averageInHospitalDay;
    @ApiModelProperty(value = "平均住院费用")
    private BigDecimal averageInHospitalFee;
    @ApiModelProperty(value = "平均药品费用")
    private BigDecimal averageDrugFee;
    @ApiModelProperty(value = "平均手术治疗费")
    private BigDecimal averageOperationFee;
    @ApiModelProperty(value = "平均一次性耗材费")
    private BigDecimal averageDisposableConsumableFee;
}
