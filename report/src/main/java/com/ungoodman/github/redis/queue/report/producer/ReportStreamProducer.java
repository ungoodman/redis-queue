package com.ungoodman.github.redis.queue.report.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ungoodman.github.redis.queue.report.model.ReportGenerateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportStreamProducer {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public ReportStreamProducer(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String streamName, String key, String value) throws JsonProcessingException {
        RecordId recordId = redisTemplate.opsForStream().add(streamName, Collections.singletonMap(key, objectMapper.writeValueAsString(new ReportGenerateRequest(value, key))));
        log.info("Add Data to Stream Name: {}:{} with Key: {} and Value: {}", streamName, recordId.toString(), key, value);
    }
}
