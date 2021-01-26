package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.RequestQueues;
import me.zhengjie.modules.opl.mapper.RequestQueuesMapper;
import me.zhengjie.modules.opl.service.RequestQueuesService;
import me.zhengjie.modules.opl.service.dto.RequestQueuesCriteria;
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
 * @description: 支持组
 * @author: ming.cao
 * @create: 2021-01-20 16:05
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RequestQueuesServiceImpl implements RequestQueuesService {
    private final RequestQueuesMapper requestQueuesMapper;

    @Override
    public Map<String, Object> findAll(Pageable pageable) {
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<RequestQueues> requestQueuesList = requestQueuesMapper.findAll();
        PageInfo<RequestQueues> pageInfo1 = new PageInfo<>(requestQueuesList);
        return PageHelpResultUtil.toPage(pageInfo1);
    }

    @Override
    public void addRequestQueues(RequestQueuesCriteria criteria) {
        //非空校验
        isEmptyTest(criteria);
        //唯一性校验
        isOnlyTest(criteria);
        RequestQueues requestQueues = new RequestQueues();
        requestQueues.setCode(criteria.getCode());
        requestQueues.setName(criteria.getName());
        requestQueues.setStatus(criteria.getStatus());
        requestQueues.setMemo(criteria.getMemo());
        requestQueues.setIsDefault(criteria.getIsDefault());
        requestQueues.setCreateUserId(""+SecurityUtils.getCurrentUserId());
        requestQueues.setCreateDateTime(new Timestamp(new Date().getTime()));
        requestQueuesMapper.insert(requestQueues);
    }

    @Override
    public void updateRequestQueues(RequestQueuesCriteria criteria) {

        if (ObjectUtil.isNotEmpty(criteria)){
          if (ObjectUtil.isEmpty(criteria.getId())){
              throw new BadRequestException("请传入修改参数的id！");
          }
        }
        //非空校验
        isEmptyTest(criteria);
        //唯一性校验
        isOnlyTest(criteria);
        RequestQueues requestQueues = new RequestQueues();
        requestQueues.setId(criteria.getId());
        requestQueues.setCode(criteria.getCode());
        requestQueues.setName(criteria.getName());
        requestQueues.setStatus(criteria.getStatus());
        requestQueues.setMemo(criteria.getMemo());
        requestQueues.setIsDefault(criteria.getIsDefault());
        requestQueues.setModifyUserId(""+SecurityUtils.getCurrentUserId());
        requestQueues.setModifyDateTime(new Timestamp(new Date().getTime()));
        requestQueuesMapper.update(requestQueues);

    }

    @Override
    @Transactional
    public void delete(List<Integer> ids) {

        for (Integer id : ids) {
            requestQueuesMapper.delete(id);
        }
    }

    /**
     * 非空校验
     * @param criteria
     */
    private void isEmptyTest(RequestQueuesCriteria criteria){
        if (ObjectUtil.isEmpty(criteria.getCode())){
            throw new BadRequestException("组代码不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getName())){
            throw new BadRequestException("组名称不能为空！");
        }

    }

    /**
     * 唯一性校验
     */
    private void isOnlyTest(RequestQueuesCriteria criteria){
        if (ObjectUtil.isNotEmpty(criteria)) {
           if (requestQueuesMapper.countRequestQueuesByCode(criteria.getCode(),criteria.getId())>0){
               throw new BadRequestException("组代码不允许重复！");
           }

           if (criteria.getIsDefault()==1){
               if (ObjectUtil.isEmpty(criteria.getId())){
                   //没id说明新增设置id
                    criteria.setId(0);
               }
               if (requestQueuesMapper.countDefaultQueue(criteria.getId())>0){
                   throw new BadRequestException("已存在默认组，请勿重复设置！");
               }
           }
        }


    }
}
