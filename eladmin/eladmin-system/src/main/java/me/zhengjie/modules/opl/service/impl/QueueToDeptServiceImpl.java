package me.zhengjie.modules.opl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.QueuesToDept;
import me.zhengjie.modules.opl.mapper.QueuesToDeptMapper;
import me.zhengjie.modules.opl.service.QueuesToDeptService;
import me.zhengjie.modules.opl.service.dto.QueuesToDeptCriteria;
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
import java.util.stream.Collectors;

/**
 * @program: eladmin
 * @description: 支持组到部门
 * @author: ming.cao
 * @create: 2021-01-22 14:34
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QueueToDeptServiceImpl implements QueuesToDeptService {
    private final QueuesToDeptMapper queuesToDeptMapper;

    @Override
    public Map<String, Object> findByQueueId(Pageable pageable, Integer queueId) {
        if (pageable!=null&&pageable.getPage()==-1) {
            List<QueuesToDept> queuesToDeptList = queuesToDeptMapper.findByQueueId(queueId);
            Map<String,Object> map = new LinkedHashMap<>(2);
            map.put("content",queuesToDeptList);
            map.put("totalElements",queuesToDeptList.size());
            return map;
        }else{
            PageHelper.startPage(pageable.getPage(),pageable.getSize());
            List<QueuesToDept> queuesToDeptList = queuesToDeptMapper.findByQueueId(queueId);
            PageInfo<QueuesToDept> pageInfo1 = new PageInfo<>(queuesToDeptList);
            return  PageHelpResultUtil.toPage(pageInfo1);
        }
    }

    @Override
    public Map<String, Object> findAllUserByDeptId(Pageable pageable, Integer deptId) {
        if (pageable!=null&&pageable.getPage()==-1) {
            List<UserForShow> userForShowList = queuesToDeptMapper.findAllUserByDeptId(deptId);
            Map<String,Object> map = new LinkedHashMap<>(2);
            map.put("content",userForShowList);
            map.put("totalElements",userForShowList.size());
            return map;
        }else{
            PageHelper.startPage(pageable.getPage(),pageable.getSize());
            List<UserForShow> userForShowList = queuesToDeptMapper.findAllUserByDeptId(deptId);
            PageInfo<UserForShow> pageInfo1 = new PageInfo<>(userForShowList);
            return  PageHelpResultUtil.toPage(pageInfo1);
        }
    }

    @Override
    @Transactional
    public void addQueuesToDept(List<QueuesToDeptCriteria> criterias) {
      List<QueuesToDept>  queuesToDepts=criterias.stream().map(criteria -> {
            QueuesToDept queuesToDept = new QueuesToDept();
            queuesToDept.setQueuesId(criteria.getQueuesId());
            queuesToDept.setSourceId(criteria.getSourceId());
            queuesToDept.setType(criteria.getType());
            queuesToDept.setStatus(criteria.getStatus());
            queuesToDept.setCreateDateTime(new Timestamp(new Date().getTime()));
            queuesToDept.setCreateUserId(""+SecurityUtils.getCurrentUserId());
            return queuesToDept;
        }).collect(Collectors.toList());

        queuesToDeptMapper.batchInsert(queuesToDepts);
    }

    @Override
    public void updateQueuesToDept(List<QueuesToDeptCriteria> criterias) {
        //删除
        queuesToDeptMapper.deleteByQueue(criterias.get(0).getQueuesId());
        //新增
        List<QueuesToDept>  queuesToDepts=criterias.stream().map(criteria -> {
            QueuesToDept queuesToDept = new QueuesToDept();
            queuesToDept.setQueuesId(criteria.getQueuesId());
            queuesToDept.setSourceId(criteria.getSourceId());
            queuesToDept.setType(criteria.getType());
            queuesToDept.setStatus(criteria.getStatus());
            queuesToDept.setModifyDateTime(new Timestamp(new Date().getTime()));
            queuesToDept.setModifyUserId(""+SecurityUtils.getCurrentUserId());
            return queuesToDept;
        }).collect(Collectors.toList());
        queuesToDeptMapper.batchInsert(queuesToDepts);

    }


    @Override
    public void deleteQueuesToDept(List<Integer> ids) {
        try{
            for (Integer id : ids) {
                queuesToDeptMapper.delete(id);
            }
        } catch (Exception e){
            throw new BadRequestException("删除失败！");
        }
    }
}
