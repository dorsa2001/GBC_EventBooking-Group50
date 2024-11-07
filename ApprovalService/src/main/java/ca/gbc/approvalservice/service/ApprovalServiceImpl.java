package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.dto.ApprovalResponse;
import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;

    @Override
    public ApprovalResponse createApproval(ApprovalRequest approvalRequest) {
        log.debug("Creating a new approval for event {}", approvalRequest.getEventId());

        Approval approval = Approval.builder()
                .eventId(approvalRequest.getEventId())
                .approverId(approvalRequest.getApproverId())
                .status(approvalRequest.getStatus())
                .comments(approvalRequest.getComments())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        approval = approvalRepository.save(approval);
        return mapToApprovalResponse(approval);
    }

    @Override
    public Optional<ApprovalResponse> getApprovalById(String id) {
        return approvalRepository.findById(id).map(this::mapToApprovalResponse);
    }

    @Override
    public List<ApprovalResponse> getAllApprovals() {
        return approvalRepository.findAll().stream()
                .map(this::mapToApprovalResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprovalResponse> getApprovalsByEventId(String eventId) {
        return approvalRepository.findAll().stream()
                .filter(approval -> approval.getEventId().equals(eventId))
                .map(this::mapToApprovalResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteApproval(String id) {
        approvalRepository.deleteById(id);
    }

    private ApprovalResponse mapToApprovalResponse(Approval approval) {
        return ApprovalResponse.builder()
                .id(approval.getId())
                .eventId(approval.getEventId())
                .approverId(approval.getApproverId())
                .status(approval.getStatus())
                .comments(approval.getComments())
                .timestamp(approval.getTimestamp())
                .build();
    }
}
