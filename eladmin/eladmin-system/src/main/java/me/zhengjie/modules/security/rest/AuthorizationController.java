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
package me.zhengjie.modules.security.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.security.config.SecurityProperties;
import me.zhengjie.modules.security.security.TokenProvider;
import me.zhengjie.modules.security.service.dto.AuthUserDto;
import me.zhengjie.modules.security.service.dto.JwtUserDto;
import me.zhengjie.modules.security.service.OnlineUserService;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.dto.Token;
import me.zhengjie.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "系统：系统授权接口")
public class AuthorizationController {

    @Value("${loginCode.expiration}")
    private Long expiration;
    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${single.login:false}")
    private Boolean singleLogin;
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    @Log("用户登录")
    @ApiOperation("登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public Map<String, Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) {
        //判断数据库里面是否有此用户
        if(ObjectUtil.isNull(userRepository.findByUsername(authUser.getUsername()))){
            //没有，生成AD验证实例
            LDAPConnector instance = LDAPConnector.getInstance();
            //域密码解密
            RSA rsa = new RSA(privateKey, null);
            String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
            //判断域中是否存在
            if(instance.validateUser(authUser.getUsername(),password)){
                //如果存在，通过域账号名获得工号
                String jobNum=instance.getEmpNo(authUser.getUsername());
                // 查询验证码
                String code = (String) redisUtils.get(authUser.getUuid());
                // 清除验证码
                redisUtils.del(authUser.getUuid());
                if (StringUtils.isBlank(code)) {
                    throw new BadRequestException("验证码不存在或已过期");
                }
                if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
                    throw new BadRequestException("验证码错误");
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jobNum,"123456");

                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 生成令牌
                String token = tokenProvider.createToken(authentication);
                final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
                // 保存在线信息
                onlineUserService.save(jwtUserDto, token, request);
                // 返回 token 与 用户信息
                Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
                    put("token", properties.getTokenStartWith() + token);
                    put("user", jwtUserDto);
                }};
                if (singleLogin) {
                    //踢掉之前已经登录的token
                    onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
                }
                // return ResponseEntity.ok(authInfo);
                return authInfo;
            }else{
                throw new BadRequestException("用户名或密码错误");
            }
        }else{
            //只让admin进入
            if("admin".equals(authUser.getUsername())){
                // 密码解密
                RSA rsa = new RSA(privateKey, null);
                String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
                // 查询验证码
                String code = (String) redisUtils.get(authUser.getUuid());
                // 清除验证码
                redisUtils.del(authUser.getUuid());
                if (StringUtils.isBlank(code)) {
                    throw new BadRequestException("验证码不存在或已过期");
                }
                if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
                    throw new BadRequestException("验证码错误");
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 生成令牌
                String token = tokenProvider.createToken(authentication);
                final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
                // 保存在线信息
                onlineUserService.save(jwtUserDto, token, request);
                // 返回 token 与 用户信息
                Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
                    put("token", properties.getTokenStartWith() + token);
                    put("user", jwtUserDto);
                }};
                if (singleLogin) {
                    //踢掉之前已经登录的token
                    onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
                }
                // return ResponseEntity.ok(authInfo);
                return authInfo;
            }else{
                throw new BadRequestException("用户名或密码错误");
            }

        }

    }

    @Log("用户登录")
    @ApiOperation("登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login2")
    public Map<String, Object> login(@Validated @RequestBody Token tokenComponent, HttpServletRequest request) throws UnsupportedEncodingException {
        //public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request){
        byte[] saltByte = DatatypeConverter.parseBase64Binary(tokenComponent.getTokenString());
        String text = new String(saltByte, "iso-8859-1");
        String[] txt = text.split(";");
        // 工号
        String jobNumber = txt[0];
        if(ObjectUtil.isEmpty(userRepository.findByUsername(jobNumber))){
            throw new BadRequestException("用户不存在");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(jobNumber, "123456");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if (singleLogin) {
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(jobNumber, token);
        }
        // return ResponseEntity.ok(authInfo);
        return authInfo;
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public UserDetails getUserInfo() {
        //public ResponseEntity<Object> getUserInfo(){
        // return ResponseEntity.ok(SecurityUtils.getCurrentUser());
        return SecurityUtils.getCurrentUser();
    }

    @AnonymousAccess
    @ApiOperation("获取验证码")
    @GetMapping(value = "/code")
    public Map<String, Object> getCode() {
        //public ResponseEntity<Object> getCode(){
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        // 保存
        redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        // return ResponseEntity.ok(imgResult);
        return imgResult;
    }

    /*    @ApiOperation("退出登录")
        @AnonymousAccess
        @DeleteMapping(value = "/logout")
        public ResponseEntity<Object> logout(HttpServletRequest request){
            onlineUserService.logout(tokenProvider.getToken(request));
            return new ResponseEntity<>(HttpStatus.OK);
        }*/
    @ApiOperation("退出登录")
    @AnonymousAccess
    @DeleteMapping(value = "/logout" , produces = "application/json;charset=utf8")
    public void logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
    }
}
