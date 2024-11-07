package ca.gbc.approvalservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApprovalResponse {
    String id;
    String eventId;
    String approverId;
    String status;
    String comments;
    String timestamp;
}
