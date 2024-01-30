package com.ungoodman.github.redis.queue.report.controller;

import com.ungoodman.github.redis.queue.report.model.ReportGenerateRequest;
import com.ungoodman.github.redis.queue.report.model.UpdateReportStatusRequest;
import com.ungoodman.github.redis.queue.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/report")
public class ReportController {
    private final ReportService service;

    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public ResponseEntity<Object> requestGenerateReport(@RequestBody ReportGenerateRequest request) {
        try {
            service.generateReport(request);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/status")
    public ResponseEntity<Object> updateReportStatus(@RequestBody UpdateReportStatusRequest request) {
        try {
            service.updateReportStatus(request);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
