package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.RequestClosureCode;
import me.zhengjie.modules.opl.mapper.RequestClosureCodeMapper;
import me.zhengjie.modules.opl.service.RequestClosureCodeService;
import me.zhengjie.modules.opl.service.dto.RequestClosureCodeCriteria;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 关闭类型业务逻辑实现
 * @author: ming.cao
 * @create: 2021-02-09 09:46
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RequestClosureCodeServiceImpl implements RequestClosureCodeService {

   private final RequestClosureCodeMapper requestClosureCodeMapper;

    @Override
    public void insert(RequestClosureCodeCriteria criteria) {
        //非空校验
        isEmptyTest(criteria);
        //唯一性校验
        isOnlyTest(criteria);
        //插入
        RequestClosureCode requestClosureCode = new RequestClosureCode();
        requestClosureCode.setDesc(criteria.getDesc());
        requestClosureCode.setName(criteria.getName());
        requestClosureCode.setCreateUserId(""+SecurityUtils.getCurrentUserId());
        requestClosureCode.setCreateDateTime(new Timestamp(new Date().getTime()));
        requestClosureCodeMapper.insert(requestClosureCode);

    }

    @Override
    public void update(RequestClosureCodeCriteria criteria) {
        //非空校验
        isEmptyTest(criteria);
        //唯一性校验
        isOnlyTest(criteria);
        //修改
        RequestClosureCode requestClosureCode = new RequestClosureCode();
        requestClosureCode.setId(criteria.getId());
        requestClosureCode.setDesc(criteria.getDesc());
        requestClosureCode.setName(criteria.getName());
        requestClosureCode.setModifyDateTime(new Timestamp(new Date().getTime()));
        requestClosureCode.setModifyUserId(""+SecurityUtils.getCurrentUserId());
        requestClosureCodeMapper.update(requestClosureCode);
    }

    @Override
    @Transactional
    public void delete(List<Integer> ids) {
        try{
            for (Integer id : ids) {
                requestClosureCodeMapper.delete(id);
            }

        }catch (Exception e){
            throw new BadRequestException("删除失败！");
        }
    }

    @Override
    public Map<String, Object> findAll(Pageable pageable, RequestClosureCodeCriteria criteria) {
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<RequestClosureCode> requestClosureCodeList = requestClosureCodeMapper.findAll(criteria);
        PageInfo<RequestClosureCode> pageInfo1 = new PageInfo<>(requestClosureCodeList);
        return PageHelpResultUtil.toPage(pageInfo1);
    }

    /**
     * 非空校验
     */
    private void isEmptyTest(RequestClosureCodeCriteria criteria){
        if (ObjectUtil.isEmpty(criteria.getDesc())){
            throw new BadRequestException("描述不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getName())){
            throw new BadRequestException("名称不能为空！");
        }
    }

    /**
     * 唯一性校验
     */
    private void isOnlyTest(RequestClosureCodeCriteria criteria){
      Integer count = requestClosureCodeMapper.countRequestClosureCode(criteria);
      if (count>0){
          throw new BadRequestException("该数据已存在，请勿重复！");
      }
    }

}
