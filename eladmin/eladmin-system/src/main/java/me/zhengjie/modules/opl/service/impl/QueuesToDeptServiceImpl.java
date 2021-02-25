package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.QueuesToDept;
import me.zhengjie.modules.opl.mapper.QueuesToDeptMapper;
import me.zhengjie.modules.opl.service.QueuesToDeptService;
import me.zhengjie.modules.opl.service.dto.QueuesToDeptCriteria;

import me.zhengjie.modules.opl.service.dto.QueuesToDeptCriteria1;
import me.zhengjie.modules.opl.service.dto.SysDeptDto;
import me.zhengjie.modules.opl.service.dto.UserForShow;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
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
public class QueuesToDeptServiceImpl implements QueuesToDeptService {
    private final QueuesToDeptMapper queuesToDeptMapper;

    @Override
    public Map<String, Object> findByQueueId(Pageable pageable, Integer queueId) {
        if (pageable!=null&&pageable.getPage()==-1) {
            List<UserForShow> queuesToDeptList = queuesToDeptMapper.findByQueueId(queueId);
            Map<String,Object> map = new LinkedHashMap<>(2);
            map.put("content",queuesToDeptList);
            map.put("totalElements",queuesToDeptList.size());
            return map;
        }else{
            PageHelper.startPage(pageable.getPage(),pageable.getSize());
            List<UserForShow> queuesToDeptList = queuesToDeptMapper.findByQueueId(queueId);
            PageInfo<UserForShow> pageInfo1 = new PageInfo<>(queuesToDeptList);
            return  PageHelpResultUtil.toPage(pageInfo1);
        }
    }

    @Override
    public Map<String, Object> findDeptByCatalogId(Integer catalogId) {
        List<SysDeptDto> deptDtoList = queuesToDeptMapper.findDeptByCatalogId(catalogId);
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",deptDtoList);
        map.put("totalElements",deptDtoList.size());
        return map;
    }

    @Override
    public Map<String, Object> findAllUserByDeptId(Pageable pageable, Integer level , Integer queuesId , String name,Integer deptId) {

        if (level == 0){  //如果是最上层公司，那么就展示已勾选的
            //if (pageable!=null&&pageable.getPage()==-1) {
                List<UserForShow> userForShowList = new ArrayList<>();
                userForShowList = queuesToDeptMapper.findAllUserInUse(queuesId,name);
                Map<String,Object> map = new LinkedHashMap<>(2);
                map.put("content",userForShowList);
                map.put("totalElements",userForShowList.size());
                return map;
            /*}else{
                PageHelper.startPage(pageable.getPage(),pageable.getSize());
                List<UserForShow> userForShowList = queuesToDeptMapper.findAllUserInUse(queuesId);
                PageInfo<UserForShow> pageInfo1 = new PageInfo<>(userForShowList);
                return PageHelpResultUtil.toPage(pageInfo1);
            }*/

        }else if (level == 1){  //如果是二级公司，及公司与子公司，则全部展示
            //查询此公司下面有没有部门
            Integer count = queuesToDeptMapper.getCountByOrgId(deptId);
            List<UserForShow> userForShowList = new ArrayList<>();
            if (count<=0){
                Map<String,Object> map = new LinkedHashMap<>(2);
                map.put("content",null);
                map.put("totalElements",0);
                return map;
            }else{
                //获取公司下的部门
                List<Integer> tempList = queuesToDeptMapper.getListByOrgId(deptId);
                //如果不是底层部门则需要遍历，获取下面所有的部门数据
                List<Integer> deptList = new ArrayList<>();
                //获取部门下面所有的部门数据
                for (Integer integer : tempList) {
                    getAllDeptList(deptList,integer);
                }
                if (deptList.size()>0){
                    userForShowList = queuesToDeptMapper.findAllUserByDeptIdAndQueueId2(queuesId, deptList, name);

                }
            }

            Map<String,Object> map = new LinkedHashMap<>(2);
            map.put("content",userForShowList);
            map.put("totalElements",userForShowList.size());
            return map;
        }
        else {     //如果是部门，获取所有来展示

            //if (pageable!=null&&pageable.getPage()==-1) {
                // List<UserForShow> userForShowList = queuesToDeptMapper.findAllUserByDeptId2(deptId);
                // List<UserForShow> userForShowList = queuesToDeptMapper.findAllUserByDeptId(deptId);
                //查询此部门是不是最底层部门
                Integer count = queuesToDeptMapper.getCountByParentId(deptId);
                List<UserForShow> userForShowList = new ArrayList<>();
                if (count<=0){
                    userForShowList = queuesToDeptMapper.findAllUserByDeptIdAndQueueId( queuesId, deptId,name);
                }else{
                    //如果不是底层部门则需要遍历，获取下面所有的部门数据
                    List<Integer> deptList = new ArrayList<>();
                    //获取部门下面所有的部门数据
                    getAllDeptList(deptList,deptId);
                    if (deptList.size()>0){
                        userForShowList = queuesToDeptMapper.findAllUserByDeptIdAndQueueId2(queuesId, deptList,name);

                    }
                }

                Map<String,Object> map = new LinkedHashMap<>(2);
                map.put("content",userForShowList);
                map.put("totalElements",userForShowList.size());
                return map;
            /*}else{
                PageHelper.startPage(pageable.getPage(),pageable.getSize());
                // List<UserForShow> userForShowList = queuesToDeptMapper.findAllUserByDeptId2(deptId);
                //List<UserForShow> userForShowList = queuesToDeptMapper.findAllUserByDeptId(deptId);
                //查询此部门是不是最底层部门
                Integer count = queuesToDeptMapper.getCountByParentId(deptId);
                List<UserForShow> userForShowList = new ArrayList<>();
                if (count<=0){
                    userForShowList = queuesToDeptMapper.findAllUserByDeptIdAndQueueId( queuesId, deptId);
                }else{
                    //如果不是底层部门则需要遍历，获取下面所有的部门数据
                    List<Integer> deptList = new ArrayList<>();
                    //获取部门下面所有的部门数据
                    getAllDeptList(deptList,deptId);
                    if (deptList.size()>0){
                        userForShowList = queuesToDeptMapper.findAllUserByDeptIdAndQueueId2(queuesId, deptList);

                    }
                }
                PageInfo<UserForShow> pageInfo1 = new PageInfo<>(userForShowList);
                return  PageHelpResultUtil.toPage(pageInfo1);
            }*/
        }


    }

    @Override
    public void addQueuesToDept(QueuesToDeptCriteria1 criteria) {
        //非空校验
        if (ObjectUtil.isEmpty(criteria.getQueuesId())){
            throw new BadRequestException("组id不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getType())){
            throw new BadRequestException("类型不能为空，请填入0，1！");
        }
        QueuesToDept queuesToDept= new QueuesToDept();
        queuesToDept.setQueuesId(criteria.getQueuesId());
        queuesToDept.setSourceId(criteria.getSourceId());
        queuesToDept.setType(criteria.getType());
        queuesToDept.setStatus("1");
        queuesToDept.setCreateDateTime(new Timestamp(new Date().getTime()));
        queuesToDept.setCreateUserId(SecurityUtils.getCurrentUsername());
        queuesToDeptMapper.insert(queuesToDept);

    }

    @Override
    public void deleteQueuesToDept(QueuesToDeptCriteria1 criteria) {
        if (ObjectUtil.isEmpty(criteria.getQueuesId())){
            throw new BadRequestException("组id不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getType())){
            throw new BadRequestException("类型不能为空，请填入0，1！");
        }
        if (criteria.getType()==1){
            queuesToDeptMapper.deleteByQueuesIdAndSourceId(criteria.getQueuesId(),criteria.getSourceId());
        }

    }

    @Override
    @Transactional
    public void batchAddQueuesToDept(QueuesToDeptCriteria criteria) {
      /*List<QueuesToDept>  queuesToDepts=criterias.stream().map(criteria -> {
            //非空校验
            isEmptyTest(criteria);
            QueuesToDept queuesToDept = new QueuesToDept();
            queuesToDept.setQueuesId(criteria.getQueuesId());


            queuesToDept.setSourceId(criteria.getSourceId());
            queuesToDept.setType(1);
            queuesToDept.setStatus("1");
            //queuesToDept.setType(criteria.getType());
            //queuesToDept.setStatus(criteria.getStatus());
            queuesToDept.setCreateDateTime(new Timestamp(new Date().getTime()));
            queuesToDept.setCreateUserId(""+SecurityUtils.getCurrentUserId());
            return queuesToDept;
        }).collect(Collectors.toList());*/
        //非空校验
        isEmptyTest(criteria);


        //前端传过来的数据
        List<QueuesToDept>  queuesToDepts=criteria.getSourceId().stream().map(
                c1->{
                    QueuesToDept queuesToDept = new QueuesToDept();
                    queuesToDept.setQueuesId(criteria.getQueuesId());
                    queuesToDept.setSourceId(c1);
                    queuesToDept.setType(1);
                    queuesToDept.setStatus("1");
                    queuesToDept.setCreateDateTime(new Timestamp(new Date().getTime()));
                    queuesToDept.setCreateUserId(SecurityUtils.getCurrentUsername());
                    return queuesToDept;
                }
        ).collect(Collectors.toList());

        //后台获取的数据
        List<QueuesToDept> findQueuesToDeptByQueuesId = queuesToDeptMapper.findQueuesToDeptByQueuesId(criteria.getQueuesId());
        List<Integer> idList = findQueuesToDeptByQueuesId.stream().map(o->o.getSourceId()).collect(Collectors.toList());
        List<QueuesToDept> queuesToDeptForAdd = queuesToDepts.stream().filter(o->!idList.contains(o.getSourceId())).collect(Collectors.toList());
        //List<QueuesToDept> queuesToDeptForAdd = queuesToDepts.stream().collect(Collectors. collectingAndThen(
          //      Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getSourceId()))), ArrayList::new));

        queuesToDeptMapper.batchInsert(queuesToDeptForAdd);
    }

    @Override
    @Transactional
    public void batchDeleteQueuesToDept(QueuesToDeptCriteria criteria) {
        //非空校验
        isEmptyTest(criteria);
        if (criteria.getType()==1){
            try{
                for (Integer sourceId : criteria.getSourceId()) {
                  if(queuesToDeptMapper.findByQueuesIdAndSourceId(criteria.getQueuesId(),sourceId)>0) {

                      queuesToDeptMapper.deleteByQueuesIdAndSourceId(criteria.getQueuesId(), sourceId);
                  }
                }
            }catch (Exception e){
                throw new BadRequestException("删除失败！");
            }
        }

    }


    @Override
    @Transactional
    public void batchUpdateQueuesToDept(QueuesToDeptCriteria criteria) {
        //非空校验
        isEmptyTest(criteria);
        //删除
        queuesToDeptMapper.deleteByQueue(criteria.getQueuesId());
        //新增
        List<QueuesToDept>  queuesToDepts=criteria.getSourceId().stream().map(c1 -> {

            QueuesToDept queuesToDept = new QueuesToDept();
            queuesToDept.setQueuesId(criteria.getQueuesId());
            queuesToDept.setSourceId(c1);
            queuesToDept.setType(1);
            queuesToDept.setStatus("1");
            queuesToDept.setCreateDateTime(new Timestamp(new Date().getTime()));
            queuesToDept.setCreateUserId(""+SecurityUtils.getCurrentUserId());
            return queuesToDept;
        }).collect(Collectors.toList());
        if (queuesToDepts.size()>0){
            queuesToDeptMapper.batchInsert(queuesToDepts);
        }

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

    /**
     * 非空校验
     * @param criteria
     */
    private void isEmptyTest(QueuesToDeptCriteria criteria){
        if (ObjectUtil.isEmpty(criteria.getQueuesId())){
            throw new BadRequestException("组id不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getType())){
            throw new BadRequestException("类型不能为空，请填入0，1！");
        }

    }

    /**
     * 递归调用
     * @param deptList
     * @param deptId
     */
    public void getAllDeptList(List<Integer> deptList ,Integer deptId){
        Integer count = queuesToDeptMapper.getCountByParentId(deptId);
        if (count<=0){
            System.out.println(deptList);
        }else{
            //获取父类下的id
            List<Integer> tempList = queuesToDeptMapper.getListByParentId(deptId);
            //插入到deptList
            deptList.addAll(tempList);
            //对部门的list进行递归
            for (Integer tempId : tempList) {
                getAllDeptList(deptList,tempId);
            }
        }

    }

}
