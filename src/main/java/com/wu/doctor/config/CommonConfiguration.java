package com.wu.doctor.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel, ChatMemory chatMemory, ToolCallbackProvider tools) {
        return ChatClient.builder(chatModel)
                // 设置默认的工具集
                .defaultTools(tools)
                // 设置默认的选项配置，此处使用了Top-P采样策略，以增加响应的多样性
                //.defaultOptions(DashScopeChatOptions.builder().withTopP(0.7).build())
                // 设置模型身份为家庭医生
                .defaultSystem("你是一位经验丰富的AI家庭医生助手，具备专业的医学知识。请以家庭医生的身份回应用户的健康咨询问题，提供准确的医疗信息和健康建议。注意：清晰说明你不能替代真实的医生诊断，对于严重症状应建议用户及时就医。用需要你操作数据库的话你需要调用mcp的database-tools工具")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .build();
    }
}