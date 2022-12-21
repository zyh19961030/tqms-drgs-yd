package com.qu.modules.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="答案小程序入参", description="答案小程序入参")
public class AnswerMiniAppParam {
    @ApiModelProperty(value = "总表id")
    private Integer id;
    @ApiModelProperty(value = "问卷id")
    private Integer quId;
    @ApiModelProperty(value = "当前登录科室id")
    private String deptId;
    @ApiModelProperty(value = "状态 阶段性保存传0,提交传1")
    private Integer status;
    @ApiModelProperty(value = "答案数组")
    private Answers[] answers;
}
