package com.qbk.boca.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过 Spring Cloud 原生注解 @RefreshScope 实现配置自动更新：
 * 入口在 ContextRefresher:refresh()的调用
 * 事件监听器 RefreshListener
 * ①提取标准参数(SYSTEM,JNDI,SERVLET)之外所有参数变量
 * ②把原来的Environment里的参数放到一个新建的Spring Context容器下重新加载，完事之后关闭新容器
 * ③提起更新过的参数(排除标准参数)
 * ④比较出变更项
 * ⑤发布环境变更事件,接收：EnvironmentChangeListener／LoggingRebinder
 * ⑥RefreshScope用新的环境参数重新生成Bean
 * 重新生成的过程很简单，清除refreshscope缓存幷销毁Bean，下次就会重新从BeanFactory获取一个新的实例（该实例使用新的配置）
 */
@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}