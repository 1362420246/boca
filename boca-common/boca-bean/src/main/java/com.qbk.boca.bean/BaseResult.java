package com.qbk.boca.bean;
import lombok.*;

import java.io.Serializable;

/**
 * 基础结果
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = -5272818655994280689L;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息描述
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public static <T> BaseResult<T> create(int code, String msg, T data) {
        return new BaseResult<T>(code,msg,data);
    }
}

