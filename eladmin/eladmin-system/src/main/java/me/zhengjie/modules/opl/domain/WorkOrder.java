package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName WorkOrder.java
 * @Description TODO
 * @createTime 2020年10月28日 10:25:00
 */
@Entity
@Getter
@Setter
public class WorkOrder implements Serializable {
    @Id
    private Long id;

    private Long creatUserId;

    private String theme;

    private String description;

    private Long solveUserId;

    private Boolean state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 入参格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8") // 出参格式化
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 入参格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8") // 出参格式化
    private Date updateTime;
}
