package me.zhengjie.modules.opl.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
@Entity
@Getter
@Setter
@ToString
public class Status implements Serializable {
    @Id
    private Integer id;
    private String status;
}
