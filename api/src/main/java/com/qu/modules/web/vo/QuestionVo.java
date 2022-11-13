package com.qu.modules.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
@ApiModel(value = "问卷Vo", description = "问卷Vo")
public class QuestionVo {
    @Excel(name = "主键", width = 15)
    @ApiModelProperty(value = "主键")
    private java.lang.Integer id;
    /**
     * 问卷名称
     */
    @Excel(name = "问卷名称", width = 15)
    @ApiModelProperty(value = "问卷名称")
    private java.lang.String quName;
    /**
     * 问卷描述
     */
    @Excel(name = "问卷描述", width = 15)
    @ApiModelProperty(value = "问卷描述")
    private java.lang.String quDesc;
    /**
     * 0:草稿箱 1:已发布
     */
    @Excel(name = "0:草稿箱 1:已发布", width = 15)
    @ApiModelProperty(value = "0:草稿箱 1:已发布")
    private java.lang.Integer quStatus;
    /**
     * 0:正常1:已停用
     */
    @Excel(name = "0:正常1:已停用", width = 15)
    @ApiModelProperty(value = "0:正常1:已停用")
    private java.lang.Integer quStop;
    /**
     * 科室查看权限，科室id逗号分割
     */
    @Excel(name = "科室查看权限，科室id逗号分割", width = 15)
    @ApiModelProperty(value = "科室查看权限，科室id逗号分割")
    private java.lang.String deptIds;
    /**
     * 答案对应数据库名
     */
    @Excel(name = "答案对应数据库名", width = 15)
    @ApiModelProperty(value = "答案对应数据库名")
    private java.lang.String tableName;
    /**
     * 0:正常1:已删除
     */
    @Excel(name = "0:正常1:已删除", width = 15)
    @ApiModelProperty(value = "0:正常1:已删除")
    private java.lang.Integer del;
    /**
     * 创建者
     */
    @Excel(name = "创建者", width = 15)
    @ApiModelProperty(value = "创建者")
    private java.lang.Integer creater;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    /**
     * 更新者
     */
    @Excel(name = "更新者", width = 15)
    @ApiModelProperty(value = "更新者")
    private java.lang.Integer updater;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;

    @ApiModelProperty(value = "题目")
    private List<SubjectVo> subjectVoList;

    @ApiModelProperty(value = "当前问卷版本号")
    private java.lang.String questionVersion;

    @ApiModelProperty(value = "数据当前问卷版本号")
    private java.lang.String questionVersionData;

}
