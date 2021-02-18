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
public class OrderStatus implements Serializable {
	@Id
	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号", position = 0)
	private Integer id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", position = 1)
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", position = 2)
	private String desc;

	/**
	 * 关闭计时器
	 */
	@ApiModelProperty(value = "关闭计时器", position = 3)
	private Integer isCloseTimer;

	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "颜色", position = 4)
	private String color;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人", position = 5)
	private String createUserId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", position = 6)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Timestamp createDateTime;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人", position = 7)
	private String modifyUserId;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间", position = 8)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Timestamp modifyDateTime;

}
