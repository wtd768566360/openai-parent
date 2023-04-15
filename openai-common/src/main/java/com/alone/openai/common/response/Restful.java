package com.alone.openai.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restful {

    public static final int CODE_OK = 0;
    public static final int CODE_ERROR = 1;

    /**
     * 状态 0-表示成功  1-表示失败
     */
    private int code = CODE_OK;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 数组总条数
     */
    private int count;

    /**
     * 数据
     */
    private Object data;

}
