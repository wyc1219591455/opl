package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestQueuesDetl implements Serializable {

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", position = 0)
	private Integer id;

	/**
	 * 主档键值(支持组主键)
	 */
	@ApiModelProperty(value = "主档键值(支持组主键)", position = 1)
	private Integer queuesId;

	/**
	 * 成员（成员主键）
	 */
	@ApiModelProperty(value = "成员（成员主键）", position = 2)
	private String memberId;

	/**
	 * 有效状态
	 */
	@ApiModelProperty(value = "有效状态", position = 3)
	private Integer status;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", position = 4)
	private String memo;

	/**
	 * 部门
	 */
	@ApiModelProperty(value = "部门", position = 5)
	private Integer deptId;

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

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序", position = 10)
	private Integer index;


}
