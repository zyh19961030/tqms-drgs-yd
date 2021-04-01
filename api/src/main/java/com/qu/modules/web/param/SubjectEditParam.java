package com.qu.modules.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.List;

@Data
@ApiModel(value="题目编辑入参", description="题目编辑入参")
public class SubjectEditParam {
    @ApiModelProperty(value = "主键")
    private java.lang.Integer id;
    /**问卷id*/
    @Excel(name = "问卷id", width = 15)
    @ApiModelProperty(value = "问卷id")
    private Integer quId;
    /**题目名称*/
    @Excel(name = "题目名称", width = 15)
    @ApiModelProperty(value = "题目名称")
    private String subName;
    @ApiModelProperty(value = "题目类型 1.单选 2.多选 3.日期 4.时间 5.下拉框 6.单行文本 7.多行文本 8.分组框 9.提示标题")
    private String subType;
    /**是否为必填 0:非必填 1:必填*/
    @Excel(name = "是否为必填 0:非必填 1:必填", width = 15)
    @ApiModelProperty(value = "是否为必填 0:非必填 1:必填")
    private Integer required;
    /**默认是否显示 0:不显示 1:显示*/
    @Excel(name = "默认是否显示 0:不显示 1:显示", width = 15)
    @ApiModelProperty(value = "默认是否显示 0:不显示 1:显示")
    private Integer display;
    /**最多字数限制*/
    @Excel(name = "最多字数限制", width = 15)
    @ApiModelProperty(value = "最多字数限制")
    private Integer limitWords;
    /**文本框校验 num:数字 email:电子邮箱 ch:中文 en:英文 idcard:身份证号码 qqno:QQ号 phone:手机号码(仅支持大陆地区) tel:电话号码*/
    @Excel(name = "文本框校验 num:数字 email:电子邮箱 ch:中文 en:英文 idcard:身份证号码 qqno:QQ号 phone:手机号码(仅支持大陆地区) tel:电话号码", width = 15)
    @ApiModelProperty(value = "文本框校验 num:数字 email:电子邮箱 ch:中文 en:英文 idcard:身份证号码 qqno:QQ号 phone:手机号码(仅支持大陆地区) tel:电话号码")
    private String textCheck;
    /**多行文本框高度*/
    @Excel(name = "多行文本框高度", width = 15)
    @ApiModelProperty(value = "多行文本框高度")
    private Integer textHight;
    /**时间是否为区间 0:非区间 1:区间*/
    @Excel(name = "时间是否为区间 0:非区间 1:区间", width = 15)
    @ApiModelProperty(value = "时间是否为区间 0:非区间 1:区间")
    private Integer section;
    /**默认值*/
    @Excel(name = "默认值", width = 15)
    @ApiModelProperty(value = "默认值")
    private String defValue;
    /**时间显示方式 0:时分 1:时分秒*/
    @Excel(name = "时间显示方式 0:时分 1:时分秒", width = 15)
    @ApiModelProperty(value = "时间显示方式 0:时分 1:时分秒")
    private Integer defDisplay;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
    /**跳题逻辑*/
    @Excel(name = "跳题逻辑", width = 15)
    @ApiModelProperty(value = "跳题逻辑")
    private String jumpLogic;

    @ApiModelProperty(value = "分组题包含题号")
    private String[] groupIds;
    @ApiModelProperty(value = "绑定值")
    private java.lang.String bindValue;

    @ApiModelProperty(value = "是否抓取0:不是抓取  1:抓取")
    private java.lang.Integer grab;

    @ApiModelProperty(value = "抓取类型")
    private java.lang.String grabType;

    /**数据库列名*/
    @Excel(name = "数据库列名", width = 15)
    @ApiModelProperty(value = "数据库列名")
    private String columnName;

    @ApiModelProperty(value = "选项")
    private List<QoptionParam>  optionParamList;
}
