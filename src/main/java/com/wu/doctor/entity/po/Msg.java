package com.wu.doctor.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.*;

import java.util.List;
import java.util.Map;

/**
 * 消息持久化对象
 * 用于将Spring AI的Message对象转换为可序列化的对象
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Msg {
    private MessageType messageType;
    private String text;
    private Map<String, Object> metadata;

    /**
     * 从Spring AI的Message对象创建Msg对象
     * @param message Spring AI消息对象
     */
    public Msg(Message message) {
        this.messageType = message.getMessageType();
        this.text = message.getText();
        this.metadata = message.getMetadata();
    }

    /**
     * 将Msg对象转换回Spring AI的Message对象
     * @return Spring AI消息对象
     */
    public Message toMessage() {
        return switch (messageType) {
            case SYSTEM -> new SystemMessage(text);
            case USER -> new UserMessage(text, List.of(), metadata);
            case ASSISTANT -> new AssistantMessage(text, metadata, List.of(), List.of());
            default -> throw new IllegalArgumentException("Unsupported message type: " + messageType);
        };
    }
}
