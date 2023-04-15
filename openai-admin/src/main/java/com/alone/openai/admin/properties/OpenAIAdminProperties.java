package com.alone.openai.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "openai")
public class OpenAIAdminProperties {

    //私钥KEY
    private String privateKey;
    //公钥KEY
    private String publicKey;

    private Web web;

    @Data
    public static class Web {
        //web端的url地址
        private String url;
    }
}
