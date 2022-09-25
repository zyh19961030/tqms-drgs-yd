package com.qu.modules.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="QuestionQueryByIdParam", description="QuestionQueryByIdParam")
public class QuestionQueryByIdParam {

    @ApiModelProperty(value = "问卷id")
    @NotNull(message = "问卷id不能为空")
    private Integer quId;

    @ApiModelProperty(value = "数据id")
    @NotNull(message = "数据id不能为空")
    private Integer answerCheckId;
}
