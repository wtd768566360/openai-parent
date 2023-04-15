package com.alone.openai.web.service.impl;

import com.alone.openai.common.util.RandomGeneratorUtil;
import com.alone.openai.web.service.EmailService;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final Cache<String, String> eamilCache;


    private SimpleMailMessage simpleMailMessage;

    public EmailServiceImpl(JavaMailSender javaMailSender,
                            SimpleMailMessage simpleMailMessage,
                            @Qualifier("email-cache") Cache<String, String> eamilCache) {
        this.javaMailSender = javaMailSender;
        this.simpleMailMessage = simpleMailMessage;
        this.eamilCache = eamilCache;
    }


    @Override
    public String sendAccount(String email, String account) {
        sendInfo(email, "找回账号", "您的账号为：" + account + " ; 请牢记账号信息！ 如果不是本人操作，请忽略此邮件");
        return account;
    }


    @Override
    public String sendCreateCodeMail(String mail) {
        String code = RandomGeneratorUtil.generate();
        sendInfo(mail, "注册验证码", "您的注册验证码为：" + code + "  十分钟内有效！ 如果不是本人注册，请忽略此邮件");
        //加入缓存
        eamilCache.put(mail, code);
        return code;
    }

    @Override
    public String sendForgetPasswordCodeMail(String mail) {
        String code = RandomGeneratorUtil.generate();
        sendInfo(mail, "重置密码验证码", "您的重置密码验证码为：" + code + "  十分钟内有效！ 如果不是本人操作，请忽略此邮件");
        //加入缓存
        eamilCache.put(mail, code);
        return code;
    }

    @Override
    public void sendInfo(String email, String subject, String msg) {
        //设置主题
        simpleMailMessage.setSubject(subject);
        //设置内容
        simpleMailMessage.setText(msg);
        //设置收件人
        simpleMailMessage.setTo(email);
        //设置发件人
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public String getCacheCode(String key) {
        try {
            return eamilCache.get(key, () -> {
                return null;
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
