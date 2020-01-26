package com.qbk.boca.bean;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 基础结果
 */
@Data
@RequiredArgsConstructor(staticName ="create")
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = -5272818655994280689L;

    /**
     * 返回码
     */
    private final int code;

    /**
     * 返回信息描述
     */
    private final String message;

    /**
     * 返回数据
     */
    private final T data;
}

