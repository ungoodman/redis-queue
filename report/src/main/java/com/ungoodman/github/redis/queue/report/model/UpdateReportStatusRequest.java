package com.ungoodman.github.redis.queue.report.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReportStatusRequest {
    String processId;
    String processStatus;
    String requestedBy;
}
