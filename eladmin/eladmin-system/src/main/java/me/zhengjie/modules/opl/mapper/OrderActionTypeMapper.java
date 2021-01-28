package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.OrderActionType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 工单类型
 * @author: ming.cao
 * @create: 2021-01-26 14:44
 **/
@Mapper
public interface OrderActionTypeMapper {
    /**
     * @title: insert
     * @description: insert
     * @date: 2021/1/26 14:50
     * @author: ming.cao
     * @param typeList
     * @return void
     * @throws
     */
    void batchInsert(List<OrderActionType> typeList);

    /**
     * @title: batchUpdate
     * @description: batchUpdate
     * @date: 2021/1/26 14:51
     * @author: ming.cao
     * @param typeList
     * @return void
     * @throws
     */
    void batchUpdate(List<OrderActionType> typeList);

    /**
     * @title: delete
     * @description: 删除
     * @date: 2021/1/26 15:01
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

}
