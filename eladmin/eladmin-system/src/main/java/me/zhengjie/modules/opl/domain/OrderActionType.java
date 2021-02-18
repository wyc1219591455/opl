package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderActionType implements Serializable {

	@Id
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", position = 0)
	private Integer id;

	/**
	 * 值
	 */
	@ApiModelProperty(value = "值", position = 1)
	private Integer text;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", position = 2)
	private String name;

	/**
	 * 创建工号
	 */
	@ApiModelProperty(value = "创建工号", position = 3)
	private String createUserId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", position = 4)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Timestamp createDateTime;

}
