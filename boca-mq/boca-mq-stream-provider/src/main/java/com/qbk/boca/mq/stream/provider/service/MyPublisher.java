package com.qbk.boca.mq.stream.provider.service;

import com.qbk.boca.bean.message.MessageMode;
import com.qbk.boca.mq.stream.provider.source.MySource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 使用自定义 Source
 * EnableBinding 绑定发布消息通道
 */
@EnableBinding(value = MySource.class)
public class MyPublisher {

    /**
     * 消息发送管道
     */
    @Resource(name = MySource.OUTPUT)
    private MessageChannel output;

    /**
     * 发送消息
     */
    public String send() {
        String serial = UUID.randomUUID().toString();
        MessageMode messageMode = MessageMode.builder().id(1).type(1).action(serial).build();
        //消息构建器
        Message<MessageMode> message = MessageBuilder.withPayload(messageMode).build();
        //发送消息
        output.send(message);
        System.out.println("message = " + messageMode);
        return "成功";
    }
}
