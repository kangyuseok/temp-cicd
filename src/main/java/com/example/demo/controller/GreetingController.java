package com.example.demo.controller;

import com.example.demo.domain.Greeting;
import com.example.demo.domain.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

//    @MessageMapping("/hello") //@RequestMapping과 비슷 => /app/hello로 들어감
//    @SendTo("/topic/greetings") //가공을 마친 것들이 /topic/greetings 경로를 구독한 사람들에게 보내짐 -> topic이 붙었으니 simpleBrocker로 보내짐
//    public Greeting greeting(HelloMessage message) throws Exception {
//        //Thread.sleep(1000); // simulated delay
//        System.out.println(message.getName());
//        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
//    }
}
