package com.qbk.boca.bean;

import lombok.experimental.UtilityClass;

/**
 * 基础结果工具类
 */
@UtilityClass
public class BaseResultUtil {

    public <T> BaseResult<T> ok(String msg,T data){
        return BaseResult.create(ResultStatus.SUCCESS.getCode(),msg,data);
    }

    public <T> BaseResult<T> ok(T data){
       return ok(ResultStatus.SUCCESS.getMsg(),data);
    }

    public <T> BaseResult<T> ok(){
        return ok(null);
    }

    public <T> BaseResult<T> error(){
        return BaseResult.create(ResultStatus.FAIL.getCode(),ResultStatus.FAIL.getMsg(),null);
    }

    public <T> BaseResult<T> error(String msg){
        return BaseResult.create(ResultStatus.FAIL.getCode(),msg,null);
    }

    public  <T> BaseResult<T> common(ResultStatus resultStatus ,T data){
        return BaseResult.create(resultStatus.getCode(),resultStatus.getMsg(),data);
    }
    public  <T> BaseResult<T> common(ResultStatus resultStatus ){
        return BaseResult.create(resultStatus.getCode(),resultStatus.getMsg(),null);
    }

    public static BaseResult<Object> common(Integer code, String msg) {
        return BaseResult.create(code,msg,null);
    }

    public static <T> BaseResult<T> common(Integer code, String msg  ,T data) {
        return BaseResult.create(code,msg,data);
    }
}
