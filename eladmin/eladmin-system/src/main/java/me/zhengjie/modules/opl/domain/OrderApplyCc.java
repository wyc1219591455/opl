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
 * 工单抄送
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderApplyCc implements Serializable {

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id", position = 0)
	@Id
	private Integer id;

	/**
	 * 请求主表编号
	 */
	@ApiModelProperty(value = "请求主表编号", position = 1)
	private Integer transId;

	/**
	 * 人员编号
	 */
	@ApiModelProperty(value = "人员编号", position = 2)
	private String empId;

	/**
	 * 1：抄送 2：订阅
	 */
	@ApiModelProperty(value = "1：抄送 2：订阅", position = 3)
	private Integer ccType;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", position = 4)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Timestamp modifyDateTime;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人", position = 7)
	private Integer modifyUserId;

	/**
	 * 表来源（0 主表crmworkorder ，1 附表 suborder）
	 */
	@ApiModelProperty(value = "来源表", position = 8)
	private Integer originalType;

}
