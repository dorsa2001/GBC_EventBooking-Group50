package ca.gbc.approvalservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "approvals")
public class Approval {

    @Id
    private String id;

    private String eventId;       // ID of the event to approve/reject
    private String approverId;    // ID of the staff or faculty approving/rejecting
    private String status;        // e.g., "APPROVED", "REJECTED"
    private String comments;      // Any additional comments or notes
    private String timestamp;     // Date and time of the approval/rejection
}

