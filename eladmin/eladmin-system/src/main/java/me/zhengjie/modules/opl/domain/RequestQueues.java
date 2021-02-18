package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: RequestQueues
 * @description: 支持组
 * @author: ming.cao
 * @create: 2021-01-20 15:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestQueues implements Serializable {

	@Id
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", position = 0)
	private Integer id;

	/**
	 * 组代码
	 */
	@ApiModelProperty(value = "组代码", position = 1)
	private String code;

	/**
	 * 组名称
	 */
	@ApiModelProperty(value = "组名称", position = 2)
	private String name;

	/**
	 * 有效状态
	 */
	@ApiModelProperty(value = "有效状态：1启用 0禁用", position = 3)
	private Integer status;

	/**
	 * 有效状态
	 */
	@ApiModelProperty(value = "描述", position = 4)
	private String memo;

	/**
	 * 是否默认组
	 */
	@ApiModelProperty(value = "是否默认组：1 是 0 否", position = 5)
	private Integer isDefault;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人", position = 6)
	private String createUserId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", position = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Timestamp createDateTime;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人", position = 8)
	private String modifyUserId;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间", position = 9)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Timestamp modifyDateTime;

}
