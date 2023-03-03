package com.qu.modules.web.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 登记表痕迹表
 * @Author: jeecg-boot
 * @Date:   2023-03-03
 * @Version: V1.0
 */
@Data
@TableName("answer_mark")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="answer_mark对象", description="登记表痕迹表")
public class AnswerMark {
    
	/**主键*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "主键")
	private Integer id;
	/**问卷id*/
	@Excel(name = "问卷id", width = 15)
    @ApiModelProperty(value = "问卷id")
	private Integer quId;
	/**题目id*/
	@Excel(name = "题目id", width = 15)
    @ApiModelProperty(value = "题目id")
	private Integer subjectId;
	/**被检查患者病案号*/
	@Excel(name = "被检查患者病案号", width = 15)
    @ApiModelProperty(value = "被检查患者病案号")
	private String caseId;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**修改时间*/
    @ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**修改人id*/
	@Excel(name = "修改人id", width = 15)
    @ApiModelProperty(value = "修改人id")
	private String createUser;
	/**修改之前数据*/
	@Excel(name = "修改之前数据", width = 15)
    @ApiModelProperty(value = "修改之前数据")
	private String dataBefore;
	/**修改之后数据*/
	@Excel(name = "修改之后数据", width = 15)
    @ApiModelProperty(value = "修改之后数据")
	private String dataAfter;
}
