package com.qbk.boca.mq.stream.consumer.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义 一个输入通道的可绑定接口。
 * 使用在 EnableBinding注解上
 */
public interface MySink {

    /**
     * channel 名称
     */
    String INPUT = "qbkInput";

    /**
     * 通过Input注解标识输入通道
     * 使用的名字 也是 StreamListener注解 和 配置中 bindings的 channel名
     */
    @Input(value = INPUT)
    SubscribableChannel input();

}