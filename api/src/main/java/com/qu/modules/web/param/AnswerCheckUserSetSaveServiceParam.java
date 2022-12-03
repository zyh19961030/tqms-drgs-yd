package com.qu.modules.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "AnswerCheckUserSetSaveServiceParam", description = "AnswerCheckUserSetSaveServiceParam")
public class AnswerCheckUserSetSaveServiceParam {
    @NotNull(message = "问卷id不能为空")
    @ApiModelProperty(value = "问卷id")
    private Integer quId;

    @ApiModelProperty(value = "用户id")
    private List<String> userId;

}
