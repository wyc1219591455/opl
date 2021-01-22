package me.zhengjie.modules.opl.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.DeptForShow;
import me.zhengjie.modules.opl.domain.OrgAs;
import me.zhengjie.modules.opl.mapper.DeptForShowMapper;
import me.zhengjie.modules.opl.mapper.OrgAsMapper;
import me.zhengjie.modules.opl.service.DeptForShowService;
import me.zhengjie.modules.opl.service.dto.OrgTreeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 部门业务逻辑实现
 * @author: ming.cao
 * @create: 2021-01-21 11:36
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptForShowServiceImpl implements DeptForShowService {
    private final OrgAsMapper orgAsMapper;

    private final DeptForShowMapper deptForShowMapper;

    @Override
    public Map<String, Object> getOrgData() {
        List<OrgTreeDto> treeList = new ArrayList<OrgTreeDto>();
        //获取公司数据
        List<OrgAs> orgAsList = orgAsMapper.findAllOrg();

        for (OrgAs orgAs : orgAsList) {
            String orgId = orgAs.getOrgId().toString();
            OrgTreeDto treeDto = new OrgTreeDto();
            treeDto.setId(orgId);
            treeDto.setText(orgAs.getShortName());
            treeDto.setValue(orgId);
            treeDto.setParentId(0L);
            /**
             * 暂无
             */
            treeDto.setChildren(getOrgChildNodes(orgId,orgAsList));
            treeDto.setLevel(1L);
        }
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",treeList);
        return map;
    }


    private List<OrgTreeDto> getOrgChildNodes(String orgId,List<OrgAs> orgAsList){
        List<OrgTreeDto> treeList = new ArrayList<>();
        //获取公司下对应的部门信息
        List<DeptForShow> deptForShowList = deptForShowMapper.findDeptByOrgId(orgId);
        for (DeptForShow deptForShow : deptForShowList) {
            //部门id
            String itemDeptId = deptForShow.getDeptId().toString();
            String value = deptForShow.getDeptId().toString();
            //设置部门树结构
            OrgTreeDto treeDto = new OrgTreeDto();
            treeDto.setId(itemDeptId);
            treeDto.setText(deptForShow.getDeptName());
            treeDto.setValue(itemDeptId);
            treeDto.setHasChildren(false);
            treeDto.setLevel(2L);
            //部门下的子部门
            treeList.add(treeDto);
        }
        return treeList;
    }

}
