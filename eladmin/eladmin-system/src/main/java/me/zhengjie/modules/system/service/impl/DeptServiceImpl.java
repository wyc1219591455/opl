/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.dto.DeptDto;
import me.zhengjie.modules.system.service.dto.DeptQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.UserMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.mapstruct.DeptMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dept")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl implements DeptService {

    private final DeptRepository deptRepository;
    private final DeptMapper deptMapper;
    private final UserRepository userRepository;

    @Override
    @Cacheable
    public List<DeptDto> queryAll(DeptQueryCriteria criteria, Boolean isQuery) throws Exception {
        Sort sort = new Sort(Sort.Direction.ASC, "deptSort");
        if (isQuery) {
            criteria.setPidIsNull(true);
            List<Field> fields = QueryHelp.getAllFields(criteria.getClass(), new ArrayList<>());
            List<String> fieldNames = new ArrayList<String>(){{ add("pidIsNull");add("enabled");}};
            for (Field field : fields) {
                //???????????????????????????????????????private??????????????????
                field.setAccessible(true);
                Object val = field.get(criteria);
                if(fieldNames.contains(field.getName())){
                    continue;
                }
                if (ObjectUtil.isNotNull(val)) {
                    criteria.setPidIsNull(null);
                    break;
                }
            }
        }
        List<DeptDto> deptDtos= deptMapper.toDto(deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),sort));
        return deptDtos;
    }

    @Override
    @Cacheable(key = "#p0")
    public DeptDto findById(Long id) {
        Dept dept = deptRepository.findById(id).orElseGet(Dept::new);
        ValidationUtil.isNull(dept.getId(),"Dept","id",id);
        return deptMapper.toDto(dept);
    }

    @Override
    @Cacheable(key = "#p0")
    public DeptDto findBySourceCode(String sourceCode) {
        Dept dept = deptRepository.findBySourceCode(sourceCode).orElseGet(Dept::new);
        ValidationUtil.isNull(dept.getSourceCode(),"Dept","id",sourceCode);
        return deptMapper.toDto(dept);
    }

    @Override
    @Cacheable
    public List<Dept> findByPid(String  pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    @Cacheable
    public String findSourceCodeById(Long id) {
        return deptRepository.findSourceCodeById(id);
    }

    @Override
    public Set<Dept> findByRoleIds(Long id) {
        return deptRepository.findByRoles_Id(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public DeptDto create(Dept resources) {
        return deptMapper.toDto(deptRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept resources) {
        if(resources.getPid() != null && resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("?????????????????????");
        }
        Dept dept = deptRepository.findById(resources.getId()).orElseGet(Dept::new);
        ValidationUtil.isNull( dept.getId(),"Dept","id",resources.getId());
        resources.setId(dept.getId());
        deptRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<DeptDto> deptDtos) {
        for (DeptDto deptDto : deptDtos) {
            //  ?????????????????????????????????
            int count = userRepository.findByDeptId(deptDto.getId());
            if(count > 0){
                throw new BadRequestException("????????????????????????????????????,????????????");
            }
            //  ????????????????????????????????????
            if(deptRepository.findRoleByDeptId(deptDto.getId()) > 0){
                throw new BadRequestException("????????????????????????????????????????????????????????????");
            }
            deptRepository.deleteById(deptDto.getId());
        }
    }

    @Override
    public void download(List<DeptDto> deptDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeptDto deptDTO : deptDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("????????????", deptDTO.getName());
            map.put("????????????", deptDTO.getEnabled() ? "??????" : "??????");
            map.put("????????????", deptDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Set<DeptDto> getDeleteDepts(List<Dept> menuList, Set<DeptDto> deptDtos) {
        for (Dept dept : menuList) {
            deptDtos.add(deptMapper.toDto(dept));
            List<Dept> depts = deptRepository.findByPid(dept.getSourceCode());
            if(depts!=null && depts.size()!=0){
                getDeleteDepts(depts, deptDtos);
            }
        }
        return deptDtos;
    }

    @Override
    public List<DeptDto> getSuperior(DeptDto deptDto, List<Dept> depts) {
        if(deptDto.getPid() == null){
            depts.addAll(deptRepository.findByPidIsNull());
            return deptMapper.toDto(depts);
        }
        depts.addAll(deptRepository.findByPid(deptDto.getPid()));
        return getSuperior(findBySourceCode(deptDto.getPid()), depts);
    }

    @Override
    public Object buildTree(List<DeptDto> deptDtos) {
        Set<DeptDto> trees = new LinkedHashSet<>();
        Set<DeptDto> depts= new LinkedHashSet<>();
        List<String> deptNames = deptDtos.stream().map(DeptDto::getName).collect(Collectors.toList());
        boolean isChild;
        for (DeptDto deptDTO : deptDtos) {
            isChild = false;
            if (deptDTO.getPid() == null) {
                trees.add(deptDTO);
            }
            for (DeptDto it : deptDtos) {
                if (deptDTO.getSourceCode().equals(it.getPid())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if(isChild) {
                depts.add(deptDTO);
            } else if(!deptNames.contains(deptRepository.findNameBySourceCode(deptDTO.getPid()))) {
                depts.add(deptDTO);
            }
        }

        if (CollectionUtil.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = deptDtos.size();

        Map<String,Object> map = new HashMap<>(2);
        map.put("totalElements",totalElements);
        map.put("content",CollectionUtil.isEmpty(trees)? deptDtos :trees);
        return map;
    }
}
