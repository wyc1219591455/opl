package me.zhengjie.modules.opl.service.impl;


import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.QueuesToDept;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.mapper.ServiceCatalogToDeptMapper;
import me.zhengjie.modules.opl.service.ServiceCatalogToDeptService;
import me.zhengjie.modules.opl.service.dto.*;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-13 19:56
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServiceCatalogToDeptServiceImpl implements ServiceCatalogToDeptService {

    private final ServiceCatalogToDeptMapper serviceCatalogToDeptMapper;

    @Override
    public List<SubServiceCatalogDto> findSubCatalogById(String userId) {
       return  serviceCatalogToDeptMapper.findAssociationById(userId);
    }

    @Override
    public List<ServiceCatalogDto> findParentCatalogById(String userId) {
        return  serviceCatalogToDeptMapper.findParentAssociationById(userId);
    }


    @Override
    public List<ServiceCatalogDto> findAllCatalogById() {
        String jobNumber = SecurityUtils.getCurrentUsername();
        List<ServiceCatalogDto> serviceCatalogDtoList= serviceCatalogToDeptMapper.findParentAssociationById(jobNumber);
        List<SubServiceCatalogDto> subServiceCatalogs=serviceCatalogToDeptMapper.findAssociationById(jobNumber);
        if(ObjectUtil.isNotEmpty(serviceCatalogDtoList))
        {
            for(ServiceCatalogDto serviceCatalogDto:serviceCatalogDtoList)
            {
                List<Integer> catalogList=new ArrayList<>();
                catalogList.add(serviceCatalogDto.getCatalogId());
                List<SubServiceCatalogDto> serviceList = subServiceCatalogs.stream().filter(catalogDto -> catalogList.contains(catalogDto.getParentId())).collect(Collectors.toList());
                serviceCatalogDto.setSubServiceCatalogDtoList(serviceList);
            }
        }
        return serviceCatalogDtoList;
    }

    @Override
    public void insertCatalogToDeptAssociation(ServiceCatalogToDept serviceCatalogToDept) {

        serviceCatalogToDeptMapper.insertCatalogToDeptAssociation(serviceCatalogToDept);

    }

    @Override
    public void addCatalogToDept(CatalogToDeptCriteria1 criteria) {
        //非空校验
        if (ObjectUtil.isEmpty(criteria.getCatalogId())){
            throw new BadRequestException("组id不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getType())){
            throw new BadRequestException("类型不能为空，请填入0，1！");
        }
        ServiceCatalogToDept serviceCatalogToDept = new ServiceCatalogToDept();
        serviceCatalogToDept.setCatalogId(criteria.getCatalogId());
        serviceCatalogToDept.setSourceId(criteria.getSourceId());
        serviceCatalogToDept.setType(criteria.getType());
        serviceCatalogToDept.setStatus(1);
        serviceCatalogToDept.setCreateDateTime(new Timestamp(new Date().getTime()));
        serviceCatalogToDept.setCreateUserId(SecurityUtils.getCurrentUsername());
        serviceCatalogToDeptMapper.insertCatalogToDeptAssociation(serviceCatalogToDept);
    }

    @Override
    public void deleteCatalogToDept(CatalogToDeptCriteria1 criteria) {
        //非空校验
        if (ObjectUtil.isEmpty(criteria.getCatalogId())){
            throw new BadRequestException("服务分类id不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getType())){
            throw new BadRequestException("类型不能为空，请填入0，1！");
        }
        if (criteria.getType()==1){
            serviceCatalogToDeptMapper.deleteByCatalogIdAndSourceId(criteria.getCatalogId(),criteria.getSourceId());
        }
    }

    @Override
    public void batchAddCatalogToDept(CatalogToDeptCriteria criteria) {
        //非空校验
        isEmptyTest(criteria);
        //前端传过来的数据
        List<ServiceCatalogToDept> serviceCatalogToDeptList = criteria.getSourceId().stream().map(
                c1->{
                    ServiceCatalogToDept serviceCatalogToDept = new ServiceCatalogToDept();
                    serviceCatalogToDept.setCatalogId(criteria.getCatalogId());
                    serviceCatalogToDept.setSourceId(c1);
                    serviceCatalogToDept.setType(1);
                    serviceCatalogToDept.setStatus(1);
                    serviceCatalogToDept.setCreateDateTime(new Timestamp(new Date().getTime()));
                    serviceCatalogToDept.setCreateUserId(SecurityUtils.getCurrentUsername());
                    return serviceCatalogToDept;
                }
        ).collect(Collectors.toList());

        //后台获取的数据
        List<ServiceCatalogToDept> findServiceCatalogToDept = serviceCatalogToDeptMapper.findServiceCatalogToDeptByCatalogId(criteria.getCatalogId());

        //去重后数据
        List<Integer> idList = findServiceCatalogToDept.stream().map(o->o.getSourceId()).collect(Collectors.toList());
        List<ServiceCatalogToDept> serviceCatalogToDeptForAdd = serviceCatalogToDeptList.stream().filter(o->idList.contains(o.getSourceId())).collect(Collectors.toList());
       /* List<ServiceCatalogToDept> serviceCatalogToDeptForAdd = serviceCatalogToDeptList.stream().collect(Collectors. collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getSourceId()))), ArrayList::new));
            */
        serviceCatalogToDeptMapper.batchInsert(serviceCatalogToDeptForAdd);
    }

    @Override
    public void batchDeleteCatalogToDept(CatalogToDeptCriteria criteria) {
        //非空校验
        isEmptyTest(criteria);
        if (criteria.getType()==1){
            try {
                for (Integer sourceId : criteria.getSourceId()){
                   if (serviceCatalogToDeptMapper.findByCatalogIdAndSourceId(criteria.getCatalogId(),sourceId)>0){
                       serviceCatalogToDeptMapper.deleteByCatalogIdAndSourceId(criteria.getCatalogId(),sourceId);
                   }
                }
            }catch (Exception e){
                throw new BadRequestException("删除失败！");
            }
        }

    }

    @Override
    public Map<String,Object> findAllUserByDeptId(Pageable pageable, Integer level, Integer catalogId, Integer deptId, String name) {

        if (level == 0) {    //如果是最上层公司，那么就展示已勾选的
            //List<UserForShow> userForShowList = serviceCatalogToDeptMapper.
            List<UserForShow> userForShowList = serviceCatalogToDeptMapper.findAllUserInUse(catalogId,name);
            Map<String,Object> map = new LinkedHashMap<>(2);
            map.put("content",userForShowList);
            map.put("totalElements",userForShowList.size());
            return map;
        } else if(level == 1) { //如果是二级公司，及公司与子公司，则全部展示
            //查询此公司下面有没有部门
            Integer count = serviceCatalogToDeptMapper.getCountByOrgId(deptId);
            List<UserForShow> userForShowList = new ArrayList<>();
            if (count<=0){
                return null;
            }else{
                //获取公司下的部门
                List<Integer> tempList = serviceCatalogToDeptMapper.getListByOrgId(deptId);
                //如果不是底层部门则需要遍历，获取下面所有的部门数据
                List<Integer> deptList = new ArrayList<>();
                //获取部门下面所有的部门数据
                for (Integer integer : tempList) {
                    getAllDeptList(deptList,integer);
                }
                /**
                 * 今天就写到这
                 */
                if (deptList.size()>0){
                    userForShowList = serviceCatalogToDeptMapper.findAllUserByDeptIdListAndCatalogId(catalogId, deptList,name);

                }
            }

            Map<String,Object> map = new LinkedHashMap<>(2);
            map.put("content",userForShowList);
            map.put("totalElements",userForShowList.size());
            return map;

        } else {        //如果是部门，获取所有来展示
            //查询此部门是不是最底层部门
            Integer count = serviceCatalogToDeptMapper.getCountByParentId(deptId);
            List<UserForShow> userForShowList = new ArrayList<>();
            if (count<=0){
                userForShowList = serviceCatalogToDeptMapper.findAllUserByDeptIdAndCatalogId(catalogId,deptId,name);
            }else{
                //如果不是底层部门则需要遍历，获取下面所有的部门数据
                List<Integer> deptList = new ArrayList<>();
                //获取部门下面所有的部门数据
                getAllDeptList(deptList,deptId);
                if (deptList.size()>0){
                    userForShowList =  serviceCatalogToDeptMapper.findAllUserByDeptIdListAndCatalogId(catalogId, deptList,name);

                }
            }

            Map<String,Object> map = new LinkedHashMap<>(2);
            map.put("content",userForShowList);
            map.put("totalElements",userForShowList.size());
            return map;

        }

    }

    /**
     * 非空校验
     * @param criteria
     */
    private void isEmptyTest(CatalogToDeptCriteria criteria){
        if (ObjectUtil.isEmpty(criteria.getCatalogId())){
            throw new BadRequestException("服务分类不能为空！");
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
        Integer count = serviceCatalogToDeptMapper.getCountByParentId(deptId);
        if (count<=0){
            System.out.println(deptList);
        }else{
            //获取父类下的id
            List<Integer> tempList = serviceCatalogToDeptMapper.getListByParentId(deptId);
            //插入到deptList
            deptList.addAll(tempList);
            //对部门的list进行递归
            for (Integer tempId : tempList) {
                getAllDeptList(deptList,tempId);
            }
        }

    }
}
