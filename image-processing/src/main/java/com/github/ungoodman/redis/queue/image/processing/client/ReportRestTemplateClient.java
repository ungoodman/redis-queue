package com.github.ungoodman.redis.queue.image.processing.client;

import com.github.ungoodman.redis.queue.image.processing.model.UpdateReportStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportRestTemplateClient {
    @Value("${backend.update-report-status-url}")
    private String updateReportStatusUrl;

    public final RestTemplate restTemplate;

    @Autowired
    public ReportRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updateReportStatus(UpdateReportStatusRequest request) {
        ResponseEntity<Object> response = restTemplate.exchange(updateReportStatusUrl,
                HttpMethod.POST, new HttpEntity<>(request), Object.class);

        if (!HttpStatus.ACCEPTED.equals(response.getStatusCode()))
            throw new RestClientException(response.getStatusCode().toString());
    }
}
