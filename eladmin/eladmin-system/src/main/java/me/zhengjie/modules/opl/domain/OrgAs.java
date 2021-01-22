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
 * @program: eladmin
 * @description: 公司
 * @author: ming.cao
 * @create: 2021-01-21 10:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgAs implements Serializable {

	/**
	 * 主键(公司编码)
	 */
	@ApiModelProperty(value = "主键(公司编码)", position = 0)
	private Long orgId;

	/**
	 * 上级
	 */
	@ApiModelProperty(value = "上级", position = 1)
	private String parentId;

	/**
	 * 公司代码
	 */
	@ApiModelProperty(value = "公司代码", position = 2)
	private String enCode;

	/**
	 * 简称
	 */
	@ApiModelProperty(value = "简称", position = 3)
	private String shortName;

	/**
	 * 全称
	 */
	@ApiModelProperty(value = "全称", position = 4)
	private String fullName;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注", position = 5)
	private String memo;

	/**
	 * 有效状态
	 */
	@ApiModelProperty(value = "有效状态", position = 6)
	private Integer status;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人", position = 7)
	private String createUserId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", position = 8)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
	private Timestamp createDateTime;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人", position = 9)
	private String modifyUserId;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间", position = 10)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
	private Timestamp modifyDateTime;

	/**
	 * ERP或HR id
	 */
	@ApiModelProperty(value = "ERP或HR id", position = 11)
	private String orgSourceId;


}
