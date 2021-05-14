package com.qu.modules.web.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "全院单病种上报数量统计-查看图表-单病种上报数据概览Vo", description = "全院单病种上报数量统计-查看图表-单病种上报数据概览Vo")
public class QSingleDiseaseTakeReportStatisticOverviewVo {
    @ApiModelProperty(value = "折线图数据")
    private List<QSingleDiseaseTakeReportStatisticOverviewLineVo> overviewLineVoList;
    @ApiModelProperty(value = "饼图数据")
    private List<QSingleDiseaseTakeReportStatisticOverviewPieVo> overviewPieVoList;
}
