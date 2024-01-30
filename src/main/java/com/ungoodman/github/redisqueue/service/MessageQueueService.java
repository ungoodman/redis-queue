package com.ungoodman.github.redisqueue.service;

import com.ungoodman.github.redisqueue.model.MessageQueueRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MessageQueueService {

    private final StringRedisTemplate redisTemplate;

    public MessageQueueService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<String> enqueueMessage(MessageQueueRequest request) {
        redisTemplate.opsForList().rightPush(request.getQueueName(), request.getMessage());

        return getQueueList(request.getQueueName(), 0, 99);
    }

    public String dequeueMessage(String queueName) {
        return redisTemplate.opsForList().leftPop(queueName);
    }

    public List<String> getQueueList(String queueName, long start, long end) {
        return redisTemplate.opsForList().range(queueName, start, end);
    }
}

