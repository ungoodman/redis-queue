package com.ungoodman.github.redis.queue.report.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ungoodman.github.redis.queue.report.model.UpdateReportStatusRequest;
import com.ungoodman.github.redis.queue.report.producer.ReportStreamProducer;
import com.ungoodman.github.redis.queue.report.model.ReportGenerateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ReportService {
    private final ReportStreamProducer reportStreamProducer;
    private final WebSocketService webSocketService;

    @Autowired
    public ReportService(ReportStreamProducer reportStreamProducer, WebSocketService webSocketService) {
        this.reportStreamProducer = reportStreamProducer;
        this.webSocketService = webSocketService;
    }

    public void generateReport(ReportGenerateRequest request) throws JsonProcessingException {
        reportStreamProducer.sendMessage("report", request.getRequestedBy(), request.getReportId());
    }

    public void updateReportStatus(UpdateReportStatusRequest request) throws JsonProcessingException {
        webSocketService.sendMessageToTopic(request.getRequestedBy(), new ObjectMapper().writeValueAsString(request));
    }
}
