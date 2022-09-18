package com.qu.modules.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(value="CheckQuestionHistoryStatisticRecordListRequest入参", description="CheckQuestionHistoryStatisticRecordListRequest入参")
public class CheckQuestionHistoryStatisticRecordListRequest {

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "日期_起始时间 格式：yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "日期_结束时间 格式：yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value = "问卷id")
    private Integer quId;

    @ApiModelProperty(value = "被检查科室id")
    private String checkedDeptId;

    @ApiModelProperty(value = "检查科室id")
    private String deptId;

    @ApiModelProperty(value = "自查科室id")
    private String selfDeptId;

}
