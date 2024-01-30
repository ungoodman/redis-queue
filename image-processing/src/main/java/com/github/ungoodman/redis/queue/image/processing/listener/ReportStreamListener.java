package com.github.ungoodman.redis.queue.image.processing.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ungoodman.redis.queue.image.processing.client.ReportRestTemplateClient;
import com.github.ungoodman.redis.queue.image.processing.model.ReportGenerateRequest;
import com.github.ungoodman.redis.queue.image.processing.model.UpdateReportStatusRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ReportStreamListener implements StreamListener<String, ObjectRecord<String, String>> {
    private final ReportRestTemplateClient client;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportStreamListener(ReportRestTemplateClient client, RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.client = client;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        if (!"report".equals(message.getStream())) return;

        redisTemplate.opsForStream().acknowledge("report", message);

        ReportGenerateRequest reportRequest = null;
        try {
            reportRequest = objectMapper.readValue(message.getValue(), ReportGenerateRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("RecordId: {} | Value: {}", message.getId().getValue(), reportRequest);

        String processId = UUID.randomUUID().toString();

        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setRequestedBy(reportRequest.getRequestedBy());
        request.setReportId(reportRequest.getReportId());
        request.setProcessId(processId);
        request.setProcessStatus("inProgress");

        client.updateReportStatus(request);

        try {
            Thread.sleep(2000);

            UpdateReportStatusRequest request2 = new UpdateReportStatusRequest();
            request2.setRequestedBy(reportRequest.getRequestedBy());
            request2.setReportId(reportRequest.getReportId());
            request2.setProcessId(processId);
            request2.setProcessStatus("done");

            client.updateReportStatus(request2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
