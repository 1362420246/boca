package com.qbk.boca.bean;

import java.util.HashMap;

/**
 * 返回map
 */
public class RespMap extends HashMap<String,Object> {
    {
        put("code",ResultStatus.SUCCESS.getCode());
        put("message",ResultStatus.SUCCESS.getMsg());
    }
    private RespMap(Object data){
        put("data",data);
    }
    public static RespMap build(Object data){
        return new RespMap(data);
    }
    public static RespMap build(){
        return build(null);
    }
}
