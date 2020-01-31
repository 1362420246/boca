package com.qbk.boca.demo.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * fegin请求拦截 可以转发请求头
 */
public class FooRequestInterceptor implements RequestInterceptor {
    /**
     * 每一个请求都会被调用。使用提供的{@link RequestTemplate}上的方法添加数据。
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                //转发请求头参数
                requestTemplate.header(name, values);
            }
        }
        //把request域对象中的参数添加到请求头中
        Enumeration<String> attributeNames = request.getAttributeNames();
        if(attributeNames != null){
            while (attributeNames.hasMoreElements()){
                String name = attributeNames.nextElement();
                if ("password".equals(name)){
                    String values = (String) request.getAttribute(name);
                    requestTemplate.header("password", values);
                }
            }
        }
    }
}
