package me.zhengjie.modules.opl.service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.base.BaseDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @program: eladmin
 * @description: 公司树结构
 * @author: ming.cao
 * @create: 2021-01-21 10:09
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgTreeDto extends BaseDTO implements Serializable {

    private String id;

    private List<OrgTreeDto> children;

    private String text;

    private String value;

    private Long parentId;

    private Boolean hasChildren = false;

    private Long level;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrgTreeDto orgTreeEntity = (OrgTreeDto) o;
        return Objects.equals(id, orgTreeEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
