package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.RequestClosureCode;
import me.zhengjie.modules.opl.service.dto.RequestClosureCodeCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 关闭状态
 * @author: ming.cao
 * @create: 2021-02-09 09:10
 **/
@Mapper
public interface RequestClosureCodeMapper {

    /**
     * @title: insert
     * @description: 新增
     * @date: 2021/2/9 9:12
     * @author: ming.cao
     * @param requestClosureCode
     * @return void
     * @throws
     */
    void insert(RequestClosureCode requestClosureCode);

    /**
     * @title: update
     * @description: 修改
     * @date: 2021/2/9 9:12
     * @author: ming.cao
     * @param requestClosureCode
     * @return void
     * @throws
     */
    void update(RequestClosureCode requestClosureCode);

    /**
     * @title: delete
     * @description: 删除
     * @date: 2021/2/9 9:12
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

    /**
     * @title: findAll
     * @description: 查询所有
     * @date: 2021/2/9 9:12
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.RequestClosureCode>
     * @throws
     */
    List<RequestClosureCode> findAll(RequestClosureCodeCriteria criteria);

    /**
     * @title: countRequestClosureCode
     * @description: 查询此数据的总数
     * @date: 2021/2/9 10:14
     * @author: ming.cao
     * @param
     * @return java.lang.Integer
     * @throws
     */
    Integer countRequestClosureCode(RequestClosureCodeCriteria criteria);

}
