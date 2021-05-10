package com.qu.modules.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设置分类Param", description = "设置分类Param")
public class UpdateCategoryIdParam {
    @ApiModelProperty(value = "问卷id")
    private String[] quId;
    @ApiModelProperty(value = "分类id")
    private Integer[] categoryId;
}
