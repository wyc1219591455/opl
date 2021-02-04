package me.zhengjie.modules.opl.service.dto;

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
public class SysDeptDto implements Serializable {

	@ApiModelProperty("部门id")
	private Long deptId;

	@ApiModelProperty("ping号")
	private Long pid;

	@ApiModelProperty("部门名称")
	private String name;

	@ApiModelProperty("部门排序")
	private Integer deptSort;

	@ApiModelProperty("是否可用")
	private String enabled;

	@ApiModelProperty("创建人")
	private String createBy;

	@ApiModelProperty("修改人")
	private String updateBy;

	@ApiModelProperty("创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
	private Timestamp createTime;

	@ApiModelProperty("修改时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
	private Timestamp updateTime;


}
