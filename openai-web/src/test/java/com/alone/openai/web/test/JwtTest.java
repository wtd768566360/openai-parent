package com.alone.openai.web.test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;

public class JwtTest {

    //过期时间 10天
    private static final Long EXPIRE_TIME = 8640000000L;

    private static final String secret = "openai-web";

    public static void main(String[] args) throws InterruptedException {

        String a = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzeXN0ZW0iLCJqdGkiOiIzOWQzNDUzOTliNjY0N2EzOGIwOTUyMTVmMjM2YzMzZiIsIm5hbWUiOiLotoXnrqEiLCJ1c2VyVHlwZSI6ImFkbWluIiwiaWF0IjoxNjgwOTIyMTc2LCJleHAiOjE2ODE1MjY5NzZ9.SCBWEnTA3AnHx5s7HSSEr3Eu_8Gto2llYhoxXFTOlTc";
        System.out.println(a.length());
    }

    public static void jwt() throws InterruptedException {
        final JWTSigner signer = JWTSignerUtil.hs256(secret.getBytes());

        final String token = JWT.create()
                .setPayload("ID", "1")
                .setPayload("name", "wtd")
                .setPayload("userType", "user")
                // 设置签发时间
                .setIssuedAt(DateUtil.date())
                .setExpiresAt(DateUtil.offsetSecond(DateUtil.date(), 10))
                .setKey(secret.getBytes())
                .sign(signer);
        Thread.sleep(1000 * 11);
        //验证jwt是否有效
        System.out.println(JWTUtil.verify(token, signer));
        try {
            JWTValidator.of(token).validateDate();
        } catch (Exception e) {
            System.out.println("无效的jwt");
        }
    }
}
