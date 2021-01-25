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
public class ServiceCatalogToQueues implements Serializable {

	@Id
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", position = 0)
	private Integer id;

	/**
	 * 服务分类ID
	 */
	@ApiModelProperty(value = "服务分类ID", position = 1)
	private Integer catalogId;

	/**
	 * 支持组ID
	 */
	@ApiModelProperty(value = "支持组ID", position = 2)
	private Integer queuesId;

	/**
	 * 状态  1代表在使用 0代表不再使用
	 */
	@ApiModelProperty(value = "状态  1代表在使用 0代表不再使用", position = 3)
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", position = 4)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
	private Timestamp createDateTime;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人", position = 5)
	private Integer createUserId;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间", position = 6)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
	private Timestamp modifyDateTime;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人", position = 7)
	private Integer modifyUserId;


}
