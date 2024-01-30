package com.ungoodman.github.redisqueue.controller;

import com.ungoodman.github.redisqueue.model.MessageQueueRequest;
import com.ungoodman.github.redisqueue.service.MessageQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/queue")
public class QueueController {
    private final MessageQueueService messageQueueService;

    @Autowired
    public QueueController(MessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getQueueList(
            @RequestParam("queueName") String queueName,
            @RequestParam("start") Long start,
            @RequestParam("end") Long end) {
        return new ResponseEntity<>(messageQueueService.getQueueList(queueName, start, end), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<String>> enqueueMessage(
            @RequestBody MessageQueueRequest request) {
        return new ResponseEntity<>(messageQueueService.enqueueMessage(request), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<String> dequeueMessage(@RequestBody MessageQueueRequest request) {
        return new ResponseEntity<>(messageQueueService.dequeueMessage(request.getQueueName()), HttpStatus.OK);
    }
}
