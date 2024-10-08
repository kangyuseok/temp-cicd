package com.example.demo.controller;

import com.example.demo.domain.ChatMessage;
import com.example.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    // 생성자를 직접 작성하여 의존성 주입
    public ChatController(ChatService chatService, SimpMessagingTemplate template) {
        this.chatService = chatService;
        this.template = template;
    }

    //이전 채팅 내용 조회
    @GetMapping("/find/chat/list/{id}")
    public Mono<ResponseEntity<List<ChatMessage>>>find(@PathVariable("id")Long id){
        Flux<ChatMessage> response = chatService.findChatMessages(id);
        return response.collectList().map(ResponseEntity::ok);
    }

    @MessageMapping("/hello") // /app/hello 로 들어감
    public Mono<Void> receiveMessage(ChatMessage chat) {
        System.out.println(chat);
        return chatService.saveChatMessage(chat).flatMap(savedMessage -> {
            template.convertAndSend("/topic/greetings/" + chat.getRoomId(), savedMessage);
            return Mono.empty();
        });
    }
}
