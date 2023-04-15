package com.alone.openai.common.constant;

import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;

/**
 * 常量
 */
public class Constant {

    //过期时间 7天
    public static final int EXPIRE_TIME_DAY = 7;

    //签名ID
    public static final String JWT_SIGNER_SECRET = "openai-web";

    //签发者 默认使用 system
    public static final String JWT_ISSUER_SYSTEM = "system";

    //jwt的加密算法
    public static final JWTSigner JWT_SIGNER = JWTSignerUtil.hs256(Constant.JWT_SIGNER_SECRET.getBytes());

    //验证方案
    public static final String JWT_TOKEN = "token";

    //admin端访问此项目时所需要携带的认证token,后面可以用非对称算法换算
    public static final String ADMIN_TOKEN = "e9aacd6ece324bb89739d5df99dc2a05";

    //用户注册的时候,默认有一千次访问量
    public static final int REGISTRY_FLOW_TOTAL = 1000;

}
