package com.qbk.boca.mq.stream.provider.controller;

import com.qbk.boca.mq.stream.provider.service.IMessageProvider;
import com.qbk.boca.mq.stream.provider.service.MyPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 发送消息
 */
@RestController
public class SendMessageController {

    @Resource
    private IMessageProvider messageProvider;

    @Resource
    private MyPublisher myPublisher;

    @GetMapping("/sendMessage")
    public String sendMessage(){
        return messageProvider.send();
    }

    @GetMapping("/send")
    public String send(){
        return myPublisher.send();
    }
}