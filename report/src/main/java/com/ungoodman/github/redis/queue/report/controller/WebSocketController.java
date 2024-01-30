package com.ungoodman.github.redis.queue.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://localhost:63342")
public class WebSocketController {

    @Autowired
    public WebSocketController() {
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        // Simulate some processing
        Thread.sleep(1000);
        return "{ \"message\": \"Hello World!\" }";
    }
}
