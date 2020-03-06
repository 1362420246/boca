package com.qbk.boca.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.net.InetSocketAddress;
import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogDTO {
    /**
     * 参数
     */
    private String params;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 返回结果
     */
    private Object result;
    /**
     * 开始时间
     */
    private long startTime;
    /**
     * 用时时长
     */
    private long executeTime;
    private String path;
    private URI uri;
    private String method;
    private Integer code;
    private HttpHeaders headers;
    private InetSocketAddress address;

}
