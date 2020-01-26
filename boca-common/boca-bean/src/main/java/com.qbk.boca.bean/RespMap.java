package com.qbk.boca.bean;

import java.util.HashMap;

/**
 * 返回map
 */
public class RespMap extends HashMap<String,Object> {
    {
        put("code",ResultStatus.SUCCESS.getCode());
        put("message",ResultStatus.SUCCESS.getMsg());
        put("data",null);
    }
}
