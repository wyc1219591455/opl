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
package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.service.DictDetailService;
import me.zhengjie.modules.system.service.dto.DictDetailDto;
import me.zhengjie.modules.system.service.dto.DictDetailQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：字典详情管理")
@RequestMapping("/api/dictDetail")
public class DictDetailController {

    private final DictDetailService dictDetailService;
    private static final String ENTITY_NAME = "dictDetail";

    /*@Log("查询字典详情")
    @ApiOperation("查询字典详情")
    @GetMapping
    public ResponseEntity<Object> query(DictDetailQueryCriteria criteria,
                                         @PageableDefault(sort = {"dictSort"}, direction = Sort.Direction.ASC) Pageable pageable){
        return new ResponseEntity<>(dictDetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }*/

    @Log("查询字典详情")
    @ApiOperation("查询字典详情")
    @GetMapping
    public Object query(DictDetailQueryCriteria criteria,
                                        @PageableDefault(sort = {"dictSort"}, direction = Sort.Direction.ASC) Pageable pageable){
        return dictDetailService.queryAll(criteria,pageable);
    }

    @Log("查询多个字典详情")
    @ApiOperation("查询多个字典详情")
    @GetMapping(value = "/map")
    public ResponseEntity<Object> getDictDetailMaps(DictDetailQueryCriteria criteria,
                                         @PageableDefault(sort = {"dictSort"}, direction = Sort.Direction.ASC) Pageable pageable){
        String[] names = criteria.getDictName().split(",");
        Map<String,Object> map = new HashMap<>(names.length);
        for (String name : names) {
            criteria.setDictName(name);
            map.put(name,dictDetailService.queryAll(criteria,pageable).get("content"));
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    /*@Log("新增字典详情")
    @ApiOperation("新增字典详情")
    @PostMapping
    @PreAuthorize("@el.check('dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody DictDetail resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity<>(dictDetailService.create(resources),HttpStatus.CREATED);
    }*/

    @Log("新增字典详情")
    @ApiOperation("新增字典详情")
    @PostMapping
    @PreAuthorize("@el.check('dict:add')")
    public DictDetailDto create(@Validated @RequestBody DictDetail resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return dictDetailService.create(resources);
    }

   /* @Log("修改字典详情")
    @ApiOperation("修改字典详情")
    @PutMapping
    @PreAuthorize("@el.check('dict:edit')")
    public ResponseEntity<Object> update(@Validated(DictDetail.Update.class) @RequestBody DictDetail resources){
        dictDetailService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @Log("修改字典详情")
    @ApiOperation("修改字典详情")
    @PutMapping
    @PreAuthorize("@el.check('dict:edit')")
    public void update(@Validated(DictDetail.Update.class) @RequestBody DictDetail resources){
        dictDetailService.update(resources);
    }

    /*@Log("删除字典详情")
    @ApiOperation("删除字典详情")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('dict:del')")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        dictDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @Log("删除字典详情")
    @ApiOperation("删除字典详情")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('dict:del')")
    public void delete(@PathVariable Long id){
        dictDetailService.delete(id);
    }
}