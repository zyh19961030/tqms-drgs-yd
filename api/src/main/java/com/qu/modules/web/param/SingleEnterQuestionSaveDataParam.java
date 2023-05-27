package com.qu.modules.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="SingleEnterQuestionSaveParam", description="SingleEnterQuestionSaveParam")
public class SingleEnterQuestionSaveDataParam {

    @ApiModelProperty(value = "mappingTableId不能为空")
    private String mappingTableId;

    @ApiModelProperty(value = "答案数组")
    private Answers[] answers;

}
