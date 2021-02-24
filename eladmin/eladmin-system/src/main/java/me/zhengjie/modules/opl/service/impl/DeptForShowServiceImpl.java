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
        List<OrgAs> orgGrade = orgAsMapper.findGradeOrg();
        List<OrgAs> orgAsList = orgAsMapper.findAllChildrenOrg();
        if (orgGrade.size()>0) {
            for (OrgAs orgAs : orgGrade) {
                String orgId = orgAs.getOrgId().toString();
                OrgTreeDto treeDto = new OrgTreeDto();
                treeDto.setId(orgId);
                treeDto.setText(orgAs.getShortName());
                treeDto.setValue(orgId);
                treeDto.setParentId(0L);
                /**
                 * 暂无
                 */
                treeDto.setChildren(getOrgChildNodes());
                treeDto.setLevel(1L);
                treeList.add(treeDto);
            }
        }
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",treeList);
        return map;
    }

    //获取子公司
    private  List<OrgTreeDto> getOrgChildNodes(){

        List<OrgTreeDto> treeList = new ArrayList<>();
        //获取子部门
        List<OrgAs> orgChildren = orgAsMapper.findAllChildrenOrg();
        //获取部门表里的子部门
        List<DeptForShow> deptChildren2 = deptForShowMapper.findDeptByGradeOrg();
        if (orgChildren.size()>0) {
            for (OrgAs orgChild : orgChildren) {
                OrgTreeDto treeDto = new OrgTreeDto();
                treeDto.setId(orgChild.getOrgId().toString());
                treeDto.setText(orgChild.getShortName());
                treeDto.setValue(orgChild.getEnCode());
                treeDto.setParentId(1L);
                treeDto.setHasChildren(getDeptChildNodesByParentId(orgChild.getOrgSourceId()).size()>0?true:false);
                treeDto.setChildren(getDeptChildNodesByParentId(orgChild.getOrgSourceId()));
                //treeDto.setHasChildren(getDeptChildNodes(orgChild.getEnCode()).size()>0?true:false);
                //treeDto.setChildren(getDeptChildNodes(orgChild.getEnCode()));
                treeDto.setLevel(1L);
                treeList.add(treeDto);
            }
        }

        if (deptChildren2.size()>0){
            for (DeptForShow deptForShow : deptChildren2) {
                OrgTreeDto treeDto = new OrgTreeDto();
                treeDto.setId(""+deptForShow.getDeptId());
                treeDto.setText(deptForShow.getDeptName());
                treeDto.setValue(deptForShow.getDeptId().toString());
                treeDto.setParentId(1L);
                treeDto.setHasChildren(getDeptChildNodesByParentId(deptForShow.getSourceCode()).size()>0?true:false);
                treeDto.setChildren(getDeptChildNodesByParentId(deptForShow.getSourceCode()));
                treeDto.setLevel(1L);
                treeList.add(treeDto);
            }
        }

        return treeList;

    }

    //获取子部门2
    private List<OrgTreeDto> getDeptChildNodesByParentId(String parentId){
        List<OrgTreeDto> treeList = new ArrayList<>();
        //找到公司下面对应的部门
        List<DeptForShow> deptForShowList = deptForShowMapper.findDeptByParentId(parentId);
        if (deptForShowList.size()>0) {
            for (DeptForShow deptForShow : deptForShowList) {
                OrgTreeDto treeDto = new OrgTreeDto();
                /**
                 * 写
                 */
                String itemDeptId = deptForShow.getDeptId().toString();
                treeDto.setId(itemDeptId);
                treeDto.setValue(deptForShow.getDeptId().toString());
                treeDto.setText(deptForShow.getDeptName());
                treeDto.setChildren(getDeptChildNodesByParentId(deptForShow.getSourceCode()));
                //treeDto.setChildren(null);
                treeDto.setLevel(2L);
                treeDto.setHasChildren(false);
                treeList.add(treeDto);
            }
        }
        return treeList;
    }


    //获取子部门
    private List<OrgTreeDto> getDeptChildNodes(String code){
        List<OrgTreeDto> treeList = new ArrayList<>();
        //找到公司下面对应的部门
        List<DeptForShow> deptForShowList = deptForShowMapper.findDeptByOrgId(code);
        if (deptForShowList.size()>0) {
            for (DeptForShow deptForShow : deptForShowList) {
                OrgTreeDto treeDto = new OrgTreeDto();
                /**
                 * 写
                 */
                String itemDeptId = deptForShow.getDeptId().toString();
                treeDto.setId(itemDeptId);
                treeDto.setValue(deptForShow.getDeptId().toString());
                treeDto.setText(deptForShow.getDeptName());
                treeDto.setChildren(null);
                treeDto.setLevel(2L);
                treeDto.setHasChildren(false);
                treeList.add(treeDto);
            }
        }
        return treeList;
    }


}
