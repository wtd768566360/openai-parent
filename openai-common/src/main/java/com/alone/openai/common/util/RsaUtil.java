package com.alone.openai.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

public class RsaUtil {

    /**
     * 通过私钥解密文本
     *
     * @param privateKey 私钥
     * @param ciphertext 通过公钥加密的文本
     * @return 解密后的数据
     */
    public static String decrypt(String privateKey, String ciphertext) {
        RSA rsa = new RSA(privateKey, null);
        return StrUtil.str(rsa.decrypt(ciphertext, KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 通过公钥加密
     *
     * @param publicKey 公钥
     * @param text      明文
     * @return 加密后的数据
     */
    public static String encrypt(String publicKey, String text) {
        RSA rsa1 = new RSA(null, publicKey);
        byte[] encrypt = rsa1.encrypt(StrUtil.bytes("123456", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        return StrUtil.str(Base64.encode(encrypt), CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 文本是否匹配
     *
     * @param privateKey 私钥
     * @param ciphertext 公钥加密的密文
     * @param text       明文文本
     * @return 解密后的数据
     */
    public static Boolean isMatch(String privateKey, String ciphertext, String text) {
        String plaintext = decrypt(privateKey, ciphertext);
        return text.equals(plaintext);
    }

}
