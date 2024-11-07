package ca.gbc.userservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRequest {
    String name;
    String email;
    String role;     // "STUDENT", "STAFF", "FACULTY"
    String userType; // Additional details if needed
}
