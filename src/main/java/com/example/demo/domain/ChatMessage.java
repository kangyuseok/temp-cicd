package com.example.demo.domain;


import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection =  "chatting_content")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    private ObjectId id;
    private Long roomId; //방 아이디
    private String content; //채팅 내용
    private Long writerId; //작성자
    private Date createdDate; // 채팅 날짜

    public ChatMessage(Long roomId, String content, Long writerId, Date date) {
        this.roomId = roomId;
        this.content = content;
        this.writerId = writerId;
        this.createdDate = date;
    }
}
