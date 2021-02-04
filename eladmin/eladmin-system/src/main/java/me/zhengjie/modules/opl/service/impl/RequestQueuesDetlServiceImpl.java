package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.RequestQueuesDetl;
import me.zhengjie.modules.opl.mapper.RequestQueuesDetlMapper;

import me.zhengjie.modules.opl.service.RequestQueuesDetlService;

import me.zhengjie.modules.opl.service.dto.OrgTreeDto;
import me.zhengjie.modules.opl.service.dto.RequestQueuesDetlCriteria;
import me.zhengjie.modules.opl.service.dto.RequestQueuesDetlDto;
import me.zhengjie.modules.opl.service.dto.UserForShow;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 组明细表业务逻辑层
 * @author: ming.cao
 * @create: 2021-01-20 18:31
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RequestQueuesDetlServiceImpl implements RequestQueuesDetlService {

    private final RequestQueuesDetlMapper requestQueuesDetlMapper;

    @Override
    public Map<String, Object> findByQueuesId(Pageable pageable, Integer id) {
        if (pageable.getPage() == -1) {
            List<RequestQueuesDetlDto> dtoList = requestQueuesDetlMapper.findByQueuesId(id);
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("content", dtoList);
            map.put("totalElements", dtoList.size());
            return map;
        } else {
            PageHelper.startPage(pageable.getPage(), pageable.getSize());
            List<RequestQueuesDetlDto> dtoList = requestQueuesDetlMapper.findByQueuesId(id);
            PageInfo<RequestQueuesDetlDto> pageInfo1 = new PageInfo<>(dtoList);
            return PageHelpResultUtil.toPage(pageInfo1);
        }
    }

    @Override
    public void addRequestQueuesDetl(RequestQueuesDetlCriteria criteria) {
        try {
            //非空校验
            isEmptyTest(criteria);
            //唯一性校验
            isOnlyTest(criteria);
            RequestQueuesDetl detl = new RequestQueuesDetl();
            detl.setDeptId(criteria.getDeptId());
            detl.setMemberId(criteria.getMemberId());
            detl.setMemo(criteria.getMemo());
            detl.setQueuesId(criteria.getQueuesId());
            detl.setStatus(criteria.getStatus());
            detl.setCreateUserId("" + SecurityUtils.getCurrentUserId());
            detl.setCreateDateTime(new Timestamp(new Date().getTime()));
            requestQueuesDetlMapper.insert(detl);
        } catch (Exception e) {
            throw new BadRequestException("新增失败！");
        }
    }

    @Override
    public void updateRequestQueuesDetl(RequestQueuesDetlCriteria criteria) {
        try {
            //非空校验
            isEmptyTest(criteria);
            //唯一性校验
            isOnlyTest(criteria);
            RequestQueuesDetl detl = new RequestQueuesDetl();
            detl.setId(criteria.getId());
            detl.setDeptId(criteria.getDeptId());
            detl.setMemberId(criteria.getMemberId());
            detl.setMemo(criteria.getMemo());
            detl.setQueuesId(criteria.getQueuesId());
            detl.setStatus(criteria.getStatus());
            detl.setCreateUserId("" + SecurityUtils.getCurrentUserId());
            detl.setCreateDateTime(new Timestamp(new Date().getTime()));
            requestQueuesDetlMapper.update(detl);
        } catch (Exception e) {
            throw new BadRequestException("更新失败！");
        }
    }

    @Override
    @Transactional
    public void deleteRequestQueuesDetl(List<Integer> ids) {
        try {
            for (Integer id : ids) {
                requestQueuesDetlMapper.delete(id);
            }
        } catch (Exception e) {
            throw new BadRequestException("删除失败！");
        }

    }

    @Override
    public List<OrgTreeDto> getOrgData() {
        return null;
    }

    @Override
    public Map<String, Object> getUserForShow(Pageable pageable, Long queuesId, Long deptId) {
        if (pageable != null && pageable.getPage() == -1) {
            List<UserForShow> queues = requestQueuesDetlMapper.findUserForShow(queuesId, null);
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("content", queues);
            map.put("totalElements", queues.size());
            return map;
        } else {
            PageHelper.startPage(pageable.getPage(), pageable.getSize());
            List<UserForShow> queues = requestQueuesDetlMapper.findUserForShow(queuesId, null);
            PageInfo<UserForShow> pageInfo1 = new PageInfo<>(queues);
            return PageHelpResultUtil.toPage(pageInfo1);
        }
    }

    /**
     * 非空校验
     *
     * @param criteria
     */
    private void isEmptyTest(RequestQueuesDetlCriteria criteria) {
        if (ObjectUtil.isEmpty(criteria.getMemberId())) {
            throw new BadRequestException("人员不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getQueuesId())) {
            throw new BadRequestException("支持组不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getDeptId())) {
            throw new BadRequestException("部门不能为空！");
        }

    }

    /**
     * 唯一性校验
     */
    private void isOnlyTest(RequestQueuesDetlCriteria criteria) {
        if (ObjectUtil.isNotEmpty(criteria)) {
            Integer count = requestQueuesDetlMapper.countMemberInQueue(criteria.getQueuesId(), Integer.parseInt(criteria.getMemberId()));
            if (count > 0) {
                throw new BadRequestException("支持组下人员不允许重复！");
            }
        }

    }

}
