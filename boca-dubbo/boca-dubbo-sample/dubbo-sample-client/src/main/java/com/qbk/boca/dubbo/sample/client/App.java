package com.qbk.boca.dubbo.sample.client;

import com.qbk.boca.dubbo.sample.api.ILoginService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 简单dubbo测试
 */
public class App {
    public static void main(String[] args) {
        ILoginService loginService = null;
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:META-INF/spring/application.xml");
        loginService = context.getBean(ILoginService.class);
        System.out.println(loginService.login("admin","admin"));
    }
}
