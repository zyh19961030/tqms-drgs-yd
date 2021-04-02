package com.qu.modules.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "按单病种填报Vo", description = "按单病种填报Vo")
public class QSingleDiseaseTakeVo {
    /*@ApiModelProperty(value = "人数")
    private int count;*/
    @ApiModelProperty(value = "图片")
    private String icon;
    @ApiModelProperty(value = "疾病名称")
    private String disease;
}
