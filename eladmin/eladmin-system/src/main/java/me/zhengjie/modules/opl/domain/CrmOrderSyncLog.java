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
 * 工单同步log日志
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrmOrderSyncLog implements Serializable {

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id", position = 0)
	@Id
	private Integer id;

	/**
	 * crm工单id
	 */
	@ApiModelProperty(value = "crm工单id", position = 1)
	private Long crmOrderId;

	/**
	 * 是否成功
	 */
	@ApiModelProperty(value = "是否成功", position = 2)
	private Integer isSuccess;

	/**
	 * 创建日期
	 */
	@ApiModelProperty(value = "创建日期", position = 3)
	private String createdBy;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人", position = 4)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
	private Timestamp createdAt;

	/**
	 * 修改日期
	 */
	@ApiModelProperty(value = "修改日期", position = 5)
	private String updatedBy;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人", position = 6)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
	private Timestamp updatedAt;

	/**
	 * 失败原因
	 */
	@ApiModelProperty(value = "失败原因", position = 7)
	private String failedReason;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", position = 8)
	private String description;

}
