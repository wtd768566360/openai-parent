package com.alone.openai.common.controller;


import com.alone.openai.common.response.Restful;

public class BaseController {

    public Restful toSuccess(Object data) {
        return Restful.builder().data(data).build();
    }

    public Restful toSuccess(String msg, Object data) {
        return Restful.builder().data(data).msg(msg).build();
    }

    public Restful toSuccess(String msg) {
        return Restful.builder().msg(msg).build();
    }

    public Restful toSuccess(int count, Object data) {
        return Restful.builder().data(data).count(count).build();
    }

    public Restful toSuccess() {
        return Restful.builder().build();
    }

    public Restful toSuccess(int count, String msg, Object data) {
        return Restful.builder().data(data).msg(msg).count(count).build();
    }


    public Restful toError() {
        return Restful.builder().code(Restful.CODE_ERROR).build();
    }


    public Restful toError(String msg) {
        return Restful.builder().code(Restful.CODE_ERROR).msg(msg).build();
    }
}
