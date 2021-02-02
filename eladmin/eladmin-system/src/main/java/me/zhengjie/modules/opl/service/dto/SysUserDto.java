package me.zhengjie.modules.opl.service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDto implements Serializable {

	@ApiModelProperty("用户id")
	private Long userId;

	@ApiModelProperty("部门id")
	private Long deptId;

	@ApiModelProperty("工号")
	private String username;

	@ApiModelProperty("姓名")
	private String nickName;

	@ApiModelProperty("邮件")
	private String email;

	@ApiModelProperty("是否在用")
	private Long enabled;

}
