package com.alone.openai.web.test;

import cn.hutool.core.util.StrUtil;

public class RandomTest {

    public static void main(String[] args) {
        System.out.println(StrUtil.uuid().replace("-", ""));
    }

}
