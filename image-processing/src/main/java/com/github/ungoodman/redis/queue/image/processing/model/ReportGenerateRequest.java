package com.github.ungoodman.redis.queue.image.processing.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportGenerateRequest {
    String reportId;
    String requestedBy;
}
