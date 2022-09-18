package com.qu.modules.web.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value="CheckQuestionHistoryStatisticRecordListParam入参", description="CheckQuestionHistoryStatisticRecordListParam入参")
public class CheckQuestionHistoryStatisticRecordListParam {

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "日期_起始时间 格式：yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "日期_结束时间 格式：yyyy-MM-dd")
    private Date endDate;

    @NotNull(message = "问卷id不能为空")
    @ApiModelProperty(value = "问卷id")
    private Integer quId;

    @ApiModelProperty(value = "被检查科室id")
    private String checkedDeptId;

    @NotBlank(message = "检查科室id不能为空")
    @ApiModelProperty(value = "检查科室id")
    private String deptId;
}
