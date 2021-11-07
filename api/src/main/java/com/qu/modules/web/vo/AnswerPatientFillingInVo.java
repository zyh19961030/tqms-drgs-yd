package com.qu.modules.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ApiModel(value = "作答Vo", description = "作答Vo")
public class AnswerPatientFillingInVo {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "问卷id")
    private Integer quId;
    @ApiModelProperty(value = "问卷名称")
    private String quName;
    /**患者姓名*/
    @Excel(name = "患者姓名", width = 15)
    @ApiModelProperty(value = "患者姓名")
    private java.lang.String patientName;
    /**住院号*/
    @Excel(name = "住院号", width = 15)
    @ApiModelProperty(value = "住院号")
    private java.lang.String hospitalInNo;
    /**年龄*/
    @Excel(name = "年龄", width = 15)
    @ApiModelProperty(value = "年龄")
    private java.lang.Integer age;
    /**住院时间*/
    @ApiModelProperty(value = "住院时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date inTime;
    /**修改时间*/
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**提交时间*/
    @Excel(name = "提交时间", width = 15)
    @ApiModelProperty(value = "提交时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.lang.String submitTime;

}
