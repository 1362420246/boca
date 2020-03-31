package com.qbk.boca.mq.stream.provider.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import javax.annotation.Resource;
import java.util.UUID;

/**
 * EnableBinding 定义消息的推送管道
 */
@EnableBinding(Source.class)
public class IMessageProvider {

    /**
     * 消息发送管道
     */
    @Resource
    private MessageChannel output;

    public String send() {
        String serial = UUID.randomUUID().toString();
        //消息构建器
        Message<String> message = MessageBuilder.withPayload(serial).build();
        //发送消息
        output.send(message);
        System.out.println("serial = " + serial);
        return serial;
    }
}