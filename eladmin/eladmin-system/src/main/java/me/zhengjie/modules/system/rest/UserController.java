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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.DataService;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.vo.UserPassVo;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.modules.system.service.VerifyService;
import me.zhengjie.utils.*;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.utils.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "?????????????????????")
@RestController
@RequestMapping(value = "/api/users" , produces = "application/json;charset=utf8")
@RequiredArgsConstructor
public class UserController {

    @Value("${rsa.private_key}")
    private String privateKey;
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    private final UserService userService;
    private final DataService dataService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final VerifyService verificationCodeService;

    @Log("??????????????????")
    @ApiOperation("??????????????????")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('user:list')")
    public void download(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        userService.download(userService.queryAll(criteria), response);
    }

   /* @Log("????????????")
    @ApiOperation("????????????")
    @GetMapping
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> query(UserQueryCriteria criteria, Pageable pageable){
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            criteria.getDeptIds().add(criteria.getDeptId());
            criteria.getDeptIds().addAll(dataService.getDeptChildren(deptService.findByPid(criteria.getDeptId())));
        }
        // ????????????
        List<Long> dataScopes = dataService.getDeptIds(userService.findById(SecurityUtils.getCurrentUserId()));
        // criteria.getDeptIds() ????????????????????????????????????????????????
        if (!CollectionUtils.isEmpty(criteria.getDeptIds()) && !CollectionUtils.isEmpty(dataScopes)){
            // ?????????
            criteria.getDeptIds().retainAll(dataScopes);
            if(!CollectionUtil.isEmpty(criteria.getDeptIds())){
                return new ResponseEntity<>(userService.queryAll(criteria,pageable),HttpStatus.OK);
            }
        } else {
            // ???????????????
            criteria.getDeptIds().addAll(dataScopes);
            return new ResponseEntity<>(userService.queryAll(criteria,pageable),HttpStatus.OK);
        }
        return new ResponseEntity<>(PageUtil.toPage(null,0),HttpStatus.OK);
    }*/

    @Log("????????????")
    @ApiOperation("????????????")
    @GetMapping
    @PreAuthorize("@el.check('user:list')")
    public Object query(UserQueryCriteria criteria, Pageable pageable){
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            criteria.getDeptIds().add(criteria.getDeptId());

            String sourceCode=deptService.findSourceCodeById(criteria.getDeptId());
            criteria.getDeptIds().addAll(dataService.getDeptChildren(deptService.findByPid(sourceCode)));
        }
        // ????????????
        List<Long> dataScopes = dataService.getDeptIds(userService.findByName(SecurityUtils.getCurrentUsername()));
        // criteria.getDeptIds() ????????????????????????????????????????????????
        if (!CollectionUtils.isEmpty(criteria.getDeptIds()) && !CollectionUtils.isEmpty(dataScopes)){
            // ?????????
            criteria.getDeptIds().retainAll(dataScopes);
            if(!CollectionUtil.isEmpty(criteria.getDeptIds())){
                return userService.queryAll(criteria,pageable);
            }
        } else {
            // ???????????????
            criteria.getDeptIds().addAll(dataScopes);
            return userService.queryAll(criteria,pageable);
        }
        return PageUtil.toPage(null,0);
    }

    /*@Log("????????????")
    @ApiOperation("????????????")
    @PostMapping
    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody User resources){
        checkLevel(resources);
        // ???????????? 123456
        resources.setPassword(passwordEncoder.encode("123456"));
        return new ResponseEntity<>(userService.create(resources),HttpStatus.CREATED);
    }*/

    @Log("????????????")
    @ApiOperation("????????????")
    @PostMapping
    @PreAuthorize("@el.check('user:add')")
    public void create( @Validated @RequestBody User resources){
        checkLevel(resources);
        // ???????????? 123456
        resources.setPassword(passwordEncoder.encode("123456"));
        userService.create(resources);
    }

   /* @Log("????????????")
    @ApiOperation("????????????")
    @PutMapping
    @PreAuthorize("@el.check('user:edit')")
    public ResponseEntity<Object> update(@Validated(User.Update.class) @RequestBody User resources){
        checkLevel(resources);
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @Log("????????????")
    @ApiOperation("????????????")
    @PutMapping
    @PreAuthorize("@el.check('user:edit')")
    public Object update(@Validated(User.Update.class) @RequestBody User resources){
        checkLevel(resources);
        return userService.update(resources);
    }

   /* @Log("???????????????????????????")
    @ApiOperation("???????????????????????????")
    @PutMapping(value = "center")
    public ResponseEntity<Object> center(@Validated(User.Update.class) @RequestBody User resources){
        if(!resources.getId().equals(SecurityUtils.getCurrentUserId())){
            throw new BadRequestException("????????????????????????");
        }
        userService.updateCenter(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
*/
   /* @Log("????????????")
    @ApiOperation("????????????")
    @DeleteMapping
    @PreAuthorize("@el.check('user:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        for (Long id : ids) {
            Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            Integer optLevel =  Collections.min(roleService.findByUsersId(id).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException("????????????????????????????????????" + userService.findById(id).getUsername());
            }
        }
        userService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @Log("????????????")
    @ApiOperation("????????????")
    @DeleteMapping
    @PreAuthorize("@el.check('user:del')")
    public void delete(@RequestBody Set<Long> ids){
        for (Long id : ids) {
            Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            Integer optLevel =  Collections.min(roleService.findByUsersId(id).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException("????????????????????????????????????" + userService.findById(id).getUsername());
            }
            if(userService.findById(id).getIsAdmin()){
                throw new BadRequestException("admin?????????????????????");
            }
        }
        userService.delete(ids);
    }

    @ApiOperation("????????????")
    @PostMapping(value = "/updatePass")
    public void updatePass(@RequestBody UserPassVo passVo){
   // public ResponseEntity<Object> updatePass(@RequestBody UserPassVo passVo){
        // ????????????
        RSA rsa = new RSA(privateKey, null);
        String oldPass = new String(rsa.decrypt(passVo.getOldPass(), KeyType.PrivateKey));
        String newPass = new String(rsa.decrypt(passVo.getNewPass(), KeyType.PrivateKey));
        UserDto user = userService.findByName(SecurityUtils.getCurrentUsername());
        if(!passwordEncoder.matches(oldPass, user.getPassword())){
            throw new BadRequestException("??????????????????????????????");
        }
        if(passwordEncoder.matches(newPass, user.getPassword())){
            throw new BadRequestException("?????????????????????????????????");
        }
        userService.updatePass(user.getUsername(),passwordEncoder.encode(newPass));
        //return new ResponseEntity<>(HttpStatus.OK);
    }

//    @ApiOperation("????????????")
//    @PostMapping(value = "/updateAvatar")
//    public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile file){
//       userService.updateAvatar(file);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @ApiOperation("????????????")
    @PostMapping(value = "/updateAvatar" , produces = "application/json;charset=utf8")
    public Map<String,String> updateAvatar(@RequestParam MultipartFile file){
        return userService.updateAvatar(file);
    }

    @Log("????????????")
    @ApiOperation("????????????")
    @PostMapping(value = "/updateEmail/{code}")
    public ResponseEntity<Object> updateEmail(@PathVariable String code,@RequestBody User user){
        // ????????????
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(user.getPassword(), KeyType.PrivateKey));
        UserDto userDto = userService.findByName(SecurityUtils.getCurrentUsername());
        if(!passwordEncoder.matches(password, userDto.getPassword())){
            throw new BadRequestException("????????????");
        }
        verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + user.getEmail(), code);
        userService.updateEmail(userDto.getUsername(),user.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("??????????????????")
    @ApiOperation("??????????????????")
    @PutMapping(value = "/updateCustInfo")
    public void updateCustomerInfo(@Validated(User.Update.class) @RequestBody User resources){
        if(!resources.getId().equals(SecurityUtils.getCurrentUserId())){
            throw new BadRequestException("????????????????????????");
        }
        userService.updateInfo(resources);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????
     * @param resources /
     */
    private void checkLevel(User resources) {
        Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("??????????????????");
        }
    }

}
