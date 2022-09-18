package com.qu.modules.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class CheckQuestionHistoryStatisticDetailListRequest {
    @ApiModelProperty(value = "问卷id")
    private Integer quId;

    @Pattern(regexp="\\d{4}-(0[1-9]|1[0-2])",message="选择检查月份_时间格式不对")
    @ApiModelProperty(value = "选择检查月份_时间  格式：月:yyyy-MM")
    private String checkMonth;

    @ApiModelProperty(value = "被检查科室id")
    private String checkedDeptId;

    @ApiModelProperty(value = "检查科室id")
    private String deptId;

    @ApiModelProperty(value = "自查科室id")
    private String selfDeptId;
}
