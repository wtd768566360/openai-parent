package com.alone.openai.client.exception;

public class OpenAIException extends RuntimeException {

    public OpenAIException() {
    }

    public OpenAIException(String message) {
        super("调用OpenAI接口异常：" + message);
    }

}