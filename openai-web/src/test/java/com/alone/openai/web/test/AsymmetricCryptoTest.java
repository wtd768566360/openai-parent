package com.alone.openai.web.test;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.nio.charset.StandardCharsets;

public class AsymmetricCryptoTest {

    public static void main(String[] args) {
        // 使用Hutool的RSA工具类生成2048位RSA密钥对
        RSA rsa = new RSA();

        // 获取公钥，将其传输给前端
        String publicKey = rsa.getPublicKeyBase64();
        System.out.println(publicKey);

        // 获取私钥，只存储在后端
        String privateKey = rsa.getPrivateKeyBase64();
        System.out.println(privateKey);

        // 加密数据
        String dataToEncrypt = "Hello, world!";
        byte[] encryptedData = rsa.encrypt(dataToEncrypt.getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);

        // 在后端中解密数据
        String decryptedData = new String(rsa.decrypt(encryptedData, KeyType.PrivateKey), StandardCharsets.UTF_8);
        System.out.println(decryptedData); // 输出：Hello, world!
    }
}
