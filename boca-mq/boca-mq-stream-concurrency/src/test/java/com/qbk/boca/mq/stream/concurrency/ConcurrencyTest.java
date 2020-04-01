package com.qbk.boca.mq.stream.concurrency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 多消费者测试
 */
@EnableBinding({
        ConcurrencyTest.MessageSource.class ,
        ConcurrencyTest.MessageSink.class
})
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConcurrencyTest {
    private Random random = new Random();
    private List<String> devEuis = new ArrayList<>(10);
    @PostConstruct
    private void initDevEuis() {
        devEuis.add("10001");
        devEuis.add("10002");
        devEuis.add("10003");
        devEuis.add("10004");
        devEuis.add("10005");
        devEuis.add("10006");
        devEuis.add("10007");
        devEuis.add("10008");
        devEuis.add("10009");
        devEuis.add("10010");
    }
    private String getDevEuis() {
        return devEuis.get(random.nextInt(10));
    }
    @Autowired
    private ConcurrentProducer concurrentProducer;
    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 100000; i++) {
            String devEui = getDevEuis();
            concurrentProducer.publish(new MsgModel(devEui, UUID.randomUUID().toString()));
        }
        Thread.sleep(1000000);
    }
    @Component
    public static class ConcurrentProducer {
        @Autowired
        private MessageSource messageSource;
        public void publish(MsgModel model) {
            log.info("发布上行数据包消息. model: [{}].", model);
            messageSource.qbkOutput().send(MessageBuilder.withPayload(model).build());
        }
    }
    @Component
    public static class ConcurrentPublisher{
        @StreamListener("qbkInput")
        public void handle(Message<MsgModel> message) throws InterruptedException {
            Thread.sleep(10);
            log.info("消费上行数据包消息. model: [{}].", message.getPayload());
        }
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MsgModel{
        private String id;
        private String data;
    }
    public interface MessageSink {
        @Input("qbkInput")
        SubscribableChannel qbkInput();
    }
    public interface MessageSource{
        @Output("qbkOutput")
        MessageChannel qbkOutput();
    }
}
