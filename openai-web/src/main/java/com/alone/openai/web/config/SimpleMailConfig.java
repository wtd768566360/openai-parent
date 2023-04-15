package com.alone.openai.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SimpleMailConfig {
    
    /**
     * 发件人 从配置文件中读取
     */
    @Value("${spring.mail.username}")
    private String emailFrom;

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件设置
        simpleMailMessage.setFrom(emailFrom);
        return simpleMailMessage;
    }

}
