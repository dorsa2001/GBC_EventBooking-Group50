package ca.gbc.approvalservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApprovalRequest {
    String eventId;
    String approverId;
    String status;      // "APPROVED" or "REJECTED"
    String comments;    // Optional comments from the approver
}
