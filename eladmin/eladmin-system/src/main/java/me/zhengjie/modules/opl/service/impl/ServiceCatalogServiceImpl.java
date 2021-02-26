package me.zhengjie.modules.opl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.*;
import me.zhengjie.modules.opl.mapper.*;
import me.zhengjie.modules.opl.service.ServiceCatalogService;
import me.zhengjie.modules.opl.service.ServiceCatalogToCategoryService;
import me.zhengjie.modules.opl.service.dto.*;
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
 * @description: 服务分类条目
 * @author: yuchao.wang
 * @create: 2021-01-20 16:05
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServiceCatalogServiceImpl implements ServiceCatalogService {


    private final ServiceCatalogMapper serviceCatalogMapper;
    private final SubServiceCatalogMapper subServiceCatalogMapper;
    private final ServiceCatalogToCategoryMapper serviceCatalogToCategoryMapper;
    private final ServiceCatalogToQueuesMapper serviceCatalogToQueuesMapper;
    private final ServiceCatalogRelateDeptMapper serviceCatalogRelateDeptMapper;
    private final DeptForShowMapper deptForShowMapper;
    private final RequestQueuesMapper requestQueuesMapper;


    @Override
    public Map<String, Object> findAllCatalog(Pageable pageable) {
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<ServiceCatalogDto> serviceCatalogDtoList = serviceCatalogMapper.findAllCatalog();
        for(ServiceCatalogDto serviceCatalogDto:serviceCatalogDtoList)
        {
            serviceCatalogDto.setSubServiceCatalogDtoList(subServiceCatalogMapper.findSubCatalogByParentId(serviceCatalogDto.getCatalogId()));
        }
        PageInfo<ServiceCatalogDto> pageInfo = new PageInfo(serviceCatalogDtoList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public Map<String, Object> findParentCatalog(Pageable pageable) {

        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<ServiceCatalogDto> serviceCatalogDtoList = serviceCatalogMapper.findAllCatalog();
        PageInfo<ServiceCatalogDto> pageInfo = new PageInfo(serviceCatalogDtoList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public Map<String, Object> findSubCatalog(Integer parentId,Pageable pageable) {

        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<SubServiceCatalogDto> subServiceCatalogDtos = subServiceCatalogMapper.findSubCatalogByParentId(parentId);
        PageInfo<SubServiceCatalogDto> pageInfo = new PageInfo(subServiceCatalogDtos);
        return PageHelpResultUtil.toPage(pageInfo);
    }



    @Override
    public void insertCatalog(ServiceCatalog serviceCatalog) {

        String createName = SecurityUtils.getCurrentUsername();
        Timestamp createDate = new Timestamp(new Date().getTime());
        serviceCatalog.setCreateDateTime(createDate);
        serviceCatalog.setCreateUserId(createName);
        serviceCatalog.setStatus(1);
        serviceCatalogMapper.insertCatalog(serviceCatalog);

    }
    @Override
    @Transactional
    public void insertSubCatalog(CatalogCriteria catalogCriteria) {

        /**
         * 唯一性校验
         */
        isOnlyTest(catalogCriteria.getSubServiceCatalog() );

        /**
         * 新增服务分类条目
         */
        SubServiceCatalog subServiceCatalog=catalogCriteria.getSubServiceCatalog();
        String createName = SecurityUtils.getCurrentUsername();
        Timestamp createDate = new Timestamp(new Date().getTime());
        subServiceCatalog.setDescription(subServiceCatalog.getDescription());
        subServiceCatalog.setCreateDateTime(createDate);
        subServiceCatalog.setCreateUserId(createName);

        subServiceCatalog.setStatus(1);
        //默认服务台
        subServiceCatalog.setDefaultQueueId(catalogCriteria.getServiceCatalogToQueues());
        //获取服务id
        Integer subServiceCatalogId= subServiceCatalogMapper.insertSubCatalog(subServiceCatalog);

        //获取其中的id
        List<Integer> serviceCatalogToCategoryIntegerList = catalogCriteria.getServiceCatalogToCategoryList();
        //通过id获取数据
        List<ServiceCatalogToCategory> serviceCatalogToCategoryList =new ArrayList<>();
        for(Integer serviceCatalogToCategoryId:serviceCatalogToCategoryIntegerList)
        {
            ServiceCatalogToCategory serviceCatalogToCategory = new ServiceCatalogToCategory();
            serviceCatalogToCategory.setCatalogId(subServiceCatalogId);
            serviceCatalogToCategory.setCategoryId(serviceCatalogToCategoryId);
            serviceCatalogToCategory.setCreateDateTime(createDate);
            serviceCatalogToCategory.setCreateUserId(createName);
            serviceCatalogToCategory.setStatus(1);
            serviceCatalogToCategoryList.add(serviceCatalogToCategory);
        }
        //插入数据
        if (serviceCatalogToCategoryList.size()>0){
            serviceCatalogToCategoryMapper.batchInsert(serviceCatalogToCategoryList);
        }

        /**
         * 插入服务台
         */
        //服务台id
        Integer serviceCatalogToQueuesId = catalogCriteria.getServiceCatalogToQueues();

        ServiceCatalogToQueues serviceCatalogToQueues =new ServiceCatalogToQueues();
        serviceCatalogToQueues.setCatalogId(subServiceCatalogId);
        serviceCatalogToQueues.setQueuesId(serviceCatalogToQueuesId);
        serviceCatalogToQueues.setStatus(1);
        serviceCatalogToQueues.setCreateDateTime(createDate);
        serviceCatalogToQueues.setCreateUserId(SecurityUtils.getCurrentUserId().intValue());
        serviceCatalogToQueuesMapper.insert(serviceCatalogToQueues);

        /**
         * 插入关联部门
         */
        List<Integer> serviceCatalogRelateDeptIntegerList = catalogCriteria.getServiceCatalogRelateDept();
        List<ServiceCatalogRelateDept> serviceCatalogRelateDeptList = new ArrayList<>();
        //List<ServiceCatalogRelateDept> serviceCatalogRelateDeptList=catalogCriteria.getServiceCatalogRelateDept();
        for(Integer serviceCatalogRelateDeptId:serviceCatalogRelateDeptIntegerList)
        {
            ServiceCatalogRelateDept serviceCatalogRelateDept = new ServiceCatalogRelateDept();
            serviceCatalogRelateDept.setCatalogId(subServiceCatalogId);
            serviceCatalogRelateDept.setDeptId(serviceCatalogRelateDeptId);
            serviceCatalogRelateDept.setStatus(1);
            serviceCatalogRelateDept.setCreateDateTime(createDate);
            serviceCatalogRelateDept.setCreateUserId(createName);
            serviceCatalogRelateDeptList.add(serviceCatalogRelateDept);
        }
        if (serviceCatalogRelateDeptList.size()>0){
            serviceCatalogRelateDeptMapper.batchInsert(serviceCatalogRelateDeptList);
        }

    }

    @Override
    @Transactional
    public void updateSubCatalog2(CatalogCriteria catalogCriteria) {
        /**
         * 唯一性校验
         */
        isOnlyTest( catalogCriteria.getSubServiceCatalog() );

        /**
         * 修改服务分类条目表数据
         */
        SubServiceCatalog subServiceCatalog=catalogCriteria.getSubServiceCatalog();
        String modifyName = SecurityUtils.getCurrentUsername();
        Timestamp modifyDate = new Timestamp(new Date().getTime());
        subServiceCatalog.setModifyDateTime(modifyDate);
        subServiceCatalog.setModifyUserId(modifyName);
        //设置默认服务台
        subServiceCatalog.setDefaultQueueId(catalogCriteria.getServiceCatalogToQueues());

        //修改服务分类条目
        subServiceCatalogMapper.updateSubCatalog(subServiceCatalog);

        /**
         * 修改服务分类条目中的工单分类
         */
        //先删除关联部门的数据
        serviceCatalogToCategoryMapper.deleteByCatalogId(subServiceCatalog.getCatalogId());

        //然后新增

        //获取其中的id
        List<Integer> serviceCatalogToCategoryIntegerList = catalogCriteria.getServiceCatalogToCategoryList();
        //通过id获取数据
        List<ServiceCatalogToCategory> serviceCatalogToCategoryList =new ArrayList<>();
        //插入数据
        if (serviceCatalogToCategoryIntegerList.size()>0){
        for(Integer serviceCatalogToCategoryId:serviceCatalogToCategoryIntegerList)
        {
            ServiceCatalogToCategory serviceCatalogToCategory = new ServiceCatalogToCategory();
            serviceCatalogToCategory.setCatalogId(catalogCriteria.getSubServiceCatalog().getCatalogId());
            serviceCatalogToCategory.setCategoryId(serviceCatalogToCategoryId);
            serviceCatalogToCategory.setCreateDateTime(new Timestamp(new Date().getTime()));
            serviceCatalogToCategory.setCreateUserId(modifyName);
            serviceCatalogToCategory.setStatus(1);
            serviceCatalogToCategoryList.add(serviceCatalogToCategory);
        }

            serviceCatalogToCategoryMapper.batchInsert(serviceCatalogToCategoryList);
        }

        /**
         * 插入服务台
         */
        //先删除
        serviceCatalogToQueuesMapper.delByCatalogId(subServiceCatalog.getCatalogId());

        //服务台id
        Integer serviceCatalogToQueuesId = catalogCriteria.getServiceCatalogToQueues();

        ServiceCatalogToQueues serviceCatalogToQueues =new ServiceCatalogToQueues();
        serviceCatalogToQueues.setCatalogId(subServiceCatalog.getCatalogId());
        serviceCatalogToQueues.setQueuesId(serviceCatalogToQueuesId);
        serviceCatalogToQueues.setStatus(1);
        serviceCatalogToQueues.setCreateDateTime(new Timestamp(new Date().getTime()));
        serviceCatalogToQueues.setCreateUserId(SecurityUtils.getCurrentUserId().intValue());
        serviceCatalogToQueuesMapper.insert(serviceCatalogToQueues);

        /**
         * 插入关联部门
         */
        //先删除
        serviceCatalogRelateDeptMapper.deleteByCatalogId(subServiceCatalog.getCatalogId());

        //再插入
        List<Integer> serviceCatalogRelateDeptIntegerList = catalogCriteria.getServiceCatalogRelateDept();
        List<ServiceCatalogRelateDept> serviceCatalogRelateDeptList = new ArrayList<>();
        //List<ServiceCatalogRelateDept> serviceCatalogRelateDeptList=catalogCriteria.getServiceCatalogRelateDept();
        for(Integer serviceCatalogRelateDeptId:serviceCatalogRelateDeptIntegerList)
        {
            ServiceCatalogRelateDept serviceCatalogRelateDept = new ServiceCatalogRelateDept();
            serviceCatalogRelateDept.setCatalogId(subServiceCatalog.getCatalogId());
            serviceCatalogRelateDept.setDeptId(serviceCatalogRelateDeptId);
            serviceCatalogRelateDept.setStatus(1);
            serviceCatalogRelateDept.setCreateDateTime(new Timestamp(new Date().getTime()));
            serviceCatalogRelateDept.setCreateUserId(SecurityUtils.getCurrentUsername());
            serviceCatalogRelateDeptList.add(serviceCatalogRelateDept);
        }
        if (serviceCatalogRelateDeptList.size()>0){
            serviceCatalogRelateDeptMapper.batchInsert(serviceCatalogRelateDeptList);
        }

    }

//    @Override
//    public void insertSubCatalog(SubServiceCatalog subServiceCatalog) {
//
//        String createName = SecurityUtils.getCurrentUsername();
//        Timestamp createDate = new Timestamp(new Date().getTime());
//        subServiceCatalog.setCreateDateTime(createDate);
//        subServiceCatalog.setCreateUserId(createName);
//        subServiceCatalog.setStatus(1);
//        subServiceCatalogMapper.insertSubCatalog(subServiceCatalog);
//
//    }

    @Override
    public void updateParentCatalog(ServiceCatalog serviceCatalog) {

        String modifyName = SecurityUtils.getCurrentUsername();
        Timestamp modifyDate = new Timestamp(new Date().getTime());
        serviceCatalog.setModifyDateTime(modifyDate);
        serviceCatalog.setModifyUserId(modifyName);
        deleteOk(serviceCatalog.getCatalogId());
        serviceCatalogMapper.updateCatalog(serviceCatalog);

    }

    @Override
    public void updateSubCatalog(SubServiceCatalog subServiceCatalog) {

        String modifyName = SecurityUtils.getCurrentUsername();
        Timestamp modifyDate = new Timestamp(new Date().getTime());
        subServiceCatalog.setModifyDateTime(modifyDate);
        subServiceCatalog.setModifyUserId(modifyName);
        subDeleteOk(subServiceCatalog.getCatalogId());
        subServiceCatalogMapper.updateSubCatalog(subServiceCatalog);

    }

    @Override
    public void deleteSubCatalog(Integer subCatalogId) {
        //判断是否可以删除
        subDeleteOk(subCatalogId);

        subServiceCatalogMapper.deleteSubCatalogById(subCatalogId);

    }

    @Override
    public Map<String,Object> findSubCatalogById(Integer catalogId) {
        Map<String, Object> map = new LinkedHashMap<>(2);
        SubCatalogVo subCatalogVo = new SubCatalogVo();
        /**
         *展示类添加到子分类中
         */
        List<SubServiceCatalog> subServiceCatalogList =subServiceCatalogMapper.findSubCatalogById(catalogId);
        SubServiceCatalog subServiceCatalog = subServiceCatalogList.get(0);
        subCatalogVo.setSubServiceCatalog(subServiceCatalog);

        /**
         * 服务分类条目中的工单分类
         */
        //左边
        List<TrequestCategory> serviceCatalogToCategoryLeftList = serviceCatalogToCategoryMapper.findCategoryByCatalogIdNotSelect(catalogId);
        //右边
        List<TrequestCategory> serviceCatalogToCategoryRightList = serviceCatalogToCategoryMapper.findCategoryByCatalogId(catalogId);
        //subCatalogVo.setCategoryLeftList(serviceCatalogToCategoryLeftList);
        List<Integer> serviceCatalogToCategoryRightIntegerList = serviceCatalogToCategoryRightList.stream().map(e->e.getId()).collect(Collectors.toList());
        subCatalogVo.setCategoryRightList(serviceCatalogToCategoryRightIntegerList);
        //subCatalogVo.setCategoryRightList(serviceCatalogToCategoryRightList);

        /**
         * 关联部门
         */
        List<DeptVo> deptForShowList = deptForShowMapper.findDeptVoInCatalogId(catalogId);
        //List<Integer> deptForShowIntegerList = deptForShowList.stream().map(e->e.getDeptId()).collect(Collectors.toList());
        subCatalogVo.setServiceCatalogRelateDept(deptForShowList);
        //subCatalogVo.setServiceCatalogRelateDept(deptForShowIntegerList);

        /**
         * 默认服务台
         */
        RequestQueues requestQueues = requestQueuesMapper.findQueuesById(subServiceCatalog.getDefaultQueueId());
        subCatalogVo.setRequestQueues(requestQueues);
        map.put("content", subCatalogVo);
        map.put("totalElements", 1);
        return map;
    }

    /**
     * 服务分类主表检验是否还有在使用的字表
     *
     * @param catalogId
     * @return void
     */
    private void deleteOk(Integer catalogId) {

        // 若查询结果非空，即数据库中存在使用中的此控制器编号
        if (serviceCatalogMapper.findOnUsedSub(catalogId) != 0) {
            throw new BadRequestException("该服务分类条目下还存在正在使用的子条目");
        }
    }

    /**
     * 服务分类子表检验是否还有在使用的工单
     *
     * @param catalogId
     * @return void
     */
    private void subDeleteOk(Integer catalogId) {

        // 若查询结果非空，即数据库中存在使用中的此控制器编号
        if (subServiceCatalogMapper.findOnUsedOrder(catalogId) != 0) {
            throw new BadRequestException("该服务分类下存在未关闭的工单");
        }
    }

    /**
     * 唯一性校验
     * @param subServiceCatalog
     */
    private void isOnlyTest( SubServiceCatalog subServiceCatalog ){
        Integer count = serviceCatalogToQueuesMapper.getCountByCatalogName(subServiceCatalog);
        if (count>0){
            throw new BadRequestException("该服务分类已存在，请勿重复！");
        }
    }

}
