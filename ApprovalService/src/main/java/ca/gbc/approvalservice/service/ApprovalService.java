package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.dto.ApprovalResponse;

import java.util.List;
import java.util.Optional;

public interface ApprovalService {
    ApprovalResponse createApproval(ApprovalRequest approvalRequest);
    Optional<ApprovalResponse> getApprovalById(String id);
    List<ApprovalResponse> getAllApprovals();
    List<ApprovalResponse> getApprovalsByEventId(String eventId);
    void deleteApproval(String id);
}
