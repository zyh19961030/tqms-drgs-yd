package com.qu.modules.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 作答
 * @Author: jeecg-boot
 * @Date:   2021-03-28
 * @Version: V1.0
 */
@Data
@TableName("answer")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="answer对象", description="作答")
public class Answer {
    
	/**主键*/
	@TableId(type = IdType.AUTO)
	@Excel(name = "主键", width = 15)
    @ApiModelProperty(value = "主键")
	private Integer id;
	/**问卷id*/
	@Excel(name = "问卷id", width = 15)
    @ApiModelProperty(value = "问卷id")
	private Integer quId;
	/**答案json*/
	@Excel(name = "答案json", width = 15)
    @ApiModelProperty(value = "答案json")
	private Object answerJson;
	/**答题人*/
	@Excel(name = "答题人", width = 15)
    @ApiModelProperty(value = "答题人")
	private Integer creater;
	/**答题时间*/
	@Excel(name = "答题时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "答题时间")
	private Date createTime;
}
