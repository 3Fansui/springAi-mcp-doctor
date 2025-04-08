package com.wu.doctor.view;

import com.wu.doctor.entity.vo.MessageVO;
import com.wu.doctor.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatHistoryController {

    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatMemory chatMemory;

    /**
     * 查询会话历史列表
     * @param type 业务类型，如：chat,service,pdf
     * @return chatId列表
     */
    @GetMapping("/ai/history/{type}")
    public List<String> getChatIds(@PathVariable("type") String type) {
        log.info("获取会话列表: type={}", type);
        List<String> chatIds = chatHistoryRepository.getChatIds(type);
        log.info("会话数量: {}", chatIds.size());
        return chatIds;
    }

    /**
     * 根据业务类型、chatId查询会话历史
     * @param type 业务类型，如：chat,service,pdf
     * @param chatId 会话id
     * @return 指定会话的历史消息
     */
    @GetMapping("/ai/history/{type}/{chatId}")
    public List<MessageVO> getChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        log.info("获取会话历史: type={}, chatId={}", type, chatId);
        List<Message> messages = chatMemory.get(chatId, Integer.MAX_VALUE);
        if(messages == null) {
            log.warn("未找到会话历史: type={}, chatId={}", type, chatId);
            return List.of();
        }
        log.info("会话历史消息数量: {}", messages.size());
        return messages.stream().map(message -> {
            MessageVO vo = new MessageVO(message);
            log.debug("消息内容: role={}, content={}", vo.getRole(), vo.getContent());
            return vo;
        }).toList();
    }
    
    /**
     * 更新聊天标题
     * 
     * @param request 包含chatId和title的请求体
     * @return 操作结果
     */
    @PutMapping("/api/history/title")
    public ResponseEntity<Map<String, Object>> updateChatTitle(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String chatId = request.get("chatId");
            String title = request.get("title");
            
            log.info("更新会话标题: chatId={}, title={}", chatId, title);
            
            // 这里可以实现标题持久化逻辑
            // 由于当前项目可能使用前端localStorage存储标题，这里先返回成功
            
            response.put("success", true);
            response.put("message", "标题更新成功");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("更新标题失败", e);
            response.put("success", false);
            response.put("message", "标题更新失败: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 删除聊天会话
     * 
     * @param chatId 会话ID
     * @return 操作结果
     */
    @DeleteMapping("/api/history/{chatId}")
    public ResponseEntity<Map<String, Object>> deleteChat(@PathVariable("chatId") String chatId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("删除会话: chatId={}", chatId);
            
            // 从会话历史记录中删除
            chatHistoryRepository.delete("chat", chatId);
            
            // 使用反射方式从聊天内存中删除
            clearChatMemory(chatId);
            
            response.put("success", true);
            response.put("message", "会话删除成功");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("删除会话失败", e);
            response.put("success", false);
            response.put("message", "会话删除失败: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 使用反射方式清除ChatMemory中的会话记录
     * 
     * @param chatId 会话ID
     * @throws Exception 反射失败时抛出异常
     */
    private void clearChatMemory(String chatId) throws Exception {
        if (!(chatMemory instanceof InMemoryChatMemory)) {
            log.warn("不支持的ChatMemory实现类: {}", chatMemory.getClass().getName());
            return;
        }
        
        try {
            // 获取InMemoryChatMemory的conversationHistory字段
            Field field = InMemoryChatMemory.class.getDeclaredField("conversationHistory");
            field.setAccessible(true);
            
            // 获取conversationHistory映射
            @SuppressWarnings("unchecked")
            Map<String, List<Message>> conversationHistory = (Map<String, List<Message>>) field.get(chatMemory);
            
            // 从映射中移除指定的会话
            if (conversationHistory != null) {
                conversationHistory.remove(chatId);
                log.info("成功从ChatMemory中移除会话: {}", chatId);
            }
        } catch (Exception e) {
            log.error("通过反射清除ChatMemory失败", e);
            throw e;
        }
    }
}
