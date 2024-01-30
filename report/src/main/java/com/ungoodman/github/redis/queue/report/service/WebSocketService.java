package com.ungoodman.github.redis.queue.report.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.messagingTemplate = simpMessagingTemplate;
    }

    public void sendMessageToTopic(String topic, String message) {
        // Construct the destination dynamically
        String destination = "/topic/" + topic;

        log.info("Send message to Client: {}", message);
        // Send the message to the specified destination
        messagingTemplate.convertAndSend(destination, message);
    }
}
