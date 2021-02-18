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
public class QueuesToDept implements Serializable {

	@Id
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", position = 0)
	private Integer id;

	/**
	 * 服务分类Id
	 */
	@ApiModelProperty(value = "支持组Id", position = 1)
	private Integer queuesId;

	/**
	 * 关联的人员或部门Id
	 */
	@ApiModelProperty(value = "关联的人员或部门Id", position = 2)
	private Integer sourceId;

	/**
	 * 类型 0代表关联部门 1代表关联人员
	 */
	@ApiModelProperty(value = "类型 0代表关联部门 1代表关联人员", position = 3)
	private Integer type;

	/**
	 * 状态 1代表在使用 0代表不再使用
	 */
	@ApiModelProperty(value = "状态 1代表在使用 0代表不再使用", position = 4)
	private String status;

	/**
	 * 创建日期
	 */
	@ApiModelProperty(value = "创建日期", position = 5)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Timestamp createDateTime;

	/**
	 * 创建人工号
	 */
	@ApiModelProperty(value = "创建人工号", position = 6)
	private String createUserId;

	/**
	 * 修改日期
	 */
	@ApiModelProperty(value = "修改日期", position = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Timestamp modifyDateTime;

	/**
	 * 修改人工号
	 */
	@ApiModelProperty(value = "修改人工号", position = 8)
	private String modifyUserId;


}
