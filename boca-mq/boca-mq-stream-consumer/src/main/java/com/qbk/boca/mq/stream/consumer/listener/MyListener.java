package com.qbk.boca.mq.stream.consumer.listener;

import com.qbk.boca.bean.message.MessageMode;
import com.qbk.boca.mq.stream.consumer.sink.MySink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

/**
 * 使用自定义Sink 收到消息的监听
 */
@EnableBinding(MySink.class)
public class MyListener {

    @Value("${server.port}")
    private Integer serverPort;

    @StreamListener(value = MySink.INPUT)
    public void handle(Message<MessageMode> message){
        final MessageMode messageMode = message.getPayload();
        System.out.println("端口" + serverPort +"\t 收到：" + messageMode);
    }
}
