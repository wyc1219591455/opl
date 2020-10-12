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

import lombok.RequiredArgsConstructor;
import me.zhengjie.config.FileProperties;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.MenuService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.*;
import me.zhengjie.modules.system.service.mapstruct.UserMapper;
import me.zhengjie.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RedisUtils redisUtils;
    private final FileProperties properties;
    private final MenuService menuService;
    private final RoleService roleService;

    @Override
    //@Cacheable
    public Object queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<User> page = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    @Cacheable
    public List<UserDto> queryAll(UserQueryCriteria criteria) {
        List<User> users = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
        return userMapper.toDto(users);
    }

    @Override
    @Cacheable(key = "#p0")
    public UserDto findById(long id) {
        User user = userRepository.findById(id).orElseGet(User::new);
        ValidationUtil.isNull(user.getId(),"User","id",id);
        return userMapper.toDto(user);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(User resources) {
        if(userRepository.findByUsername(resources.getUsername())!=null){
            //throw new EntityExistException(User.class,"username",resources.getUsername());
            throw new BadRequestException("用户名已存在");
        }
        if(userRepository.findByEmail(resources.getEmail())!=null){
            //throw new EntityExistException(User.class,"email",resources.getEmail());
            throw new BadRequestException("邮箱已存在");
        }
        User user = userRepository.save(resources);


    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Object update(User resources) {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        //  用户修改之前的username
        String oldUserName = user.getUsername();
        //验证user.getId()是否为空，为空则抛出异常
        ValidationUtil.isNull(user.getId(),"User","id",resources.getId());
        User user1 = userRepository.findByUsername(resources.getUsername());
        User user2 = userRepository.findByEmail(resources.getEmail());

        if(user1 !=null&&!user.getId().equals(user1.getId())){
            //  throw new EntityExistException(User.class,"username",resources.getUsername());
            throw new BadRequestException("用户名已存在");
        }

        if(user2!=null&&!user.getId().equals(user2.getId())){
            //  throw new EntityExistException(User.class,"email",resources.getEmail());
            throw new BadRequestException("邮箱已存在");
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisUtils.del(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisUtils.del(key);
        }

        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRoles(resources.getRoles());
        user.setDept(resources.getDept());
        user.setJobs(resources.getJobs());
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        user.setGender(resources.getGender());
        userRepository.save(user);
        //  是否修改自己的用户名标识， false ： 否； true：是
        Boolean updateSelfFlag = false;
        //  修改的用户是当前登录用户并且修改了用户名，就清除缓存，让用户重新登录
        String currentUsername = SecurityUtils.getCurrentUsername();
        if(oldUserName.equals(currentUsername) && !oldUserName.equals(resources.getUsername())){
            redisUtils.del("user::username:" + currentUsername);   //清除缓存，让用户重新登录
            updateSelfFlag = true;
        }
        //  将updateSelfFlag字段返回给前端，用于修改自己的用户名时，退出登录
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("updateSelfFlag", updateSelfFlag);
        return map;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(User resources) {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        user.setNickName(resources.getNickName());
        user.setPhone(resources.getPhone());
        user.setGender(resources.getGender());
        userRepository.save(user);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            userRepository.deleteById(id);
        }
    }

    @Override
    @Cacheable(key = "'loadUserByUsername:'+#p0")
    public UserDto findByName(String userName) {
        User user;
        if(ValidationUtil.isEmail(userName)){
            user = userRepository.findByEmail(userName);
        } else {
            user = userRepository.findByUsername(userName);
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        userRepository.updatePass(username,pass,new Date());
        redisUtils.del("user::username:" + username);   //清除缓存，让用户重新登录
    }

    /*@Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(MultipartFile multipartFile) {
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername());
        String oldPath = user.getAvatarPath();
        File file = FileUtil.upload(multipartFile, properties.getPath().getAvatar());
        user.setAvatarPath(Objects.requireNonNull(file).getPath());
        user.setAvatarName(file.getName());
        userRepository.save(user);
        if(StringUtils.isNotBlank(oldPath)){
            FileUtil.del(oldPath);
        }
    }*/

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,String> updateAvatar(MultipartFile multipartFile) {
        File file = FileUtil.upload(multipartFile, properties.getPath().getAvatar());
        String avatarPath = Objects.requireNonNull(file).getPath();
        String avatarName = file.getName();
        Map<String,String> map = new HashMap<String,String>(2){{
            put("avatarPath", avatarPath);
            put("avatarName", avatarName);
        }};
        return map;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userRepository.updateEmail(username,email);
    }

    @Override
    public void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDto userDTO : queryAll) {
            List<String> roles = userDTO.getRoles().stream().map(RoleSmallDto::getName).collect(Collectors.toList());
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户名", userDTO.getUsername());
            map.put("角色", roles);
            map.put("部门", userDTO.getDept().getName());
            map.put("岗位", userDTO.getJobs().stream().map(JobSmallDto::getName).toString());
            map.put("邮箱", userDTO.getEmail());
            map.put("状态", userDTO.getEnabled() ? "启用" : "禁用");
            map.put("手机号码", userDTO.getPhone());
            map.put("修改密码的时间", userDTO.getPwdResetTime());
            map.put("创建日期", userDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(User resources){
        User currentUser = userRepository.findByUsername(SecurityUtils.getCurrentUsername());
        String oldPath = currentUser.getAvatarPath();
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        user.setAvatarPath(resources.getAvatarPath());
        user.setAvatarName(resources.getAvatarName());
        user.setNickName(resources.getNickName());
        user.setGender(resources.getGender());
        userRepository.save(user);
        if(StringUtils.isNotBlank(oldPath) && !oldPath.equals(resources.getAvatarPath())){
            FileUtil.del(oldPath);
        }
    }
}
