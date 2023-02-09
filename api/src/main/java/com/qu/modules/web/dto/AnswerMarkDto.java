package com.qu.modules.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AnswerMarkDto", description = "AnswerMarkDto")
public class AnswerMarkDto {

    @ApiModelProperty(value = "问卷id")
    private Integer qu_id;
    @ApiModelProperty(value = "题目的id")
    private Integer sub_id;
    @ApiModelProperty(value = "病案号")
    private String case_id;
    @ApiModelProperty(value = "答案")
    private String answer;

}
