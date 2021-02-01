package me.zhengjie.modules.opl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

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
	private java.util.Date createDateTime;

}
