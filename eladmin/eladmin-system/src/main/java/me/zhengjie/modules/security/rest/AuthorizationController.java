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
 * ???????????????token????????????????????????
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "???????????????????????????")
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

    @Log("????????????")
    @ApiOperation("????????????")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public Map<String, Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) {
        //???????????????????????????????????????
        if(ObjectUtil.isNull(userRepository.findByUsername(authUser.getUsername()))){
            //???????????????AD????????????
            LDAPConnector instance = LDAPConnector.getInstance();
            //???????????????
            RSA rsa = new RSA(privateKey, null);
            String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
            //????????????????????????
            if(instance.validateUser(authUser.getUsername(),password)){
                //?????????????????????????????????????????????
                String jobNum=instance.getEmpNo(authUser.getUsername());
                // ???????????????
                String code = (String) redisUtils.get(authUser.getUuid());
                // ???????????????
                redisUtils.del(authUser.getUuid());
                if (StringUtils.isBlank(code)) {
                    throw new BadRequestException("??????????????????????????????");
                }
                if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
                    throw new BadRequestException("???????????????");
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jobNum,"123456");

                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // ????????????
                String token = tokenProvider.createToken(authentication);
                final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
                // ??????????????????
                onlineUserService.save(jwtUserDto, token, request);
                // ?????? token ??? ????????????
                Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
                    put("token", properties.getTokenStartWith() + token);
                    put("user", jwtUserDto);
                }};
                if (singleLogin) {
                    //???????????????????????????token
                    onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
                }
                // return ResponseEntity.ok(authInfo);
                return authInfo;
            }else{
                throw new BadRequestException("????????????????????????");
            }
        }else{
            //??????admin??????
            if("admin".equals(authUser.getUsername())){
                // ????????????
                RSA rsa = new RSA(privateKey, null);
                String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
                // ???????????????
                String code = (String) redisUtils.get(authUser.getUuid());
                // ???????????????
                redisUtils.del(authUser.getUuid());
                if (StringUtils.isBlank(code)) {
                    throw new BadRequestException("??????????????????????????????");
                }
                if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
                    throw new BadRequestException("???????????????");
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // ????????????
                String token = tokenProvider.createToken(authentication);
                final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
                // ??????????????????
                onlineUserService.save(jwtUserDto, token, request);
                // ?????? token ??? ????????????
                Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
                    put("token", properties.getTokenStartWith() + token);
                    put("user", jwtUserDto);
                }};
                if (singleLogin) {
                    //???????????????????????????token
                    onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
                }
                // return ResponseEntity.ok(authInfo);
                return authInfo;
            }else{
                throw new BadRequestException("????????????????????????");
            }

        }

    }

    @Log("????????????")
    @ApiOperation("????????????")
    @AnonymousAccess
    @PostMapping(value = "/login2")
    public Map<String, Object> login(@Validated @RequestBody Token tokenComponent, HttpServletRequest request) throws UnsupportedEncodingException {
        //public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request){
        byte[] saltByte = DatatypeConverter.parseBase64Binary(tokenComponent.getTokenString());
        String text = new String(saltByte, "iso-8859-1");
        String[] txt = text.split(";");
        // ??????
        String jobNumber = txt[0];
        if(ObjectUtil.isEmpty(userRepository.findByUsername(jobNumber))){
            throw new BadRequestException("???????????????");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(jobNumber, "123456");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // ????????????
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // ??????????????????
        onlineUserService.save(jwtUserDto, token, request);
        // ?????? token ??? ????????????
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if (singleLogin) {
            //???????????????????????????token
            onlineUserService.checkLoginOnUser(jobNumber, token);
        }
        // return ResponseEntity.ok(authInfo);
        return authInfo;
    }

    @ApiOperation("??????????????????")
    @GetMapping(value = "/info")
    public UserDetails getUserInfo() {
        //public ResponseEntity<Object> getUserInfo(){
        // return ResponseEntity.ok(SecurityUtils.getCurrentUser());
        return SecurityUtils.getCurrentUser();
    }

    @AnonymousAccess
    @ApiOperation("???????????????")
    @GetMapping(value = "/code")
    public Map<String, Object> getCode() {
        //public ResponseEntity<Object> getCode(){
        // ???????????? https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // ?????????????????????????????????
        captcha.setLen(2);
        // ?????????????????????
        String result = captcha.text();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        // ??????
        redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES);
        // ???????????????
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        // return ResponseEntity.ok(imgResult);
        return imgResult;
    }

    /*    @ApiOperation("????????????")
        @AnonymousAccess
        @DeleteMapping(value = "/logout")
        public ResponseEntity<Object> logout(HttpServletRequest request){
            onlineUserService.logout(tokenProvider.getToken(request));
            return new ResponseEntity<>(HttpStatus.OK);
        }*/
    @ApiOperation("????????????")
    @AnonymousAccess
    @DeleteMapping(value = "/logout" , produces = "application/json;charset=utf8")
    public void logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
    }
}
