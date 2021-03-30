package com.qu.modules.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
@ApiModel(value="选项新增入参", description="选项新增入参")
public class QoptionParam {
    @ApiModelProperty(value = "主键")
    private java.lang.Integer id;
    /**选项名称*/
    @Excel(name = "选项名称", width = 15)
    @ApiModelProperty(value = "选项名称")
    private java.lang.String opName;
    /**跳题逻辑*/
    @Excel(name = "跳题逻辑", width = 15)
    @ApiModelProperty(value = "跳题逻辑")
    private java.lang.String jumpLogic;
    @ApiModelProperty(value = "是否其他  0:否 1:是")
    private Integer others;
    @ApiModelProperty(value = "绑定值")
    private java.lang.String bindValue;
}
