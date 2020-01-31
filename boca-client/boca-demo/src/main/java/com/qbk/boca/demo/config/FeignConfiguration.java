package com.qbk.boca.demo.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;


/**
 * Feign配置
 * 不需要用注释@Configuration
 */
public class FeignConfiguration {

    /**
     * 注解契约
     * 可用 @RequestLine
     */
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }
}
