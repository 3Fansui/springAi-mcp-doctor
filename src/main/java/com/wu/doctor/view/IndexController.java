package com.wu.doctor.view;

import com.wu.doctor.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 首页控制器
 * 处理根路径请求
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ChatHistoryRepository chatHistoryRepository;

    /**
     * 处理根路径请求，返回doctor页面
     * 
     * @param id 会话ID（可选）
     * @param model Spring MVC模型
     * @return doctor视图名称
     */
    @GetMapping("/")
    public String index(@RequestParam(value = "id", required = false) String id, Model model) {
        log.info("访问首页，id参数: {}", id);
        
        // 获取聊天历史ID列表
        List<String> chatIds = chatHistoryRepository.getChatIds("chat");
        log.info("获取到会话历史列表，数量: {}", chatIds.size());
        
        // 向模型添加聊天ID列表
        model.addAttribute("chatIds", chatIds);
        
        // 如果提供了会话ID，则添加到模型
        if (id != null && !id.isEmpty()) {
            model.addAttribute("currentChatId", id);
        }
        
        return "doctor";
    }
    
    /**
     * 处理/doctor/chat路径的请求，返回doctor页面
     * 
     * @param id 会话ID（必需）
     * @param model Spring MVC模型
     * @return doctor视图名称
     */
    @GetMapping("/doctor/chat")
    public String doctorChat(@RequestParam("id") String id, Model model) {
        log.info("访问会话页面，id参数: {}", id);
        
        // 获取聊天历史ID列表
        List<String> chatIds = chatHistoryRepository.getChatIds("chat");
        log.info("获取到会话历史列表，数量: {}", chatIds.size());
        
        // 向模型添加聊天ID列表
        model.addAttribute("chatIds", chatIds);
        
        // 添加当前会话ID到模型
        model.addAttribute("currentChatId", id);
        
        return "doctor";
    }
}