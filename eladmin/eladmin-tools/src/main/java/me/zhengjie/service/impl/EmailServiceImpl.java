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
package me.zhengjie.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.EmailConfig;
import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.repository.EmailRepository;
import me.zhengjie.service.EmailService;
import me.zhengjie.utils.EncryptUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "email")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    @Override
    @CachePut(key = "'1'")
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig update(EmailConfig emailConfig, EmailConfig old) {
        try {
            if(!emailConfig.getPass().equals(old.getPass())){
                // 对称加密
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailRepository.save(emailConfig);
    }

    @Override
    @Cacheable(key = "'1'")
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = emailRepository.findById(1L);
        return emailConfig.orElseGet(EmailConfig::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig){
        if(emailConfig == null){
            throw new BadRequestException("请先配置，再操作");
        }
        // 封装
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser()+"<"+emailConfig.getFromUser()+">");
        // ssl方式发送
        account.setSslEnable(false);
        String content = emailVo.getContent();
        // 发送
        try {
            int size = emailVo.getTos().size();
            if (emailVo.getCcs()!=null&&emailVo.getCcs().size()>0){
                Mail.create(account)
                        .setTos(emailVo.getTos().toArray(new String[size]))
                        .setCcs(emailVo.getCcs().toArray(new String[size]))
                        .setTitle(emailVo.getSubject())
                        .setContent(content)
                        .setHtml(true)
                        //关闭session
                        .setUseGlobalSession(false)
                        .send();
            }else{
                Mail.create(account)
                        .setTos(emailVo.getTos().toArray(new String[size]))
                        .setTitle(emailVo.getSubject())
                        .setContent(content)
                        .setHtml(true)
                        //关闭session
                        .setUseGlobalSession(false)
                        .send();
            }

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
