package com.alone.openai.web.service;

public interface EmailService {

    String sendCreateCodeMail(String mail);

    String sendForgetPasswordCodeMail(String mail);

    String sendAccount(String email, String account);

    void sendInfo(String email, String subject, String msg);

    String getCacheCode(String key);

}
