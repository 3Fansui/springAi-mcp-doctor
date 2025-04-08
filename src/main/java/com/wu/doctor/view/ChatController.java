package com.wu.doctor.view;


import com.wu.doctor.repository.ChatHistoryRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * 聊天控制器
 * 处理与AI聊天交互相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    // 依赖注入
    private final ChatClient chatClient;
    private final ChatHistoryRepository chatHistoryRepository;
    
    /**
     * 生成流式响应的API端点
     * 
     * @param response HTTP响应对象，用于设置响应编码
     * @param id 会话ID
     * @param prompt 用户输入的提示词
     * @return 流式聊天响应
     */
    @RequestMapping(value = "/generate_stream", method = RequestMethod.GET)
    public Flux<ChatResponse> generateStream(HttpServletResponse response, 
                                            @RequestParam("id") String id, 
                                            @RequestParam("prompt") String prompt) {
        // 设置响应编码为UTF-8
        response.setCharacterEncoding("UTF-8");
        chatHistoryRepository.save("chat",id);
        // 按照Spring AI新版API正确使用流式处理
        Flux<ChatResponse> responseFlux = chatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, id))
                .stream()
                .chatResponse();

        
        return responseFlux;
    }


}