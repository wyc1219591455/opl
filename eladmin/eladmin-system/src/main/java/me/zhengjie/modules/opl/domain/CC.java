package me.zhengjie.modules.opl.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
@Getter
@Setter
@Entity
public class CC implements Serializable {

    @Id
    private Integer id;
    private Integer number;
    private Integer ccPerson;
}
