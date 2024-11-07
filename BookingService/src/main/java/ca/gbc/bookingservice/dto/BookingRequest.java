package ca.gbc.bookingservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookingRequest {
    String userId;
    String roomId;
    String startTime;
    String endTime;
    String purpose;
}
