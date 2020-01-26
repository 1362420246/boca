package com.qbk.boca.core.exception;


import com.qbk.boca.bean.ResultStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasicException extends RuntimeException{

    private static final long serialVersionUID = 5524588728666419128L;

    private Integer code;
    private String msg;

    /**
     * 继承exception，加入错误状态值
     */
    public BasicException(ResultStatus resultStatus) {
        this.msg = resultStatus.getMsg();
        this.code = resultStatus.getCode();
    }

    /**
     * 自定义错误信息
     */
    public BasicException(Integer code, String message) {
        this.msg = message;
        this.code = code;
    }

    /**
     * 自定义错误信息
     */
    public BasicException(String message) {
        this.code = ResultStatus.FAIL.getCode();
        this.msg = message;
    }

}
