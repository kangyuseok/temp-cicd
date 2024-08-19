package com.example.demo.service;

import com.example.demo.domain.ChatMessage;
import com.example.demo.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
    @Transactional
    public Flux<ChatMessage> findChatMessages(Long id) {
        return chatMessageRepository.findAllByRoomId(id);
    }

    @Transactional
    // 메시지를 저장하는 메서드
    public Mono<ChatMessage> saveChatMessage(ChatMessage chatMessage) {
        chatMessage.setCreatedDate(new Date()); // 메시지 생성 날짜 설정
        return chatMessageRepository.save(chatMessage);
    }
}
