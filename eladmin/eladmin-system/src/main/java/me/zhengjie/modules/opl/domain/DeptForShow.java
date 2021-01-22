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
public class DeptForShow implements Serializable {

	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id", position = 0)
	private Long deptId;

	/**
	 * 上级部门
	 */
	@ApiModelProperty(value = "上级部门", position = 1)
	private String parentId;

	/**
	 * 所属公司
	 */
	@ApiModelProperty(value = "所属公司", position = 2)
	private String orgId;

	@ApiModelProperty(value = "部门全称", position = 3)
	private String deptFullName;

	@ApiModelProperty(value = "部门名称", position = 4)
	private String deptName;

	/**
	 * 员工姓名
	 */
	@ApiModelProperty(value = "员工姓名", position = 5)
	private String deptCode;

	/**
	 * erp或hr部门编号
	 */
	@ApiModelProperty(value = "erp或hr部门编号", position = 6)
	private String sourceCode;

	@ApiModelProperty(value = "排序", position = 7)
	private Long sort;

	@ApiModelProperty(value = "描述", position = 8)
	private String description;

	/**
	 * 有效状态
	 */
	@ApiModelProperty(value = "有效状态", position = 9)
	private Integer flag;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人", position = 10)
	private String createUserId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", position = 11)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
	private Timestamp createDateTime;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人", position = 12)
	private String modifyUserId;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间", position = 13)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
	private Timestamp modifyDateTime;


}
