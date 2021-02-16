package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.dto.RequestClosureCodeCriteria;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 关闭类型业务逻辑层
 * @author: ming.cao
 * @create: 2021-02-09 09:41
 **/
public interface RequestClosureCodeService {

    /**
     * @title: insert
     * @description: 新增
     * @date: 2021/2/9 9:44
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void insert(RequestClosureCodeCriteria criteria);

    /**
     * @title: update
     * @description: 修改
     * @date: 2021/2/9 9:45
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void update(RequestClosureCodeCriteria criteria);

    /**
     * @title: delete
     * @description: 删除
     * @date: 2021/2/9 9:45
     * @author: ming.cao
     * @param ids
     * @return void
     * @throws
     */
    void delete(List<Integer> ids);

    /**
     * @title: findAll
     * @description: 查询所有
     * @date: 2021/2/9 9:45
     * @author: ming.cao
     * @param criteria
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findAll(Pageable pageable, RequestClosureCodeCriteria criteria);

}
