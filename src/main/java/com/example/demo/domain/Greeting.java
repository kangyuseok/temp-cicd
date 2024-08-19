package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Greeting {
    private String content;

    public Greeting() {
    }

    // 모든 필드를 포함한 생성자
    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
