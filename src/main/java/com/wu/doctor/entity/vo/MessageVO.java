package com.wu.doctor.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

/**
 * 消息视图对象
 * 用于API响应中返回简化的消息格式
 */
@NoArgsConstructor
@Data
public class MessageVO {
    private String role;
    private String content;

    /**
     * 从Spring AI的Message对象创建MessageVO对象
     * @param message Spring AI消息对象
     */
    public MessageVO(Message message) {
        this.role = switch (message.getMessageType()) {
            case USER -> "user";
            case ASSISTANT -> "assistant";
            case SYSTEM -> "system";
            default -> "";
        };
        this.content = message.getText();
    }
}