package com.qbk.boca.mq.stream.provider.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义 一个输出通道的可绑定接口。
 * 使用在 EnableBinding注解上
 */
public interface MySource {

    /**
     * channel 名称
     */
    String OUTPUT = "qbkOutput";

    /**
     * 通过Output注解标识输出通道
     * 使用的名字 也是 注入的MessageChannel 和 配置中 bindings的 channel名
     */
    @Output(value = OUTPUT)
    MessageChannel output();

}
