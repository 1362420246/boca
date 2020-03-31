package com.qbk.boca.bean.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * mq发送的自定义消息实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageMode {
    private Integer id;
    private Integer type;
    private String action;
}
