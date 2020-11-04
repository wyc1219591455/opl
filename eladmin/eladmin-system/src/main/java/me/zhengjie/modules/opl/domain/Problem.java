package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
@Getter
@Setter
@Entity
public class Problem implements Serializable {
    @Id
    private Integer id;

    @ApiModelProperty(hidden = true)
    private String number;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 入参格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8") // 出参格式化
    private Date matterDate;

    private Integer projectId;

    private String description;

    private Integer faePerson;

    private Integer modelId;

    private Integer deptId;
}
